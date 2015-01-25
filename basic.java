package jp.gr.java_conf.coronacl.HeapBoxGame;

public class basic {
	//“ñŸŒ³À•W(float)
	static public class Pixel2f{
		Pixel2f(float _x , float _y){
			x = _x;
			y = _y;
		}
		float x;
		float y;
	}
	
	// “ñŸŒ³À•W(int)
	static public class Pixel2i {
		Pixel2i(int _x, int _y) {
			x = _x;
			y = _y;
		}

		int x;
		int y;
	}
	
	//OŠpŒ`î•ñŠi”[
	public class Triangle{
		Pixel2f[] vertices = new Pixel2f[3];
	}
	
	//Fî•ñŠi”[
	static public class ColorBox{
		ColorBox(float _r , float _g , float _b , float _a){
			r = _r;
			g = _g;
			b = _b;
			a = _a;
		}
		float r;
		float g;
		float b;
		float a;
	}
	
	static public class GamePhase{
		enum Status{
			select_menu ,
			game_play;
		}
	}
	
	
}
