package com.whistledemo.github.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Hashtable;


public class IssuesViewModel extends ViewModel {
    private final MutableLiveData<Hashtable<Long, Issue>> mIssueList = new MutableLiveData<Hashtable<Long, Issue>>();

    public void setIssueList(Hashtable<Long, Issue> fullList) {
        mIssueList.setValue(fullList);
    }

    public Hashtable<Long, Issue> getIssueList() {
        return mIssueList != null ? mIssueList.getValue() : null;
    }
}
