package ashok.subedi009.bhagchal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class BaagchalActivity extends Activity implements OnClickListener{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
              
final MyVariables var=new MyVariables();
Button b1;
final Button b2;
Button b3;
Button b4;
Button btnComputer;
var.setMyTest(false);
		
		b1=(Button) findViewById(R.id.play); 
		b1.setOnClickListener(new View.OnClickListener() {		
			
			@Override
			public void onClick(View arg0) {
				if(var.getMyTest()){
					MediaPlayer mp = MediaPlayer.create(BaagchalActivity.this, R.raw.beep);
					mp.start();					
				}
				var.setPlayMode(0);
				startActivity(new Intent("ashok.subedi009.bhagchal.MUSIC"));
				
				
				
			}
		});
		
		btnComputer=(Button) findViewById(R.id.playAsGoat); 
		btnComputer.setOnClickListener(new View.OnClickListener() {		
			
			@Override
			public void onClick(View arg0) {
				if(var.getMyTest()){
					MediaPlayer mp = MediaPlayer.create(BaagchalActivity.this, R.raw.beep);
					mp.start();					
				}
				var.setPlayMode(1);
				startActivity(new Intent("ashok.subedi009.bhagchal.MUSIC"));
				
				
			}
		});
		
		b3=(Button) findViewById(R.id.theme);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(var.getMyTest()){
					MediaPlayer mp = MediaPlayer.create(BaagchalActivity.this, R.raw.beep);
					mp.start();					
				}
				startActivity(new Intent("ashok.subedi009.bhagchal.THEME"));
				
			}
		});
		b2=(Button)findViewById(R.id.sound);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String hi = (String) b2.getText();
				if(hi=="Sound: On"){
					b2.setText("Sound: Off");
					var.setMyTest(false);
					}
				else{
					b2.setText("Sound: On");
					MediaPlayer mp = MediaPlayer.create(BaagchalActivity.this, R.raw.beep);
					mp.start();
					var.setMyTest(true);
					}						
			}
		});
		
		b4=(Button) findViewById(R.id.about);
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(var.getMyTest()){
					MediaPlayer mp = MediaPlayer.create(BaagchalActivity.this, R.raw.beep);
					mp.start();					
				}
				startActivity(new Intent("ashok.subedi009.bhagchal.HELP"));				
				
			}
		});
		
		
    }
	
	@Override
	public void onClick(View arg0) {
		
		
	}

	@Override
	    public void onBackPressed() {
		AlertDialog.Builder builder= new AlertDialog.Builder(BaagchalActivity.this);
		builder.setMessage("Are you sure you want to exit");
		builder.setCancelable(false);
		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				BaagchalActivity.this.finish();
				System.exit(0);
				
			}
		});
		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();					
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	    }	
}