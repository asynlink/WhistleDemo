package com.whistledemo.github.http;

import com.whistledemo.github.data.Issue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IssueAPI {
    @GET("ReactiveX/RxJava/issues")
    Call<List<Issue>> loadIssues();
}
