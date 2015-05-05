package com.li.volley.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {

	public static int NETWORK_WIFI = 1;
	public static int NETWORK_NO = -1;
	public static int NETWORK_MOBILE = 2;

	/**
	 * 检查手机系统是否连接网络
	 * 
	 * ps：注意在添加 ACCESS_NETWORK_STATE 权限
	 * 
	 * @return NETWORK_WIFI NETWORK_NO NETWORK_MOBILE
	 * 
	 * @author 18834
	 * **/
	public static int getNetWorkStatus(Context context) {

		ConnectivityManager netManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo wifi = netManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifi != null && wifi.isConnectedOrConnecting()) {
			return NETWORK_WIFI;
		} else {
			NetworkInfo mobile = netManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			
			if (mobile != null && mobile.isConnectedOrConnecting()) {
				return NETWORK_MOBILE;
			} else {
				return NETWORK_NO;
			}
		}
	}

}
