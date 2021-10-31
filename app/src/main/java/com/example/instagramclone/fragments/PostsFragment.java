package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Post;
import com.example.instagramclone.PostsAdapter;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    private SwipeRefreshLayout swipeContainer;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        //1. Create layout for one row in the list
        //2. Create the adapter
        //3. Create the data source

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        //4. Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        //5. Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                adapter.addAll(allPosts);

                queryPosts();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts.", e);
                    return;
                }
                for (Post post: posts) {
                    Log.i(TAG, "Post:" + post.getDescription());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}