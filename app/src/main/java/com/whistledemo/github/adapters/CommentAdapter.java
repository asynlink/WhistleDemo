package com.whistledemo.github.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whistledemo.github.R;
import com.whistledemo.github.data.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<Comment> mComments;

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

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setData(List<Comment> data) {
        mComments.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView body;

        public ViewHolder(View view) {
            super(view);
            author = view.findViewById(R.id.author);
            body = view.findViewById(R.id.body);
        }
    }
}
