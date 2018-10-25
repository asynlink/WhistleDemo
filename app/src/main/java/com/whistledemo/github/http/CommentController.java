package com.whistledemo.github.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whistledemo.github.data.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentController implements Callback<List<Comment>> {
    NetworkCallback mActivityCallback;

    public void start(NetworkCallback ck, String url, long number) {
        mActivityCallback = ck;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CommentAPI commentAPI = retrofit.create(CommentAPI.class);

        Call<List<Comment>> call = commentAPI.loadComments(number);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
        if(response.isSuccessful()) {
            List<Comment> issueList = response.body();
            if (mActivityCallback != null) {
                mActivityCallback.onCommentResult(issueList);
            }
        } else {
            if (mActivityCallback != null) {
                mActivityCallback.onFailure(response.errorBody().toString());
            }
        }
    }

    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        t.printStackTrace();
    }
}
