package com.ykq.ykqfrost.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ykq.ykqfrost.R;
import com.ykq.ykqfrost.adapter.PhotoAdapter;

import java.util.ArrayList;

/**
 * @author ykq
 * @date 2020/11/3
 */
public class PhotoWallActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);
        RecyclerView recyclerView = findViewById(R.id.photo_wall);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ArrayList<String> list = new ArrayList<>();
        list.add("https://w.wallhaven.cc/full/g8/wallhaven-g83m3d.jpg");
        list.add("https://th.wallhaven.cc/small/xl/xlx8ez.jpg");
        list.add("https://th.wallhaven.cc/small/ox/oxzk8m.jpg");
        list.add("https://th.wallhaven.cc/small/39/39dmkd.jpg");
        list.add("https://th.wallhaven.cc/small/5w/5w26x1.jpg");
        list.add("https://th.wallhaven.cc/small/2e/2eroxm.jpg");
        list.add("https://th.wallhaven.cc/small/96/967zyk.jpg");
        list.add("https://th.wallhaven.cc/small/ey/eyl1xr.jpg");
        list.add("https://th.wallhaven.cc/small/96/96qy3w.jpg");
        list.add("https://th.wallhaven.cc/small/ym/ym1wp7.jpg");
        list.add("https://th.wallhaven.cc/small/5w/5we787.jpg");
        list.add("https://th.wallhaven.cc/small/xl/xlq1rv.jpg");
        list.add("https://th.wallhaven.cc/small/2e/2em38y.jpg");
        list.add("https://th.wallhaven.cc/small/dg/dgrgql.jpg");
        list.add("https://th.wallhaven.cc/small/6k/6k8kkx.jpg");
        list.add("https://th.wallhaven.cc/small/39/3911w9.jpg");
        list.add("https://th.wallhaven.cc/small/dg/dg7y23.jpg");
        list.add("https://th.wallhaven.cc/small/zm/zmm7mw.jpg");
        list.add("https://th.wallhaven.cc/small/md/mdzdok.jpg");
        list.add("https://th.wallhaven.cc/small/x1/x1wroo.jpg");
        list.add("https://th.wallhaven.cc/small/ey/eymzjk.jpg");
        recyclerView.setAdapter(new PhotoAdapter(this, list));
    }

    public static void goToActivity(Context context) {
        Intent intent = new Intent(context, PhotoWallActivity.class);
        context.startActivity(intent);
    }

}
