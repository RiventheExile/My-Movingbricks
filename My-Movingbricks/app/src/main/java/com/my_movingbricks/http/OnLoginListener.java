package com.my_movingbricks.http;


import okhttp3.Call;

public interface OnLoginListener {

   void loginSuccess(String response, int id);

   void loginError(Call call, Exception e, int id);

}