package com.xudu.android.kbs;

import android.content.Context;
import android.widget.Toast;

public class Commons {

	public static void showToastAlert(Context context,	String toastMsg, boolean isShort) {
		if(isShort)
		Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show();
		
	}

}
