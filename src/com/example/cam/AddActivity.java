package com.example.cam;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {
	private static final int BARCODE_CAMERA_REQUEST = 1888;
	private static final int LOGO_CAMERA_REQUEST = 1889;
	private static final int TEXT_CAMERA_REQUEST = 1890;
	private RawProduct rawProduct = new RawProduct();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		((Button) findViewById(R.id.set_barcode)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, BARCODE_CAMERA_REQUEST);
			}
		});
		
		((Button) findViewById(R.id.set_logo)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, LOGO_CAMERA_REQUEST);
			}
		});
		
		((Button) findViewById(R.id.add_text)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, TEXT_CAMERA_REQUEST);
			}
		});
		
		((Button) findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText editName = (EditText)findViewById(R.id.editText1);
				EditText editDescription = (EditText)findViewById(R.id.editText2);
				rawProduct.setName(editName.getText().toString());
				rawProduct.setDescription(editDescription.getText().toString());
				SendProductTask sendTask = new SendProductTask();
				sendTask.execute(rawProduct);
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
	
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == BARCODE_CAMERA_REQUEST && resultCode == RESULT_OK) {  
	            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            photo.compress(CompressFormat.JPEG, 100, stream);
	            rawProduct.setBarcodeImage(stream.toByteArray());
	        }  
	        if (requestCode == LOGO_CAMERA_REQUEST && resultCode == RESULT_OK) {
	        	Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            photo.compress(CompressFormat.JPEG, 100, stream);
	            rawProduct.setLogoImage(stream.toByteArray());
	        }
	        if (requestCode == TEXT_CAMERA_REQUEST && resultCode == RESULT_OK) {
	        	Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            photo.compress(CompressFormat.JPEG, 100, stream);
	            rawProduct.addTextImage(stream.toByteArray());
	        }
	 }
}
