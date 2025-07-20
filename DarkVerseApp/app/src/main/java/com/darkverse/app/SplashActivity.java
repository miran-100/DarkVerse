package com.darkverse.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        ImageView logoImageView = findViewById(R.id.iv_logo);
        TextView appNameTextView = findViewById(R.id.tv_app_name);
        TextView welcomeTextView = findViewById(R.id.tv_welcome);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        // Apply animations
        logoImageView.startAnimation(fadeIn);
        appNameTextView.startAnimation(slideUp);
        welcomeTextView.startAnimation(fadeIn);

        // Delay and check authentication
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserAuthentication();
            }
        }, SPLASH_DURATION);
    }

    private void checkUserAuthentication() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        
        Intent intent;
        if (currentUser != null) {
            // User is signed in, go to main activity
            intent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            // No user is signed in, go to authentication activity
            intent = new Intent(SplashActivity.this, AuthActivity.class);
        }
        
        startActivity(intent);
        finish();
        
        // Add transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}

