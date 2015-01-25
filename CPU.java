package jp.gr.java_conf.coronacl.HeapBoxGame;

import java.util.Random;
import java.util.Vector;

import jp.gr.java_conf.coronacl.HeapBoxGame.basic.Pixel2i;

public class CPU
{
	int ROWS_BOX = 12;
	int COLS_BOX = 30;
	Pixel2i[] direction;
	
	////置かれたところ管理用テーブル
	//デフォルト-1,you 0 ,cpu 1 , next -2
	int[][] cpu_table;
	int cpu_state;
	int you_state;
	Vector<Next> possible_move;
	
	public class Next
	{
		int row;
		int col;
		int point;
		Next(int row , int col){
			this.row = row;
			this.col = col;
			this.point = 0;
		}
	}

	
	CPU(int _cpu_state){
		this.cpu_state = _cpu_state;//後手
		this.you_state = (_cpu_state  + 1) % 2;
		
		cpu_table = new int[ROWS_BOX][COLS_BOX];
		for(int j=0;j<ROWS_BOX; j++){
			for(int i=0;i<COLS_BOX;i++){
				cpu_table[j][i] = -1;
			}
		}
		direction = new Pixel2i[7];
		direction[0] = new Pixel2i(1,0);
		direction[1] = new Pixel2i(1,1);
		direction[2] = new Pixel2i(-1,1);
		direction[3] = new Pixel2i(-1,0);
		direction[4] = new Pixel2i(-1,-1);
		direction[5] = new Pixel2i(0,-1);
		direction[6] = new Pixel2i(1,-1);
		
		possible_move = new Vector<Next>();
		
	}
	
	int get_cpu_state(){return(cpu_state);};
	
	boolean canPutCpuBoard(int row , int col)
	{
		boolean flag_can = false;
		if( 0 <= row && row < ROWS_BOX && 0 < col && col < COLS_BOX -1){
			if(cpu_table[row][col] == -1){
				if(row == 0 && (cpu_table[row][col-1]>= 0 || cpu_table[row][col+1] >=0)) {
					cpu_table[row][col] = -2;
					flag_can = true;
				}else if(row > 0){
					if(cpu_table[row-1][col] >= 0 ){
						cpu_table[row][col] = -2;
						flag_can = true;
					}
				}
			}
		}
		return(flag_can);
	}

	void copyTable(int[][] table) {
		for (int j = 0; j < ROWS_BOX; j++) {
			for (int i = 0; i < COLS_BOX; i++) {
				cpu_table[j][i] = table[j][i];
			}
		}
	}
	
