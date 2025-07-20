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
import com.darkverse.app.models.Story;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private Context context;
    private List<Story> storyList;

    public StoryAdapter(Context context, List<Story> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new StoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Story story = storyList.get(position);
        
        // Set user name
        holder.tvUserName.setText(story.getUserName());
        
        // Load story preview
        if (story.getMediaUrl() != null && !story.getMediaUrl().isEmpty()) {
            Glide.with(context)
                    .load(story.getMediaUrl())
                    .placeholder(R.drawable.ic_default_avatar)
                    .into(holder.ivStoryPreview);
        } else {
            holder.ivStoryPreview.setImageResource(R.drawable.ic_default_avatar);
        }
        
        // Load user profile image
        if (story.getUserProfileImage() != null && !story.getUserProfileImage().isEmpty()) {
            Glide.with(context)
                    .load(story.getUserProfileImage())
                    .placeholder(R.drawable.ic_default_avatar)
                    .into(holder.ivUserAvatar);
        } else {
            holder.ivUserAvatar.setImageResource(R.drawable.ic_default_avatar);
        }
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivStoryPreview;
        CircleImageView ivUserAvatar;
        TextView tvUserName;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivStoryPreview = itemView.findViewById(R.id.iv_story_preview);
            ivUserAvatar = itemView.findViewById(R.id.iv_user_avatar);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
        }
    }
}

