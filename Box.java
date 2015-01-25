package jp.gr.java_conf.coronacl.HeapBoxGame;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.ColorBox;
import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2f;
import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2i;

public class Box {
	public int tex_id;
	public Pixel2f pos;
	public float width;
	public float height;
	public int row;
	public int col;
	public boolean flag_win_box = false;
	public boolean flag_visible;
	public boolean flag_is_orange;
	
	Box(Pixel2f _pos ,float _width , float _height , int _tex_id , boolean _flag_is_orange){
		//ïWìIÇÃèâä˙âª
		this.pos = _pos;
		this.width = _width;
		this.height = _height;
		this.tex_id = _tex_id;
		this.flag_visible = false;
		this.flag_is_orange = _flag_is_orange;
	};
	
	public void setSize(float _width , float _height){
		this.width = _width;
		this.height = _height;
	}
	
	public int getTex_id() {
		return tex_id;
	}

	public Pixel2f getPos() {
		return pos;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isFlag_win_box() {
		return flag_win_box;
	}

	public boolean isFlag_visible() {
		return flag_visible;
	}

	void init(){
		this.flag_win_box = false;
		this.flag_visible = false;
	}
	
	void setPos(float _x , float _y){
		this.pos.x = _x;
		this.pos.y = _y;
	}
	void set_flag_visible(boolean _tmp){
		this.flag_visible = _tmp;
	}
	void setMatPos(int row ,int col){
		this.row = row;
		this.col = col;
	}
	Pixel2i getMatPos(){
		Pixel2i tmp = new Pixel2i(this.col,this.row);
		return(tmp);
	}
	void setTexture(int _tex_id){
		this.tex_id = _tex_id;
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
	
	//0ï`âÊÇ»Çµ
	//1îºìßñæ
	//2ï`âÊ
	void draw(GL10 gl , int level_visible){
		if (flag_visible) {
			if (flag_win_box) {
				if (flag_is_orange) {
					if (Global.index_box == 1) {
						GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, tex_id, new ColorBox(1.0f, 1.0f,1.0f, 0.4f));
					} else {
						GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, Global.tex_orange, new ColorBox(1.0f, 1.0f, 1.0f, 0.4f));
					}
				} else {
					if (Global.index_box == 1) {
						GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, tex_id, new ColorBox(1.0f, 1.0f,1.0f, 0.4f));
					} else {
						GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, Global.tex_blue, new ColorBox(1.0f, 1.0f, 1.0f, 0.4f));
					}
				}
				GraphicUtil.drawTexture(gl, pos, width, height, tex_id,new ColorBox(1.0f, 1.0f, 1.0f, 1.0f));
			} else {
				if (level_visible == 2) {
					GraphicUtil.drawTexture(gl, pos, width, height, tex_id,new ColorBox(1.0f, 1.0f, 1.0f, 1.0f));
				} else if (level_visible == 1) {
					GraphicUtil.drawTexture(gl, pos, width, height, tex_id,new ColorBox(1.0f, 1.0f, 1.0f, 0.4f));
					// ç≈å„Ç…íÖéËÇµÇΩéË
				} else if (level_visible == 3) {
					if (flag_is_orange) {
						if (Global.index_box == 1) {
							GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, tex_id, new ColorBox(1.0f,1.0f, 1.0f, 0.4f));
						} else {
							GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, Global.tex_orange,new ColorBox(1.0f, 1.0f, 1.0f, 0.4f));
						}
					} else {
						if (Global.index_box == 1) {
							GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, tex_id, new ColorBox(1.0f,1.0f, 1.0f, 0.4f));
						} else {
							GraphicUtil.drawTexture(gl, pos, width * 1.4f,height * 1.4f, Global.tex_blue,new ColorBox(1.0f, 1.0f, 1.0f, 0.4f));
						}
					}

					GraphicUtil.drawTexture(gl ,pos , width , height , tex_id , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				}
			}
			
		}
	}
	
}
