package com.example.toaqui.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //private static final String BASE_URL = "http://192.168.0.85:8080/";
    private static final String BASE_URL = "http://172.17.104.160:8080/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientAuth(String authToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + authToken)
                            .build();
                    return chain.proceed(request);
                })
                .build();



            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

 //share preference
        return retrofit;
    }

    public static UsuarioService getUsuarioService() {
        return getClient().create(UsuarioService.class);
    }

    public static QrcodeService getQrcodeService(String authToken) {
        return getClientAuth(authToken).create(QrcodeService.class);
    }

    public static AlunoService getAlunoService(String authToken) {
        return getClientAuth(authToken).create(AlunoService.class);
    }
}
