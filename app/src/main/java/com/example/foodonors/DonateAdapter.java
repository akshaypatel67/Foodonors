package com.example.foodonors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonors.HelperClasses.FoodHelperClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class DonateAdapter extends FirebaseRecyclerAdapter<FoodHelperClass, DonateAdapter.ViewHolder> {

    public DonateAdapter(@NonNull FirebaseRecyclerOptions<FoodHelperClass> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateAdapter.ViewHolder holder, int position, @NonNull final FoodHelperClass foodHelperClass) {
        holder.txtDesc.setText(foodHelperClass.getContents());
        holder.txtAvail.setText(foodHelperClass.getAvailableTime());
        holder.txtQuantity.setText(String.valueOf(foodHelperClass.getQuantity()));
    }


    @NonNull
    @Override
    public DonateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_single_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDesc, txtAvail, txtQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.donateDesc);
            txtAvail = itemView.findViewById(R.id.avail_time);
            txtQuantity = itemView.findViewById(R.id.quantity);
        }
    }
}
