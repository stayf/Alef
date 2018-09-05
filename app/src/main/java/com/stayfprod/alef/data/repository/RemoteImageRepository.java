package com.stayfprod.alef.data.repository;

import com.stayfprod.alef.AppExecutor;
import com.stayfprod.alef.data.entity.DataState;
import com.stayfprod.alef.data.entity.Listing;
import com.stayfprod.alef.data.entity.RemoteImage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class RemoteImageRepository {

    private final AppExecutor mAppExecutor = new AppExecutor();

    public Listing<RemoteImage> getImagesAsync(int pageSize) {
        final RemoteImageDataSourceFactory sourceFactory = new RemoteImageDataSourceFactory(mAppExecutor.networkIO());

        LiveData<PagedList<RemoteImage>> livePagedList = new LivePagedListBuilder<>(sourceFactory,
                new PagedList.Config.Builder().setPageSize(pageSize).setEnablePlaceholders(false).build())
                .setFetchExecutor(mAppExecutor.networkIO())
                .build();

        LiveData<DataState> refreshState = Transformations.switchMap(
                sourceFactory.getSourceLiveData(), PositionalRemoteImageDataSource::getInitialLoad);

        LiveData<DataState> networkState = Transformations.switchMap(
                sourceFactory.getSourceLiveData(), PositionalRemoteImageDataSource::getNetworkState);

        return new Listing<>(livePagedList, networkState, refreshState,
                () -> {
                    PositionalRemoteImageDataSource source = sourceFactory.getSourceLiveData().getValue();
                    if (source != null) source.retry();
                },
                () -> {
                    PositionalRemoteImageDataSource source = sourceFactory.getSourceLiveData().getValue();
                    if (source != null) source.invalidate();
                });
    }
}
