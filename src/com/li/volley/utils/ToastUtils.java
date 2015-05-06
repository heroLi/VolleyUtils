package com.li.volley.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	public static void showText(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showText(Context context, int id) {
		Toast.makeText(context, context.getResources().getString(id),
				Toast.LENGTH_LONG).show();
	}

}
