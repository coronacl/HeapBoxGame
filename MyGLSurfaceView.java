package jp.gr.java_conf.coronacl.HeapBoxGame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

//���GUI�����S��
public class MyGLSurfaceView extends GLSurfaceView {
	
	//��ʂ̃T�C�Y
	private float mWidth;
	private float mHeight;
	
	//Renderer
	private MyRenderer mMyRenderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		
		//�^�b�`�C�x���g�̓o�^
		setFocusable(true);
	}
	
	@Override
	public void setRenderer(Renderer renderer){
		super.setRenderer(renderer);
		this.mMyRenderer = (MyRenderer)renderer;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder , int format , int w , int h){
		super.surfaceChanged(holder, format, w, h);
		this.mWidth = w;
		this.mHeight = h;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		float x = (float)event.getX() / mWidth *1.0f;
		float y = 1.5f - (float)event.getY() / mHeight * 1.5f;
		
		System.out.println("touched" + x + "," + y);
		
		mMyRenderer.touched(x,y);
		
		return false;
	}

}
