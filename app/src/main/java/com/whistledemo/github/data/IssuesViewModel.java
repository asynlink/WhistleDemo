package com.whistledemo.github.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Hashtable;
import java.util.List;


public class IssuesViewModel extends ViewModel {
    private final MutableLiveData<Hashtable<Long, Issue>> mIssueTable = new MutableLiveData<Hashtable<Long, Issue>>();
    private final MutableLiveData<List<Issue>> mIssueList = new MutableLiveData<List<Issue>>();

    public void setIssueTable(Hashtable<Long, Issue> table) {
        mIssueTable.setValue(table);
    }

    public Hashtable<Long, Issue> getIssueTable() {
        return mIssueTable != null ? mIssueTable.getValue() : null;
    }

    public void setIssueList(List<Issue> list) {
        mIssueList.setValue(list);
    }

    public List<Issue> getIssueList() {
        return mIssueList != null ? mIssueList.getValue() : null;
    }
}
