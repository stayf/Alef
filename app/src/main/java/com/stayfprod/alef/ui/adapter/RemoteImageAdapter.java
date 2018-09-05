package com.stayfprod.alef.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.stayfprod.alef.R;
import com.stayfprod.alef.data.entity.RemoteImage;
import com.stayfprod.alef.databinding.ItemImageBinding;
import com.stayfprod.alef.util.Objects;
import com.stayfprod.alef.util.Screen;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RemoteImageAdapter extends PagedListAdapter<RemoteImage, RemoteImageAdapter.ViewHolderItem> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final int mSpanCount;
    private final int mItemSize;

    public RemoteImageAdapter(@NonNull Context context, GridLayoutManager layoutManager) {
        super(new DiffUtil.ItemCallback<RemoteImage>() {
            @Override
            public boolean areItemsTheSame(@NonNull RemoteImage oldItem, @NonNull RemoteImage newItem) {
                return oldItem.getSrc().equals(newItem.getSrc());
            }

            @Override
            public boolean areContentsTheSame(@NonNull RemoteImage oldItem, @NonNull RemoteImage newItem) {
                return oldItem.equals(newItem);
            }
        });

        if (layoutManager == null)
            throw new NullPointerException("LayoutManager is null");

        if (layoutManager.getSpanCount() <= 0)
            throw new IllegalArgumentException("SpanCount must be more than 0");

        mSpanCount = layoutManager.getSpanCount();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItemSize = Screen.getWidth() / mSpanCount;
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderItem(DataBindingUtil.inflate(mLayoutInflater, R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, final int position) {
        holder.bind(getItem(position));
    }

    @SuppressLint("RestrictedApi")
    class ViewHolderItem extends RecyclerView.ViewHolder {

        ItemImageBinding binding;

        ViewHolderItem(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.container.getLayoutParams().height = mItemSize;
        }

        void bind(RemoteImage image) {
            if (Objects.isNotBlank(image.getSrc())) {
                binding.image.setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.parse(image.getSrc()))
                        .setResizeOptions(new ResizeOptions(mItemSize, mItemSize))
                        .build());
            } else {
                binding.image.setImageURI((String) null);
            }
        }
    }
}
