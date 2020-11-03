package com.ykq.ykqfrost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.utils.DisplayUtils;
import com.ykq.ykqfrost.utils.ImageLoader;

import java.util.List;

/**
 * @author ykq
 * @date 2020/11/3
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mList;
    private Context context;

    public PhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_photo, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Holder holder = (Holder) viewHolder;
        String uri = mList.get(position);
        ImageLoader.bindBitmap(uri, holder.mImageView, DisplayUtils.dip2px(100), 0);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class Holder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.photo);
        }
    }
}
