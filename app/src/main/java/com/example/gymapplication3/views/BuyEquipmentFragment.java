package com.example.gymapplication3.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gymapplication3.R;
import com.example.gymapplication3.database.GymMatDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyEquipmentFragment extends Fragment {

    @BindView(R.id.howMany_edittext_frag)
    EditText howManyEditText;
    @BindView(R.id.material_textview_frag)
    TextView materialTextView;
    @BindView(R.id.purchaseButton_frag)
    Button purchaseButton;
    @BindView(R.id.cancelButton_frag)
    Button cancelButton;

    private StupidFragment stupidFragment;

    public interface StupidFragment{
        void redrawCall();
    }

    public BuyEquipmentFragment(StupidFragment stupidFragment) {
        this.stupidFragment = stupidFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        final String merch = getArguments().getString("my_merch");
        if(merch!=null){
            materialTextView.setText(merch);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                howManyEditText.setText("");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextNum = howManyEditText.getText().toString().trim();
                if(editTextNum.equals("")){
                    Toast.makeText(getContext(),"Please input a valid number",Toast.LENGTH_LONG).show();
                }else{
                    howManyEditText.setText("");
                    GymMatDatabase db = new GymMatDatabase(getContext(), null);
                    Log.d("TAG_P", "onClickPre: "+db.getAmount(merch));
                    db.onPurchase(merch, Integer.parseInt(editTextNum));
                    Log.d("TAG_P", "onClickPost: "+db.getAmount(merch));
                    getActivity().getSupportFragmentManager().popBackStack();
                    stupidFragment.redrawCall();
                }
            }
        });
    }

}
