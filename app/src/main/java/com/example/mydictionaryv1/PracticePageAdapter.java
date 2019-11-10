package com.example.mydictionaryv1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class PracticePageAdapter extends PagerAdapter {
    private Context mContext;
    Activity activity;
    TextView tvResult;
    private int mLayoutId;
    private ArrayList<Data> mList;
    public PracticePageAdapter(View v,Context context,int layout,ArrayList<Data> list){
//        super(v);
        mContext=context;
        mLayoutId=layout;
        mList=list;
        activity=(Activity)mContext;
    }
    public void onSpeak(){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speech something");
        activity.startActivityForResult(intent,1);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);
        //activity.startActivityForResult();

        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                }
                break;
            }

        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
