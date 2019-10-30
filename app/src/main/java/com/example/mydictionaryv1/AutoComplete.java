package com.example.mydictionaryv1;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class AutoComplete extends AppCompatAutoCompleteTextView {

    public AutoComplete(Context context) {
        super(context);
    }

    public AutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }
    /*
     * after a selection we have to capture the new value and append to the existing text
     */
    @Override
    protected void replaceText(final CharSequence text) {
        super.replaceText(text);
    }
}
