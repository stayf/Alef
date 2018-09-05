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
    private final RemoteImageRepository remoteImageRepository;
    private final LiveData<Listing<RemoteImage>> repoResult;
    private final LiveData<PagedList<RemoteImage>> data;
    private final LiveData<DataState> networkState;
    private final LiveData<DataState> refreshState;

    public MainActivityVM() {
        remoteImageRepository = new RemoteImageRepository();
        MutableLiveData init = new MutableLiveData<>();
        init.setValue(null);
        repoResult = Transformations.map(init,
                (Function<Listing<RemoteImage>, Listing<RemoteImage>>) input -> remoteImageRepository.getImagesAsync(PAGE_SIZE));
        networkState = Transformations.switchMap(repoResult, Listing::getNetworkState);
        refreshState = Transformations.switchMap(repoResult, Listing::getRefreshState);
        data = Transformations.switchMap(repoResult, Listing::getPagedList);
    }

    public final LiveData<PagedList<RemoteImage>> getData() {
        return this.data;
    }

    public final LiveData<DataState> getNetworkState() {
        return this.networkState;
    }

    public final LiveData<DataState> getRefreshState() {
        return this.refreshState;
    }

    public final void retry() {
        Listing listing = repoResult.getValue();
        if (listing != null) {
            Runnable retry = listing.getRetry();
            if (retry != null) retry.run();
        }
    }

    public final void refresh() {
        Listing listing = repoResult.getValue();
        if (listing != null) {
            Runnable runnable = listing.getRefresh();
            if (runnable != null) {
                runnable.run();
            }
        }

    }
}
