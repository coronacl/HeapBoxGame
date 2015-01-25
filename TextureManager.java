package jp.gr.java_conf.coronacl.HeapBoxGame;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class TextureManager {

	//テクスチャを保存
	private static Map<Integer , Integer> mTextures = new Hashtable<Integer , Integer>();
	
	//ロードしたテクスチャを追加
	public static final void addTexture(int resId , int texId){
		mTextures.put(resId , texId);
	}
	
	//テクスチャを削除
	public static final void deleteTexture(GL10 gl , int resId){
		if(mTextures.containsKey(resId)){
			int[] texId = new int[1];
			texId[0] = mTextures.get(resId);
			gl.glDeleteTextures(1, texId , 0);
			mTextures.remove(resId);
		}
	}
	
	//全てのテクスチャを削除
	public static final void deleteAll(GL10 gl){
		List<Integer> keys = new ArrayList<Integer>(mTextures.keySet());
		for(Integer key : keys){//拡張for文：全要素を走査：keys[i] = keyとなっている
			deleteTexture(gl , key);
		}
	}
}
