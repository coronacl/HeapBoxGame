package jp.gr.java_conf.coronacl.HeapBoxGame;

import android.content.Context;
import android.media.MediaPlayer;

public class MyBgm
{
	private MediaPlayer[] bgm;

	
	public MyBgm(Context context){
		this.bgm = new MediaPlayer[4];
		this.bgm[0] = MediaPlayer.create(context, R.raw.waiting_room);
		this.bgm[1] = MediaPlayer.create(context,R.raw.hurry);
		this.bgm[2] = MediaPlayer.create(context, R.raw.fear);
		this.bgm[3] = MediaPlayer.create(context, R.raw.dairy);
		this.bgm[0].setLooping(true);
		this.bgm[1].setLooping(true);
		this.bgm[2].setLooping(true);
		this.bgm[3].setLooping(true);
		this.bgm[0].setVolume(1.0f, 1.0f);
		this.bgm[1].setVolume(1.0f, 1.0f);
		this.bgm[2].setVolume(1.0f, 1.0f);
		this.bgm[3].setVolume(1.0f, 1.0f);
		
		bgm[0].start();
		bgm[1].stop();bgm[2].stop();bgm[3].stop();
		bgm[1].prepareAsync();
		bgm[2].prepareAsync();
		bgm[3].prepareAsync();
	}
	
	//BGMを再生
	public void start(int index){
		for(int i = 0; i<4;i++){
			if(i == index){
				if(!bgm[i].isPlaying()){
					bgm[i].seekTo(0);//開始位置の設定（ミリ秒）
					bgm[i].start();
				}
			}else{
				if(bgm[i].isPlaying()){
					bgm[i].stop();
					bgm[i].prepareAsync();//再生準備
				}
			}
		}

	}
	//BGMを停止
	public void stop(int index){
		for(int i = 0; i<4;i++){
			if(i == index){
				if(bgm[i].isPlaying()){
					bgm[index].stop();
					bgm[index].prepareAsync();//再生準備
				}
			}else{
				if(!bgm[i].isPlaying()){
					bgm[i].stop();
					bgm[i].prepareAsync();//再生準備
				}
			}
		}

	}
		
}
