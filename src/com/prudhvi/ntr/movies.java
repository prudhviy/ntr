package com.prudhvi.ntr;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class movies extends Activity {
	
	private ViewFlipper viewFlipper;
	private GestureDetector gestureDetector;
	private OnTouchListener gestureListener;
	
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
    private Animation slideRightOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies);
     	
     	slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
         
     	viewFlipper = (ViewFlipper)findViewById(R.id.flipper);
     	gestureDetector = new GestureDetector(new MyGestureDetector());
    	gestureListener = new OnTouchListener() {
             public boolean onTouch(View v, MotionEvent event) {
                 if (gestureDetector.onTouchEvent(event)) {
     				return true;
                 }
                 return false;
             }
         };
    }
    
    private final class MyGestureDetector extends SimpleOnGestureListener {

    	private int SWIPE_MIN_DISTANCE_PX;
    	private int SWIPE_MAX_OFF_PATH_PX;
    	final int SWIPE_THRESHOLD_VELOCITY = 100;
    	
    	private MyGestureDetector(){
    		final DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			
			// The swipe length expressed in dp
			final int SWIPE_MIN_DISTANCE_DP = 50;
		    final int SWIPE_MAX_OFF_PATH_DP = 200;
			
			// Convert the dps to pixels
			final float scale = metrics.density;
			SWIPE_MIN_DISTANCE_PX = (int) ( SWIPE_MIN_DISTANCE_DP  * scale + 0.5f );
			SWIPE_MAX_OFF_PATH_PX = (int) ( SWIPE_MAX_OFF_PATH_DP  * scale + 0.5f );
    	}
    	
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH_PX)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE_PX && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                	viewFlipper.showNext();
                }//swipe to right  
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE_PX && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                	viewFlipper.showPrevious();
                }
            } catch (Exception e) {
                // nothing
            }
            return true;
        }
        
        /*@Override
        public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        	Toast toast = Toast.makeText(getApplicationContext(), SWIPE_MIN_DISTANCE_PX + " " + distanceX, Toast.LENGTH_SHORT);
			toast.show();
        	try {
                // right to left swipe
                if(distanceX > SWIPE_MIN_DISTANCE_PX ) {
                	viewFlipper.setInAnimation(slideLeftIn);
                    viewFlipper.setOutAnimation(slideLeftOut);
                	viewFlipper.showNext();
                }//swipe to right  
                else if (distanceX < SWIPE_MIN_DISTANCE_PX && distanceX < 0 ) {
                	viewFlipper.setInAnimation(slideRightIn);
                    viewFlipper.setOutAnimation(slideRightOut);
                	viewFlipper.showPrevious();
                }
            } catch (Exception e) {
                // nothing
            }
            return true;
        }
        */
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (gestureDetector.onTouchEvent(event))
	        return true;
	    else
	    	return false;
    }
}