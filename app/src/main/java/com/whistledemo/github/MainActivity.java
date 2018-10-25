package com.whistledemo.github;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.whistledemo.github.data.Issue;
import com.whistledemo.github.data.IssuesViewModel;
import com.whistledemo.github.http.Controller;
import com.whistledemo.github.http.NetworkCallback;
import com.whistledemo.github.http.NetworkStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    IssuesViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                long issueId = (long)v.getTag();
                Log.i("Wilbur", " =========== date " + getDate(issueId));
            }

            @Override
            public void onItemLongClick(int position, View v) {
            }
        });

        mModel = ViewModelProviders.of(this).get(IssuesViewModel.class);
    }

    private String getDate(long issueId) {
        Hashtable<Long, Issue> table = mModel.getIssueList();
        if (table != null) {
            Issue issue = table.get(issueId);
            return issue.getUpdated_at();
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
            Controller controller = new Controller();
            controller.start(new MyNetworkCallback());
        }
    }

    public class MyNetworkCallback implements NetworkCallback {
        public void onResult(List<Issue> issues) {
            if (issues != null) {
                //Sort in decending order!
                Collections.sort(issues, new Comparator<Issue>() {
                    public int compare(Issue issue1, Issue issue2) {
                        return issue1.getUpdated_at().compareTo(issue2.getUpdated_at());
                    }
                });
                Collections.reverse(issues);

                Hashtable<Long, Issue> table = new Hashtable<Long, Issue>();
                for (Issue issue : issues) {
                    table.put(issue.getId(), issue);
                }

                mModel.setIssueList(table);
                recyclerViewAdapter.setData(issues);
            }
        }

        public void onFailure() {

        }
    }
}
