package com.example.caloriescounter_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caloriescounter_app.database.Activity;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ActivitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_GREEN = 0;
    private final int TYPE_PINK = 1;

    private ArrayList<Activity> items;
    private Context context;

    public ActivitiesAdapter(Context context, ArrayList<Activity> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0){
            return TYPE_GREEN;
        }

        return TYPE_PINK;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activities_row, parent, false);

        RecyclerView.ViewHolder viewHolder = new ActivitiesAdapter.ViewHolder(view);
        return viewHolder;

        //return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView nameTextView = ((ViewHolder)viewHolder).nameTextView;
        TextView durationTextView = ((ViewHolder)viewHolder).durationTextView;
        TextView calBurnedTextView = ((ViewHolder)viewHolder).calBurnedTextView;

        nameTextView.setText(items.get(position).name);
        durationTextView.setText(items.get(position).duration);
        calBurnedTextView.setText(items.get(position).duration);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView durationTextView;
        public TextView calBurnedTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.activty_name);
            durationTextView = itemView.findViewById(R.id.activity_duration);
            calBurnedTextView = itemView.findViewById(R.id.activity_cal_burned);
        }
    }
}
