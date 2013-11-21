package com.example.cam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;

import com.google.gson.Gson;

import android.os.AsyncTask;

public class SendProductTask extends AsyncTask<RawProduct, Integer, Integer> {

	@Override
	protected Integer doInBackground(RawProduct... params) {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpclient.getParams().setParameter(
				HttpConnectionParams.CONNECTION_TIMEOUT, 5000);

		HttpPost httppost = new HttpPost("http://jamo.fi:3000/products");

		MultipartEntity mpEntity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		
		ContentBody cd = new InputStreamBody(
				new ByteArrayInputStream(params[0].getBarcodeImage()), "barcode.jpg");
		mpEntity.addPart("barcode", cd);
		cd = new InputStreamBody(new ByteArrayInputStream(params[0].getLogoImage()), "logo.jpg");
		mpEntity.addPart("logo", cd);
		for (int i = 0; i < params[0].getTextImages().size(); i++) {
			byte[] img = params[0].getTextImages().get(i);
			cd = new InputStreamBody(new ByteArrayInputStream(img), "text" + i + ".jpg");
			mpEntity.addPart("text" + i, cd);
		}
		
		try {
			System.out.println("Adding JSON: " + params[0].toJSON());
			mpEntity.addPart("product",	new StringBody(params[0].toJSON()));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
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
				// Eventually we can put here some sign that tells whether it was successful or not
				// Like a Toast call
				resEntity.consumeContent();
			}

			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			System.out.println("Failed sending product content");
			e.printStackTrace();
		}

		return null;
	}

}
