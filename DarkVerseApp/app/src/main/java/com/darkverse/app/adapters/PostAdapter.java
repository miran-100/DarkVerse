package com.darkverse.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.darkverse.app.R;
import com.darkverse.app.models.Post;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        
        // Set user info
        holder.tvUserName.setText(post.getUserName());
        holder.tvPostTime.setText(getTimeAgo(post.getTimestamp()));
        
        // Load user profile image
        if (post.getUserProfileImage() != null && !post.getUserProfileImage().isEmpty()) {
            Glide.with(context)
                    .load(post.getUserProfileImage())
                    .placeholder(R.drawable.ic_default_avatar)
                    .into(holder.ivUserAvatar);
        } else {
            holder.ivUserAvatar.setImageResource(R.drawable.ic_default_avatar);
        }
        
        // Set post content
        if (post.getContent() != null && !post.getContent().isEmpty()) {
            holder.tvPostContent.setText(post.getContent());
            holder.tvPostContent.setVisibility(View.VISIBLE);
        } else {
            holder.tvPostContent.setVisibility(View.GONE);
        }
        
        // Set post media
        if (post.getMediaUrl() != null && !post.getMediaUrl().isEmpty()) {
            if ("image".equals(post.getMediaType()) || "gif".equals(post.getMediaType())) {
                holder.ivPostMedia.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(post.getMediaUrl())
                        .into(holder.ivPostMedia);
            } else {
                holder.ivPostMedia.setVisibility(View.GONE);
            }
        } else {
            holder.ivPostMedia.setVisibility(View.GONE);
        }
        
        // Set like and comment counts
        holder.tvLikeCount.setText(String.valueOf(post.getLikeCount()));
        holder.tvCommentCount.setText(String.valueOf(post.getCommentCount()));
        
        // Set like button state
        if (post.isLiked()) {
            holder.ivLike.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.ivLike.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private String getTimeAgo(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        
        if (diff < 60000) { // Less than 1 minute
            return "Just now";
        } else if (diff < 3600000) { // Less than 1 hour
            return (diff / 60000) + "m";
        } else if (diff < 86400000) { // Less than 1 day
            return (diff / 3600000) + "h";
        } else if (diff < 604800000) { // Less than 1 week
            return (diff / 86400000) + "d";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivUserAvatar;
        TextView tvUserName, tvPostTime, tvPostContent;
        ImageView ivPostMedia, ivLike, ivMoreOptions;
        TextView tvLikeCount, tvCommentCount;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivUserAvatar = itemView.findViewById(R.id.iv_user_avatar);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvPostTime = itemView.findViewById(R.id.tv_post_time);
            tvPostContent = itemView.findViewById(R.id.tv_post_content);
            ivPostMedia = itemView.findViewById(R.id.iv_post_media);
            ivLike = itemView.findViewById(R.id.iv_like);
            ivMoreOptions = itemView.findViewById(R.id.iv_more_options);
            tvLikeCount = itemView.findViewById(R.id.tv_like_count);
            tvCommentCount = itemView.findViewById(R.id.tv_comment_count);
        }
    }
}

