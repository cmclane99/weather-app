/**
 * Chris McLane
 */
package com.example.weatherapp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;


import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.databinding.FragmentWeatherInfoBinding;

public class FragmentWeatherInfo extends Fragment {

    FragmentWeatherInfoBinding binding;
    final String TEMPLATE = "#";
    private MyFragmentDataPassListener cv_listener;

    public FragmentWeatherInfo() {
        // Required empty public constructor
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
        binding = FragmentWeatherInfoBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        return v;
    }

    public void cf_fetchData(MyRecyclerViewData data) {

        binding.vvTvCurrentTemp.setText(data.getTemp()+"\u00B0");
        binding.vvTvCityName.setText(data.getCity());
        binding.vvTvCurrentWeather.setText(data.getCond());

        // Set icon for current weather condition
        switch (data.getCond()) {
            case "Clear":
                binding.vvTvClimaconCurrent.setText("I");
                break;
            case "Snow":
                binding.vvTvClimaconCurrent.setText("7");
                break;
            case "Rain":
                binding.vvTvClimaconCurrent.setText("%");
                break;
            case "Thunderstorm":
                binding.vvTvClimaconCurrent.setText("F");
                break;
            default:
                binding.vvTvClimaconCurrent.setText(TEMPLATE);
        }

        // Placeholder icons for three day forecast
        Typeface lv_customFont = ResourcesCompat.getFont(getContext(), R.font.climacons);
        binding.vvTvClimaconCurrent.setTypeface(lv_customFont);
        binding.vvTvClimaconCurrent.setTextSize(TypedValue.COMPLEX_UNIT_SP,216);

        binding.climaconDay1.setText("I");
        binding.climaconDay1.setTypeface(lv_customFont);
        binding.climaconDay1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);

        binding.climaconDay2.setText("7");
        binding.climaconDay2.setTypeface(lv_customFont);
        binding.climaconDay2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);

        binding.climaconDay3.setText("6");
        binding.climaconDay3.setTypeface(lv_customFont);
        binding.climaconDay3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,40);

    }


}