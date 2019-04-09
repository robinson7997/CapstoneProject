package com.alexanderarobinson.easyschedules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class ViewPendingShiftsRecyclerAdapter extends RecyclerView.Adapter<ViewPendingShiftsRecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private List<String> date;
    private List<String> shift_user;
    private List<String> picking_up_user;
    private List<Integer> shift_id;
    private List<Integer> user_id;
    private Context context;


    public ViewPendingShiftsRecyclerAdapter(List<String> list,List<String> date,List<String> shift_user, List<String> picking_up_user, List<Integer>shift_id, List<Integer>user_id,Context context) {
        this.list = list;
        this.date = date;
        this.shift_user = shift_user;
        this.picking_up_user = picking_up_user;
        this.context = context;
        this.shift_id = shift_id;
        this.user_id = user_id;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the custom layout
        View scheduleView = inflater.inflate(R.layout.view_pending_shifts_text_view_layout, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(scheduleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Date.setText(date.get(position));
        holder.Schedule.setText(list.get(position));
        holder.ShiftUser.setText(shift_user.get(position));
        holder.PickingUpUser.setText(picking_up_user.get(position));
        //Shift id
        final int id = shift_id.get(position);
        final String stringId = id+"";

        final int userId = user_id.get(position);
        final String stringUserId = userId+"";


        Button button = holder.AcceptShift;
        button.setText("Accept");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ;
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try{
                            //Convert to JSON object
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                System.out.println("worked");
                            }else{
                                System.out.println("did not work");
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                };

                //Add shift id, user id, shift user id, and company id
                AcceptShiftRequest acceptShiftRequest = new   AcceptShiftRequest(stringUserId,stringId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(acceptShiftRequest);


            }
        });

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Date;
        TextView Schedule;
        TextView ShiftUser;
        TextView PickingUpUser;
        Button AcceptShift;

        public MyViewHolder(View itemView) {

            super(itemView);

            Date = (TextView) itemView.findViewById(R.id.date);
            Schedule = (TextView) itemView.findViewById(R.id.schedule);
            ShiftUser = (TextView) itemView.findViewById(R.id.shift_user);
            PickingUpUser = (TextView) itemView.findViewById(R.id.picking_up_user);
            AcceptShift = (Button) itemView.findViewById(R.id.acceptShift);

        }
    }
}
