package org.nyjsl.swallow.http.retrofit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by weix01 on 2015-11-17.
 */
public class BaseAPI<T> {

    protected static String baseURL = "nothing here";

    protected Retrofit retrofit;

    protected BaseAPI(){
        if(null == retrofit)
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public T getServiceImpl(Class<T> klazz) {
        return retrofit.create(klazz);
    }
}
