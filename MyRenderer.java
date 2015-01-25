package jp.gr.java_conf.coronacl.HeapBoxGame;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.ColorBox;
import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2f;
import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2i;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;


//主にGLの描画を担当
public class MyRenderer implements GLSurfaceView.Renderer 
{
	private int mWidth;
	private int mHeight;
	
	//テクスチャ管理用のID
	private int texture_back;
	private int texture_arrow_r;
	private int texture_arrow_l;
	private int texture_title;

	private int texture_you_win_orange;

	private int texture_you_lose_blue;
	private int texture_1p_win;
	private int texture_2p_win;
	
	private int texture_check_end;
	private glButton button_yes;
	private glButton button_no;
	
	//ボックスのテクスチャ
	int texture_box1a;
	int texture_box1b;

	int	texture_box2a;
	int	texture_box2b;

	int	texture_box3a;
	int	texture_box3b;

	int	texture_box4a;
	int	texture_box4b;
	

	//描画
	Camera camera;
	Camera const_camera;
	
	//カメラ用ボタン
	glButton button_arrow_r;
	glButton button_arrow_l;

	//ゲーム
	boolean flag_gameover = false;
	private long start_time;
	int game_phase;
	int before_game_phase;
	GameManager mg_game;
		
	//メニュ-ボタン
	glButton button_menu1;
	glButton button_menu2;
	glButton button_menu3;
	glButton button_menu4;
	
	//順番選択
	private int texture_select_board;
	glButton button_first;
	glButton button_second;
	
	//オプション画面
	int texture_select_option;
	int texture_selected1;
	int texture_selected2;
	glButton button_select_bgm1;
	glButton button_select_bgm2;
	glButton button_select_bgm3;
	glButton button_select_bgm4;
	glButton button_select_box1;
	glButton button_select_box2;
	glButton button_select_box3;
	glButton button_select_box4;
	glButton button_return_option;
	
	//説明画面
	private int texture_explain;
	glButton button_return1;
	glButton button_return2;
	
	//音楽
	private MySE se;
	
	//ボックス
	private static final int NUM_BOX = 1000;
	private static Box[] box;
	
	//コンテキスト
	private Context mContext;
	
	//ハンドラー
	private Handler handler = new Handler();
	
	//コンストラクタ
	public MyRenderer(Context context){
		this.mContext = context;
	
		startNewGame();
		
		this.se = new MySE(context);
	}
	
	public void startNewGame()
	{
		game_phase = 0;
		camera = new Camera();
		const_camera = new Camera(new Pixel2f(0.0f,1.5f) , 1.0f , 1.5f);//左上、幅、高さ
		mg_game = new GameManager(false);
		box = new Box[NUM_BOX];
	}
	
	public void reStartNewGame()
	{
		game_phase = 0;
	
		for(int counter = 0;counter<NUM_BOX;counter++){
			box[counter].init();
		}
		mg_game.init();
		//box = new Box[NUM_BOX];
		//GameManager tmp = new GameManager(false);
		//mg_game = tmp;
	}
	
	public int timer()
	{
		int passed_time;
		passed_time = (int)((System.currentTimeMillis() - start_time) / 1000.0);
		return(passed_time);
	}
	

	
	//描画処理
	public void renderMenu(GL10 gl){
		const_camera.project(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		//背景描画
		gl.glPushMatrix();{
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.75f) ,1.0f , 1.5f , texture_back , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,1.15f) , 0.8f , 0.4f , texture_title , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			button_menu1.draw(gl);
			button_menu2.draw(gl);
			button_menu3.draw(gl);
			button_menu4.draw(gl);
		}gl.glPopMatrix();

