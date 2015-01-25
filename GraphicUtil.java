package jp.gr.java_conf.coronacl.HeapBoxGame;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.ColorBox;
import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2f;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;



public class GraphicUtil {
	
	//バッファ確保
	public static final FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	//円の描画
	public static final void drawCircle(GL10 gl , float x , float y , int divides , float radius , 
										float r , float g , float b , float a)
	{	
		int vertix_id = 0;
		float[] vertices = new float[divides * 3 * 2];//三角形の個数＊頂点数×座標のX,Y
		
		for(int i = 0; i < divides ; i++){
			float theta_l = 2.0f / (float)divides * (float)i * (float)Math.PI;
			float theta_r = 2.0f / (float)divides * (float)(i+1) * (float)Math.PI;
			vertices[vertix_id ++] = x;
			vertices[vertix_id ++] = y;
			
			vertices[vertix_id ++] = (float)Math.cos((double)theta_l) * radius + x;
			vertices[vertix_id ++] = (float)Math.sin((double)theta_l) * radius + y;
			
			vertices[vertix_id ++] = (float)Math.cos((double)theta_r) * radius + x;
			vertices[vertix_id ++] = (float)Math.sin((double)theta_r) * radius + y;
		}
		FloatBuffer polygonVertices = makeFloatBuffer(vertices);
		
		gl.glColor4f(r, g, b, a);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, divides * 3);
		
	}
	
	//直線の描画
	public final static void drawLine(GL10 gl , Pixel2f start_pos , Pixel2f end_pos  , ColorBox cl)
	{
		float[] vertex = {start_pos.x , start_pos.y , end_pos.x , end_pos.y};
		float[] colors = { cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a}; 
		
		FloatBuffer polygonVertices = GraphicUtil.makeFloatBuffer(vertex);
		FloatBuffer polygonColors = GraphicUtil.makeFloatBuffer(colors);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
	}
	
	//直線の描画
	public final static void drawLine(GL10 gl , Pixel2f start_pos , Pixel2f end_pos  ,float size, ColorBox cl)
	{
		float[] vertex = {start_pos.x , start_pos.y , end_pos.x , end_pos.y};
		float[] colors = { cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a}; 
		
		FloatBuffer polygonVertices = GraphicUtil.makeFloatBuffer(vertex);
		FloatBuffer polygonColors = GraphicUtil.makeFloatBuffer(colors);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glLineWidth( size );
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
	}
	
	//座標軸の描画
	public static final void drawAxis(GL10 gl)
	{
		ColorBox tmp_cl = new ColorBox(1.0f,0.0f,0.0f,1.0f);
		drawLine(gl , new Pixel2f(0.001f,0.001f) ,new Pixel2f(1.0f,0.001f) ,  tmp_cl );
		drawLine(gl , new Pixel2f(0.001f,0.001f) ,new Pixel2f(0.001f,1.0f) , new ColorBox(0.0f,1.0f,0.0f,1.0f)  );
	}
	
	//四角形描画
	public static final void drawSquare(GL10 gl 
			, Pixel2f pos, float width , float height
			,ColorBox cl){
		
		float[] vertices = { 
				pos.x + width * 0.5f , pos.y + height * 0.5f
				,pos.x - width * 0.5f , pos.y + height * 0.5f
				,pos.x + width * 0.5f , pos.y - height * 0.5f
				,pos.x - width * 0.5f , pos.y - height * 0.5f
				};

		float[] colors = { 
				cl.r , cl.g , cl.b , cl.a ,
				cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a  };
		
		FloatBuffer polygonVertices = GraphicUtil.makeFloatBuffer(vertices);
		FloatBuffer polygonColors = GraphicUtil.makeFloatBuffer(colors);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	//画像の読み込み
	public static final int loadTexture(GL10 gl , Resources resources , int res_id)
	{
		//bitmapの作成
/*		Options options = null;
		Bitmap bmp = BitmapFactory.decodeResource(resources, res_id , options);
		if(bmp == null){
			return 0;
		}*/
		InputStream is = resources.openRawResource(res_id);
		Bitmap bmp;
		try{
			bmp = BitmapFactory.decodeStream(is);
		}
		finally{
			try{
				is.close();
			}
			catch(IOException e){   }
		}

		//openGL用のテクスチャを作成
		int[] textures = new int[1];
		gl.glGenTextures(1 , textures , 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D , textures[0]);
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);//OpenGL ES用のメモリ領域に画像データを渡す。上でバインドされたテクスチャIDと結び付けられる。
		
		// テクスチャ座標が1.0fを超えたときの、テクスチャを繰り返す設定
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
		
		//テクスチャを元のサイズから拡大、縮小して使用したときの色の使い方を設定
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		
		//openGLへのBMPの転送が完了したので、VMメモリ上のBitmapを削除
		bmp.recycle();
		
		//TextureManagerに登録
		TextureManager.addTexture(res_id, textures[0]);
		
		return textures[0];
	}
	
	private static final BitmapFactory.Options options = new BitmapFactory.Options();
	static {
		options.inScaled =false;
		options.inPreferredConfig = Config.ARGB_8888;
	}
	
	//テクスチャの描画
	public static final void drawTexture(GL10 gl , Pixel2f pos  , float width , float height , int texture 
			,ColorBox cl)
	{
		float[] vertices = { 
				pos.x - width * 0.5f , pos.y - height * 0.5f
				,pos.x + width * 0.5f , pos.y - height * 0.5f
				,pos.x - width * 0.5f , pos.y + height * 0.5f
				,pos.x + width * 0.5f , pos.y + height * 0.5f};

		float[] colors = { 
				cl.r , cl.g , cl.b , cl.a ,
				cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a  };
		
		float[] coords = {
				0.0f , 1.0f ,
				1.0f , 1.0f ,
				0.0f , 0.0f , 
				1.0f , 0.0f ,};
		
		FloatBuffer polygonVertices = GraphicUtil.makeFloatBuffer(vertices);
		FloatBuffer polygonColors = GraphicUtil.makeFloatBuffer(colors);
		FloatBuffer texCoords = GraphicUtil.makeFloatBuffer(coords);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//テクスチャ有効化
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);//テクスチャオブジェクトの指定
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);//テクスチャ無効
	}
	
	//画像の指定領域のみ描画
	public static final void drawROI(GL10 gl , Pixel2f pos  , float width , float height , int texture 
			,ColorBox cl , Pixel2f roi_pos , float roi_w , float roi_h)
	{
		float[] vertices = { 
				pos.x - width * 0.5f , pos.y - height * 0.5f
				,pos.x + width * 0.5f , pos.y - height * 0.5f
				,pos.x - width * 0.5f , pos.y + height * 0.5f
				,pos.x + width * 0.5f , pos.y + height * 0.5f};

		float[] colors = { 
				cl.r , cl.g , cl.b , cl.a ,
				cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a , 
				cl.r , cl.g , cl.b , cl.a  };
		
		float[] coords = {
				roi_pos.x , roi_pos.y + roi_h ,
				roi_pos.x + roi_w , roi_pos.y + roi_h ,
				roi_pos.x , roi_pos.y , 
				roi_pos.x + roi_w , roi_pos.y ,};
		
		FloatBuffer polygonVertices = GraphicUtil.makeFloatBuffer(vertices);
		FloatBuffer polygonColors = GraphicUtil.makeFloatBuffer(colors);
		FloatBuffer texCoords = GraphicUtil.makeFloatBuffer(coords);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//テクスチャ有効化
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);//テクスチャオブジェクトの指定
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);//テクスチャ無効
	}
	
	//一ケタの数字を描画
	public static final void drawNumber(GL10 gl , Pixel2f pos  , float width , float height , int texture 
			, int number ,ColorBox cl)
	{
		float u = (float)(number % 4) * 0.25f;
		float v = (float)(number / 4) * 0.25f;
		
		drawROI(gl ,pos , width , height , texture, new ColorBox(1.0f , 1.0f , 1.0f , 1.0f)
		,new Pixel2f(u,v) , 0.25f , 0.25f);
	}
	
	//複数の桁の数字を描画
	public static final void drawNumbers(GL10 gl , Pixel2f pos  , float width , float height , int texture 
			, int number ,int figures , ColorBox cl)
	{
		float total_width = width * (float)figures;//ｎ文字分の幅
		float rightX = pos.x + (total_width * 0.5f);//右端のｘ座標
		float figlX =rightX - width * 0.5f;//一番右の桁の中心のｘ座標
		
		for(int i= 0; i < figures; i++){
			float figNX = figlX - (float)i * width;//N＋１桁目の中心のｘ座標
			int number_to_draw = number % 10;
			number = number / 10;
			drawNumber(gl , new Pixel2f(figNX,pos.y) , width , height , texture , number_to_draw , new ColorBox(1.0f,1.0f,1.0f,1.0f));
		}	
	}
	
	


	


}
