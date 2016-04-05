package com.example.administrator.testa.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.testa.R;

/**
 * Created by wp on 2015/12/24.
 */
public class FragmentTest extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_layout1,null);
        return inflater.inflate(R.layout.fragment_layout1, container,false);
    }
}
