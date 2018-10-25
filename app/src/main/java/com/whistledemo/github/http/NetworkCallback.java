package com.whistledemo.github.http;

import com.whistledemo.github.data.Comment;
import com.whistledemo.github.data.Issue;

import java.util.List;

public interface NetworkCallback {
    public void onIssueResult(List<Issue> issues);
    public void onCommentResult(List<Comment> comments);
    public void onFailure();
}
