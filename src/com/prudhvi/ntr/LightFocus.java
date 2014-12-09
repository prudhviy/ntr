package com.prudhvi.ntr;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.PorterDuff;
import android.graphics.BlurMaskFilter.Blur;

public class LightFocus extends SurfaceView implements SurfaceHolder.Callback{
	
	private LightFocusThread thread;
	private Paint paintHole = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint paintFocus = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint paintTriangle = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Path pathEllipse = new Path();
	private Path pathTriangle = new Path();
	private float offc = 50.0f;
	private float coff = 40.0f;
	private float xBR,yBR;

	private float cx, cy;
	private float offx = 3.0f;
	private float offy = 3.0f;
	private boolean focusOutAnim = false;

	public LightFocus(Context context) {
		super(context);
		init();
	}
	public LightFocus(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public LightFocus(Context context, AttributeSet attrs, int defStyle) {
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

		paintTriangle.setMaskFilter(new BlurMaskFilter(10.0f, Blur.NORMAL));
		//paintTriangle.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		
		//paintFocus.setFlags(Paint.DITHER_FLAG);
		paintFocus.setStyle(Paint.Style.FILL);
		//paintFocus.setColor(0x66FFFFFF);
		//paintFocus.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) { }

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new LightFocusThread(getHolder(), this);
		thread.setRunning(true);
		if(!focusOutAnim){
			thread.start();
		}
		xBR = (float)getWidth();
		yBR = (float)getHeight();
		cx = xBR - xBR/2.0f;
		cy = yBR - 50.0f;
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
		if(!focusOutAnim){
			//canvas.save();
			canvas.drawColor(0xE3000000, PorterDuff.Mode.SRC);
			//canvas.drawARGB(255, 0, 0, 0);
			
			pathTriangle.reset();
			pathTriangle.moveTo(cx + offc, cy - offc);
			paintTriangle.setShader(new LinearGradient(xBR, yBR, cx, cy, Color.WHITE, 0x77FFFFFF, Shader.TileMode.MIRROR));
			pathTriangle.lineTo(xBR , yBR);
			pathTriangle.lineTo(cx - offc, cy + offc);
			pathTriangle.lineTo(cx + offc, cy - offc);
			pathTriangle.close();
			canvas.drawPath(pathTriangle, paintTriangle);
	
			pathEllipse.reset();
			pathEllipse.moveTo(cx + offc, cy - offc);
			paintFocus.setShader(new RadialGradient(cx, cy, offc, 0x44FFFFFF, 0xAAFFFFFF, Shader.TileMode.MIRROR));
			pathEllipse.cubicTo(cx + offc + coff, cy - offc + coff, cx - offc + coff, cy + offc + coff, cx - offc, cy + offc);
			pathEllipse.cubicTo(cx - offc - coff, cy + offc - coff, cx + offc - coff, cy - offc - coff, cx + offc, cy - offc);
			pathEllipse.close();
			canvas.drawPath(pathEllipse, paintHole);
	        canvas.drawPath(pathEllipse, paintFocus);
	
	        if (cx < xBR/3.0f) {
				focusOutAnim = true;
			}
			cx += offx;
			if (cx > xBR || (cx < 0)){
				offx *= -1;
				cx += offx;
			}
			
			cy += offy;
			if (cy > yBR || (cy < 0)){
				offy *= -1;
				cy += offy;
			}
		}
		else {
			offc += 5.0f;
			canvas.drawColor(0xDD000000, PorterDuff.Mode.SRC);
			canvas.drawCircle(cx, cy, offc, paintHole);
			if (cx + offc > yBR) {
				thread.setRunning(false);
			}
		}
		//canvas.restore();
		//super.onDraw(canvas);
	}
}

