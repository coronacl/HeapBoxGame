package jp.gr.java_conf.coronacl.HeapBoxGame;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2i;
import android.view.Gravity;
import android.widget.Toast;

public class GameManager
{
	int ROWS_BOX = 12;
	int COLS_BOX = 30;
	int turn;
	Pixel2i[] direction;
	
	int winner;
	Pixel2i win_pos;
	Pixel2i win_direction;
	boolean flag_find_winner;
	
	////置かれたところ管理用テーブル
	//デフォルト-1,先手0,後手1
	int[][] table;
	
	boolean flag_cpu;
	CPU cpu;
	
	GameManager(boolean _flag_cpu){
		
		table = new int[ROWS_BOX][COLS_BOX];
		for(int j=0;j<ROWS_BOX; j++){
			for(int i=0;i<COLS_BOX;i++){
				table[j][i] = -1;
			}
		}
		turn = -1;
		
		direction = new Pixel2i[8];
		direction[0] = new Pixel2i(1,0);
		direction[1] = new Pixel2i(1,1);
		direction[2] = new Pixel2i(0,1);
		direction[3] = new Pixel2i(-1,1);
		direction[4] = new Pixel2i(-1,0);
		direction[5] = new Pixel2i(-1,-1);
		direction[6] = new Pixel2i(0,-1);
		direction[7] = new Pixel2i(1,-1);
		
		flag_cpu = false;
	}
	
	void init()
	{
		
		for(int j=0;j<ROWS_BOX; j++){
			for(int i=0;i<COLS_BOX;i++){
				table[j][i] = -1;
			}
		}
		turn = -1;
		flag_cpu = false;
		flag_find_winner = false;
	}
	
	void plusTurn(){
		this.turn = this.turn +1;
	}
	int getTurn(){
		return(turn);
	}
	
	void setState(int row ,int col,int player){
		table[row][col] = player;
		System.out.println("player = " + player + "(" + row + "," + col + ")");
	}
	
	void activateCPU(int cpu_turn){
		flag_cpu = true;
		cpu = new CPU(cpu_turn);
	}
	
	Pixel2i calTablePos(float x , float y){
		Pixel2i dst = new Pixel2i(-1,-1);
		
		dst.x = (int)(( x + Global.left - 1.0f -0.05f) / 0.1f);
		dst.y = (int)(( y + Global.bottom- 0.2f -0.05f) / 0.1f);
		
		return(dst);
	}
	
	boolean canPut(int row , int col)
	{
		boolean flag_can_put = false;

		
		if( 0 <= row && row < ROWS_BOX && 0 < col && col < COLS_BOX){
			if(table[row][col] == -1){
				if(row == 0 && (table[row][col-1] != -1 || table[row][col+1] != -1)) {
					flag_can_put = true;
				}else if(row != 0){
					if(table[row-1][col] != -1){
						flag_can_put = true;
					}
				}
			}
		}

		if(flag_can_put){
			//置いた場所の登録
			table[row][col] = turn % 2;
			
			//turn ++;
		}
		
		return(flag_can_put);
	}
	
	boolean touchProcess(float x , float y)
	{
		if(turn == -1){
			return(false);
		}
		Pixel2i table_pos = calTablePos(x,y);
		return(canPut(table_pos.y,table_pos.x));
	}
	
	boolean firstTouch()
	{
		boolean flag_first_touch = false;
		if(turn == -1){
			flag_first_touch = true;
			turn ++;
		}
		table[0][14] = 0;
		return(flag_first_touch);
	}
	
	void judge()
	{
		Pixel2i check = new Pixel2i(0,0);
		win_pos = new Pixel2i(0,0);
		int counter0;//先手用のカウンター
		int counter1;//後手用のカウンター
		winner = -1;
		flag_find_winner = false;
		for(int j=0;j<ROWS_BOX; j++){
			for(int i=0;i<COLS_BOX;i++){
				if(table[j][i] != -1){
					for(int n = 0 ; n<8;n++){
						counter0 = 0;
						counter1 = 0;
						for(int depth = 0;depth<4;depth++){
							check.x = i + direction[n].x * depth;
							check.y = j + direction[n].y * depth;
							if(0<=check.x && check.x < COLS_BOX && 0<=check.y && check.y<ROWS_BOX){
								if(table[check.y][check.x]==0){counter0++;}
								if(table[check.y][check.x]==1){counter1++;}
							}
						}
						if(counter0 == 4){
							winner = 0;
							win_pos.x = i;
							win_pos.y = j;
							win_direction = direction[n];
							flag_find_winner = true;
							System.out.println("winner = " + winner);
							System.out.println("winner.pos = " + win_pos.x + "," + win_pos.y);
						}
						if(counter1 == 4){
							winner = 1;
							win_pos.x = i;
							win_pos.y = j;
							win_direction = direction[n];
							flag_find_winner = true;
							System.out.println("winner = " + winner);
							System.out.println("winner.pos = " + win_pos.x + "," + win_pos.y);
						}
						
					}
				}
			}
		}
	}
	
	boolean isCpuTurn(){
		boolean flag_is_cpu_turn = false;
		
		if((turn  % 2) == cpu.get_cpu_state()){
			flag_is_cpu_turn = true;
		}
		return(flag_is_cpu_turn);
	}
	
	Pixel2i getCpuChoice(){
		
		Pixel2i dst = cpu.indicateNextChoice(this.table);
		return(dst);
	}
	
	int getCpuState(){
		int tmpi = winner;//抵当に初期化
		if(!(cpu == null)){
			tmpi = cpu.cpu_state;
		}
		return(tmpi);
	}

}
