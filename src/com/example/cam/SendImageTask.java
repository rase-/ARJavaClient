package com.example.cam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.view.SurfaceView;
import android.widget.FrameLayout;

public class SendImageTask extends AsyncTask<byte[], Integer, Product> {
	private CamTestActivity activity;

	public SendImageTask(CamTestActivity context) {
		this.activity = context;
	}

	@Override
	protected Product doInBackground(byte[]... params) {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclient.getParams().setParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, 5000);

		HttpPost httppost = new HttpPost("http://jamo.fi:3000/process");

		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		ContentBody cd = new InputStreamBody(
				new ByteArrayInputStream(params[0]), "img.jpg");
		mpEntity.addPart("file", cd);

		httppost.setEntity(mpEntity);
		System.out.println("executing request " + httppost.getRequestLine());
		Product p = null;
		HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			System.out.println("Post executed");
			HttpEntity resEntity = response.getEntity();

			System.out.println(response.getStatusLine());
			if (resEntity != null) {
				InputStream is = resEntity.getContent();
				String responseContent = IOUtils.toString(is);
				System.out.println("Response content: " + responseContent);
				resEntity.consumeContent();
			}

			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			Gson mapper = new Gson();
			String jsonString = "{\"name\": \"product\",\"description\": \"This product is not very expensive, and fairly good quality\",\"thumbsUp\": 10,\"thumbsDown\": 3}";
			p = mapper.fromJson(jsonString, Product.class);
		}

		return p;
	}

	@Override
	protected void onPostExecute(Product p) {
		System.out.println(p);
		// Here we actually want to put some overlay that is removable with some action
		activity.getDrawView().setText(p.toString());
		// The following codeline complains about already having a parent... how to update on the fly??
		((FrameLayout) activity.findViewById(R.id.preview)).removeView(activity.getDrawView());
		((FrameLayout) activity.findViewById(R.id.preview)).addView(activity.getDrawView());
	}
	
}
