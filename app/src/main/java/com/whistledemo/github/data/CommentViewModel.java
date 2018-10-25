package com.whistledemo.github.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class CommentViewModel extends ViewModel {
    private final MutableLiveData<List<Comment>> mCommentList = new MutableLiveData<List<Comment>>();

    public void setCommentList(List<Comment> list) {
        mCommentList.setValue(list);
    }

    public List<Comment> getCommentList() {
        return mCommentList != null ? mCommentList.getValue() : null;
    }
}