	//最大評価となった位置を返す
	int evaluate()
	{
		int[] tmpX = new int[4];
		int[] tmpY = new int[4];
		int flag_2way = 0;
		for(int k =0; k < possible_move.size(); k++){
			Next tmp = (Next)possible_move.get(k);
			for(int l=0; l < 7;l++){
				tmpX[3] = tmp.col + direction[l].x * 3;
				tmpY[3] = tmp.row + direction[l].y * 3;
				tmpX[2] = tmp.col + direction[l].x * 2;
				tmpY[2] = tmp.row + direction[l].y * 2;
				tmpX[1] = tmp.col + direction[l].x * 1;
				tmpY[1] = tmp.row + direction[l].y * 1;
				tmpX[0] = tmp.col + direction[l].x * (-1);
				tmpY[0] = tmp.row + direction[l].y * (-1);
				if( 0 <= tmpY[3] && tmpY[3] < ROWS_BOX && 0 < tmpX[3] && tmpX[3] < COLS_BOX -1){
					//x+++の場合
					if(cpu_table[tmpY[3]][tmpX[3]] == cpu_state 
							&& cpu_table[tmpY[2]][tmpX[2]] == cpu_state
							&& cpu_table[tmpY[1]][tmpX[1]] == cpu_state){
						tmp.point = tmp.point + 20;
					}
					//x---の場合
					if(cpu_table[tmpY[3]][tmpX[3]] == you_state 
							&& cpu_table[tmpY[2]][tmpX[2]] == you_state
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state){
						tmp.point = tmp.point + 15;
					}
					//x++?
					if(cpu_table[tmpY[2]][tmpX[2]] == you_state
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state){
						flag_2way ++;
						if(flag_2way >= 2){
							tmp.point = tmp.point + 5;
						}
					}
				}
				if( 0 <= tmpY[0] && tmpY[0] < ROWS_BOX && 0 < tmpX[0] && tmpX[0] < COLS_BOX -1
						&& 0 <= tmpY[2] && tmpY[2] < ROWS_BOX && 0 < tmpX[2] && tmpX[2] < COLS_BOX -1){
					//+x++の場合
					if(cpu_table[tmpY[0]][tmpX[0]] == cpu_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == cpu_state
							&& cpu_table[tmpY[2]][tmpX[2]] == cpu_state){
						tmp.point = tmp.point + 20;
					}
					//-x---
					if(cpu_table[tmpY[0]][tmpX[0]] == you_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state
							&& cpu_table[tmpY[2]][tmpX[2]] == you_state){
						tmp.point = tmp.point + 15;
					}
				}
				if( 0 <= tmpY[0] && tmpY[0] < ROWS_BOX && 0 < tmpX[0] && tmpX[0] < COLS_BOX -1
						&& 0 <= tmpY[1] && tmpY[1] < ROWS_BOX && 0 < tmpX[1] && tmpX[1] < COLS_BOX -1){
					//+x+の場合
					if(cpu_table[tmpY[0]][tmpX[0]] == cpu_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == cpu_state){
						tmp.point = tmp.point + 1;
					}
					//-x-
					if(cpu_table[tmpY[0]][tmpX[0]] == you_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state){
						tmp.point = tmp.point + 1;
					}
				}
				if( 0 <= tmpY[2] && tmpY[2] < ROWS_BOX && 0 < tmpX[2] && tmpX[2] < COLS_BOX -1){
					//x++の場合
					if(cpu_table[tmpY[2]][tmpX[2]] == cpu_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == cpu_state){
						tmp.point = tmp.point + 1;
					}
					//x--
					if(cpu_table[tmpY[2]][tmpX[2]] == you_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state){
						tmp.point = tmp.point + 1;
					}
				}
			}
			flag_2way = 0;
		}
		
		//自分が置いた上で相手が勝たないかどうか
		for(int k =0; k < possible_move.size(); k++){
			Next tmp = (Next)possible_move.get(k);
			cpu_table[tmp.row][tmp.col] = cpu_state;
			
			for(int l=0; l < 7;l++){
				tmpX[3] = tmp.col + direction[l].x * 3;
				tmpY[3] = tmp.row + 1 + direction[l].y * 3;
				tmpX[2] = tmp.col + direction[l].x * 2;
				tmpY[2] = tmp.row + 1 + direction[l].y * 2;
				tmpX[1] = tmp.col + direction[l].x * 1;
				tmpY[1] = tmp.row + 1 + direction[l].y * 1;
				tmpX[0] = tmp.col + direction[l].x * (-1);
				tmpY[0] = tmp.row + 1 + direction[l].y * (-1);
				if( 0 <= tmpY[3] && tmpY[3] < ROWS_BOX && 0 < tmpX[3] && tmpX[3] < COLS_BOX -1){
					//x---の場合
					if(cpu_table[tmpY[3]][tmpX[3]] == you_state 
							&& cpu_table[tmpY[2]][tmpX[2]] == you_state
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state){
						tmp.point = tmp.point - 10;
					}
				}
				if( 0 <= tmpY[0] && tmpY[0] < ROWS_BOX && 0 < tmpX[0] && tmpX[0] < COLS_BOX -1
						&& 0 <= tmpY[2] && tmpY[2] < ROWS_BOX && 0 < tmpX[2] && tmpX[2] < COLS_BOX -1){
					//-x--
					if(cpu_table[tmpY[0]][tmpX[0]] == you_state 
							&& cpu_table[tmpY[1]][tmpX[1]] == you_state
							&& cpu_table[tmpY[2]][tmpX[2]] == you_state){
						tmp.point = tmp.point - 10;
					}
				}
			}
			cpu_table[tmp.row][tmp.col] = -1;
		}
		
		//最大評価となった位置を計算
		Next next_tmp = possible_move.get(0);
		int max_value = next_tmp.point;
		int max_pos = 0;
		boolean flag_minus = false;
		for(int k =0; k < possible_move.size()-1; k++){
			Next tmp = (Next)possible_move.get(k);
			Next tmp2 = (Next)possible_move.get(k+1);
			if(tmp.point < 0 || tmp2.point < 0){
				flag_minus = true;
			}
			if(max_value < tmp2.point){
				max_value = tmp2.point;
				max_pos = k + 1;
			}
		}
		//全部０点だったら、乱数で返す
		if(max_value == 0 && !flag_minus){
		    Random rand = Global.rand;
		    max_pos = (int)(rand.nextFloat() * (possible_move.size() -1));
		}
		return(max_pos);
	}
	
	Pixel2i indicateNextChoice(int[][] table){
		copyTable(table);
		
		//候補手の決定
		possible_move.clear();
		boolean flag = false;
		for(int j=0;j<ROWS_BOX;j++){
			for(int i=0;i<COLS_BOX;i++){
				flag = canPutCpuBoard(j,i);
				if(flag == true){
					possible_move.add(new Next(j,i));
				}
			}			
		}
		System.out.println("vector size = " + possible_move.size());
		
		//候補手を評価
		int choiceI = evaluate();
		
		//確認
	    for (int i = 0; i < possible_move.size(); i++) {
	      Next tmp = (Next)possible_move.get(i);  // Object 型なのに注意することになる。
	      System.out.println("next[" + i +"] = " + tmp.col + "," + tmp.row );
			System.out.println("next[" + i +"].point = " + tmp.point );
	    }

		Next dst = (Next) possible_move.get(choiceI);
		System.out.println("choiced next[" + choiceI +"] = " + dst.col + "," + dst.row );

		return(new Pixel2i(dst.col,dst.row));
	}

}
