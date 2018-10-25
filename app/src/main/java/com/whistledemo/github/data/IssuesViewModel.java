package com.whistledemo.github.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;


public class IssuesViewModel extends ViewModel {
    private final MutableLiveData<List<Issue>> mIssueList = new MutableLiveData<List<Issue>>();

    public void setIssueList(List<Issue> fullList) {
        mIssueList.setValue(fullList);
    }

    public List<Issue> getIssueList() {
        return mIssueList != null ? mIssueList.getValue() : null;
    }
}
