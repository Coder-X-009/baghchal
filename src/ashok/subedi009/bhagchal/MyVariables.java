package ashok.subedi009.bhagchal;

import android.app.Application;
import android.graphics.Color;

public class MyVariables extends Application{
	private static boolean myTestSound;//will be true when sound is on
	private static int myColor=Color.WHITE;//will preserve the board background
	private static int myTiger=R.drawable.tiger1;//will preserve the image for tiger and goat
	private static int myGoat=R.drawable.goat1;
	private static int mySize=0;
	private static int playMode=0;//1 as gotat vs compuetr.
	
	public int getPlayMode(){
		return playMode;
	}	
	public void setPlayMode(int number){
		MyVariables.playMode=number;
	}
	public boolean getMyTest(){
		return myTestSound;
	}
	public void setMyTest(boolean myTestSound){
		MyVariables.myTestSound=myTestSound;
	}
	public int getColor(){
		return myColor;		
	}
	public void setColor(int myColor){
		MyVariables.myColor=myColor;
	}
	public int getMyGoat(){
		return myGoat;
	}
	public void setMyGoat(int myGoat){
		MyVariables.myGoat=myGoat;
	}
	public int getMyTiger(){
		return myTiger;
	}
	public void setMyTiger(int myTiger){
		MyVariables.myTiger=myTiger;
	}
	public int getWinner(){
		return mySize;		
	}
	public void setWinner(int winner){
		MyVariables.mySize=winner;
	}

}
