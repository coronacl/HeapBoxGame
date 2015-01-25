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
		//(�ǂݍ��ރt�@�C����,�ǂݍ��ގ��,�ǂݍ��ގ�)
		this.sound_pool = new SoundPool(1 , AudioManager.STREAM_MUSIC, 0);
		this.sound_pool2 = new SoundPool(1 , AudioManager.STREAM_MUSIC, 0);
		//(�R���e�L�X�g�A�ǂݍ��ރ��\�[�XID�A���̗D��x)
		this.hit_sound = sound_pool.load(context, R.raw.comical, 1);
		this.hit_sound2 = sound_pool2.load(context, R.raw.comic2, 1);
	}
	
	public void playHitSound(){
		//(�Đ�����T�E���hID�A���̃{�����[���A�E�̃{�����[���A�D��x�A���[�v�񐔁A�Đ����[�g)
		sound_pool.play(hit_sound, 1.0f, 1.0f, 1, 0, 1.0f);
	}
	
	public void playHitSound2(){
		//(�Đ�����T�E���hID�A���̃{�����[���A�E�̃{�����[���A�D��x�A���[�v�񐔁A�Đ����[�g)
		sound_pool2.play(hit_sound2, 1.0f, 1.0f, 1, 0, 1.0f);
	}

}
