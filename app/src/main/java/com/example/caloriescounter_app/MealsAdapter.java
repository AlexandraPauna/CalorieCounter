package com.example.caloriescounter_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caloriescounter_app.database.Meal;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MealsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE = 0;

    private ArrayList<Meal> items;
    private Context context;


    public MealsAdapter(Context context, ArrayList<Meal> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {

        return TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.meals_row, parent, false);

        RecyclerView.ViewHolder viewHolder = new MealsAdapter.ViewHolder(view);
        return viewHolder;

        //return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        TextView nameTextView = ((ViewHolder)viewHolder).nameTextView;
        TextView brandTextView = ((ViewHolder)viewHolder).brandText;
        TextView servingSizeTextView = ((ViewHolder)viewHolder).servingSizeText;
        TextView caloriesTextView = ((ViewHolder)viewHolder).caloriesText;
        TextView proteinTextView = ((ViewHolder)viewHolder).proteinText;
        TextView fatTextView = ((ViewHolder)viewHolder).fatText;
        TextView carbsTextView = ((ViewHolder)viewHolder).carbsText;

        nameTextView.setText(items.get(position).name);
        brandTextView.setText(items.get(position).brand);
        servingSizeTextView.setText(items.get(position).servingSize);
        caloriesTextView.setText(((Integer)items.get(position).calories).toString());
        proteinTextView.setText(((Integer)items.get(position).protein).toString());
        fatTextView.setText(((Integer)items.get(position).fat).toString());
        carbsTextView.setText(((Integer)items.get(position).carbs).toString());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView brandText;
        public TextView servingSizeText;
        public TextView caloriesText;
        public TextView proteinText;
        public TextView fatText;
        public TextView carbsText;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.meal_name);
            brandText = itemView.findViewById(R.id.meal_brand);
            servingSizeText = itemView.findViewById(R.id.serving_size);
            caloriesText = itemView.findViewById(R.id.calories);
            proteinText = itemView.findViewById(R.id.protein);
            fatText = itemView.findViewById(R.id.fat);
            carbsText = itemView.findViewById(R.id.carbs);
        }
    }
}
