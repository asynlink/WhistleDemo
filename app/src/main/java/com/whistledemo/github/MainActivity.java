package com.whistledemo.github;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.whistledemo.github.adapters.IssuesAdapter;
import com.whistledemo.github.data.Comment;
import com.whistledemo.github.data.Issue;
import com.whistledemo.github.data.IssuesViewModel;
import com.whistledemo.github.http.IssueController;
import com.whistledemo.github.http.NetworkCallback;
import com.whistledemo.github.http.NetworkStatus;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_NUMBER = "issue_number";

    RecyclerView recyclerView;
    IssuesAdapter recyclerViewAdapter;

    IssuesViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.screenTitle)).setText(getResources().getString(R.string.issue_title));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new IssuesAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(new IssuesAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                long issueId = (long)v.getTag();

                Intent myIntent = new Intent(MainActivity.this, CommentActivity.class);
                myIntent.putExtra(INTENT_NUMBER, getIssueNumber(issueId));
                startActivity(myIntent);
            }

            @Override
            public void onItemLongClick(int position, View v) {
            }
        });

        mModel = ViewModelProviders.of(this).get(IssuesViewModel.class);
    }

    private long getIssueNumber(long issueId) {
        Hashtable<Long, Issue> table = mModel.getIssueTable();
        if (table != null && table.containsKey(issueId)) {
            Issue issue = table.get(issueId);
            return issue.getNumber();
        }
        return -1;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mModel.getIssueList() != null) {
            recyclerViewAdapter.setData(mModel.getIssueList());
        } else {
            if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                IssueController controller = new IssueController();
                controller.start(new MyNetworkCallback(), getResources().getString(R.string.base_url));
            } else {
                Toast.makeText(getApplicationContext(), "Please double check your network connection!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class MyNetworkCallback implements NetworkCallback {
        public void onIssueResult(List<Issue> issues) {
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

                mModel.setIssueTable(table);
                mModel.setIssueList(issues);
                recyclerViewAdapter.setData(issues);
            }
        }

        public void onCommentResult(List<Comment> comments) {
        }

        public void onFailure(String message) {
            Toast.makeText(getApplicationContext(), "Unable to retrieve data. " + message, Toast.LENGTH_LONG).show();
        }
    }
}
