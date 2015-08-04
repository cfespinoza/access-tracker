package com.caflores.access_tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.caflores.access_tracker.exporter.ExportService;
import com.caflores.access_tracker.exporter.Exporter;

public class TrackerActivity extends Activity {

	private static final String TAG = "TrackerActivity";
	private static final String FILE_NAME = "prueba.txt";
    private static final int REQUEST_CODE = 1234;
	
    private boolean isEntrance = false;
    private JSONObject register = new JSONObject();
    private ExportService exporter = new Exporter();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracker);
		Button goingInButton = (Button) findViewById(R.id.buttonIn);
		Button goingOutButton = (Button) findViewById(R.id.buttonOut);
		Button beginTurn = (Button)	findViewById(R.id.buttonStart);
		beginTurn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				beginTurn(v);
			}
		});
		Button finishTurn = (Button) findViewById(R.id.buttonFinish);
		finishTurn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishTurn(v);
			}
		});
		this.setEnabledButtons(false);
		this.setEnabledTurnsButtons(true, false);

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
    
    private void beginTurn(View view){
    	Log.d(TAG, "This is beginTurn method");
    	this.setEnabledTurnsButtons(false, true);
    	this.setEnabledButtons(true);
    	String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
		try {
			register.put("date", currentDateTimeString);
			JSONArray dataCollection = new JSONArray();
			JSONObject indexes = new JSONObject();
			register.put("indexes", indexes);
			register.put("data", dataCollection);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void finishTurn(View view){
    	this.setEnabledTurnsButtons(true, false);
    	this.setEnabledButtons(false);
//    	FileOutputStream outputStream;
//    	File auxFile = getExternalFilesDir("/storage/emulated/0/Reportes/"+FILE_NAME);
    	
//    	File file = new File(getExternalFilesDir(null), FILE_NAME);
    	Log.d(TAG, "This is finishTurn method: "+ register.toString());
    	Log.d(TAG, "This is auxFile absoltePath: "+ auxFile.getAbsolutePath());
    	Log.d(TAG, "This is auxFile path: "+ auxFile.getPath());

    	Log.d(TAG, "This is file absoltePath: "+ file.getAbsolutePath());
    	Log.d(TAG, "This is file path: "+ file.getPath());
    	try {
			// outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
//			outputStream = new FileOutputStream(file);
//    		outputStream.write(register.toString().getBytes());
//			outputStream.close();
    		exporter.exportXLS();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(this.isEntrance){
            	this.setIncomingItem(matches.get(0));
            }
            else{
            	this.setOutgoingItem(matches.get(0));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    /**
     * Auxiliaries methods
     */
    private void setIncomingItem(String recognizedString){
    	Log.d(TAG, "This is setIncomingItem method: "+recognizedString);
    	System.out.println("This is setIncomingItem method: "+recognizedString);
    }

    private void setOutgoingItem(String recognizedString){
    	Log.d(TAG, "This is setOutgoingItem method: "+recognizedString);
    	System.out.println("This is setOutgoingItem method: "+recognizedString);
    }
    
    private void setEnabledButtons(Boolean enabled){
    	Button goingInButton = (Button) findViewById(R.id.buttonIn);
		Button goingOutButton = (Button) findViewById(R.id.buttonOut);
		
		goingInButton.setEnabled(enabled);
		goingOutButton.setEnabled(enabled);
    }
    
    private void setEnabledTurnsButtons(Boolean enabledBegin, Boolean enabledFinish){
		Button beginTurn = (Button)	findViewById(R.id.buttonStart);
		Button finishTurn = (Button) findViewById(R.id.buttonFinish);
		
		beginTurn.setEnabled(enabledBegin);
		finishTurn.setEnabled(enabledFinish);
    }
}
