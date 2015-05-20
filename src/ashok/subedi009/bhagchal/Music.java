package ashok.subedi009.bhagchal;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Music extends Activity{
	MediaPlayer themeSong;
	//private boolean myBool=true;
	MyVariables var=new MyVariables(); 
	boolean myBool=var.getMyTest();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music);
		 
		 if(myBool==true){
			 if(themeSong != null) {  
				 themeSong.reset();  
				 themeSong.release();  
		        }  
			 themeSong = MediaPlayer.create(Music.this, R.raw.sample); 
			 if(themeSong!=null){
				 themeSong.start();
			 }
	     }
	        
	        Thread myThread = new Thread(){
	        	public void run(){
	        		try{
	        			sleep(3200);
	        			Intent menuIntent = new Intent("ashok.subedi009.bhagchal.PLAY");
	        			startActivity(menuIntent);
	        			
	        		} 
	        		catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		finally{
	        			finish();
	        			
	        		}
	        	}
	        };
	        myThread.start();
	    }

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			if(myBool==true){
				if(themeSong != null) {  
					 themeSong.reset();  
					 themeSong.release();  
			        }
			}
		}	 
	}