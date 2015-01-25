package jp.gr.java_conf.coronacl.HeapBoxGame;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class AllMyParcelable implements Parcelable {
	private ArrayList<MyParcelable> contactList;

	@Override
	public int describeContents() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeTypedList(contactList);

	}

	public static final Parcelable.Creator<AllMyParcelable> CREATOR = new Parcelable.Creator<AllMyParcelable>() {
		public AllMyParcelable createFromParcel(Parcel in) {
			return new AllMyParcelable(in);
		}

		public AllMyParcelable[] newArray(int size) {
			return new AllMyParcelable[size];
		}
	};

	private AllMyParcelable(Parcel in) {
		contactList = in.createTypedArrayList(MyParcelable.CREATOR);
	}

	public AllMyParcelable(ArrayList<MyParcelable> contactList) {
		this.contactList = contactList;
	}
}
