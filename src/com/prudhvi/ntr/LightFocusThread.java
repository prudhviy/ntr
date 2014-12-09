package com.prudhvi.ntr;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class LightFocusThread extends Thread {
	private SurfaceHolder myThreadSurfaceHolder;
	private LightFocus myThreadSurfaceView;
	private boolean myThreadRun = false;
	
	public LightFocusThread(SurfaceHolder surfaceHolder, LightFocus surfaceView) {
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
				sleep(0);
			}
			catch (InterruptedException e) {
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

