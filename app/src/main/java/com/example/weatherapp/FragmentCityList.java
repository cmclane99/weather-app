/**
 * Chris McLane
 */
package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.databinding.FragmentCityListBinding;

import java.util.ArrayList;

public class FragmentCityList extends Fragment {

    FragmentCityListBinding binding;
    private MyFragmentDataPassListener cv_listener;
    MyRecyclerViewAdapter lv_adapter;
    ArrayList<MyRecyclerViewData> lv_data;
    ArrayList<Integer> lv_tempFahrenheit;

    public FragmentCityList() {
        // Required empty public constructor
    }

    public FragmentCityList(ArrayList<MyRecyclerViewData> data) {
        lv_data = data;
        lv_tempFahrenheit = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyFragmentDataPassListener) {
            cv_listener = (MyFragmentDataPassListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must MyFragmentDataPassListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCityListBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        binding.vvRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        lv_adapter = new MyRecyclerViewAdapter(lv_data, cv_listener);
        binding.vvRvList.setAdapter(lv_adapter);

        // Switch to toggle C/F values
        binding.vvSwTempMetric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double convert_units;

                if (binding.vvSwTempMetric.isChecked()) {
                    binding.vvSwTempMetric.setText("C\u00B0");

                    for(int i = 0; i < lv_data.size(); i++) {
                        lv_tempFahrenheit.add(lv_data.get(i).getTemp());
                        convert_units = Math.round((5.0/9.0) * (lv_data.get(i).getTemp() - 32));
                        lv_data.get(i).setTemp((int) convert_units);
                    }
                }
                else {
                    binding.vvSwTempMetric.setText("F\u00B0");

                    for(int i = 0; i < lv_data.size(); i++) {
                        lv_data.get(i).setTemp(lv_tempFahrenheit.get(i));
                    }

                }

                lv_adapter.notifyDataSetChanged();

            }
        });
        return v;
    }

}