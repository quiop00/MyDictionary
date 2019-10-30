package com.example.mydictionaryv1;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;

import com.example.mydictionaryv1.ui.eng_vn.eng_vn;
import com.example.mydictionaryv1.ui.vn_eng.vn_eng;

import java.util.ArrayList;

public class CustomAutoComplete implements TextWatcher {
    public Context context;
    public ArrayList<String> list;
    public CustomAutoComplete(Context context){
        this.context = context;
        this.list=new ArrayList<String>();
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
         MainActivity mainActivity=((MainActivity) context);
        // query the database based on the user input
          Fragment fragment=mainActivity.fragment;
          if(fragment instanceof eng_vn){
              mainActivity.searchTerm="SELECT word FROM eng_vn WHERE word LIKE %" + s + "%";
             list=mainActivity.getItemsFromDb(mainActivity.searchTerm);
             eng_vn engVn=(eng_vn)fragment;
              engVn.myAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, list);
              engVn.autoSearch.setAdapter(engVn.myAdapter);
//              Bundle bundle = new Bundle();
//              bundle.putStringArrayList("data",mainActivity.list);
//              // set Fragmentclass Arguments
//              eng_vn frag = new eng_vn();
//              frag.setArguments(bundle);
          }else if(fragment instanceof vn_eng){
              mainActivity.searchTerm="SELECT word FROM vn_eng WHERE word LIKE %" + s + "%";
              list=mainActivity.getItemsFromDb(mainActivity.searchTerm);
              vn_eng vnEng=(vn_eng) fragment;
              vnEng.myAdapter=new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, list);
              vnEng.autoSearch.setAdapter(vnEng.myAdapter);
//              Bundle bundle = new Bundle();
//              bundle.putStringArrayList("data",mainActivity.list);
//              // set Fragmentclass Arguments
//              vn_eng frag = new vn_eng();
//              frag.setArguments(bundle);
          }//
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
