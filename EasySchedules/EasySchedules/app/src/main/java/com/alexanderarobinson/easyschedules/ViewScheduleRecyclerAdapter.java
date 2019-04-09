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

public class ViewScheduleRecyclerAdapter extends RecyclerView.Adapter<ViewScheduleRecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private List<Integer> id;
    private List<Integer> shift_posted;
    private Context context;


    public ViewScheduleRecyclerAdapter(List<String> list,List<Integer> id, List<Integer> shift_posted, Context context) {
        this.list = list;
        this.id = id;
        this.shift_posted = shift_posted;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        //Inflate the custom layout
        View scheduleView = inflater.inflate(R.layout.view_schedule_text_view_layout, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(scheduleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Schedule.setText(list.get(position));
        Button button = holder.DropShift;
        final String stringId =  id.get(position)+"";
        final String shift_posted_string = shift_posted.get(position)+"";
        int num = Integer.parseInt(shift_posted_string );
        if(num == 1){
            button.setText("Drop");
        }else{
            button.setText("Posted");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(stringId);
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

                //Add user data from text fields for login request and add to queue
                DropShiftRequest loginRequest = new DropShiftRequest(stringId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(loginRequest);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Schedule;
        Button DropShift;

        public MyViewHolder(View itemView) {

            super(itemView);

            Schedule = (TextView) itemView.findViewById(R.id.schedule);
            DropShift = (Button) itemView.findViewById(R.id.dropShift);

        }
    }
}
