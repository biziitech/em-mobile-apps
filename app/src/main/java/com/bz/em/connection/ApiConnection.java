package com.bz.em.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Bellal Hossain
 **/
public class ApiConnection {

    //private static final String BASE_URL ="http://103.48.19.191:8080/api/";
    private static final String BASE_URL ="http://103.48.19.159:8080/api/";

    //private static final String BASE_URL ="http://192.168.10.98:8080/api/";//localhost

    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;

    }
}
