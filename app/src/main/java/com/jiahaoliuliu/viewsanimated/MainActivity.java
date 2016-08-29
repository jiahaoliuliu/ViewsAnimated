package com.jiahaoliuliu.viewsanimated;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final Long ANIMATION_TRANSITION_TIME = 500L; // 500 MS

    // Internal variables
    private Context mContext;
    private int mHiddenLinearLayoutHeight;

    // Views
    private Button mShowElementsButton;
    private RelativeLayout mAllElementsRelativeLayout;
    private LinearLayout mHiddenLinearLayout;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mLastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init internal variable
        mContext = this;

        // Link the views
        mShowElementsButton = (Button) findViewById(R.id.show_elements_button);
        mShowElementsButton.setOnClickListener(mOnClickListener);

        mAllElementsRelativeLayout = (RelativeLayout) findViewById(R.id.all_elements_relative_layout);

        mHiddenLinearLayout = (LinearLayout) findViewById(R.id.hidden_linear_layout);
        mHiddenLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                        // http://stackoverflow.com/questions/9575706/android-get-height-of-a-view-before-it%C2%B4s-drawn Jim Baca
                        // gets called after layout has been done but before display
                        // so we can get the height then hide the view
                        mHiddenLinearLayoutHeight = mAllElementsRelativeLayout.getHeight();

                        mHiddenLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener( this );
                        mHiddenLinearLayout.setVisibility(View.GONE);
                        updateShowElementsButton();
                    }
                });

        // Button 1
        mButton1 = (Button) findViewById(R.id.button_1);
        mButton1.setOnClickListener(mOnClickListener);

        // Button 2
        mButton2 = (Button) findViewById(R.id.button_2);
        mButton2.setOnClickListener(mOnClickListener);

        // Button 3
        mButton3 = (Button) findViewById(R.id.button_3);
        mButton3.setOnClickListener(mOnClickListener);

        // Button 4
        mButton4 = (Button) findViewById(R.id.button_4);
        mButton4.setOnClickListener(mOnClickListener);

        // Last button
        mLastButton = (Button) findViewById(R.id.last_button);
        mLastButton.setOnClickListener(mOnClickListener);

        mShowElementsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Show or hide the element
                if (areElementsVisible()) {
                     hideElements();
                } else {
                     showElements();
                }
            }
        });
    }

    private boolean areElementsVisible() {
        return mHiddenLinearLayout.getVisibility() == View.VISIBLE;
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

        // Animate the hidden linear layout as visible and set
        mHiddenLinearLayout
                .animate()
                .setDuration(ANIMATION_TRANSITION_TIME)
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.v(TAG, "Animation ended. Set the view as gone");
                        super.onAnimationEnd(animation);
                        mHiddenLinearLayout.setVisibility(View.GONE);
                        // Hack: Remove the listener. So it won't be executed when
                        // any other animation on this view is executed
                        mHiddenLinearLayout.animate().setListener(null);
                    }
                })
        ;

        mLastButton
                .animate()
                .setDuration(ANIMATION_TRANSITION_TIME)
                .translationY(0);

        // Update the high of all the elements relativeLayout
        LayoutParams layoutParams = mAllElementsRelativeLayout.getLayoutParams();

        // TODO: Add vertical margins
        layoutParams.height = mLastButton.getHeight();

        updateShowElementsButton();
    }

    private void showElements() {
        // Precondition
        if (areElementsVisible()) {
            Log.w(TAG, "The view is already visible. Nothing to do here");
            return;
        }

        // Animate the hidden linear layout as visible and set
        // the alpha as 0.0. Otherwise the animation won't be shown
        mHiddenLinearLayout.setVisibility(View.VISIBLE);
        mHiddenLinearLayout.setAlpha(0.0f);
        mHiddenLinearLayout
                .animate()
                .setDuration(ANIMATION_TRANSITION_TIME)
                .alpha(1.0f);

        mLastButton
                .animate()
                .setDuration(ANIMATION_TRANSITION_TIME)
                .translationY(mHiddenLinearLayoutHeight);

        // Update the high of all the elements relativeLayout
        LayoutParams layoutParams = mAllElementsRelativeLayout.getLayoutParams();

        // TODO: Add vertical margins
        layoutParams.height = mLastButton.getHeight() + mHiddenLinearLayoutHeight;

        updateShowElementsButton();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            // Hack: For now there are only buttons
            Toast.makeText(mContext, ((Button) view).getText(), Toast.LENGTH_SHORT).show();
        }
    };
}
