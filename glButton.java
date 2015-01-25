package jp.gr.java_conf.coronacl.HeapBoxGame;

import javax.microedition.khronos.opengles.GL10;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.ColorBox;
import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2f;

public class glButton {
	public int tex_id;
	public Pixel2f pos;
	public float width;
	public float height;
	
	
	glButton(Pixel2f _pos ,float _width , float _height , int _tex_id){
		//ïWìIÇÃèâä˙âª
		this.pos = _pos;
		this.width = _width;
		this.height = _height;
		this.tex_id = _tex_id;
		
	};
	
	void movePos(float x ,float y){
		this.pos.x = this.pos.x + x;
		this.pos.y = this.pos.y + y;
	}
	
	//è’ìÀîªíË
	public boolean isPointInside(float x , float y){
		float l_up_x = this.pos.x - this.width/2;
		float l_up_y = this.pos.y + this.height/2;
		float r_down_x = this.pos.x + this.width/2;
		float r_down_y = this.pos.y - this.height/2;
		boolean isFlag = false;
		if(l_up_x <= x && x <= r_down_x && r_down_y <= y && y <= l_up_y){
			isFlag = true;
		}
		return(isFlag);
	}
	
	void draw(GL10 gl){
		GraphicUtil.drawTexture(gl ,pos , width , height , tex_id , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
	}
	
}
