package com.whistledemo.github;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.whistledemo.github.data.Comment;
import com.whistledemo.github.data.CommentViewModel;
import com.whistledemo.github.data.Issue;
import com.whistledemo.github.http.CommentController;
import com.whistledemo.github.http.NetworkCallback;
import com.whistledemo.github.http.NetworkStatus;

import java.util.List;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentAdapter mCommentAdapter;

    long mIssueNumber;

    CommentViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.screenTitle)).setText(getResources().getString(R.string.comment_title));

        Intent intent = getIntent();
        mIssueNumber = intent.getLongExtra(MainActivity.INTENT_NUMBER, -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentAdapter = new CommentAdapter();
        recyclerView.setAdapter(mCommentAdapter);

        mModel = ViewModelProviders.of(this).get(CommentViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mModel.getCommentList() != null) {
            mCommentAdapter.setData(mModel.getCommentList());
        } else {
            if (NetworkStatus.getInstance(getApplicationContext()).isOnline()) {
                CommentController controller = new CommentController();
                controller.start(new MyNetworkCallback(), getResources().getString(R.string.base_url), mIssueNumber);
            } else {
                Toast.makeText(getApplicationContext(), "Please double check your network connection!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class MyNetworkCallback implements NetworkCallback {
        public void onCommentResult(List<Comment> comments) {
            if (comments != null) {
                mModel.setCommentList(comments);
                mCommentAdapter.setData(comments);
            }
        }

        public void onIssueResult(List<Issue> issues) {
        }

        public void onFailure(String message) {
            Toast.makeText(getApplicationContext(), "Unable to retrieve data. " + message, Toast.LENGTH_LONG).show();
        }
    }
}
