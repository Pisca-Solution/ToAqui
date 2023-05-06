package com.example.toaqui.api;

import com.example.toaqui.model.Qrcode;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface QrcodeService {

    @POST("qrcode/descriptografar")
    Call<String> descriptografar(@Body Qrcode codigo);
}
