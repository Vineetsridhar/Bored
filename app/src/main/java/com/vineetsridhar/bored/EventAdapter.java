package com.vineetsridhar.bored;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context context;
    private int layout;
    private ArrayList<Event> eventList;
    private LayoutInflater mInflator;
    private View row;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, title, desc, number, location;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.descr);
            number = view.findViewById(R.id.number);
            location =  view.findViewById(R.id.location);
        }
    }

    public EventAdapter(Context context, int layout, ArrayList<Event> resultList) {
        this.context = context;
        this.layout = layout;
        this.eventList  = resultList;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event e = eventList.get(position);
        holder.name.setText(e.getName());
        holder.title.setText(e.getTitle());
        holder.desc.setText(e.getDesc());
        holder.number.setText(e.getNumber());
        holder.location.setText(e.getLocation());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layout,parent, false);
        return new MyViewHolder(v);
    }
}
