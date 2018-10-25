package com.whistledemo.github;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whistledemo.github.data.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {
    private List<Issue> mIssues;

    private static final int MAX_LENGTH = 140;

    private static ClickListener clickListener;

    public IssuesAdapter() {
        mIssues = new ArrayList<>();
    }

    @Override
    public IssuesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_layout, parent, false);

        IssuesAdapter.ViewHolder viewHolder = new IssuesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IssuesAdapter.ViewHolder holder, int position) {
        Issue issue = mIssues.get(position);
        holder.title.setText("Issue: " + issue.getTitle());
        if (issue.getBody().length() > MAX_LENGTH) {
            holder.body.setText(issue.getBody().substring(0, MAX_LENGTH));
        } else {
            holder.body.setText(issue.getBody());
        }
        holder.itemView.setTag(issue.getId());
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        IssuesAdapter.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mIssues.size();
    }

    public void setData(List<Issue> data) {
        mIssues.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public TextView body;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = view.findViewById(R.id.title);
            body = view.findViewById(R.id.body);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
