package com.whistledemo.github.http;

import android.util.Log;

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
    static final String BASE_URL = "https://api.github.com/repos/";

    NetworkCallback mActivityCallback;

    public void start(NetworkCallback ck, long number) {
        mActivityCallback = ck;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CommentAPI commentAPI = retrofit.create(CommentAPI.class);

        Call<List<Comment>> call = commentAPI.loadComments(number);
        call.enqueue(this);
        Log.i("Wilbur", " url =========== " + call.request().url());

    }

    @Override
    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
        if(response.isSuccessful()) {
            List<Comment> issueList = response.body();
            Log.i("Wilbur", " ====================  " + issueList.size());
            if (mActivityCallback != null) {
                mActivityCallback.onCommentResult(issueList);
            }
        } else {
            System.out.println(response.errorBody());
            if (mActivityCallback != null) {
                mActivityCallback.onFailure();
            }
        }
    }

    @Override
    public void onFailure(Call<List<Comment>> call, Throwable t) {
        t.printStackTrace();
    }
}
