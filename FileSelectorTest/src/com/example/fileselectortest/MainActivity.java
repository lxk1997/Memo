package com.example.fileselectortest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import dr.android.fileselector.FileSelectConstant;

public class MainActivity extends Activity {
	private Button btn;
	private static final String TAG = "MainActivity";
    private static final Integer FILE_SELECTOR_REQUEST_CODE = 2016;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), FsActivity.class);
                intent.putExtra(FileSelectConstant.SELECTOR_REQUEST_CODE_KEY, FileSelectConstant.SELECTOR_MODE_FOLDER);
                startActivityForResult(intent, FILE_SELECTOR_REQUEST_CODE);
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent bundle) {
        super.onActivityResult(requestCode, resultCode, bundle);
        Log.i(TAG, "requestCode: " + requestCode + "; resultCode: " + resultCode);
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "paths: " + bundle.getStringArrayListExtra(FileSelectConstant.SELECTOR_BUNDLE_PATHS),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
