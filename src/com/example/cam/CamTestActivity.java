package com.example.cam;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class CamTestActivity extends Activity {
	private static final String TAG = "CamTestActivity";
	Preview preview;
	Button buttonClick;
	Camera camera;
	String fileName;
	CamTestActivity act;
	Context ctx;
	
	DrawView drawView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		act = this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		
		preview = new Preview(this, (SurfaceView)findViewById(R.id.surfaceView));
		preview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		preview.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				camera.takePicture(shutterCallback, rawCallback, jpegCallback);
			}
			
		});
		preview.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				camera.autoFocus(new AutoFocusCallback() {

					@Override
					public void onAutoFocus(boolean success, Camera camera) {
						camera.takePicture(shutterCallback, rawCallback, jpegCallback);
					}
					
				});
				return true;
			}
		});
		((FrameLayout) findViewById(R.id.preview)).addView(preview);
		drawView = new DrawView(this);
		((FrameLayout) findViewById(R.id.preview)).addView(drawView);
		preview.setKeepScreenOn(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//      preview.camera = Camera.open();
		camera = Camera.open();
		camera.startPreview();
		preview.setCamera(camera);
	}

	@Override
	protected void onPause() {
		if(camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	private void resetCam() {
		camera.startPreview();
		preview.setCamera(camera);
	}

	public DrawView getDrawView() {
		return drawView;
	}
	
	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			// Log.d(TAG, "onShutter'd");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			 Log.d(TAG, "onPictureTaken - raw");
		}
	};

	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
//			FileOutputStream outStream = null;
//			try {
//				// Write to SD Card
//				fileName = String.format("/sdcard/camtest/%d.jpg", System.currentTimeMillis());
//				outStream = new FileOutputStream(fileName);
//				outStream.write(data);
//				outStream.close();
//				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
//
//				resetCam();
//
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//			}
			
			new SendImageTask(act).execute(data);
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};
}
