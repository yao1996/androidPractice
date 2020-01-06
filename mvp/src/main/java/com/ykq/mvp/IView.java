package com.ykq.mvp;

import com.ykq.mvp.listener.OnDataGetListner;

/**
 * @author ykq
 * @date 2020-01-02
 */
public interface IView {

    void getData(OnDataGetListner callback);
}
