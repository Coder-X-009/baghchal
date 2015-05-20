package ashok.subedi009.bhagchal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Theme extends Activity implements OnClickListener{
	ImageView displayTiger,displayBoard,displayGoat;
	final MyVariables var=new MyVariables();
	int colo=Color.WHITE;
	int tgImage=R.drawable.tiger1;//default tiger image
	int gtImage=R.drawable.goat1;//default goat image

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.theme);
		//for display of selected things
		displayTiger = (ImageView) findViewById(R.id.tiger);
		displayBoard = (ImageView) findViewById(R.id.theme);
		displayGoat = (ImageView) findViewById(R.id.goat );
		// to choose theme
		ImageView board1=(ImageView) findViewById(R.id.theme1);
		ImageView board2=(ImageView) findViewById(R.id.theme2);
		ImageView board3=(ImageView) findViewById(R.id.theme3);
		ImageView board4=(ImageView) findViewById(R.id.theme4);
		ImageView board5=(ImageView) findViewById(R.id.theme5);
		ImageView board6=(ImageView) findViewById(R.id.theme6);
		//to choose tiger and goat
		ImageView tiger1=(ImageView) findViewById(R.id.tiger1);
		ImageView tiger2=(ImageView) findViewById(R.id.tiger2);
		ImageView tiger3=(ImageView) findViewById(R.id.tiger3);
		ImageView goat1=(ImageView) findViewById(R.id.goat1);
		ImageView goat2=(ImageView) findViewById(R.id.goat2);
		ImageView goat3=(ImageView) findViewById(R.id.goat3);
		
		Button setTheme = (Button) findViewById(R.id.themeButton);
		Button play2 = (Button) findViewById(R.id.themeButton2);
		
		board1.setOnClickListener(this);
		board2.setOnClickListener(this);
		board3.setOnClickListener(this); 
		board4.setOnClickListener(this);
		board5.setOnClickListener(this);
		board6.setOnClickListener(this);
		tiger1.setOnClickListener(this);
		tiger2.setOnClickListener(this);
		tiger3.setOnClickListener(this);		
		goat1.setOnClickListener(this);
		goat2.setOnClickListener(this);
		goat3.setOnClickListener(this);
		setTheme.setOnClickListener(this);
		play2.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()){
		case R.id.theme1:
			displayBoard.setImageResource(R.drawable.board1);
			colo=Color.WHITE;
			break;
		case R.id.theme2:
			displayBoard.setImageResource(R.drawable.board2);
			colo=Color.rgb(255, 153, 204);
			break;
		case R.id.theme3:
			displayBoard.setImageResource(R.drawable.board3);
			colo=Color.rgb(177, 255, 61);
			break;
		case R.id.theme4:
			displayBoard.setImageResource(R.drawable.board4);
			colo=Color.rgb(194, 194, 194);
			break;
		case R.id.theme5:
			displayBoard.setImageResource(R.drawable.board5);
			colo=Color.rgb(255, 133, 10);
			break;
		case R.id.theme6:
			displayBoard.setImageResource(R.drawable.board6);
			colo=Color.rgb(163, 71, 255);
			break;
		case R.id.tiger1:
			displayTiger.setImageResource(R.drawable.tiger1);
			tgImage=R.drawable.tiger1;
			break;
		case R.id.tiger2:
			displayTiger.setImageResource(R.drawable.tiger2);
			tgImage=R.drawable.tiger2;
			break;
		case R.id.tiger3:
			displayTiger.setImageResource(R.drawable.tiger3);
			tgImage=R.drawable.tiger3;
			break;		
		case R.id.goat1:
			displayGoat.setImageResource(R.drawable.goat1);
			gtImage=R.drawable.goat1;
			break;
		case R.id.goat2:
			displayGoat.setImageResource(R.drawable.goat2);
			gtImage=R.drawable.goat2;
			break;
		case R.id.goat3:
			displayGoat.setImageResource(R.drawable.goat3);
			gtImage=R.drawable.goat3;
			break;

		case R.id.themeButton:   
			var.setColor(colo);//save the theme selected
			var.setMyGoat(gtImage);
			var.setMyTiger(tgImage);
			if(var.getMyTest()){
				MediaPlayer mp = MediaPlayer.create(Theme.this, R.raw.beep);
				mp.start();					
			}
			var.setPlayMode(0); //Multiplayer
			startActivity(new Intent("ashok.subedi009.bhagchal.MUSIC")); 			
			break;
			
		case R.id.themeButton2:
			var.setColor(colo);//save the theme selected
			var.setMyGoat(gtImage);
			var.setMyTiger(tgImage);
			if(var.getMyTest()){
				MediaPlayer mp = MediaPlayer.create(Theme.this, R.raw.beep);
				mp.start();					
			}
			var.setPlayMode(1);// vs CPU
			startActivity(new Intent("ashok.subedi009.bhagchal.MUSIC")); 			
			break;
			
			 
		}
		
	}
	

}
