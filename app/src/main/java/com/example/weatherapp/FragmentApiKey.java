package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.databinding.FragmentApiKeyBinding;

public class FragmentApiKey extends Fragment {
    FragmentApiKeyBinding binding;
    private MyFragmentDataPassListener cv_listener;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String API_KEY = "apiKey";

    public FragmentApiKey() {
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
        binding = FragmentApiKeyBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        binding.vvBtSaveKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.vvEtApiKey.getText().toString().equals("")) {
                    cv_listener.saveApiKey(binding.vvEtApiKey.getText().toString());
                }
                else {
                    binding.vvTvError.setText("Enter a valid API key");
                }
            }
        });

        return v;
    }


}