package com.uvita.andrey.modules.repository.remote;
import retrofit2.Response;

public interface ResponseListener<T extends Object> {
    void errorResponseHandler(Throwable error);
    void successResponserHandler(Response<T> response);
}