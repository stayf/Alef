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

    private static volatile int sCounter = 0;

    private final MutableLiveData<DataState> mNetworkState;
    private final MutableLiveData<DataState> mInitialLoad;
    private final Executor mRetryExecutor;
    private Runnable mRetryRunnable;

    public PositionalRemoteImageDataSource(Executor retryExecutor) {
        mNetworkState = new MutableLiveData<>();
        mInitialLoad = new MutableLiveData<>();
        mRetryExecutor = retryExecutor;
    }

    public void retry() {
        if (mRetryRunnable != null) mRetryRunnable.run();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<RemoteImage> callback) {
        mRetryRunnable = () -> mRetryExecutor.execute(() -> loadInitial(params, callback));
        Call<List<RemoteImage>> request = ApiClient.getApi().getImages();
        mNetworkState.postValue(DataState.loading());
        mInitialLoad.postValue(DataState.loading());
        try {
            Thread.sleep(2000);
            Response<List<RemoteImage>> response = request.execute();
            List<RemoteImage> result = response.body();
            if (Objects.isBlank(result))
                result = new ArrayList<>();

            if (sCounter == 0) {
                sCounter++;
                result.remove(3);
                result.remove(1);
                result.remove(3);
            }

            callback.onResult(result, 0);
            mNetworkState.postValue(DataState.loaded());
            mInitialLoad.postValue(DataState.loaded());
        } catch (Exception e) {
            DataState error = DataState.error(App.getResource().getString(R.string.error_500));
            mNetworkState.postValue(error);
            mInitialLoad.postValue(error);
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<RemoteImage> callback) {
        callback.onResult(new ArrayList<>());
    }

    public MutableLiveData<DataState> getNetworkState() {
        return mNetworkState;
    }

    public MutableLiveData<DataState> getInitialLoad() {
        return mInitialLoad;
    }
}
