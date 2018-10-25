package com.whistledemo.github.http;

import com.whistledemo.github.data.Issue;

import java.util.List;

public interface NetworkCallback {
    public void onResult(List<Issue> issues);
    public void onFailure();
}
