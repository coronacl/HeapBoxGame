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
	
	//�o�b�t�@�m��
	public static final FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	//�~�̕`��
	public static final void drawCircle(GL10 gl , float x , float y , int divides , float radius , 
										float r , float g , float b , float a)
	{	
		int vertix_id = 0;
		float[] vertices = new float[divides * 3 * 2];//�O�p�`�̌������_���~���W��X,Y
		
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
	
	//�����̕`��
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
	
	//�����̕`��
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
	
	//���W���̕`��
	public static final void drawAxis(GL10 gl)
	{
		ColorBox tmp_cl = new ColorBox(1.0f,0.0f,0.0f,1.0f);
		drawLine(gl , new Pixel2f(0.001f,0.001f) ,new Pixel2f(1.0f,0.001f) ,  tmp_cl );
		drawLine(gl , new Pixel2f(0.001f,0.001f) ,new Pixel2f(0.001f,1.0f) , new ColorBox(0.0f,1.0f,0.0f,1.0f)  );
	}
	
	//�l�p�`�`��
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
	
	//�摜�̓ǂݍ���
	public static final int loadTexture(GL10 gl , Resources resources , int res_id)
	{
		//bitmap�̍쐬
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

		//openGL�p�̃e�N�X�`�����쐬
		int[] textures = new int[1];
		gl.glGenTextures(1 , textures , 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D , textures[0]);
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);//OpenGL ES�p�̃������̈�ɉ摜�f�[�^��n���B��Ńo�C���h���ꂽ�e�N�X�`��ID�ƌ��ѕt������B
		
		// �e�N�X�`�����W��1.0f�𒴂����Ƃ��́A�e�N�X�`�����J��Ԃ��ݒ�
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
		
		//�e�N�X�`�������̃T�C�Y����g��A�k�����Ďg�p�����Ƃ��̐F�̎g������ݒ�
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		
		//openGL�ւ�BMP�̓]�������������̂ŁAVM���������Bitmap���폜
		bmp.recycle();
		
		//TextureManager�ɓo�^
		TextureManager.addTexture(res_id, textures[0]);
		
		return textures[0];
	}
	
	private static final BitmapFactory.Options options = new BitmapFactory.Options();
	static {
		options.inScaled =false;
		options.inPreferredConfig = Config.ARGB_8888;
	}
	
	//�e�N�X�`���̕`��
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
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//�e�N�X�`���L����
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);//�e�N�X�`���I�u�W�F�N�g�̎w��
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);//�e�N�X�`������
	}
	
	//�摜�̎w��̈�̂ݕ`��
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
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//�e�N�X�`���L����
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);//�e�N�X�`���I�u�W�F�N�g�̎w��
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);//�e�N�X�`������
	}
	
	//��P�^�̐�����`��
	public static final void drawNumber(GL10 gl , Pixel2f pos  , float width , float height , int texture 
			, int number ,ColorBox cl)
	{
		float u = (float)(number % 4) * 0.25f;
		float v = (float)(number / 4) * 0.25f;
		
		drawROI(gl ,pos , width , height , texture, new ColorBox(1.0f , 1.0f , 1.0f , 1.0f)
		,new Pixel2f(u,v) , 0.25f , 0.25f);
	}
	
	//�����̌��̐�����`��
	public static final void drawNumbers(GL10 gl , Pixel2f pos  , float width , float height , int texture 
			, int number ,int figures , ColorBox cl)
	{
		float total_width = width * (float)figures;//���������̕�
		float rightX = pos.x + (total_width * 0.5f);//�E�[�̂����W
		float figlX =rightX - width * 0.5f;//��ԉE�̌��̒��S�̂����W
		
		for(int i= 0; i < figures; i++){
			float figNX = figlX - (float)i * width;//N�{�P���ڂ̒��S�̂����W
			int number_to_draw = number % 10;
			number = number / 10;
			drawNumber(gl , new Pixel2f(figNX,pos.y) , width , height , texture , number_to_draw , new ColorBox(1.0f,1.0f,1.0f,1.0f));
		}	
	}
	
	


	


}
