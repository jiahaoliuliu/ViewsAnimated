package com.jiahaoliuliu.viewsanimated;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Views
    private Button mShowElementsButton;
    private LinearLayout mHiddenLinearLayout;
    private Button mLastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the views
        mShowElementsButton = (Button) findViewById(R.id.show_elements_button);
        mHiddenLinearLayout = (LinearLayout) findViewById(R.id.hidden_linear_layout);
        mLastButton = (Button) findViewById(R.id.last_button);

        mShowElementsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Show or hide the element
                if (areElementsVisible()) {
                    // hideElements();
                    fadeOutElements();
                } else {
                    // showElements();
                    fadeInElements();
                }
            }
        });
    }

    private boolean areElementsVisible() {
        return mHiddenLinearLayout.getVisibility() == View.VISIBLE
                && mHiddenLinearLayout.getAlpha() == 1.0f;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateShowElementsButton();
    }

    private void updateShowElementsButton() {
        // Update the button
        if (areElementsVisible()) {
            mShowElementsButton.setText(R.string.hide_elements);
        } else {
            mShowElementsButton.setText(R.string.show_elements);
        }
    }

    private void hideElements() {
        // Precondition
        if (!areElementsVisible()) {
            Log.w(TAG, "The view is already non-visible. Nothing to do here");
            return;
        }

        mHiddenLinearLayout.setVisibility(View.GONE);
//        mLastButton
//                .animate()
//                .translationY(-mHiddenLinearLayout.getHeight() + mLastButton.getHeight())
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        // Coming back to the previous state
//                        mLastButton.setTranslationY(0);
//                    }
//                });
        updateShowElementsButton();
    }

    private void showElements() {
        // Precondition
        if (areElementsVisible()) {
            Log.w(TAG, "The view is already visible. Nothing to do here");
            return;
        }

        mLastButton
                .animate()
                .translationY(mHiddenLinearLayout.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mHiddenLinearLayout
                                .animate()
                                .alpha(1.0f);
                        mHiddenLinearLayout.setVisibility(View.VISIBLE);
                        // Appears under the new buttons
                        mLastButton.setTranslationY(0);
                    }
                });;
        updateShowElementsButton();
    }

    private void fadeInElements() {
        // Precondition
        if (areElementsVisible()) {
            Log.w(TAG, "The view is already visible. Nothing to do here");
            return;
        }

        mHiddenLinearLayout
                .animate()
                .alpha(1.0f);

        updateShowElementsButton();
    }

    private void fadeOutElements() {
        // Precondition
        if (mHiddenLinearLayout.getAlpha() == 0.0f) {
            Log.w(TAG, "The view is already non-visible. Nothing to do here");
            return;
        }

        mHiddenLinearLayout
                .animate()
                .alpha(0.0f);

        updateShowElementsButton();
    }

}
