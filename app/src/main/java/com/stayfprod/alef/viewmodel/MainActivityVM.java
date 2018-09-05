package com.stayfprod.alef.viewmodel;

import com.stayfprod.alef.data.entity.DataState;
import com.stayfprod.alef.data.entity.RemoteImage;
import com.stayfprod.alef.data.entity.Listing;
import com.stayfprod.alef.data.repository.RemoteImageRepository;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

@SuppressWarnings("unchecked")
public class MainActivityVM extends ViewModel {
    private final static int PAGE_SIZE = 30;
    private final RemoteImageRepository mRemoteImageRepository;
    private final LiveData<Listing<RemoteImage>> mRepoResult;
    private final LiveData<PagedList<RemoteImage>> mData;
    private final LiveData<DataState> mNetworkState;
    private final LiveData<DataState> mRefreshState;

    public MainActivityVM() {
        mRemoteImageRepository = new RemoteImageRepository();
        MutableLiveData init = new MutableLiveData<>();
        init.setValue(null);
        mRepoResult = Transformations.map(init, (Function<Listing<RemoteImage>,
                Listing<RemoteImage>>) input -> mRemoteImageRepository.getImagesAsync(PAGE_SIZE));
        mNetworkState = Transformations.switchMap(mRepoResult, Listing::getNetworkState);
        mRefreshState = Transformations.switchMap(mRepoResult, Listing::getRefreshState);
        mData = Transformations.switchMap(mRepoResult, Listing::getPagedList);
    }

    public LiveData<PagedList<RemoteImage>> getData() {
        return mData;
    }

    public LiveData<DataState> getNetworkState() {
        return mNetworkState;
    }

    public LiveData<DataState> getRefreshState() {
        return mRefreshState;
    }

    public void retry() {
        Listing listing = mRepoResult.getValue();
        if (listing != null) {
            Runnable retry = listing.getRetry();
            if (retry != null) retry.run();
        }
    }

    public void refresh() {
        Listing listing = mRepoResult.getValue();
        if (listing != null) {
            Runnable refresh = listing.getRefresh();
            if (refresh != null) refresh.run();
        }
    }
}
