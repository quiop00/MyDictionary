package com.example.mydictionaryv1.ui.vn_eng;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mydictionaryv1.CustomAutoComplete;
import com.example.mydictionaryv1.Data;
import com.example.mydictionaryv1.DatabaseAccess;
import com.example.mydictionaryv1.ILoadMore;
import com.example.mydictionaryv1.R;
import com.example.mydictionaryv1.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.UUID;

public class vn_eng extends Fragment{

    private ListView listView;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
    public ArrayAdapter<String> myAdapter;
    public AppCompatAutoCompleteTextView autoSearch;
    public ArrayList<Data> listWords=new ArrayList<Data>();
    public static vn_eng newInstance() {
        return new vn_eng();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.vn_eng_fragment, container, false);
        autoSearch=view.findViewById(R.id.edt_search);
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        final DatabaseAccess databaseAccess=new DatabaseAccess(getContext(),"vi_eng.db");
        databaseAccess.open();
        final String tableName="vi_eng";
        String query="SELECT word,content FROM "+tableName+" LIMIT "+0+",10";
        listWords=databaseAccess.getWords(query);
        databaseAccess.close();
        final RecyclerViewAdapter adapter=new RecyclerViewAdapter(getContext(),listWords,recyclerView);
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
                            int end = index+10;
                            databaseAccess.open();
                            String query="SELECT word,content FROM "+tableName+" LIMIT "+index+",10";
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
//        CustomAutoComplete customAutoComplete=new CustomAutoComplete(getContext());
//        list= customAutoComplete.list;
//        myAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, list);
//        autoSearch.setAdapter(myAdapter);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel


    }

}
