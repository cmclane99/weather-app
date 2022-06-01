/**
 * Chris McLane
 */

package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final ArrayList<MyRecyclerViewData> mv_data;
    MyFragmentDataPassListener mListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView cv_tvCity, cv_tvZip, cv_tvTemp;
        MyFragmentDataPassListener mListener;

        public MyViewHolder(View view, MyFragmentDataPassListener listener) {
            super(view);
            mListener = listener;
            // Define click listener for the ViewHolder's View
            cv_tvCity = (TextView) view.findViewById(R.id.vv_tvCity);
            cv_tvZip = (TextView) view.findViewById(R.id.vv_tvZip);
            cv_tvTemp = (TextView) view.findViewById(R.id.vv_tvTemp);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mv_data.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public MyRecyclerViewAdapter(ArrayList<MyRecyclerViewData> data, MyFragmentDataPassListener listener) {
        mv_data = data;
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View lv_view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_row, viewGroup, false);
        return new MyViewHolder(lv_view, mListener);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.cv_tvCity.setText(mv_data.get(position).getCity());
        holder.cv_tvZip.setText(mv_data.get(position).getZip());
        holder.cv_tvTemp.setText(mv_data.get(position).getTemp()+"\u00B0");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cf_firedByFragment(mv_data.get(holder.getAdapterPosition()));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mv_data.size();
    }
}
