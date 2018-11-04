package com.vineetsridhar.bored;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;

public class RecycleritemListener implements RecyclerView.OnItemTouchListener {
    private RecyclerTouchListener listener;
    private GestureDetector gd;

    public interface RecyclerTouchListener {
        public void onClickItem(View v, int position) ;
        public void onLongClickItem(View v, int position);
    }
}
