package com.ykq.ykqfrost.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ykq.ykqfrost.utils.LogUtil;

/**
 * @author ykq
 * @date 2020/9/15
 */
public class BaseFragment extends Fragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LogUtil.d("lifeCycle onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("lifeCycle onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d("lifeCycle onCreate");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d("lifeCycle onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("lifeCycle onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("lifeCycle onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("lifeCycle onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d("lifeCycle onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("lifeCycle onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("lifeCycle onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.d("lifeCycle onDetach");
    }
}
