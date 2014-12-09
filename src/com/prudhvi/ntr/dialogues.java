package com.prudhvi.ntr;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class dialogues extends Activity {
    /** Called when the activity is first created. */
	private static MediaPlayer mp  = new MediaPlayer();
	//private static final String TAG = "ntr";
	private int nowPlaying = 0;
	
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
     	setContentView( R.layout.dialogues );
     	setVolumeControlStream(AudioManager.STREAM_MUSIC);
     	
     	final Button bPlayDialogueBrundhavanam = ( Button ) findViewById( R.id.brundhavanam_listen );
     	bPlayDialogueBrundhavanam.setOnTouchListener( playDialogueListener );
     	
     	final Button bPlayDialogueYamadonga = ( Button ) findViewById( R.id.yamadonga_listen );
     	bPlayDialogueYamadonga.setOnTouchListener( playDialogueListener );
     	
     	final Button bPlayDialogueYamadonga2 = ( Button ) findViewById( R.id.yamadonga_emantivi_listen );
     	bPlayDialogueYamadonga2.setOnTouchListener( playDialogueListener );
     	
     	final Button bPlayDialogueAadi1 = ( Button ) findViewById( R.id.aadi_ammathodu );
     	bPlayDialogueAadi1.setOnTouchListener( playDialogueListener );
     	
     	final Button bPlayDialogueAadi2 = ( Button ) findViewById( R.id.aadi_magadu );
     	bPlayDialogueAadi2.setOnTouchListener( playDialogueListener );
     	
     	final Button bPlayDialogueRakhi = ( Button ) findViewById( R.id.rakhi_train );
     	bPlayDialogueRakhi.setOnTouchListener( playDialogueListener );
     	
    }
    private final OnTouchListener playDialogueListener = new OnTouchListener() {
    	@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				
				int dialogue_resource = R.id.brundhavanam_listen;
				
				if(nowPlaying != v.getId()){
					if(nowPlaying != 0){
						Button b = (Button) findViewById(nowPlaying);
						b.setBackgroundResource(R.drawable.play_default_background);
					}
				}
				switch(v.getId()){
					case R.id.brundhavanam_listen : dialogue_resource = R.raw.brundhavanam;break;
					case R.id.yamadonga_listen : dialogue_resource = R.raw.yamadonga_puli;break;
					case R.id.yamadonga_emantivi_listen : 	dialogue_resource = R.raw.yamadonga_emantivi;break;
					case R.id.aadi_ammathodu : dialogue_resource = R.raw.aadi_ammathodu;break;
					case R.id.aadi_magadu : dialogue_resource = R.raw.aadi_magadu;break;
					case R.id.rakhi_train : dialogue_resource = R.raw.rakhi_train;break;
					default : dialogue_resource = R.raw.brundhavanam;break;
				}
				
				nowPlaying = v.getId();
				Button bt = (Button) findViewById(nowPlaying);
				bt.setBackgroundResource(R.drawable.shape1);
				
				mp.release();
				mp = MediaPlayer.create(getBaseContext(), dialogue_resource);
				mp.start();
				mp.setVolume(1.0f, 1.0f);
				mp.setOnCompletionListener( playerRelease );
				
	    	 }
			return true;
		}
    };
    
    private final OnCompletionListener playerRelease = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp){
			mp.release();
		}
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		mp.release();
	}
	
}