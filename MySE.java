package jp.gr.java_conf.coronacl.HeapBoxGame;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class MySE
{
	private SoundPool sound_pool;
	private SoundPool sound_pool2;
	private int hit_sound;
	private int hit_sound2;
	
	public MySE(Context context){
		//(読み込むファイル数,読み込む種類,読み込む質)
		this.sound_pool = new SoundPool(1 , AudioManager.STREAM_MUSIC, 0);
		this.sound_pool2 = new SoundPool(1 , AudioManager.STREAM_MUSIC, 0);
		//(コンテキスト、読み込むリソースID、音の優先度)
		this.hit_sound = sound_pool.load(context, R.raw.comical, 1);
		this.hit_sound2 = sound_pool2.load(context, R.raw.comic2, 1);
	}
	
	public void playHitSound(){
		//(再生するサウンドID、左のボリューム、右のボリューム、優先度、ループ回数、再生レート)
		sound_pool.play(hit_sound, 1.0f, 1.0f, 1, 0, 1.0f);
	}
	
	public void playHitSound2(){
		//(再生するサウンドID、左のボリューム、右のボリューム、優先度、ループ回数、再生レート)
		sound_pool2.play(hit_sound2, 1.0f, 1.0f, 1, 0, 1.0f);
	}

}
