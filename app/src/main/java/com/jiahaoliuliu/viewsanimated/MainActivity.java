package com.jiahaoliuliu.viewsanimated;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the views
        mShowElementsButton = (Button) findViewById(R.id.show_elements_button);
        mHiddenLinearLayout = (LinearLayout) findViewById(R.id.hidden_linear_layout);

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

        mHiddenLinearLayout.setVisibility(View.GONE);
        updateShowElementsButton();
    }

    private void showElements() {
        // Precondition
        if (areElementsVisible()) {
            Log.w(TAG, "The view is already visible. Nothing to do here");
            return;
        }

        mHiddenLinearLayout.setVisibility(View.VISIBLE);
        updateShowElementsButton();
    }
}
