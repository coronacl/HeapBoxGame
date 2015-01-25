package jp.gr.java_conf.coronacl.HeapBoxGame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

//主にGUI操作を担当
public class MyGLSurfaceView extends GLSurfaceView {
	
	//画面のサイズ
	private float mWidth;
	private float mHeight;
	
	//Renderer
	private MyRenderer mMyRenderer;

	public MyGLSurfaceView(Context context) {
		super(context);
		
		//タッチイベントの登録
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
