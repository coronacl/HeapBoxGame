package jp.gr.java_conf.coronacl.HeapBoxGame;

import android.os.Parcel;
import android.os.Parcelable;


//writeToParcel(Parcel out, int flags)
//Ç≈èëÇ´çûÇﬁèáî‘Ç∆
//MyParcelable(Parcel in)
//Ç≈ì«Ç›èoÇ∑èáî‘ÇÕìØÇ∂Ç…ÇµÇ»ÇØÇÍÇŒÇ»ÇËÇ‹ÇπÇÒÅB

public class MyParcelable implements Parcelable {
	public int getTex_id() {
		return tex_id;
	}

	public float getPos_x() {
		return pos_x;
	}

	public float getPos_y() {
		return pos_y;
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

	public int getFlag_win_box() {
		return flag_win_box;
	}

	public int getFlag_visible() {
		return flag_visible;
	}

	public static Parcelable.Creator<MyParcelable> getCreator() {
		return CREATOR;
	}

	private int tex_id;
	private float pos_x;
	private float pos_y;
	private float width;
	private float height;
	private int row;
	private int col;
	public int flag_win_box;
	private int flag_visible;

	@Override
	public int describeContents() {
		return 0;
	}

	//ì«Ç›çûÇ›
	@Override
	public void writeToParcel(Parcel out, int flags){
		out.writeInt(tex_id);
		out.writeFloat(pos_x);
		out.writeFloat(pos_y);
		out.writeFloat(width);
		out.writeFloat(height);
		out.writeInt(row);
		out.writeInt(col);
		out.writeInt(flag_win_box);
		out.writeInt(flag_visible);
	}

	public static final Parcelable.Creator<MyParcelable> CREATOR = new Parcelable.Creator<MyParcelable>() {
		public MyParcelable createFromParcel(Parcel in) {
			return new MyParcelable(in);
		}

		public MyParcelable[] newArray(int size) {
			return new MyParcelable[size];
		}
	};

	//ï€ë∂
	private MyParcelable(Parcel in) {
		this.tex_id = in.readInt();
		this.pos_x = in.readFloat();
		this.pos_y = in.readFloat();
		this.width = in.readFloat();
		this.height = in.readFloat();
		this.row = in.readInt();
		this.col = in.readInt();
		this.flag_win_box = in.readInt();
		this.flag_visible = in.readInt();
	}

	public MyParcelable(int tex_id,float pos_x,float pos_y,float width,float height,int row,int col,int flag_win_box,int flag_visible) {
		this.tex_id = tex_id;
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		this.row = row;
		this.col = col;
		this.flag_win_box = flag_win_box;
		this.flag_visible = flag_visible;
	}
}
