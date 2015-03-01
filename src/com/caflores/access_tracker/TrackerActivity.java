package com.caflores.access_tracker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TrackerActivity extends Activity {

	private static final String TAG = "TrackerActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_tracker);
		setContentView(R.layout.goingin_view);
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
    
    /**
     * Button's click controllers
     */
    
    public void goingOut(View view){
    	Log.d(TAG, "This is goingOut method");
    }

    public void goingIn(View view){
    	Log.d(TAG, "This is goingIn method");
    	
    }
}
