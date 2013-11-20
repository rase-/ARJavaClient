package com.example.cam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.SurfaceView;

public class DrawView extends SurfaceView {
	private TextPaint textPaint = new TextPaint();
	private String text;

	public DrawView(Context context) {
		super(context);
		// Create out paint to use for drawing
		textPaint.setARGB(255, 200, 0, 0);
		textPaint.setTextSize(30);
		// This call is necessary, or else the
		// draw method will not be called.
		setWillNotDraw(false);
		text = "Hello World!";
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int textX = 50;
		int textY = 50;
		StaticLayout layout = new StaticLayout(text, textPaint, canvas.getWidth(), 
				Alignment.ALIGN_NORMAL, 1.0f, 1.0f, false);
		canvas.save();
		canvas.translate(textX, textY);
		layout.draw(canvas);
		canvas.restore();
	}
	
	public void setText(String text) {
		this.text = text;
	}
}