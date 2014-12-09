package com.prudhvi.ntr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerCloseListener;

public class home extends Activity {
	//private Handler h = new Handler();
	@SuppressWarnings("unchecked")
	protected static final Class[] activityList = {
        pictureCube.class,
        dialogues.class,
        movies.class,
        gallery.class,
        Biography.class
	};

	@Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
     	setContentView( R.layout.main );
     	/*final ImageView menuButton = ( ImageView ) findViewById( R.id.menu_button );
        menuButton.setOnClickListener( menuListener );
        Runnable r = new Runnable() {
			public void run() {
				menuButton.setVisibility(View.VISIBLE);
			}
		};
        h.postDelayed(r,1500);
        */
     	final ImageView bMovies = ( ImageView ) findViewById( R.id.movies );
        bMovies.setOnClickListener( new ButtonClick(2) );
        
        final ImageView bBiography = ( ImageView ) findViewById( R.id.biography );
        bBiography.setOnClickListener( new ButtonClick(4) );
        
        final ImageView bPictures = ( ImageView ) findViewById( R.id.pictures );
        bPictures.setOnClickListener( new ButtonClick(3) );

        final ImageView bDialogues = ( ImageView ) findViewById( R.id.dialogues );
        bDialogues.setOnClickListener( new ButtonClick(1) );
        
        final ImageView bVideos = ( ImageView ) findViewById( R.id.videos );
        bVideos.setOnClickListener( new ButtonClick(0) );
        
        final SlidingDrawer drawer = ( SlidingDrawer ) findViewById( R.id.drawer );
		drawer.setOnDrawerOpenListener( drawerOpen );
        drawer.setOnDrawerCloseListener( drawerClose );
        
    }

	private final OnDrawerOpenListener drawerOpen = new OnDrawerOpenListener() {
		@Override
		public void onDrawerOpened() {
			final LinearLayout drawerContainer = ( LinearLayout ) findViewById( R.id.drawerContainer );
			drawerContainer.setBackgroundColor(0xAA000000);
		}
	};
	
	private final OnDrawerCloseListener drawerClose = new OnDrawerCloseListener() {
		@Override
		public void onDrawerClosed() {
			final LinearLayout drawerContainer = ( LinearLayout ) findViewById( R.id.drawerContainer );
			drawerContainer.setBackgroundColor(0x00000000);
		}
	};

	private Animation menu_icon_animation(View v){
    	Animation icon_anim = AnimationUtils.loadAnimation(home.this, R.anim.menu_icon_bulge);
        v.startAnimation(icon_anim);        
        return icon_anim;
    }

	private final class ButtonClick implements OnClickListener{
		private final int activityPosition;
		private ButtonClick(int position){
			activityPosition = position;
		}
		@Override
		public void onClick(View v) {
			Animation anim;
        	anim = menu_icon_animation(v);
        	anim.setAnimationListener( new NextActivity(activityPosition));
		}
		
	}

	private final class NextActivity implements Animation.AnimationListener {
        private final int activityPosition;

        private NextActivity(int position) {
            activityPosition = position;
        }
        @Override
        public void onAnimationStart(Animation animation) {}
        @Override
        public void onAnimationEnd(Animation animation) {
        	startActivity( new Intent( home.this, activityList[activityPosition] ) );
			overridePendingTransition( R.anim.hold, R.anim.fade );
        }
        @Override
        public void onAnimationRepeat(Animation animation) {}
    }
	
	/*  
    @Override
    public void onStart()
    {
    	super.onStart();
    	CharSequence text = "in start";
    	int duration = Toast.LENGTH_SHORT;
    	Toast toast = Toast.makeText(getApplicationContext(), text, duration);
    	toast.show();
    }

    private OnClickListener menuListener = new OnClickListener()
    {
        public void onClick( View v ) {
        	startActivity( new Intent( home.this, menu.class ) );
            overridePendingTransition( R.anim.hold, R.anim.fade );
        }
    };
    */
}