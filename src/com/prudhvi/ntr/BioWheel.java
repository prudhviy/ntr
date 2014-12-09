package com.prudhvi.ntr;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.BlurMaskFilter.Blur;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BioWheel extends SurfaceView implements SurfaceHolder.Callback{
	
	private BioWheelThread thread;
	private Paint paintHole = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float xBR,yBR;

	public BioWheel(Context context) {
		super(context);
		init();
	}
	public BioWheel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public BioWheel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		getHolder().addCallback(this);
		getHolder().setFormat(PixelFormat.TRANSPARENT);
		setZOrderOnTop(true);
		 
		setFocusable(true); // make sure we get key events
		//setClickable(true);
		
		//paintHole.setFlags(Paint.DITHER_FLAG);
		paintHole.setStyle(Paint.Style.FILL);
		paintHole.setColor(Color.TRANSPARENT);
		paintHole.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) { }

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new BioWheelThread(getHolder(), this);
		thread.setRunning(true);

		xBR = (float)getWidth();
		yBR = (float)getHeight();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while(retry) {
			try {
				thread.join();
				retry = false;
			} 
			catch(InterruptedException e) {
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.restore();
		//super.onDraw(canvas);
	}
}