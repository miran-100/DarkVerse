package com.darkverse.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.darkverse.app.R;
import com.darkverse.app.adapters.PostAdapter;
import com.darkverse.app.adapters.StoryAdapter;
import com.darkverse.app.models.Post;
import com.darkverse.app.models.Story;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvStories, rvPosts;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout layoutEmpty;
    
    private StoryAdapter storyAdapter;
    private PostAdapter postAdapter;
    private List<Story> storyList;
    private List<Post> postList;
    
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        storyList = new ArrayList<>();
        postList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        initViews(view);
        setupRecyclerViews();
        setupSwipeRefresh();
        loadData();
        
        return view;
    }

    private void initViews(View view) {
        rvStories = view.findViewById(R.id.rv_stories);
        rvPosts = view.findViewById(R.id.rv_posts);
        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        layoutEmpty = view.findViewById(R.id.layout_empty);
    }

    private void setupRecyclerViews() {
        // Setup Stories RecyclerView
        storyAdapter = new StoryAdapter(getContext(), storyList);
        rvStories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvStories.setAdapter(storyAdapter);
        
        // Setup Posts RecyclerView
        postAdapter = new PostAdapter(getContext(), postList);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(postAdapter);
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setColorSchemeResources(R.color.dark_primary);
        swipeRefresh.setOnRefreshListener(this::loadData);
    }

    private void loadData() {
        loadStories();
        loadPosts();
    }

    private void loadStories() {
        mDatabase.child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storyList.clear();
                
                for (DataSnapshot storySnapshot : snapshot.getChildren()) {
                    Story story = storySnapshot.getValue(Story.class);
                    if (story != null) {
                        story.setId(storySnapshot.getKey());
                        storyList.add(story);
                    }
                }
                
                storyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void loadPosts() {
        mDatabase.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    if (post != null) {
                        post.setId(postSnapshot.getKey());
                        postList.add(post);
                    }
                }
                
                // Sort posts by timestamp (newest first)
                Collections.sort(postList, (p1, p2) -> Long.compare(p2.getTimestamp(), p1.getTimestamp()));
                
                postAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
                
                // Show/hide empty state
                if (postList.isEmpty()) {
                    layoutEmpty.setVisibility(View.VISIBLE);
                    rvPosts.setVisibility(View.GONE);
                } else {
                    layoutEmpty.setVisibility(View.GONE);
                    rvPosts.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefresh.setRefreshing(false);
                // Handle error
            }
        });
    }
}

