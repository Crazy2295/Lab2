package com.example.crazy.lab2.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.CityClickResponse;

import java.util.List;

public class AdapterChooseCity extends RecyclerView.Adapter<AdapterChooseCity.MyViewHolder> {
    private List<String> mDataset;
    public static CityClickResponse delegate = null;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.choose_city_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.itemResponse(getAdapterPosition());
                }
            });
        }
    }

    public AdapterChooseCity(List<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public AdapterChooseCity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_city, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String text = mDataset.get(position);
        holder.mTextView.setText(text);

        if (text.length() > 7)
            holder.mTextView.setTextColor(Color.parseColor("green"));
        else if (text.length() > 5)
            holder.mTextView.setTextColor(Color.parseColor("red"));
        else
            holder.mTextView.setTextColor(Color.parseColor("blue"));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
