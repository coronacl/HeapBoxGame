package jp.gr.java_conf.coronacl.HeapBoxGame;

import javax.microedition.khronos.opengles.GL10;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2f;

public class Camera
{
	//ï`âÊîÕàÕÇÃç∂è„ÇÃç¿ïW
	Pixel2f pos;
	float width;
	float height;
	
	Camera(){
		this.pos = new Pixel2f(0.0f,1.5f);
		this.width =1.0f;
		this.height = 1.5f;
		Global.left = this.pos.x;
		Global.bottom = this.pos.y - height;
		Global.right = this.pos.x + width;
		Global.top = this.pos.y;
	}
	
	Camera(Pixel2f pos , float width , float height){
		this.pos = pos;
		this.width =width;
		this.height = height;
	}
	

	void setDrawArea(Pixel2f pos , float width , float height){
		this.pos = pos;
		this.width = width;
		this.height = height;
		Global.left = this.pos.x;
		Global.bottom = this.pos.y - height;
		Global.right = this.pos.x + width;
		Global.top = this.pos.y;
	}
	
	void project(GL10 gl)
	{
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof( pos.x , pos.x+width , pos.y-height , pos.y , 0.5f , -0.5f);//left,right,bottom,top,zNear,zFar
		
	}

}
