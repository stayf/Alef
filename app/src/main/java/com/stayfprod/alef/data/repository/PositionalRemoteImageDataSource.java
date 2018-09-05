package com.stayfprod.alef.data.repository;

import com.stayfprod.alef.App;
import com.stayfprod.alef.R;
import com.stayfprod.alef.data.entity.DataState;
import com.stayfprod.alef.data.entity.RemoteImage;
import com.stayfprod.alef.data.remote.ApiClient;
import com.stayfprod.alef.util.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PositionalDataSource;
import retrofit2.Call;
import retrofit2.Response;

public class PositionalRemoteImageDataSource extends PositionalDataSource<RemoteImage> {

    private static volatile int k = 0;

    private final MutableLiveData<DataState> networkState;
    private final MutableLiveData<DataState> initialLoad;
    private final Executor retryExecutor;
    private Runnable retryRunnable;

    public PositionalRemoteImageDataSource(Executor retryExecutor) {
        networkState = new MutableLiveData<>();
        initialLoad = new MutableLiveData<>();
        this.retryExecutor = retryExecutor;
    }

    public void retry() {
        if (retryRunnable != null) retryRunnable.run();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<RemoteImage> callback) {
        retryRunnable = () -> retryExecutor.execute(() -> loadInitial(params, callback));
        Call<List<RemoteImage>> request = ApiClient.getApi().getImages();
        networkState.postValue(DataState.loading());
        initialLoad.postValue(DataState.loading());
        try {
            Thread.sleep(2000);
            Response<List<RemoteImage>> response = request.execute();
            List<RemoteImage> result = response.body();
            if (Objects.isBlank(result))
                result = new ArrayList<>();

            if (k == 0) {
                k++;
                result.remove(3);
                result.remove(1);
                result.remove(3);
            }

            callback.onResult(result, 0);
            networkState.postValue(DataState.loaded());
            initialLoad.postValue(DataState.loaded());

        } catch (Exception e) {
            DataState error = DataState.error(App.getResource().getString(R.string.error_500));
            networkState.postValue(error);
            initialLoad.postValue(error);
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<RemoteImage> callback) {
        callback.onResult(new ArrayList<>());
    }

    public final MutableLiveData<DataState> getNetworkState() {
        return networkState;
    }

    public final MutableLiveData<DataState> getInitialLoad() {
        return initialLoad;
    }
}
