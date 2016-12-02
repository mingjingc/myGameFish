package com.myGameFish.activity;

import com.myGameFish.constant.ConstantUtil;
import com.myGameFish.souds.GameSoundPool;
import com.myGameFish.view.EndView;
import com.myGameFish.view.MainView;
import com.myGameFish.view.ReadyView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

public class MainActivity extends Activity {
	private EndView endView;
	private MainView mainView;
	private ReadyView readyView;
	private GameSoundPool sounds;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
		if(msg.what == ConstantUtil.TO_MAIN_VIEW){
			toMainView();
		}
		else  if(msg.what == ConstantUtil.TO_END_VIEW){
			toEndView(msg.arg1);
		}
		else  if(msg.what == ConstantUtil.END_GAME){
			endGame();
		}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sounds = new GameSoundPool(this);
		sounds.initGameSound();
		readyView = new ReadyView(this,sounds);
		setContentView(readyView);
	}
	//显示游戏的主界面
	public void toMainView(){
		if(mainView == null){
			mainView = new MainView(this,sounds);
		}
		setContentView(mainView);
		readyView = null;
		endView = null;
	}
	//显示游戏结束的界面
	public void toEndView(int score){
		if(endView == null){
			endView = new EndView(this,sounds);
			endView.setScore(score);
		}
		setContentView(endView);
		mainView = null;
	}
	//结束游戏
	public void endGame(){
		if(readyView != null){
			readyView.setThreadFlag(false);
		}
		else if(mainView != null){
			mainView.setThreadFlag(false);
		}
		else if(endView != null){
			endView.setThreadFlag(false);
		}
		this.finish();
	}
	//getter和setter方法
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("mainActivity","mainActivity 被销毁");
		sounds.realseSoundPool();
		super.onDestroy();
	}
}
