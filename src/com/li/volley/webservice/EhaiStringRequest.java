package com.li.volley.webservice;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.li.volley.utils.MD5;
import com.li.volley.utils.MyLogger;

/**
 * 一嗨接送数据请求Request
 * 
 * @author 18834
 * **/
public class EhaiStringRequest extends Request<String> {

	private final Listener<String> reListener;
	
	private final String userAgent = "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	private final String reParmas;
	
	private static MyLogger logger = MyLogger.getLogger("EhaiStringRequest");
	
	private String url ="";
	
	private static final String PROTOCOL_CHARSET = "utf-8";

	private static final String PROTOCOL_CONTENT_TYPE = String.format(
			"application/json; charset=%s", PROTOCOL_CHARSET);

	public EhaiStringRequest(int method, String url, String parmas,
			Listener<String> listener,ErrorListener errorListener) {
		super(method, url, errorListener);
		url = url;
		reListener = listener;
		reParmas = parmas;
		logger.d(reParmas);
	}

	public EhaiStringRequest(String url, Listener<String> listener,ErrorListener errorListener) {
		this(Method.GET, url, null, listener,errorListener);
	}

	// 解析服务器返回数据
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String reString = "";
		try {
			reString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			reString = new String(response.data);
		}
		logger.d(reString);
		return Response.success(reString,
				HttpHeaderParser.parseCacheHeaders(response));
	}
	
	/**
	 * 设置Content-Type  当我们getbody（）时 json设置Content-Type
	 * **/
	@Override
	public String getBodyContentType() {
		// TODO Auto-generated method stub
		return PROTOCOL_CONTENT_TYPE;
	}


	// 进行成功回调
	@Override
	protected void deliverResponse(String response) {
		reListener.onResponse(response);

	}

	// 重新定制参数
	@Override
	public byte[] getBody() throws AuthFailureError {
		// TODO Auto-generated method stub
		return reParmas == null ? super.getBody() : reParmas.getBytes();
	}


	// 设置请求头
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("charset", "utf-8");
//		headers.put("Content-Type", "application/json; charset=utf-8");
		headers.put("User-Agent", userAgent);
		headers.put("Authorization", MD5.getMD5Str(EhaiVolleyFactory.md5Key+url));
		return headers;
	}

	// 设置响应超时
	@Override
	public RetryPolicy getRetryPolicy() {
		// TODO Auto-generated method stub
		RetryPolicy policy = new DefaultRetryPolicy(
				EhaiVolleyFactory.TIME_OUT,
				0,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return policy;
	}

}
