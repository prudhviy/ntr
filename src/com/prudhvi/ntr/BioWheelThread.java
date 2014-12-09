package com.prudhvi.ntr;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class BioWheelThread extends Thread {
	private SurfaceHolder myThreadSurfaceHolder;
	private BioWheel myThreadSurfaceView;
	private boolean myThreadRun = false;
	
	public BioWheelThread(SurfaceHolder surfaceHolder, BioWheel surfaceView) {
		myThreadSurfaceHolder = surfaceHolder;
		myThreadSurfaceView = surfaceView;
	}
	
	public void setRunning(boolean b) {
		myThreadRun = b;
	}

	@Override
	public void run() {
		while(myThreadRun) {
			Canvas c = null;
			try {
				c = myThreadSurfaceHolder.lockCanvas(null);
				synchronized (myThreadSurfaceHolder) {
					myThreadSurfaceView.onDraw(c);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					myThreadSurfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
