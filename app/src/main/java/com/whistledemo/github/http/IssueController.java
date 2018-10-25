package com.whistledemo.github.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whistledemo.github.data.Issue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IssueController implements Callback<List<Issue>> {
    static final String BASE_URL = "https://api.github.com/repos/";

    NetworkCallback mActivityCallback;

    public void start(NetworkCallback ck) {
        mActivityCallback = ck;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        IssueAPI issueApi = retrofit.create(IssueAPI.class);

        Call<List<Issue>> call = issueApi.loadIssues();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
        if(response.isSuccessful()) {
            List<Issue> issueList = response.body();
            Log.i("Wilbur", " ====================  " + issueList.size());
            if (mActivityCallback != null) {
                mActivityCallback.onIssueResult(issueList);
            }
//            changesList.forEach(change -> System.out.println(change.subject));
        } else {
            System.out.println(response.errorBody());
            if (mActivityCallback != null) {
                mActivityCallback.onFailure();
            }
        }
    }

    @Override
    public void onFailure(Call<List<Issue>> call, Throwable t) {
        t.printStackTrace();
    }
}
