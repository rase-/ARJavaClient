package com.example.cam;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AddActivity extends Activity {
	private byte[] barcodeImage;
	private byte[] logoImage;
	private byte[][] textImages;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		((Button) findViewById(R.id.set_barcode)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Take picture and set barcodeImage value
			}
		});
		
		((Button) findViewById(R.id.set_logo)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Take picture and set logoImage value
			}
		});
		
		((Button) findViewById(R.id.add_text)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Take picture and set textImages value
			}
		});
		
		((Button) findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Send to server here ... eventually
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
