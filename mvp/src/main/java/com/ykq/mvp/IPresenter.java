package com.ykq.mvp;

/**
 * @author ykq
 * @date 2020-01-02
 * presenter
 */
public interface IPresenter<T extends IView> {

    void attachView(T view);

    void detachView(T view);

}
