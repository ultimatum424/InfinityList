package com.example.ultim.infinitylist;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamtheib.infiniterecyclerview.InfiniteAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ultim on 03.05.2017.
 */

public class FeedAdapter extends InfiniteAdapter<RecyclerView.ViewHolder> {
    private ArrayList<NewsFeedList> mList;
    private Context mContext;

    public FeedAdapter(Context context, ArrayList<NewsFeedList> data) {
        mContext = context;
        mList = data;
    }

    @Override
    public RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent) {
        View loadingView = LayoutInflater.from(mContext).inflate(R.layout.loading_view, parent, false);
        return new LoadingViewHolder(loadingView);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public int getViewType(int position) {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_v, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            ObjectAnimator animator = ObjectAnimator.ofFloat(loadingViewHolder.loadingImage, "rotation", 0, 360);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(1000);
            animator.start();
            return;
        }
        else {
            ((ItemViewHolder) holder).text.setText(mList.get(position).text);
            ((ItemViewHolder) holder).imageView.setImageResource(R.drawable.empty);
            if (mList.get(position) != null){
                if (!Objects.equals((mList.get(position).getUrlImage()), "")){
                    Picasso.with(mContext)
                            .load(mList.get(position).getUrlImage())
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(((ItemViewHolder) holder).imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                }
                                @Override
                                public void onError() {
                                    Picasso.with(mContext)
                                            .load((mList.get(position).getUrlImage()))
                                            .error(R.drawable.empty)
                                            .into(((ItemViewHolder) holder).imageView, new Callback() {
                                                @Override
                                                public void onSuccess() {
                                                }

                                                @Override
                                                public void onError() {
                                                }
                                            });
                                }
                            });
                }
            }
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getVisibleThreshold() {
        return 2;
    }


    public class LoadingViewHolder extends  RecyclerView.ViewHolder{
        public ImageView loadingImage;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            loadingImage = (ImageView) itemView.findViewById(R.id.loadingImage);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView imageView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text_v);
            imageView = (ImageView) itemView.findViewById(R.id.image_v);

        }
    }
}
