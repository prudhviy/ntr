package com.prudhvi.ntr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

public class splash extends Activity {
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.splash );
        Thread welcomeThread = new Thread() {
     			@Override
     			public void run() {
     				try {
     					sleep( 1500 );
			        }
		            catch ( Exception e ) {
		                   //do nothing
			        } 
		            finally {
		            	finish();
		            	startActivity( new Intent( splash.this, home.class ) );
		            	overridePendingTransition( 0, R.anim.scale );
		            }
     			}
     	};
     	welcomeThread.start();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Refer http://android-developers.blogspot.com/2009/12/back-and-other-hard-keys-three-stories.html
        	// for back button events
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        return;
    }
    
    @Override
	public void onPause(){
		super.onPause();
	}
}

/*class SplashSreenTask extends AsyncTask<Integer,Void,Void> {
    protected Void onPostExecute() throws InterruptedException, ExecutionException, TimeoutException {
    	get (1500,TimeUnit.MILLISECONDS);
    	return null;
    }

	@Override
	protected Void doInBackground(Integer... params) {
		return null;
	}
}*/