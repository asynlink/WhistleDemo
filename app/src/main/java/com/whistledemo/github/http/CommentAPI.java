package com.whistledemo.github.http;

import com.whistledemo.github.data.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommentAPI {
    @GET("ReactiveX/RxJava/issues/{number}/comments")
    Call<List<Comment>> loadComments(@Path(value="number") long number);
}
