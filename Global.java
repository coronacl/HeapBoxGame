package jp.gr.java_conf.coronacl.HeapBoxGame;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;

public class Global {

	////GLコンテキストを保持する変数
	//コンテキスト：アプリケーションの環境情報をOSの全域で受けわたしするためのインターフェース
	//要するに、状態の管理用
	public static GL10 gl;
	
	public static float range_y;//結局使っていない
	
	public static Random rand = new Random(System.currentTimeMillis());
	
	public static MainActivity main_activity;
	
	public static float left;
	public static float right;
	public static float top;
	public static float bottom;
	
	//音楽関連
	public static MyBgm bgm;
	public static int index_bgm = 0;
	
	//box
	public static int index_box = 0;
	public static int tex_orange;
	public static int tex_blue;
	
	//保存処理用
	public static AllMyParcelable save_data;
	public static final int NUM_BOX = 1000;
	public static MyParcelable[] save_box;
	
	static Bundle p_save;
}
