package com.ykq.mvp.login;

import android.os.Handler;

import com.ykq.mvp.IView;
import com.ykq.mvp.listener.OnDataGetListner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ykq
 * @date 2020-01-02
 */
public class LoginView implements IView {

    @Override
    public void getData(final OnDataGetListner callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nickname", "霜降");
                    jsonObject.put("avatar", "https://img2.woyaogexing.com/2020/01/02/0fe2f9c715c34d21b853245e1f885b01!400x400.jpeg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onGetData(jsonObject);
            }
        }, 2000);
    }
}
