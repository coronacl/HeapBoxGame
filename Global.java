package jp.gr.java_conf.coronacl.HeapBoxGame;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;

public class Global {

	////GL�R���e�L�X�g��ێ�����ϐ�
	//�R���e�L�X�g�F�A�v���P�[�V�����̊�����OS�̑S��Ŏ󂯂킽�����邽�߂̃C���^�[�t�F�[�X
	//�v����ɁA��Ԃ̊Ǘ��p
	public static GL10 gl;
	
	public static float range_y;//���ǎg���Ă��Ȃ�
	
	public static Random rand = new Random(System.currentTimeMillis());
	
	public static MainActivity main_activity;
	
	public static float left;
	public static float right;
	public static float top;
	public static float bottom;
	
	//���y�֘A
	public static MyBgm bgm;
	public static int index_bgm = 0;
	
	//box
	public static int index_box = 0;
	public static int tex_orange;
	public static int tex_blue;
	
	//�ۑ������p
	public static AllMyParcelable save_data;
	public static final int NUM_BOX = 1000;
	public static MyParcelable[] save_box;
	
	static Bundle p_save;
}
