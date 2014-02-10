package com.xudu.android.kbs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author rapidera
 * 
 */
public class PreferencesManager {

	private static final String PREF_NAME = "pref";

	public static void setAlpha(Context context, int alpha) {
		SharedPreferences.Editor editor = context.getSharedPreferences(
				PREF_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt("alpha", alpha);
		editor.commit();
	}

	public static int getAlpha(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		int recid = prefs.getInt("alpha", 50);
		return recid;
	}

	public static void setDownloadStatus(Context context, boolean download) {
		SharedPreferences.Editor editor = context.getSharedPreferences(
				PREF_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean("download", download);
		editor.commit();
	}

	public static boolean getDownloadStatus(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		boolean download = prefs.getBoolean("download", false);
		return download;
	}

}
