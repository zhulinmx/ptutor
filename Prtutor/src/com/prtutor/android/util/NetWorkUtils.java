package com.prtutor.android.util;

import android.content.Context;
import android.net.NetworkInfo.State;

public class NetWorkUtils {
	private Context mContext;
	public State wifiState = null;
	public State mobileState = null;
	
	public NetWorkUtils(Context context) {
		mContext = context;
	}
	public enum NetWorkState {
		WIFI, MOBILE, NONE;
	}
	
}
