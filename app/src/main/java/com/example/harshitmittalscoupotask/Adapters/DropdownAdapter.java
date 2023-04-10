package com.example.harshitmittalscoupotask;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.harshitmittalscoupotask.Models.CarDetailsModel;
import com.example.harshitmittalscoupotask.Models.MakerDetailsModel;

import java.util.ArrayList;
import java.util.List;
public class DropdownAdapter<T> extends ArrayAdapter<T> {

    public DropdownAdapter(Context context, List<T> algorithmList) {
        super(context, 0, algorithmList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.drop_down, parent, false);
        TextView textViewName = convertView.findViewById(R.id.drop_value);
        if(getItem(position) instanceof MakerDetailsModel){

            String currentItem = ((MakerDetailsModel) getItem(position)).getMakeName();
            textViewName.setText(currentItem);
        }else{
            String currentItem = ((CarDetailsModel) getItem(position)).getModel_Name();
            textViewName.setText(currentItem);
        }

        return convertView;
    }
}

