package com.example.mydictionaryv1;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

class LoadingViewHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar=(ProgressBar) itemView.findViewById(R.id.progress_bar);
    }
}
class RecyclerViewHolder extends RecyclerView.ViewHolder {
    LinearLayout linearLayout;
    TextView tvWord;
    CardView cardView;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        linearLayout=(LinearLayout) itemView.findViewById(R.id.linear);
        cardView=(CardView) itemView.findViewById(R.id.card_view);
        tvWord=itemView.findViewById(R.id.tv_word);
    }

}
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM=0,VIEW_TYPE_LOADING=1;
    ILoadMore loadMore;
    boolean isLoading;
    int visibleThreshold=5;
    int lastVisibleItem,totalItemCount;
    private Context mContext;
    private List<Data> data = new ArrayList<Data>();

    public RecyclerViewAdapter(Context mContext, List<Data> data,RecyclerView recyclerView) {
        this.mContext = mContext;
        this.data = data;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= (lastVisibleItem+visibleThreshold))
                {
                    if(loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM; // So sánh nếu item được get tại vị trí này là null thì view đó là loading view , ngược lại là item
    }
    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM)
        {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item,parent,false);
            return new RecyclerViewHolder(view);
        }
        else if(viewType == VIEW_TYPE_LOADING)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof  RecyclerViewHolder)
        {
            final Data item = data.get(position);

            RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
              viewHolder.tvWord.setText(item.getWord()+" ");
              viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,item.getWord()+"",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(mContext,Detail.class);
                    intent.putExtra("word",item.getWord());
                    intent.putExtra("content",item.getContent());
                    mContext.startActivity(intent);
                }
            });
        }
        else if(holder instanceof LoadingViewHolder)
        {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public void setLoaded() {
        isLoading = false;
    }
}

