package com.stayfprod.alef.data.repository;

import com.stayfprod.alef.data.entity.RemoteImage;

import java.util.concurrent.Executor;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class RemoteImageDataSourceFactory extends DataSource.Factory<Integer, RemoteImage> {
    private final MutableLiveData<PositionalRemoteImageDataSource> sourceLiveData;
    private Executor retryExecutor;

    public RemoteImageDataSourceFactory(Executor retryExecutor) {
        sourceLiveData = new MutableLiveData<>();
        this.retryExecutor = retryExecutor;
    }

    public MutableLiveData<PositionalRemoteImageDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

    @Override
    public DataSource<Integer, RemoteImage> create() {
        PositionalRemoteImageDataSource source = new PositionalRemoteImageDataSource(retryExecutor);
        sourceLiveData.postValue(source);
        return source;
    }
}
