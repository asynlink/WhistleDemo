package com.whistledemo.github;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.whistledemo.github.data.Comment;
import com.whistledemo.github.data.Issue;
import com.whistledemo.github.http.CommentController;
import com.whistledemo.github.http.NetworkCallback;
import com.whistledemo.github.http.NetworkStatus;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentAdapter mCommentAdapter;

    long mIssueNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.screenTitle)).setText(getResources().getString(R.string.comment_title));

        Intent intent = getIntent();
        mIssueNumber = intent.getLongExtra("issue_number", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CommentAdapter();
        recyclerView.setAdapter(mCommentAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
            CommentController controller = new CommentController();
            controller.start(new MyNetworkCallback(), mIssueNumber);
        }
    }

    public class MyNetworkCallback implements NetworkCallback {
        public void onCommentResult(List<Comment> comments) {
            if (comments != null) {
                mCommentAdapter.setData(comments);
            }
        }

        public void onIssueResult(List<Issue> issues) {
        }

        public void onFailure() {
            Toast.makeText(getApplicationContext(), "aaaaaaaaa", Toast.LENGTH_LONG).show();
        }
    }
}