		//ゲーム終了
		if(timer() == 0 ){

			handler.post(new Runnable(){
				@Override
				public void run(){

				}
			});
		}
	}
	
	public void renderGame(GL10 gl){
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		//背景描画
		gl.glPushMatrix();{
			GraphicUtil.drawTexture(gl ,new Pixel2f(2.5f,0.75f) , 3.0f , 1.5f , texture_back , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
		}gl.glPopMatrix();
		
		//ボックスの描画
		gl.glPushMatrix();{
			for(int i=0 ; i<NUM_BOX; i++){
				box[i].draw(gl,2);
			}
		}gl.glPopMatrix();
		
		//最後の着手したボックス
		lightLastChoice(gl);

		
		//ゲーム終了
		if(mg_game.flag_find_winner){

			handler.post(new Runnable(){
				@Override
				public void run(){

				}
			});
		}
	}
	
	public void renderConstArea(GL10 gl)
	{
		const_camera.project(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		GraphicUtil.drawLine(gl , new Pixel2f(0.0f,0.25f)
		, new Pixel2f(1.0f,0.25f)  ,10.0f, new ColorBox(0.9f,0.9f,0.9f,1.0f));
		//, new Pixel2f(Global.left+0.9f,Global.bottom+0.2f)  ,10.0f, new ColorBox(1.0f,1.0f,0.0f,1.0f));
		//矢印の描画
		gl.glPushMatrix();{
			button_arrow_r.draw(gl);
			button_arrow_l.draw(gl);
			
			//returnボタン
			button_return2.draw(gl);
		}gl.glPopMatrix();
		
		
		

		//ゲーム終了時
		gl.glPushMatrix();
		{
			//勝利ロゴの表示
			if(mg_game.flag_find_winner ){
				if(mg_game.winner == 0 && game_phase == 2){
					GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f , 1.1f) 
					, 1.0f , 0.4f , texture_1p_win , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				}else if(mg_game.winner == 1 && game_phase == 2){
					GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f , 1.1f) 
					, 1.0f , 0.4f , texture_2p_win , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				}else if(game_phase == 1){
					if(mg_game.winner == 0 && mg_game.getCpuState() ==1){
						GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f , 1.1f) 
						, 1.0f , 0.4f , texture_you_win_orange , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
					}else if(mg_game.winner == 0 && mg_game.getCpuState() ==0){
						GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f , 1.1f) 
						, 1.0f , 0.4f , texture_you_lose_blue , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
					}else if(mg_game.winner == 1 && mg_game.getCpuState() ==1){
						GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f , 1.1f) 
						, 1.0f , 0.4f , texture_you_lose_blue , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
					}else if(mg_game.winner == 1 && mg_game.getCpuState() ==0){
						GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f , 1.1f) 
						, 1.0f , 0.4f , texture_you_win_orange , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
					}
				}
				
			}
			
		}
		gl.glPopMatrix();
	}
	
	public void renderSelectTurn(GL10 gl)
	{
		const_camera.project(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		//背景描画
		gl.glPushMatrix();{
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.75f) ,1.0f , 1.5f , texture_back , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.8f) , 0.9f , 0.9f , texture_select_board , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			button_first.draw(gl);
			button_second.draw(gl);
		}gl.glPopMatrix();

	}
	
	public void renderExplain(GL10 gl)
	{
		const_camera.project(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		//背景描画
		gl.glPushMatrix();{
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.75f) ,1.0f , 1.5f , texture_back , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.8f) , 0.9f , 0.9f , texture_explain , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			button_return1.draw(gl);
		}gl.glPopMatrix();
	}
	
	public void renderCheckEnd(GL10 gl)
	{
		const_camera.project(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		//背景描画
		gl.glPushMatrix();{
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.75f) ,1.0f , 1.5f , texture_back , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.8f) , 0.9f , 0.9f , texture_check_end , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			button_yes.draw(gl);
			button_no.draw(gl);
		}gl.glPopMatrix();
	}
	
	public void renderOption(GL10 gl)
	{
		const_camera.project(gl);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		//背景描画
		gl.glPushMatrix();{
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.75f) ,1.0f , 1.5f , texture_back , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			GraphicUtil.drawTexture(gl ,new Pixel2f(0.5f,0.8f) , 1.1f , 1.1f , texture_select_option , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
			button_select_bgm1.draw(gl);
			button_select_bgm2.draw(gl);
			button_select_bgm3.draw(gl);
			button_select_bgm4.draw(gl);
			button_select_box1.draw(gl);
			button_select_box2.draw(gl);
			button_select_box3.draw(gl);
			button_select_box4.draw(gl);
			button_return_option.draw(gl);
			
			switch(Global.index_bgm){
			case 0:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.2f,1.0f) , 0.155f , 0.155f , texture_selected1 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			case 1:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.4f,1.0f) , 0.155f , 0.155f , texture_selected1 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			case 2:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.6f,1.0f) , 0.155f , 0.155f , texture_selected1 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			case 3:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.805f,0.995f) , 0.155f , 0.155f , texture_selected1 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			}
			
			switch(Global.index_box){
			case 0:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.3f,0.655f) , 0.35f , 0.16f  , texture_selected2 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			case 1:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.7f,0.65f) , 0.35f , 0.16f , texture_selected2 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			case 2:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.3f,0.45f) , 0.35f , 0.16f , texture_selected2 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			case 3:
				GraphicUtil.drawTexture(gl ,new Pixel2f(0.7f,0.45f) , 0.35f , 0.16f , texture_selected2 , new ColorBox(1.0f , 1.0f , 1.0f , 1.0f));
				break;
			}
		}gl.glPopMatrix();
	}
	
	//GLの初期化処理
	public void initGL(GL10 gl){
/*		if(mWidth < mHeight){
			gl.glViewport( (-mHeight+mWidth)/2 , 0 , mHeight , mHeight);
		}else{
			gl.glViewport( 0 , (mHeight-mWidth)/2 , mWidth , mWidth);
		}*/
		gl.glViewport( 0 , 0 , mWidth , mHeight);
		Global.range_y = (float)mHeight/mWidth;
		
		camera.project(gl);
	
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glClearColor(0.5f, 0.5f 	, 0.5f ,  1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void commandCPU(GL10 gl){
		if(mg_game.isCpuTurn() && !mg_game.flag_find_winner){
			Pixel2i tmp = mg_game.getCpuChoice();
			box[mg_game.getTurn()].set_flag_visible(true);
			box[mg_game.getTurn()].setPos(tmp.x * 0.1f + 1.0f + 0.1f,tmp.y * 0.1f + 0.2f + 0.1f);
			box[mg_game.getTurn()].setMatPos(tmp.y, tmp.x);
			mg_game.setState(tmp.y, tmp.x, mg_game.getTurn() % 2);
			mg_game.plusTurn();
			
			mg_game.judge();
			int tmp_row,tmp_col;
			//４つそろった後
			if(mg_game.flag_find_winner){
				for(int i=0 ; i<NUM_BOX; i++){
					tmp = box[i].getMatPos();
					for(int depth = 0;depth<4;depth++){
						tmp_col = mg_game.win_pos.x + depth * mg_game.win_direction.x;
						tmp_row = mg_game.win_pos.y + depth * mg_game.win_direction.y;
						if(tmp_row == tmp.y && tmp_col == tmp.x){
							box[i].flag_win_box = true;
						}
					}
				}
			}
		}
	}
	
	public void lightLastChoice(GL10 gl)
	{
		int tmp_turn = mg_game.getTurn();
		if(tmp_turn >0){
			box[tmp_turn-1].draw(gl,3);
		}
		
	}

	@Override
	public void onDrawFrame(GL10 gl){
		initGL(gl);
		//モードセレクト
		if(game_phase == 0){
			renderMenu(gl);
		//二人対戦
		}else if(game_phase == 2 ){
			renderGame(gl);
			renderConstArea(gl);
		//cpuとの対戦
		}else if(game_phase == 1 ){
			renderGame(gl);
			renderConstArea(gl);
			commandCPU(gl);
		}else if(game_phase == 3){
			renderSelectTurn(gl);
		}else if(game_phase ==4){
			renderExplain(gl);
		//終了確認
		}else if(game_phase == 5){
			renderCheckEnd(gl);
		//オプション選択
		}else if(game_phase == 6){
			renderOption(gl);
		}
	}
	
	//画面サイズが変わったときの処理
	@Override
	public void onSurfaceChanged(GL10 gl , int width , int height){
		this.mWidth = width;
		this.mHeight = height;
		Global.gl = gl;
		
		//テクスチャをロード
		loadTextures(gl);


	}
	private void loadBox(GL10 gl)
	{
		//Resourcesはresのdrawableにあるのとかの管理クラス
		Resources res = mContext.getResources();
		
		texture_box1a = GraphicUtil.loadTexture(gl , res , R.drawable.box_orange);
		texture_box1b = GraphicUtil.loadTexture(gl , res , R.drawable.box_blue);
		Global.tex_orange = texture_box1a;
		Global.tex_blue = texture_box1b;

		texture_box2a = GraphicUtil.loadTexture(gl , res , R.drawable.moon_red);
		texture_box2b = GraphicUtil.loadTexture(gl , res , R.drawable.moon_blue);
	
		texture_box3a = GraphicUtil.loadTexture(gl , res , R.drawable.remi1);
		texture_box3b = GraphicUtil.loadTexture(gl , res , R.drawable.fran1);
	
		texture_box4a = GraphicUtil.loadTexture(gl , res , R.drawable.remi2);
		texture_box4b = GraphicUtil.loadTexture(gl , res , R.drawable.fran2);
		
		switch(Global.index_box){
		case 0:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box1a , true);
				}else{
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box1b , false);
				}
			}
			break;
		case 1:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box2a , true);
				}else{
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box2b , false);
				}
			}
			break;
		case 2:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box3b , true);
					box[i].setSize(0.09f,0.09f);
				}else{
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box3a , false);
					box[i].setSize(0.09f,0.09f);				}
			}
			break;
		case 3:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box4b , true);
					box[i].setSize(0.09f,0.09f);
				}else{
					box[i] = new Box(new Pixel2f(2.5f,0.25f) , 0.1f , 0.1f , texture_box4a , false);
					box[i].setSize(0.09f,0.09f);
				}
			}
			break;
		}

	}
	
	private void resetBoxTexture(){
		switch(Global.index_box){
		case 0:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i].setTexture(texture_box1a);
					box[i].setSize(0.1f,0.1f);
				}else{
					box[i].setTexture(texture_box1b);
					box[i].setSize(0.1f,0.1f);
				}
			}
			break;
		case 1:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i].setTexture(texture_box2a);
					box[i].setSize(0.1f,0.1f);
				}else{
					box[i].setTexture(texture_box2b);
					box[i].setSize(0.1f,0.1f);
				}
			}
			break;
		case 2:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i].setTexture(texture_box3b);
					box[i].setSize(0.09f,0.09f);
				}else{
					box[i].setTexture(texture_box3a);
					box[i].setSize(0.09f,0.09f);
				}
			}
			break;
		case 3:
			for(int i = 0; i<NUM_BOX; i++){
				if(i%2 == 0){
					box[i].setTexture(texture_box4b);
					box[i].setSize(0.09f,0.09f);
				}else{
					box[i].setTexture(texture_box4a);
					box[i].setSize(0.09f,0.09f);
				}
			}
			break;
		}

	}
	
	private void loadTextures(GL10 gl){
		//Resourcesはresのdrawableにあるのとかの管理クラス
		Resources res = mContext.getResources();
		
		//テクスチャの生成
		this.texture_back = GraphicUtil.loadTexture(gl , res , R.drawable.back);
		this.texture_title = GraphicUtil.loadTexture(gl , res , R.drawable.title);

		
		int texture_menu1 = GraphicUtil.loadTexture(gl , res , R.drawable.menu1);
		int texture_menu2 = GraphicUtil.loadTexture(gl , res , R.drawable.menu2);
		int texture_menu3 = GraphicUtil.loadTexture(gl , res , R.drawable.menu3);
		int texture_menu4 = GraphicUtil.loadTexture(gl , res , R.drawable.menu4);
		

		
		//texture_clear = GraphicUtil.loadTexture(gl , res , R.drawable.clear2);
		texture_you_win_orange = GraphicUtil.loadTexture(gl , res , R.drawable.you_win_orange);
		texture_you_lose_blue = GraphicUtil.loadTexture(gl , res , R.drawable.you_lose_blue);
		texture_1p_win = GraphicUtil.loadTexture(gl , res , R.drawable.win1p);
		texture_2p_win = GraphicUtil.loadTexture(gl , res , R.drawable.win2p);
		
		//テクスチャのボタンも生成
		button_menu1 = new glButton(new Pixel2f(0.5f,0.8f) , 0.6f , 0.15f , texture_menu1);
		button_menu2 = new glButton(new Pixel2f(0.5f,0.6f) , 0.6f , 0.15f , texture_menu2);
		button_menu3 = new glButton(new Pixel2f(0.5f,0.4f) , 0.6f , 0.15f , texture_menu3);
		button_menu4 = new glButton(new Pixel2f(0.5f,0.2f) , 0.6f , 0.15f , texture_menu4);
		
		//select turn
		int texture_first = GraphicUtil.loadTexture(gl , res , R.drawable.botton_first);
		int texture_second = GraphicUtil.loadTexture(gl , res , R.drawable.botton_second);
		texture_select_board = GraphicUtil.loadTexture(gl , res , R.drawable.select_board);
		button_first = new glButton(new Pixel2f(0.5f,0.85f) , 0.4f , 0.2f , texture_first);
		button_second = new glButton(new Pixel2f(0.5f,0.6f) , 0.4f , 0.2f , texture_second);
		
		//説明画面
		int texture_return = GraphicUtil.loadTexture(gl , res , R.drawable.button_return);
		int texture_return2 = GraphicUtil.loadTexture(gl , res , R.drawable.button_exit);
		texture_explain = GraphicUtil.loadTexture(gl , res , R.drawable.explain);
		button_return1 = new glButton(new Pixel2f(0.5f,0.35f) , 0.6f , 0.125f , texture_return);
		button_return2 = new glButton(new Pixel2f(0.7f,0.1f) , 0.15f , 0.14f , texture_return2);
		
		//オプション画面
		texture_select_option = GraphicUtil.loadTexture(gl , res , R.drawable.select_option);
		texture_selected1 = GraphicUtil.loadTexture(gl , res , R.drawable.selected1);
		texture_selected2 = GraphicUtil.loadTexture(gl , res , R.drawable.selected2);
		int texture_select_box1 = GraphicUtil.loadTexture(gl , res , R.drawable.select_box1);
		int texture_select_box2 = GraphicUtil.loadTexture(gl , res , R.drawable.select_box2);
		int texture_select_box3 = GraphicUtil.loadTexture(gl , res , R.drawable.select_box3);
		int texture_select_box4 = GraphicUtil.loadTexture(gl , res , R.drawable.select_box4);
		int texture_select_bgm1 = GraphicUtil.loadTexture(gl , res , R.drawable.select_bgm1);
		int texture_select_bgm2 = GraphicUtil.loadTexture(gl , res , R.drawable.select_bgm2);
		int texture_select_bgm3 = GraphicUtil.loadTexture(gl , res , R.drawable.select_bgm3);
		int texture_select_bgm4 = GraphicUtil.loadTexture(gl , res , R.drawable.select_bgm4);
		button_select_bgm1 = new glButton(new Pixel2f(0.2f,1.0f) , 0.15f , 0.15f , texture_select_bgm1);
		button_select_bgm2 = new glButton(new Pixel2f(0.4f,1.0f) , 0.15f , 0.15f , texture_select_bgm2);
		button_select_bgm3 = new glButton(new Pixel2f(0.6f,1.0f) , 0.15f , 0.15f , texture_select_bgm3);
		button_select_bgm4 = new glButton(new Pixel2f(0.8f,1.0f) , 0.15f , 0.15f , texture_select_bgm4);
		button_select_box1 = new glButton(new Pixel2f(0.3f,0.65f) , 0.3f , 0.15f , texture_select_box1);
		button_select_box2 = new glButton(new Pixel2f(0.7f,0.65f) , 0.3f , 0.15f , texture_select_box2);
		button_select_box3 = new glButton(new Pixel2f(0.3f,0.45f) , 0.3f , 0.15f , texture_select_box3);
		button_select_box4 = new glButton(new Pixel2f(0.7f,0.45f) , 0.3f , 0.15f , texture_select_box4);
		button_return_option = new glButton(new Pixel2f(0.5f,0.15f) , 0.6f , 0.125f , texture_return);
		
		//boxの設定
		loadBox(gl);
		
		//終了確認
		texture_check_end = GraphicUtil.loadTexture(gl , res , R.drawable.check_end);
		int texture_yes = GraphicUtil.loadTexture(gl , res , R.drawable.yes);
		int texture_no = GraphicUtil.loadTexture(gl , res , R.drawable.no);
		button_yes = new glButton(new Pixel2f(0.3f,0.71f) , 0.395f , 0.18f , texture_yes);
		button_no = new glButton(new Pixel2f(0.7f,0.7f) , 0.4f , 0.2f , texture_no);
		
		//カメラ用ボタン
		texture_arrow_r = GraphicUtil.loadTexture(gl , res , R.drawable.arrow_r);
		texture_arrow_l = GraphicUtil.loadTexture(gl , res , R.drawable.arrow_l);

	}
	
	@Override
	public void onSurfaceCreated(GL10 gl , javax.microedition.khronos.egl.EGLConfig config)
	{	
	}
	
	//広告の表示の切り替え
	public void callMain(final int root)
	{
		handler.post(new Runnable(){
			
			@Override
			public void run(){
				if(root == 0){
					Global.main_activity.showAd();
				}
				if(root ==1){
					Global.main_activity.hideAd();
				}
			}
		});
	}
	
	//音楽の切り替え
	public void changeMusicPlayer(final int root)
	{
		handler.post(new Runnable(){
			
			@Override
			public void run(){
				se.playHitSound2();
				Global.main_activity.changeMusicPlayer(root);
			}
		});
	}
	
	//終了確認
	public void processCheckEnd(float x , float y){
		if(game_phase == 5 && !mg_game.flag_find_winner){	
			if(button_yes.isPointInside(x, y)){
				se.playHitSound2();
				callMain(0);
				reStartNewGame();
			}else if(button_no.isPointInside(x, y)){
				se.playHitSound2();
				game_phase = before_game_phase;
			}
		}else if(game_phase == 5 && mg_game.flag_find_winner){
			se.playHitSound2();
			callMain(0);
			reStartNewGame();
		}
	}
	

	
	////画面クリック時の処理
	//MyRendererのonTouchEventより、呼び出し
	public void touched(float x , float y){
		//メニュー画面
		if(game_phase == 0){
			se.playHitSound2();
			//CPUと対戦モード
			if(button_menu1.isPointInside(x, y)){
				game_phase = 3;
				callMain(1);
				resetBoxTexture();
			}
			//対人対戦モード
			if(button_menu2.isPointInside(x, y)){
				game_phase = 2;
				camera.setDrawArea(new Pixel2f(2.0f,1.5f),1.0f,1.5f);
				button_arrow_r = new glButton(new Pixel2f(0.1f , 0.1f) , 0.15f , 0.15f , texture_arrow_l);
				button_arrow_l = new glButton(new Pixel2f(0.3f , 0.1f) , 0.15f , 0.15f , texture_arrow_r);
				callMain(1);
				resetBoxTexture();
			}
			//各種設定
			if(button_menu3.isPointInside(x, y)){
				game_phase = 6;
			}
			if(button_menu4.isPointInside(x, y)){
				game_phase = 4;
			}
		//対戦画面
		}else if(game_phase == 2){
			se.playHitSound();
			//タッチした位置の計算
			if(mg_game.touchProcess(x, y) == true && mg_game.flag_find_winner == false){
				box[mg_game.getTurn()].set_flag_visible(true);
				
				Pixel2i tmp = mg_game.calTablePos(x,y);
				System.out.println("row = " + tmp.y + " col = " + tmp.x);
				System.out.println("turn = " + mg_game.getTurn() );
				box[mg_game.getTurn()].setPos(tmp.x * 0.1f + 1.0f + 0.1f, tmp.y * 0.1f + 0.2f + 0.1f);
				box[mg_game.getTurn()].setMatPos(tmp.y,tmp.x);
				mg_game.setState(tmp.y,tmp.x, mg_game.getTurn()%2);
				
				mg_game.judge();
				int tmp_row,tmp_col;
				//４つそろった後
				if(mg_game.flag_find_winner){
					for(int i=0 ; i<NUM_BOX; i++){
						tmp = box[i].getMatPos();
						for(int depth = 0;depth<4;depth++){
							tmp_col = mg_game.win_pos.x + depth * mg_game.win_direction.x;
							tmp_row = mg_game.win_pos.y + depth * mg_game.win_direction.y;
							if(tmp_row == tmp.y && tmp_col == tmp.x){
								box[i].flag_win_box = true;
							}
						}
					}
				}
				mg_game.plusTurn();
			}
			
			//初めの一回目
			if(mg_game.firstTouch()){
				mg_game.plusTurn();
				box[0].set_flag_visible(true);
				box[0].setPos(14.0f * 0.1f + 1.0f + 0.1f, 0.0f * 0.1f + 0.2f + 0.1f);
				box[0].setMatPos(0, 14);
				mg_game.setState(0, 14, 0);
			}
			
			//カメラの移動
			if(button_arrow_r.isPointInside(x, y)){
				if(Global.left > 1.0f){
					camera.setDrawArea(new Pixel2f(Global.left-0.1f,Global.top),1.0f,1.5f);
				}
			}else if(button_arrow_l.isPointInside(x, y)){
				if(Global.left <= 2.9f){
					camera.setDrawArea(new Pixel2f(Global.left+0.1f,Global.top),1.0f,1.5f);		
				}
			}
			
			if(button_return2.isPointInside(x, y)){
				game_phase = 5;
				before_game_phase = 2;
				
			}
			
		//CPUとの対戦時
		}else if(game_phase == 1){
			se.playHitSound();
			if (! mg_game.isCpuTurn())
			{
				// 初めの一回目
				if (mg_game.firstTouch() && mg_game.getCpuState() == 1) {
					box[0].set_flag_visible(true);
					box[0].setPos(14.0f * 0.1f + 1.0f + 0.1f,0.0f * 0.1f + 0.2f + 0.1f);
					box[0].setMatPos(0, 14);
					mg_game.setState(0, 14, 0);
					mg_game.plusTurn();
				}				
				//タッチした位置の計算
				if(mg_game.touchProcess(x, y) == true && mg_game.flag_find_winner == false)
				{
					box[mg_game.getTurn()].set_flag_visible(true);
					
					Pixel2i tmp = mg_game.calTablePos(x,y);
					System.out.println("row = " + tmp.y + " col = " + tmp.x);
					System.out.println("turn = " + mg_game.getTurn() );
					box[mg_game.getTurn()].setPos(tmp.x * 0.1f + 1.0f + 0.1f, tmp.y * 0.1f + 0.2f + 0.1f);
					box[mg_game.getTurn()].setMatPos(tmp.y,tmp.x);
					mg_game.setState(tmp.y,tmp.x, (mg_game.getCpuState()+1)%2);
					mg_game.judge();
					int tmp_row,tmp_col;
					
					//４つそろった後
					if(mg_game.flag_find_winner){
						for(int i=0 ; i<NUM_BOX; i++){
							tmp = box[i].getMatPos();
							for(int depth = 0;depth<4;depth++){
								tmp_col = mg_game.win_pos.x + depth * mg_game.win_direction.x;
								tmp_row = mg_game.win_pos.y + depth * mg_game.win_direction.y;
								if(tmp_row == tmp.y && tmp_col == tmp.x){
									box[i].flag_win_box = true;
								}
							}
						}
					}
					mg_game.plusTurn();
	
				}
			}
			// カメラの移動
			if (button_arrow_r.isPointInside(x, y)) {
				if (Global.left > 1.0f) {
					camera.setDrawArea(new Pixel2f(Global.left - 0.1f,Global.top), 1.0f, 1.5f);
				}
			} else if (button_arrow_l.isPointInside(x, y)) {
				if (Global.left <= 2.9f) {
					camera.setDrawArea(new Pixel2f(Global.left + 0.1f,Global.top), 1.0f, 1.5f);
				}
			}
			//終了
			if(button_return2.isPointInside(x, y)){
				game_phase = 5;
				before_game_phase = 1;
			}
		}
		//前後を決定する画面
		else if(game_phase == 3){
			se.playHitSound2();
			//CPUと対戦モード
			if(button_first.isPointInside(x, y)){
				mg_game.activateCPU(1);
				camera.setDrawArea(new Pixel2f(2.0f,1.5f),1.0f,1.5f);
				button_arrow_r = new glButton(new Pixel2f(0.1f , 0.1f) , 0.15f , 0.15f , texture_arrow_l);
				button_arrow_l = new glButton(new Pixel2f(0.3f , 0.1f) , 0.15f , 0.15f , texture_arrow_r);
				game_phase = 1;
				
				callMain(1);
			}else if(button_second.isPointInside(x, y)){
				mg_game.activateCPU(0);
				camera.setDrawArea(new Pixel2f(2.0f,1.5f),1.0f,1.5f);
				button_arrow_r = new glButton(new Pixel2f(0.1f , 0.1f) , 0.15f , 0.15f , texture_arrow_l);
				button_arrow_l = new glButton(new Pixel2f(0.3f , 0.1f) , 0.15f , 0.15f , texture_arrow_r);
				game_phase = 1;
				
				box[0].set_flag_visible(true);
				box[0].setPos(14.0f * 0.1f + 1.0f + 0.1f,0.0f * 0.1f + 0.2f + 0.1f);
				box[0].setMatPos(0, 14);
				mg_game.setState(0, 14, 0);
				mg_game.plusTurn();///-1から０へ
				mg_game.plusTurn();///０から１へ
				
				callMain(1);
			}
		}
		//説明画面
		else if(game_phase == 4){
			se.playHitSound2();
			if(button_return1.isPointInside(x, y)){
				game_phase = 0;
			}
		}
		//オプション画面
		else if(game_phase == 6){
			se.playHitSound2();
			if(button_select_bgm1.isPointInside(x, y)){
				Global.index_bgm = 0;
				changeMusicPlayer(Global.index_bgm);
			}else if(button_select_bgm2.isPointInside(x, y)){
				Global.index_bgm = 1;
				changeMusicPlayer(Global.index_bgm);
			}else if(button_select_bgm3.isPointInside(x, y)){
				Global.index_bgm = 2;
				changeMusicPlayer(Global.index_bgm);
			}else if(button_select_bgm4.isPointInside(x, y)){
				Global.index_bgm = 3;
				changeMusicPlayer(Global.index_bgm);
			}else if(button_select_box1.isPointInside(x, y)){
				Global.index_box = 0;
			}else if(button_select_box2.isPointInside(x, y)){
				Global.index_box = 1;
			}else if(button_select_box3.isPointInside(x, y)){
				Global.index_box = 2;
			}else if(button_select_box4.isPointInside(x, y)){
				Global.index_box = 3;
			}else if(button_return_option.isPointInside(x,y)){
				game_phase = 0;
			}
			
		}
		processCheckEnd(x,y);
	}
	
	public void subtractPausedTime(long pausedTime){
		start_time += pausedTime;
	}



	public long getStart_time() {
		return start_time;
	}

	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}
	
	//int tex_id,float pos_x,float pos_y,float width,float height,int row,int col,int flag_win_box,int flag_visible
	public void saveParcelable()
	{
		
		for(int i=0;i<Global.NUM_BOX;i++){
			int tmp_bool_win;
			int tmp_bool_visible;
			if(box[i].isFlag_win_box()){
				tmp_bool_win = 1;
			}else{
				tmp_bool_win = 0;
			}
			if(box[i].isFlag_visible()){
				tmp_bool_visible = 1;
			}else{
				tmp_bool_visible = 0;
			}
			
			Global.save_box[i] = new MyParcelable(box[i].getTex_id()
					,box[i].getPos().x,box[i].getPos().y,box[i].getWidth(),box[i].getHeight(),
					box[i].getRow(),box[i].getCol(),tmp_bool_win,tmp_bool_visible);
		}
	}
	public void loadParcelable()
	{
		for(int i=0;i<Global.NUM_BOX;i++){
			boolean tmp_bool_win;
			boolean tmp_bool_visible;
			if(Global.save_box[i].getFlag_visible() == 1){
				tmp_bool_win = true;
			}else{
				tmp_bool_win = false;
			}
			if(Global.save_box[i].getFlag_visible() == 1){
				tmp_bool_visible = true;
			}else{
				tmp_bool_visible = false;
			}
			
			box[i].tex_id = Global.save_box[i].getTex_id();
			box[i].pos.x = Global.save_box[i].getPos_x();
			box[i].pos.y  = Global.save_box[i].getPos_y();
			box[i].width = Global.save_box[i].getWidth();
			box[i].height = Global.save_box[i].getHeight();
			box[i].row = Global.save_box[i].getRow();
			box[i].col = Global.save_box[i].getCol();
			box[i].flag_win_box = tmp_bool_win;
			box[i].flag_visible = tmp_bool_visible;
			System.out.println("OOOOOOOOOOOOOOOOk");
		}
	}

}
