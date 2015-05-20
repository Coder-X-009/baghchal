package ashok.subedi009.bhagchal;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class Play extends Activity implements OnTouchListener {
	OurView v;
	Bitmap tiger,goat,wallPaper,winner,winner2;
	float x, y;
	int canvasH,oldX,oldY,newX,newY,currentGotti,badUp;//badUp to check if move is invalid
	int[][] node  = new int[5][5];
	int[][]nodeColor = new int[5][5];
	int[][] nodeX = new int[5][5];
	int[][] nodeY  = new int[5][5];
	int turn,goatLeft,goatKilled,testGoatUp,tigerTrapped,fromTiger;//testGoatUp to keep inserting more goats on board.
	boolean loadSaver=true;
	boolean error=false;//to print error message for invalid move.
	Canvas c;Paint myP;
	int test1,test2;//these are OOighat variables to be deleted.
	//getting the theme data
	MyVariables var=new MyVariables();
	int themeColor=var.getColor();
	boolean soundHere=var.getMyTest();//to check if sound is on or off.
	//Getting the sounds
	MediaPlayer tgSound,gtSound,gtKilled,tgWin;
	
	int playMode = var.getPlayMode();
	
	private int mWidth;
	private int mHeight;
	private float mAngle;
			 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		v = new OurView(this);
		v.setOnTouchListener(this);
		tiger=BitmapFactory.decodeResource(getResources(),var.getMyTiger());
		goat=BitmapFactory.decodeResource(getResources(),var.getMyGoat());
		wallPaper=BitmapFactory.decodeResource(getResources(), R.drawable.mypic);
		winner=BitmapFactory.decodeResource(getResources(), R.drawable.tigerwins);
		winner2=BitmapFactory.decodeResource(getResources(), R.drawable.goatwins);
		x=y=20;
		for (int i=0;i<=4;i++){
			for (int j=0;j<=4;j++){
				node[i][j]=0;
				nodeColor[i][j]=0;
				}
			}
		node[0][0]=2;node[0][4]=2;node[4][0]=2;node[4][4]=2;//to fill corner four nodes with tigers
		badUp=0;turn=1;goatLeft=20;goatKilled=0;tigerTrapped=0;fromTiger=0;		
		tgSound=MediaPlayer.create(Play.this, R.raw.tigersoundi);//initializing sounds
		tgWin=MediaPlayer.create(Play.this, R.raw.tigerwin);
		gtSound=MediaPlayer.create(Play.this, R.raw.goatsound); 
		gtKilled=MediaPlayer.create(Play.this, R.raw.goatkilled); 
		myP=new Paint();
		
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
		    Display display = getWindowManager().getDefaultDisplay();
		    mWidth = display.getWidth();
		    mHeight = display.getHeight();
		} else {
		    Display display = getWindowManager().getDefaultDisplay();
		    Point size = new Point();
		    display.getSize(size);
		    mWidth = size.x;
		    mHeight = size.y;
		}
		
		test2 = mWidth;
		myP.setTextSize(test2/25);
		myP.setColor(Color.RED);
		setContentView(v);
		
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		v.pause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		v.resume();  
	}
	public class OurView extends SurfaceView implements Runnable{
		Thread t=null;
		SurfaceHolder holder;
		boolean isItOk = false;
		
		public OurView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			holder=getHolder();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isItOk==true){ 
				//perform canvas drawing
				if(!holder.getSurface().isValid()){
					continue;
				}
				int a=30,b=30;
				c=holder.lockCanvas();
				c.drawColor(themeColor);
				c.drawBitmap(wallPaper, canvasH+40, 10, null);
				int len=c.getHeight()-(b+b);
				canvasH=len;
				Paint p=new Paint();
				Paint p2 = new Paint();
				p2.setColor(Color.GREEN);
				p2.setStrokeWidth(4);
				p.setStrokeWidth(4);
				
				
				//drawing five vertical lines
				c.drawLine(a, b, a,len+b , p);				
				
				c.drawLine(len/4+a, b, len/4+a, len+b, p);
				c.drawLine(len/2+a, b, len/2+a, len+b, p);
				c.drawLine(3*len/4+a, b,3*len/4+a, len+b, p);
				c.drawLine(len+a, b,len+a, len+b, p);
				//drawing five horizontal lines
				c.drawLine(a, b,len+a,b , p);
				c.drawLine(a, len/4+b,len+a,len/4+b , p);
				c.drawLine(a, len/2+b,len+a,len/2+b , p);
				c.drawLine(a, 3*len/4+b,len+a,3*len/4+b , p);
				c.drawLine(a, len+b,len+a,len+b , p);
				//drawing four diagonal lines 
				c.drawLine(a, len/2+b, len/2+a, b, p);
				c.drawLine(len/2+a, b, len+a, len/2+b, p);
				c.drawLine(len+a, len/2+b, len/2+a, len+b, p);
				c.drawLine(len/2+a, len+b, a, len/2+b, p);
				
				c.drawLine(a, b, len+a,len+b , p);
				c.drawLine(a,len+b, len+a,b , p);
				
				//saving the position of node to an array
				if(loadSaver==true){
				int increaseX=a;
				int increaseY=b;
				for (int i=0;i<=4;i++){
					for (int j=0;j<=4;j++){
						nodeX[i][j]=increaseX;
						nodeY[i][j]=increaseY;
						increaseX+=canvasH/4;
					}
					increaseX=a;
					increaseY+=canvasH/4;
				}
				loadSaver=false;
				}
				//drawing tigers or goats on the board
				for(int k=0;k<=4;k++){
					for(int l=0;l<=4;l++){
						String hi;
						if(turn==1) hi="Goat"; else hi="Tiger";
						c.drawText(""+hi+"'s Turn", canvasH+100, 90, myP);
						c.drawText("Goat Left "+goatLeft+"  ", 0, 12, canvasH+100, 140, myP);
						c.drawText("Goat Killed "+goatKilled+"  ", 0, 13, canvasH+100, 200, myP);
						c.drawText("Tiger Trapped "+tigerTrapped+"   ", 0, 15, canvasH+100, 260, myP);
						
						if(error==true){
							c.drawText("Invalid move!!", canvasH+100, 30, myP);							
						}
						if(node[k][l]==1){
							c.drawBitmap(goat, nodeX[k][l]-(goat.getWidth()/2), nodeY[k][l]-(goat.getHeight()/2-5), null);							
						}
						if(node[k][l]==2){
							c.drawBitmap(tiger, nodeX[k][l]-(tiger.getWidth()/2), nodeY[k][l]-(tiger.getHeight()/2-5), null);
						}
						if(nodeColor[k][l]==1){
							Paint greenPaint = new Paint();
							greenPaint.setColor(Color.GREEN);							
							RectF rect = new RectF(nodeX[k][l]-(tiger.getWidth()/2)-5, nodeY[k][l]-(tiger.getHeight()/2)-5,nodeX[k][l]+(tiger.getWidth()/2)+5, nodeY[k][l]+(tiger.getHeight()/2+10));  
							greenPaint.setStyle(Paint.Style.STROKE);
							greenPaint.setStrokeWidth(3);
							c.drawRect(rect, greenPaint); 													
						}
						if(nodeColor[k][l]==2){
							Paint redPaint = new Paint();
							redPaint.setColor(Color.RED);
							RectF rect = new RectF(nodeX[k][l]-(tiger.getWidth()/2)-5, nodeY[k][l]-(tiger.getHeight()/2)-5,nodeX[k][l]+(tiger.getWidth()/2)+5, nodeY[k][l]+(tiger.getHeight()/2+10));  
							redPaint.setStyle(Paint.Style.STROKE);
							redPaint.setStrokeWidth(3);
							c.drawRect(rect, redPaint);							
						}
																
					}
				}
				if(test1==5){					
					callMe(4000);
					Intent b1 = new Intent(Play.this,BaagchalActivity.class);
					b1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(b1);
					finish();
				}
				if(goatKilled==5){
					int cx = (mWidth - winner.getWidth()) >> 1; // same as (...) / 2
				    int cy = (mHeight - winner.getHeight()) >> 1;
					c.drawBitmap(winner, cx, cy, myP);
					test1=5;goatKilled=0;
				}
				if(tigerTrapped==4){
					int cx = (mWidth - winner2.getWidth()) >> 1; // same as (...) / 2
				    int cy = (mHeight - winner2.getHeight()) >> 1;
					c.drawBitmap(winner2, cx, cy, myP);
					test1=5;
				}
				holder.unlockCanvasAndPost(c);				
			}
			
		}
		public void pause(){
	
			isItOk=false;
			while(true){
				try{
					t.join();
				}
				catch(InterruptedException e){
					e.printStackTrace();
					}
				break;
			}
			t=null;
		}
		public void resume(){
			isItOk=true;
			t = new Thread(this);
			t.start();
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		
		// TODO Auto-generated method stub
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(me.getAction()){
		case MotionEvent.ACTION_DOWN:
			x=me.getX();
			y=me.getY();
			currentGotti=0;
			testGoatUp=0;
			for (int i=0;i<=4;i++){
				for (int j=0;j<=4;j++){										
					float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
					if((x<(nodeX[i][j]+pixels))&&(x>(nodeX[i][j]-pixels))&&(y<(nodeY[i][j]+pixels))&&(y>nodeY[i][j]-pixels)){
						if((node[i][j]==2)&&(turn==2)){
							oldX=i;	oldY=j;
							node[i][j]=0;
							currentGotti=2;
							break;
						}
						if((goatLeft>=1)&&(turn==1)&&(node[i][j]==0)){
							oldX=i;	oldY=j;
							testGoatUp=1;
							}
						if((node[i][j]==1)&&(turn==1)&&(goatLeft==0)){
							oldX=i;	oldY=j;
							node[i][j]=0;
							currentGotti=1;
							break;
						}
						if((node[i][j]==1)&&(playMode==1)){ // when player is playing as tiger
							
						}
						
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			x=me.getX();
			y=me.getY();
			badUp=0;
			test1=0;
			error=false;
			for (int i=0;i<=4;i++){
				for (int j=0;j<=4;j++){
					float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
					if((x<(nodeX[i][j]+pixels))&&(x>(nodeX[i][j]-pixels))&&(y<(nodeY[i][j]+pixels))&&(y>nodeY[i][j]-pixels)){
						if((node[i][j]==0)&&(currentGotti==2)){//IF target node doesn't contain any gotties.
								newX=i;	newY=j;
								if(moveChecker(oldX,oldY,newX,newY)==true){//function call for legal moves.
									badUp=1;//badUp equals one for every legal moves
									node[i][j]=2;
									turn=1;
									if(soundHere){tgSound.start();} 
									tigerTrapped();									
									break;
								}
							}
							if(testGoatUp==1){
								goatLeft-=1;badUp=1;
								node[i][j]=1;								
								if(soundHere){gtSound.start();}
								tigerTrapped();
								clearColor();
								if(playMode==1){									
									tigerMoveByComputer();
								}else{
									turn=2;
								}
								
							}
							else{						
							if((node[i][j]==0)&&(currentGotti==1)){						
								newX=i;	newY=j;								
								if(moveChecker(oldX,oldY,newX,newY)==true){
									badUp=1;//badUp equals one for every legal moves
									node[i][j]=1 ;									
									if(soundHere){gtSound.start();}
									tigerTrapped();
									clearColor();
									if(playMode==1){
										tigerMoveByComputer();
									}else{
										turn=2;
									}
									
									break;
								}
								
							}
						}	
					}
				}
			}	
		if((!(badUp==1))&&(currentGotti==2)){
			node[oldX][oldY]=2;
			error=true;
			}
		if((!(badUp==1))&&(currentGotti==1)){
			node[oldX][oldY]=1;
			error=true;
			}			
			break;
		case MotionEvent.ACTION_MOVE:
			x=me.getX();
			y=me.getY();
			}
		return true;
	}
	private void clearColor() {
		// TODO Auto-generated method stub
		for (int i=0;i<=4;i++){
			for (int j=0;j<=4;j++){
				nodeColor[i][j]=0;
				
			}
		}
		
	}



	private void tigerMoveByComputer() {
		for (int i=0;i<=4;i++){
			for (int j=0;j<=4;j++){
				int mark=0;
				if(node[i][j]==2){					
					if(i+2<=4){
						if(node[i+1][j]==1 && node[i+2][j]==0){
							node[i][j]=0;
							node[i+1][j]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[i+2][j]=2;	
							nodeColor[i][j]=1;
							nodeColor[i+2][j]=2;
							return;
						}
					}
					if(i-2>=0){
						if(node[i-1][j]==1 && node[i-2][j]==0){
							node[i][j]=0;
							node[i-1][j]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[i-2][j]=2;
							nodeColor[i][j]=1;
							nodeColor[i-2][j]=2;
							return;
						}
					}
					if(j+2<=4){
						if(node[i][j+1]==1 && node[i][j+2]==0){
							node[i][j]=0;
							node[i][j+1]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[i][j+2]=2;
							nodeColor[i][j]=1;
							nodeColor[i][j+2]=2;
							return;
						}
					}
					if(j-2>=0){
						if(node[i][j-1]==1 && node[i][j-2]==0){
							node[i][j]=0;
							node[i][j-1]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[i][j-2]=2;
							nodeColor[i][j]=1;
							nodeColor[i][j-2]=2;
							return;
						}
					}
					
					//for rombus moves
					if(i==2 && j==0){
						if(node[1][1]==1 && node[0][2]==0){
							node[i][j]=0;
							node[1][1]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[0][2]=2;
							nodeColor[i][j]=1;
							nodeColor[0][2]=2;
							return;
							
						}
						if(node[3][1]==1 && node[4][2]==0){
							node[i][j]=0;
							node[3][1]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[4][2]=2;
							nodeColor[i][j]=1;
							nodeColor[4][2]=2;
							return;
							
						}
					}
					if(i==0 && j==2){
						if(node[1][1]==1 && node[2][0]==0){
							node[i][j]=0;
							node[1][1]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[2][0]=2;
							nodeColor[i][j]=1;
							nodeColor[2][0]=2;
							return;
							
						}
						if(node[1][3]==1 && node[2][4]==0){
							node[i][j]=0;
							node[1][3]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[2][4]=2;
							nodeColor[i][j]=1;
							nodeColor[2][4]=2;
							return;
							
						}
					}
					if(i==4 && j==2){
						if(node[3][1]==1 && node[2][0]==0){
							node[i][j]=0;
							node[3][1]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[2][0]=2;
							nodeColor[i][j]=1;
							nodeColor[2][0]=2;
							return;
							
						}
						if(node[3][3]==1 && node[2][4]==0){
							node[i][j]=0;
							node[3][3]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[2][4]=2;
							nodeColor[i][j]=1;
							nodeColor[2][4]=2;
							return;
							
						}
						
					}
					if(i==2 && j==4){
						if(node[3][3]==1 && node[4][2]==0){
							node[i][j]=0;
							node[3][3]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[4][2]=2;
							nodeColor[i][j]=1;
							nodeColor[4][2]=2;
							return;
							
						}
						if(node[1][3]==1 && node[0][2]==0){
							node[i][j]=0;
							node[1][3]=0;
							goatKilled++;
							if(soundHere)gtKilled.start();
							Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
							v.vibrate(100);
							showToast();
							node[0][2]=2;
							nodeColor[i][j]=1;
							nodeColor[0][2]=2;
							return;							
						}						
					}	
					// for other diagonal
					if(i==j){
						if(i+2<=4){
							if(node[i+1][j+1]==1 && node[i+2][j+2]==0){
								node[i][j]=0;
								node[i+1][j+1]=0;
								goatKilled++;
								if(soundHere)gtKilled.start();
								Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
								v.vibrate(100);
								showToast();
								node[i+2][j+2]=2;
								nodeColor[i][j]=1;
								nodeColor[i+2][j+2]=2;
								return;								
							}						
						}
						if(i-2>=0){
							if(node[i-1][j-1]==1 && node[i-2][j-2]==0){
								node[i][j]=0;
								node[i-1][j-1]=0;
								goatKilled++;
								if(soundHere)gtKilled.start();
								Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
								v.vibrate(100);
								showToast();
								node[i-2][j-2]=2;
								nodeColor[i][j]=1;
								nodeColor[i-2][j-2]=2;
								return;								
							}						
						}
					}
					
					if (i+j==4){
						if(i-2>=0){
							if(node[i-1][j+1]==1 && node[i-2][j+2]==0){
								node[i][j]=0;
								node[i-1][j+1]=0;
								goatKilled++;
								if(soundHere)gtKilled.start();
								Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
								v.vibrate(100);
								showToast();
								node[i-2][j+2]=2;
								nodeColor[i][j]=1;
								nodeColor[i-2][j+2]=2;
								return;							
							}						
						}
						if(i+2<=4){
							if(node[i+1][j-1]==1 && node[i+2][j-2]==0){
								node[i][j]=0;
								node[i+1][j-1]=0;
								goatKilled++;
								if(soundHere)gtKilled.start();
								Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
								v.vibrate(100);
								showToast();
								node[i+2][j-2]=2;
								nodeColor[i][j]=1;
								nodeColor[i+2][j-2]=2;
								return;							
							}
						}
					}
				}
			}
		}
		for (int i=0;i<=4;i++){
			for (int j=0;j<=4;j++){
				int mark=0;
				if(node[i][j]==2){
					
					//if No goat Killed
					int nextX=-1, nextY=-1, goatKilled = 0;
					int nX=-1, nY=-1;
					if(moveChecker2(i, j, i-1, j-1)){
						if(noOfGoatKilled(i-1, j-1)>goatKilled){
							goatKilled = noOfGoatKilled(i-1, j-1);
							nextX = i-1; 
							nextY=j-1;							
						}						
						
					}
					if(moveChecker2(i, j, i-1, j)){
						if(noOfGoatKilled(i-1, j)>goatKilled){
							goatKilled = noOfGoatKilled(i-1, j);
							nextX = i-1; 
							nextY=j;							
						}						
					}
					if(moveChecker2(i, j, i-1, j+1)){
						if(noOfGoatKilled(i-1, j+1)>goatKilled){
							goatKilled = noOfGoatKilled(i-1, j+1);
							nextX = i-1; 
							nextY=j+1;							
						}
						
					}
					if(moveChecker2(i, j, i, j-1)){
						if(noOfGoatKilled(i, j-1)>goatKilled){
							goatKilled = noOfGoatKilled(i, j-1);
							nextX = i; 
							nextY=j-1;							
						}
						
					}
					if(moveChecker2(i, j, i, j+1)){
						if(noOfGoatKilled(i, j+1)>goatKilled){
							goatKilled = noOfGoatKilled(i, j+1);
							nextX = i; 
							nextY=j+1;							
						}
						
					}
					if(moveChecker2(i, j, i+1, j-1)){
						if(noOfGoatKilled(i+1, j-1)>goatKilled){
							goatKilled = noOfGoatKilled(i+1, j-1);
							nextX = i+1; 
							nextY=j-1;							
						}
						
					}
					if(moveChecker2(i, j, i+1, j)){
						if(noOfGoatKilled(i+1, j)>goatKilled){
							goatKilled = noOfGoatKilled(i+1, j);
							nextX = i+1; 
							nextY=j;							
						}
						
					}
					if(moveChecker2(i, j, i+1, j+1)){
						if(noOfGoatKilled(i+1, j+1)>goatKilled){
							goatKilled = noOfGoatKilled(i+1, j+1);
							nextX = i+1; 
							nextY=j+1;							
						}
						
					}
					
					if(nextX!=-1){
						node[i][j]=0;
						node[nextX][nextY]=2;
						nodeColor[i][j]=1;
						nodeColor[nextX][nextY]=2;
						return;						
					}
					
				}
			}
		}
		//else randomly move tiger
		ArrayList<int[]> action = new ArrayList<int[]>();
		for (int i=0;i<=4;i++){
			for (int j=0;j<=4;j++){				
				if(node[i][j]==2){
					for (int k=0;k<=4;k++){
						for(int l=0;l<=4;l++){
							if(moveChecker2(i, j, k, l)){
								action.add(new int[]{i,j,k,l});								
							}
						}
						
					}
					
				}
			}
		}
		
		int index = action.size();
		Random rand = new Random();
		if(index>0){
			int w = 0,x = 0,y = 0,z = 0;
			for(int i=0; i<=2;i++){
				
		int arrayIndex = rand.nextInt(index);
		w = action.get(arrayIndex)[0];
		x = action.get(arrayIndex)[1];
		y = action.get(arrayIndex)[2];
		z = action.get(arrayIndex)[3];
		if(y==0 || z==0){
			node[w][x]=0;
			node[y][z]=2;
			nodeColor[w][x]=1;
			nodeColor[y][z]=2;
		return;	}
		}
			node[w][x]=0;
			node[y][z]=2;
			nodeColor[w][x]=1;
			nodeColor[y][z]=2;
		return;
			
		}
		
	}
	
	public int noOfGoatKilled(int i, int j){
		int goatKilled =0;
		if(i+2<=4){
			if(node[i+1][j]==1 && node[i+2][j]==0){
				goatKilled++;
			}
		}
		if(i-2>=0){
			if(node[i-1][j]==1 && node[i-2][j]==0){
				goatKilled++;
			}
		}
		if(j+2<=4){
			if(node[i][j+1]==1 && node[i][j+2]==0){
				goatKilled++;
			}
		}
		if(j-2>=0){
			if(node[i][j-1]==1 && node[i][j-2]==0){
				goatKilled++;
			}
		}
		
		//for rombus moves
		if(i==2 && j==0){
			if(node[1][1]==1 && node[0][2]==0){
				goatKilled++;
				
			}
			if(node[3][1]==1 && node[4][2]==0){
				goatKilled++;
				
			}
		}
		if(i==0 && j==2){
			if(node[1][1]==1 && node[2][0]==0){
				goatKilled++;
				
			}
			if(node[1][3]==1 && node[2][4]==0){
				goatKilled++;
			}
		}
		if(i==4 && j==2){
			if(node[3][1]==1 && node[2][0]==0){
				goatKilled++;
				
			}
			if(node[3][3]==1 && node[2][4]==0){
				goatKilled++;
				
			}
			
		}
		if(i==2 && j==4){
			if(node[3][3]==1 && node[4][2]==0){
				goatKilled++;
				
			}
			if(node[1][3]==1 && node[0][2]==0){
				goatKilled++;						
			}						
		}	
		// for other diagonal
		if(i==j){
			if(i+2<=4){
				if(node[i+1][j+1]==1 && node[i+2][j+2]==0){
					goatKilled++;								
				}						
			}
			if(i-2>=0){
				if(node[i-1][j-1]==1 && node[i-2][j-2]==0){
					goatKilled++;								
				}						
			}
		}
		
		if (i+j==4){
			if(i-2>=0){
				if(node[i-1][j+1]==1 && node[i-2][j+2]==0){
					goatKilled++;							
				}						
			}
			if(i+2<=4){
				if(node[i+1][j-1]==1 && node[i+2][j-2]==0){
					goatKilled++;							
				}
			}
		}
		return goatKilled;
	}
	
	public void tigerTrapped(){
		tigerTrapped=0;
		for (int i=0;i<=4;i++){
			for (int j=0;j<=4;j++){
				int mark=0;
				if(node[i][j]==2){
					int sum=i+j;int diff=absolute(i-j);
					if((i+1<=4 && node[i+1][j]==0)||(i-1>=0 && node[i-1][j]==0)||(j+1<=4 && node[i][j+1]==0)||(j-1>=0 && node[i][j-1]==0)){
						mark=1;
					}
					if((i+2<=4 && node[i+1][j]==1 && node[i+2][j]==0)||(i-2>=0 && node[i-1][j]==1 && node[i-2][j]==0)
							||(j+2<=4 && node[i][j+1]==1 && node[i][j+2]==0)||(j-2>=0 && node[i][j-1]==1 && node[i][j-2]==0)){
						mark=1;
					}
				//for rombus trap
					if(sum==2||diff==2||i*j==9){
						for(int k=0;k<=4;k++){
								for(int l=0;l<=4;l++){
									int s=k+l;int d=absolute(k-l);
									if(s==2||d==2||k*l==9){
										if(((absolute(i-k)+absolute(j-l))==2) && node[k][l]==0){
											mark=1;
										}
										if((i==l||j==k)&&(node[(i+k)/2][(j+l)/2]==1)&&(node[k][l]==0)){
											mark=1;
										}
										
									}
								}
							}						
						}
					//for diagonal trap
					if(sum==4||i==j){
						for(int k=0;k<+4;k++){
							for(int l=0;l<=4;l++){
								if((k==l&&i==j)||(k+l==4&&i+j==4)){
									int temp1=(i+k)/2;int temp2=(j+k)/2;
									if(absolute(i-k)==1&&node[k][l]==0){
										mark=1;
									}
									if(absolute(i-k)==2&&node[temp1][temp2]==1&&node[k][l]==0){
										mark=1;
									}
								}
								
							}
						}
						
					}
					
					if(mark==0){tigerTrapped+=1;}
				} 					
			}
		}
	}
	
				
				

	
	public boolean moveChecker(int a, int b, int m, int n){
		boolean currentError=false;//this will be valid for good moves.
		int subtract=absolute(a-m)+absolute(b-n);
		int sum1,sum2,dif1,dif2;//sum or diff must be 2 for diagonal nodes
		int interX,interY;//To check if intermediate node contains goat for tiger jump over goat.
		sum1=a+b;dif1=absolute(a-b);sum2=m+n;dif2=absolute(m-n);
		if(subtract==1){//for simple moves
			currentError=true;			
		}//following for rhombus moves
		if((sum1==2||dif1==2)&&(sum2==2||dif2==2)&&(a!=m && b!=n)||(a*b==9 || m*n==9)){//for 3,3 node
			if(((absolute(a-m)+absolute(b-n))==2)){//except 3, 3 and node position are not equal for legal moves ie 1,1 and 1,3 
				currentError=true;
				if(a*m+b*n==12){currentError=false;}//for invalid move in 3,1 to  3,3 or 1,3 to 3,3
				}
			//for tiger jump over goat on diagonal nodes.
			interX=(a+m)/2;	interY=(b+n)/2;
			if((currentGotti==2 && node[interX][interY]==1)){
				if((a+m)!=4 && (b+n)!=4 && a!=interX && b!=interY){//for 3,1 and 1,3 or 1,1 and 3,3 move and goat between.													
				currentError=true;
				if(fromTiger==0){
				node[interX][interY]=0;
				showToast();
				if(soundHere)gtKilled.start();
				Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
				v.vibrate(100);
				goatKilled+=1;fromTiger=0;} 
				}
			}			
		}
		//for goat killed 
		if(subtract==2 && currentGotti==2 && (a==m||b==n)){
			if(a==m){interX=a;interY=(b+n)/2;}
			else{interX=(a+m)/2;interY=b;}
			if(node[interX][interY]==1){
				if(fromTiger==0){
					node[interX][interY]=0;
					showToast();
					if(soundHere)gtKilled.start();
					Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
					v.vibrate(100);
					goatKilled+=1;fromTiger=0;}
					currentError=true;
				}
		}
		//for other diagonals
		if((a==b&&m==n)||(a+b==4&&m+n==4)){
			if(absolute(a-m)==1){
				currentError=true;
			}
			//for tiger jump
			int temp1=(a+m)/2;int temp2=(b+n)/2;
			if(absolute(a-m)==2&&node[temp1][temp2]==1){
				if(fromTiger==0){node[temp1][temp2]=0;
				showToast();
				if(soundHere)gtKilled.start();
				Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);							
				v.vibrate(100);
				goatKilled+=1;fromTiger=0;}
				currentError=true;
			}
				
		}
		
		return currentError;		
	}
	
	
	
	public boolean moveChecker2(int a, int b, int m, int n){
		if(m<0 || n<0 || m>4 || n>4){
			return false;
		}
		boolean currentError=false;//this will be valid for good moves.
		int subtract=absolute(a-m)+absolute(b-n);
		int sum1,sum2,dif1,dif2;//sum or diff must be 2 for diagonal nodes
		int interX,interY;//To check if intermediate node contains goat for tiger jump over goat.
		sum1=a+b;dif1=absolute(a-b);sum2=m+n;dif2=absolute(m-n);
		if(subtract==1){//for simple moves
			currentError=true;			
		}//following for rhombus moves
		if((sum1==2||dif1==2)&&(sum2==2||dif2==2)&&(a!=m && b!=n)||(a*b==9 || m*n==9)){//for 3,3 node
			if(((absolute(a-m)+absolute(b-n))==2)){//except 3, 3 and node position are not equal for legal moves ie 1,1 and 1,3 
				currentError=true;
				if(a*m+b*n==12){currentError=false;}//for invalid move in 3,1 to  3,3 or 1,3 to 3,3
				}
						
		}
		//for goat killed 
		
		//for other diagonals
		if((a==b&&m==n)||(a+b==4&&m+n==4)){
			if(absolute(a-m)==1){
				currentError=true;
			}				
		}
		if (node[m][n]!=0){
			currentError=false;
		}
		
		return currentError;		
	}
	
	
	
	public int absolute( int diff){
		if(diff<0){
			diff-=2*diff;
		}		
		return diff;
	}
	public void callMe(int n){
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public void showToast(){
		
		Toast myToast=Toast.makeText(Play.this, "Goat Killed!!", Toast.LENGTH_SHORT);
		myToast.setGravity(Gravity.CENTER, 0, 0);
		if(goatKilled!=5)myToast.show();
		
	}
}
	


