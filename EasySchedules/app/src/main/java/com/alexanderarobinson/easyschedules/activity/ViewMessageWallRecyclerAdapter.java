package com.alexanderarobinson.easyschedules.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;;
import com.alexanderarobinson.easyschedules.R;

import java.util.List;

public class ViewMessageWallRecyclerAdapter extends RecyclerView.Adapter<ViewMessageWallRecyclerAdapter.MyViewHolder> {

    private List<String> userinfo;
    private List<String> datetime;
    private List<String> message;
    private Context context;


    public ViewMessageWallRecyclerAdapter(List<String> userinfo,List<String> message,List<String> datetime,Context context) {
        this.userinfo = userinfo;
        this.message = message;
        this.datetime = datetime;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the custom layout
        View scheduleView = inflater.inflate(R.layout.view_message_wall_text_view_layout, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(scheduleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Info.setText(userinfo.get(position));
        holder.Datetime.setText(datetime.get(position));
        holder.Message.setText(message.get(position));

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return message.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Info;
        TextView Datetime;
        TextView Message;

        public MyViewHolder(View itemView) {

            super(itemView);

            Info = (TextView) itemView.findViewById(R.id.info);
            Datetime = (TextView) itemView.findViewById(R.id.datetime);
            Message = (TextView) itemView.findViewById(R.id.message);

        }
    }
}

