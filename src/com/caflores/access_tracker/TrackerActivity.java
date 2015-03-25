package com.caflores.access_tracker;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class TrackerActivity extends Activity {

	private static final String TAG = "TrackerActivity";
    private static final int REQUEST_CODE = 1234;
	
    private boolean isEntrance = false;
    private JSONObject register = new JSONObject();
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracker);
		Button goingInButton = (Button) findViewById(R.id.buttonIn);
		Button goingOutButton = (Button) findViewById(R.id.buttonOut);

		PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
        	goingInButton.setEnabled(false);
        	goingInButton.setText("Recognizer not present");
        	goingOutButton.setEnabled(false);
        	goingOutButton.setText("Recognizer not present");
        }
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
    	this.isEntrance = false;
    	startVoiceRecognitionActivity("salida");
    }

    public void goingIn(View view){
    	Log.d(TAG, "This is goingIn method");
    	this.isEntrance = true;
    	startVoiceRecognitionActivity("entrada");
    	
    }
    
    private void startVoiceRecognitionActivity(String msg){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Reconociendo "+msg);
        startActivityForResult(intent, REQUEST_CODE);
    }
    
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(this.isEntrance){
            	this.setIncomingItem(matches.get(0));
            }
            else{
            	this.setOutgoingItem(matches.get(0));
            }
            /*
            wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
            */
            
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    /**
     * Auxiliaries methods
     */
    private void setIncomingItem(String recognizedString){
    	Log.d(TAG, "This is setIncomingItem method: "+recognizedString);
    }

    private void setOutgoingItem(String recognizedString){
    	Log.d(TAG, "This is setOutgoingItem method: "+recognizedString);
    }
}
