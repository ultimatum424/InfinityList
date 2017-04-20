package com.example.ultim.infinitylist;

import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Ultim on 19.04.2017.
 */

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

    public static final int DEFAULT_VISIBLE_THRESHOLD = 10;

    private int visibleThreshold;
    private int currentPage;
    private int previousTotal;
    private boolean loading;

    public EndlessScrollListener() {
        this(DEFAULT_VISIBLE_THRESHOLD);
    }
    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
        currentPage = 0;
        previousTotal = 0;
        loading = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        ListView listView = (ListView) view;
        int headerViewsCount = listView.getHeaderViewsCount();
        int footerViewsCount = listView.getFooterViewsCount();
        int liquidTotalItemCount = totalItemCount - headerViewsCount - footerViewsCount;
        if (loading) {
            if (liquidTotalItemCount > previousTotal) {
                loading = false;
                previousTotal = liquidTotalItemCount;
                currentPage++;
            }
        }
        if (!loading && hasMoreDataToLoad() && (firstVisibleItem + visibleItemCount + visibleThreshold) >= (totalItemCount - footerViewsCount)) {
            loading = true;
            loadMoreData(currentPage);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    protected abstract boolean hasMoreDataToLoad();
    protected abstract void loadMoreData(int page);

    public boolean isLoading() {
        return loading;
    }
}
