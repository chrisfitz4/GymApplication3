package com.example.gymapplication3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.gymapplication3.R;
import com.example.gymapplication3.adapters.RecyclerViewAdapterInventory;
import com.example.gymapplication3.adapters.RecyclerViewAdapterMerchandise;
import com.example.gymapplication3.database.GymMatDatabase;
import com.example.gymapplication3.views.BuyEquipmentFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapterMerchandise.MerchandiseDelegate, BuyEquipmentFragment.StupidFragment {

    ArrayList<String> merchandise = new ArrayList<>();
    ArrayList<String> inventory = new ArrayList<>();
    @BindView(R.id.recyclerViewMerchandise)
    RecyclerView recyclerViewMerchandise;
    @BindView(R.id.recyclerViewInventory)
    RecyclerView recyclerViewInventory;
    String TAG = "TAG_H";
    GymMatDatabase db;

    BuyEquipmentFragment buyEquipmentFragment = new BuyEquipmentFragment(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        db = new GymMatDatabase(this, null);

        readDatabase();
        setUpRecyclerViews();
    }

    private void setUpRecyclerViews(){

//        merchandise.add("Weights");
//        merchandise.add("Dumb Bells");
//        merchandise.add("Bicycles");
//        inventory.add("Weights");
//        inventory.add("More Weights");
//        inventory.add("Even More Weights");


        Log.d("TAG_Y", "setUpRecyclerViews: "+db.getAmount("Weights"));
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter1 = new RecyclerViewAdapterMerchandise(merchandise,this);
        recyclerViewMerchandise.setLayoutManager(layoutManager1);
        recyclerViewMerchandise.setAdapter(adapter1);
        recyclerViewMerchandise.addItemDecoration(decoration);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter2 = new RecyclerViewAdapterInventory(inventory);
        recyclerViewInventory.setLayoutManager(layoutManager2);
        recyclerViewInventory.setAdapter(adapter2);
        recyclerViewInventory.addItemDecoration(decoration);
    }

    //get the gym materials from the database and update the arraylists accordingly
    private void readDatabase(){
        merchandise = new ArrayList<>();
        inventory = new ArrayList<>();
        Cursor cursor = db.getMaterials();
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            int id = cursor.getColumnIndex(GymMatDatabase.COLUMN_MATERIAL_ID);
            String material = cursor.getString(cursor.getColumnIndex(GymMatDatabase.COLUMN_MATERIAL_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndex(GymMatDatabase.COLUMN_MATERIAL_QUANTITY));
            Log.d("TAG_P", "readDatabase: "+id+" "+material+" "+quantity);
            if(quantity==0){
                merchandise.add(material);
            }else{
                merchandise.add(material);
                inventory.add(material+","+quantity);
            }
        }
        if(merchandise.size()==0){
            db.insertMaterial("");
            db.insertMaterial("Weights");
            db.insertMaterial("Bicycles");
            db.insertMaterial("Treadmills");
            db.insertMaterial("Unicycles");
            db.insertMaterial("Pools");
            db.insertMaterial("Golf Courses");
            db.insertMaterial("Food Truck");
            readDatabase();
        }



    }

    @Override
    public void onClickMerchandise(String merch) {
        //start a fragment
        Bundle bundle = new Bundle();
        bundle.putString("my_merch",merch);

        buyEquipmentFragment.setArguments(bundle);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_framelayout,buyEquipmentFragment)
                .addToBackStack(buyEquipmentFragment.getTag())
                .commit();
    }

    @Override
    public void redrawCall() {
        readDatabase();
        setUpRecyclerViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }
}
