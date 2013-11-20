package com.example.cam;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class SendImageTask extends AsyncTask<byte[], Integer, String> {

	@Override
	protected String doInBackground(byte[]... params) {
		HttpClient httpclient = new DefaultHttpClient();
	    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

	    HttpPost httppost = new HttpPost("http://jamo.fi:3000/process");

	    MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    ContentBody cd = new InputStreamBody(new ByteArrayInputStream(params[0]), "img.jpg");
	    mpEntity.addPart("file", cd);

	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    String responseContent = null;
	    HttpResponse response;
		try {
			response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

		    System.out.println(response.getStatusLine());
		    if (resEntity != null) {
		      System.out.println(EntityUtils.toString(resEntity));
		    }
		    if (resEntity != null) {
		    	InputStream is = resEntity.getContent();
		    	responseContent = IOUtils.toString(is);
		    	System.out.println("Response content: " + responseContent);
		    	resEntity.consumeContent();
		    }

		    httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return responseContent;
	}

}
