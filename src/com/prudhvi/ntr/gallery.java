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
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class gallery extends Activity {
	
	private static final int[] mImageIds = {
		R.drawable.ntr_icon,
		R.drawable.jrntr,
        R.drawable.ntr
    };
	private static int imagePosition = 0;
	private GestureDetector gestureDetector;
	private OnTouchListener gestureListener;
	private ImageView wrapper;
	
	@Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        imagePosition = 0;
        wrapper = (ImageView) findViewById(R.id.image_display);
        //wrapper.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
        
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
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (gestureDetector.onTouchEvent(event))
	        return true;
	    else
	    	return false;
    }

	// Second half rotation
	private final class ImageFlipSecondHalf implements AnimationListener {
		
		final private View view;
		final private float fromDegrees;
		final private float toDegrees;
		final private int count;
		
		private ImageFlipSecondHalf(View v, float fromD, float toD, int c) {
			view = v;
			fromDegrees = fromD;
			toDegrees = toD;
			count = c;
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			ImageView img = (ImageView) findViewById(R.id.image_display);
			final CustomAnim flip = new CustomAnim(fromDegrees, toDegrees, view.getWidth()/2.0f, view.getHeight()/2.0f, 150.0f, true);
			
			setImagePosition();
	    	img.setImageResource(mImageIds[imagePosition]);
	    	view.startAnimation(flip);
		}
		
		private void setImagePosition() {
			if((mImageIds.length - 1) == imagePosition) {
				if(count == 1){
					imagePosition = 0;
				}
				else{
					imagePosition = imagePosition + count;
				}
			}
			else if(imagePosition == 0) {
				if(count == -1){
					imagePosition = mImageIds.length - 1;
				}
				else{
					imagePosition = imagePosition + count;
				}
			}
			else {
				imagePosition = imagePosition + count;
			}
		}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	}
    
    private final class MyGestureDetector extends SimpleOnGestureListener {

    	private int SWIPE_MIN_DISTANCE_PX;
    	private int SWIPE_MAX_OFF_PATH_PX;
    	final int SWIPE_THRESHOLD_VELOCITY = 100;
    	
    	private MyGestureDetector() {
    	
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
   
                	final CustomAnim flip = new CustomAnim(0, -90, wrapper.getWidth()/2.0f, wrapper.getHeight()/2.0f, 150.0f, true);
        			wrapper.startAnimation(flip);
        	    	flip.setAnimationListener(new ImageFlipSecondHalf(wrapper, 90, 0, 1));
                }
                //left to right swipe  
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE_PX && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
   
                	final CustomAnim flip = new CustomAnim(0, 90, wrapper.getWidth()/2.0f, wrapper.getHeight()/2.0f, 150.0f, true);
        			wrapper.startAnimation(flip);
        	    	flip.setAnimationListener(new ImageFlipSecondHalf(wrapper, -90, 0, -1));
                }
            }
            catch (Exception e) {
                // nothing
            }
            return true;
        }
    }
    
	/*public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

		int mWidthPx;
		int mHeightPx;
		
	    public ImageAdapter(Context c) {
	    	DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			
			// The gesture threshold expressed in dp
			final float GALLERY_IMAGE_WIDTH_DP  = 150.0f;
			final float GALLERY_IMAGE_HEIGHT_DP = 100.0f;
			
			// Convert the dps to pixels
			final float scale = metrics.density;
			mWidthPx  = (int) ( GALLERY_IMAGE_WIDTH_DP  * scale + 0.5f );
			mHeightPx = (int) ( GALLERY_IMAGE_HEIGHT_DP * scale + 0.5f );
			
	        mContext = c;
	        TypedArray a = obtainStyledAttributes(R.styleable.HelloGallery);
	        mGalleryItemBackground = a.getResourceId(
	                R.styleable.HelloGallery_android_galleryItemBackground, 0);
	        a.recycle();
	    }

		public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView i = new ImageView(mContext);

	        i.setImageResource( mImageIds[position] );
	        i.setLayoutParams( new Gallery.LayoutParams( mWidthPx, mHeightPx ) );
	        i.setScaleType( ImageView.ScaleType.CENTER_INSIDE );
	        i.setBackgroundResource( mGalleryItemBackground );
	        return i;
	    }
	}*/
}

