package com.example.mydictionaryv1.ui.eng_vn;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydictionaryv1.CustomAutoComplete;
import com.example.mydictionaryv1.Data;
import com.example.mydictionaryv1.DatabaseAccess;
import com.example.mydictionaryv1.ILoadMore;
import com.example.mydictionaryv1.R;
import com.example.mydictionaryv1.RecyclerViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class eng_vn extends Fragment {

    private ListView listView;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    public ArrayAdapter<String> myAdapter;
    public AutoCompleteTextView autoSearch;
    TextView speechSeacrh;
    public static eng_vn newInstance() {
        return new eng_vn();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.eng_vn_fragment, container, false);
        autoSearch=view.findViewById(R.id.edt_search);
        speechSeacrh=view.findViewById(R.id.voice_search);
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        final DatabaseAccess databaseAccess=new DatabaseAccess(getContext(),"eng_vn.db");
        final String tableName="eng_vn";
        String query="SELECT * FROM "+tableName+" LIMIT "+0+",10";
        databaseAccess.open();
        final ArrayList<Data> listWords=databaseAccess.getWords(query);
        databaseAccess.close();
        final RecyclerViewAdapter adapter=new RecyclerViewAdapter(getContext(),listWords,recyclerView,"eng_vn");
        recyclerView.setAdapter(adapter);
        //Set Load more event
        adapter.setLoadMore(new ILoadMore() {
            @Override
            public void onLoadMore() {
                if(listWords.size() <= 3000) // Change max size
                {
                    listWords.add(null);
                    adapter.notifyItemInserted(listWords.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listWords.remove(listWords.size()-1);
                            adapter.notifyItemRemoved(listWords.size());
                            //Random more data
                            int index = listWords.size();
                            databaseAccess.open();
                            String query="SELECT * FROM "+tableName+" LIMIT "+index+",10";
                            ArrayList<Data> listTemp=databaseAccess.getWords(query);
                            databaseAccess.close();
                            for (Data b:listTemp) {
                                listWords.add(b);
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },1000); // Time to load
                }
            }
        });
        autoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                list=new ArrayList<String>();
                databaseAccess.open();
                String query= "SELECT word FROM eng_vn WHERE word LIKE '%"+s+"%' LIMIT 0,1000";
                list=databaseAccess.getWord(query);
                databaseAccess.close();
                if(list!=null){
                    //autoSearch.dr
                    myAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, list);
                    autoSearch.setAdapter(myAdapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        speechSeacrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speech something");
                startActivityForResult(intent,1);
            }
        });



        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    autoSearch.setText(result.get(0));
                }
                break;
            }

        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // mViewModel = ViewModelProviders.of(this).get(EngVnViewModel.class);
        // TODO: Use the ViewModel

    }

//    @Override
//    public void onItemClick(int postion) {
//
//    }
}
