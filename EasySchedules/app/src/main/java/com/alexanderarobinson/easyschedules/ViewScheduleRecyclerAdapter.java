package com.alexanderarobinson.easyschedules;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ViewScheduleRecyclerAdapter extends RecyclerView.Adapter<ViewScheduleRecyclerAdapter.MyViewHolder> {

    private List<String> list;

    public ViewScheduleRecyclerAdapter(List<String> list)
    {
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textview = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_schedule_text_view_layout,parent,false );
        MyViewHolder myViewHolder = new MyViewHolder(textview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Schedule.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView Schedule;
        public MyViewHolder(TextView itemView){
            super(itemView);
            Schedule = itemView;
        }
    }
}
