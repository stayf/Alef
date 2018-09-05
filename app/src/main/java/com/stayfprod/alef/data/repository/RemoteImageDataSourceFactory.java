package com.stayfprod.alef.data.repository;

import com.stayfprod.alef.data.entity.RemoteImage;

import java.util.concurrent.Executor;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class RemoteImageDataSourceFactory extends DataSource.Factory<Integer, RemoteImage> {
    private final MutableLiveData<PositionalRemoteImageDataSource> mSourceLiveData;
    private final Executor mRetryExecutor;

    public RemoteImageDataSourceFactory(Executor retryExecutor) {
        mSourceLiveData = new MutableLiveData<>();
        mRetryExecutor = retryExecutor;
    }

    public MutableLiveData<PositionalRemoteImageDataSource> getSourceLiveData() {
        return mSourceLiveData;
    }

    @Override
    public DataSource<Integer, RemoteImage> create() {
        PositionalRemoteImageDataSource source = new PositionalRemoteImageDataSource(mRetryExecutor);
        mSourceLiveData.postValue(source);
        return source;
    }
}
