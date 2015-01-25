package jp.gr.java_conf.coronacl.HeapBoxGame;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
	//�Q�[��
	private long pause_time = 0L;

	//�����_�[
	private MyRenderer renderer;
	
	//�L��
	   AdView adView1;
	   AdView adView2;
	   AdRequest adRequest;
	   static FrameLayout.LayoutParams params;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//�t���X�N���[���\��
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//�^�C�g���o�[���\��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//���ʕύX�̋���
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		//BGM
		Global.bgm = new MyBgm(this);
			
		//MainActivity�ւ̃|�C���^
		Global.main_activity = this;
		
		//�ۑ��p
		Global.p_save = savedInstanceState;
		Global.save_box = new MyParcelable[Global.NUM_BOX];
		
		renderer = new MyRenderer(this);
		
		MyGLSurfaceView glSurfaceView = new MyGLSurfaceView(this);
		glSurfaceView.setRenderer(renderer);
		
		setContentView(glSurfaceView);
		
		initAd();
        
	}
	
	public void changeMusicPlayer(int index){

		Global.bgm.start(index);
	}
	
	public void initAd()
	{
		////�ȉ��A�L��
        adView1 = new AdView(this);
        adView1.setAdUnitId("ca-app-pub-3936269748965298/7799778962");
        adView1.setAdSize(AdSize.BANNER);
       
		params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER_HORIZONTAL |Gravity.TOP;
        addContentView(adView1,params);

        // Test Mode
/*        adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
        .build();
        adView1.loadAd(adRequest);*/
        //adView2.loadAd(adRequest);
        
        // �{��
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
        //adView1.setVisibility(AdView.GONE);
      
	}
	
	public void showAd()
	{
		//params.gravity = Gravity.CENTER_HORIZONTAL |Gravity.BOTTOM;
		//
		//adView2.setVisibility(AdView.GONE);
	}
	
	public void hideAd()
	{
		//params.gravity = Gravity.CENTER_HORIZONTAL |Gravity.TOP;
		//adView1.setVisibility(AdView.VISIBLE);
		//adView2.setVisibility(AdView.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//�o�b�N�O���E���h���畜�A��
	@Override
	public void onResume() {
		super.onResume();
		
		renderer.startNewGame();

		if (pause_time != 0) {
			long puased_time = System.currentTimeMillis() - pause_time;
			renderer.subtractPausedTime(puased_time);
		}
		adView1.resume();
		//adView2.resume();
		
		Global.bgm.start(Global.index_bgm);
/*		if (Global.p_save != null) {
			for (int i = 0; i < Global.NUM_BOX; i++) {
				// Global.save_box[i] =
				// savedInstanceState.getParcelable("contact_box" +
				// String.valueOf(i));
				Global.save_box[i] = Global.p_save.getParcelable("contact_box");
			}
			renderer.loadParcelable();
			Toast.makeText(this, "�����Ăяo��", Toast.LENGTH_LONG).show();
		}*/
		

	}
	
	//�o�b�N�O���E���h�ɂȂ����Ƃ�
	@Override
	public void onPause(){
		super.onPause();
		//�e�N�X�`�����폜
		//TextureManager.deleteAll(Global.gl);
		
		Global.bgm.stop(Global.index_bgm);
		pause_time = System.currentTimeMillis();
		adView1.pause();
		//adView2.pause();
	}


	//��Ԃ̕ۑ�����
	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		//renderer.saveParcelable();
/*		for(int i=0;i<Global.NUM_BOX;i++){
			//outState.putParcelable("contact_box"+String.valueOf(i), Global.save_box[i]);
			outState.putParcelable("contact_box", Global.save_box[i]);
			
		}
		Toast.makeText(this, "�ۑ�"+"contact_box"+String.valueOf(10), Toast.LENGTH_LONG).show();

		  outState.putInt("score" , 1);*/
	}
	
	//��Ԃ̌Ăяo��
	@Override    
	protected void onRestoreInstanceState(Bundle savedInstanceState) {    
	   super.onRestoreInstanceState(savedInstanceState);    
	   /* �����ŕۑ�������Ԃ�ǂݏo���Đݒ� */  
		//��Ԃ̕���

	
	} 
	


}
