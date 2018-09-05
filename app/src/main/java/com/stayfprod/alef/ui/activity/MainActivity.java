package com.stayfprod.alef.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.stayfprod.alef.R;
import com.stayfprod.alef.data.entity.DataState;
import com.stayfprod.alef.databinding.ActivityMainBinding;
import com.stayfprod.alef.ui.adapter.RemoteImageAdapter;
import com.stayfprod.alef.viewmodel.MainActivityVM;

import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AbsActivity {

    private ActivityMainBinding mBind;
    private RemoteImageAdapter mAdapter;
    private MainActivityVM mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new RemoteImageAdapter(this, (GridLayoutManager) mBind.list.getLayoutManager());
        mBind.list.setAdapter(mAdapter);
        mModel = ViewModelProviders.of(this).get(MainActivityVM.class);
        subscribe();
    }

    private void subscribe() {
        mModel.getData().observe(this, remoteImages -> mAdapter.submitList(remoteImages));
        mModel.getNetworkState().observe(this, dataState -> {
            if (dataState.getStatus() == DataState.Status.FAILED)
                processErrorWithAction(mBind.getRoot(), dataState, v -> mModel.retry());
            else
                hideSnackBar();
        });
        mBind.refresh.setOnRefreshListener(() -> mModel.refresh());
        mModel.getRefreshState().observe(this, dataState -> mBind.refresh.setRefreshing(dataState == DataState.loading()));
    }
}
