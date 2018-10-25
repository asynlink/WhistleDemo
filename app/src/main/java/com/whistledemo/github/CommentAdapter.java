package com.whistledemo.github;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whistledemo.github.data.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> mComments;

    private static IssuesAdapter.ClickListener clickListener;

    public CommentAdapter() {
        mComments = new ArrayList<>();
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);

        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.author.setText("User: "+ comment.getUser().getLogin());
        holder.body.setText(comment.getBody());
    }

    public void setOnItemClickListener(IssuesAdapter.ClickListener clickListener) {
        CommentAdapter.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setData(List<Comment> data) {
        mComments.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView author;
        public TextView body;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            author = view.findViewById(R.id.author);
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
