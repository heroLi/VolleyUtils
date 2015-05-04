package com.li.volley.webservice;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.li.volley.response.EhaiJsonResponse;
import com.li.volley.utils.MD5;
import com.li.volley.utils.MyLogger;

/***
 * 一嗨接口返回請求類,返回jsonobject对象，使用前导入 Volley Gson包
 * 
 * @author 18834
 * @param EhaiJsonResponse
 *            <T>
 * 
 **/
public class EhaiGsonRequest<T> extends Request<T> {

	private Listener<T> listener;

	private String paramsString;

	private Class resultClass;

	private String url = "";

	private static MyLogger logger = MyLogger.getLogger("EhaiGsonRequest");

	private static final String PROTOCOL_CHARSET = "utf-8";

	private static final String PROTOCOL_CONTENT_TYPE = String.format(
			"application/json; charset=%s", PROTOCOL_CHARSET);

	private final String userAgent = "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	public EhaiGsonRequest(int method, String url, String param,
			Listener<T> listener, ErrorListener errorListener,
			Class responseClass) {
		super(method, url, errorListener);
		this.listener = listener;
		this.paramsString = param;
		logger.d(paramsString);
		url = url;
		resultClass = responseClass;
	}

	public EhaiGsonRequest(String url, Listener<T> listener,
			ErrorListener errorListener, Class responseClass) {
		this(Method.GET, url, null, listener, errorListener, responseClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String data;
		Gson gson = new Gson();
		try {
			data = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			logger.d(data == null ? "" : data);
			Type type = GsonType(EhaiJsonResponse.class, resultClass);
			Response<T> result = null;
			try {
				result = (Response<T>) Response.success(
						gson.fromJson(data, type),
						HttpHeaderParser.parseCacheHeaders(response));
			} catch (Exception e) {
				// result = (Response<T>) Response.success(
				// gson.fromJson(data, EhaiResult.class),
				// HttpHeaderParser.parseCacheHeaders(response));
			}
			return result;
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	// 表单提交參數
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return super.getParams();
	}

	// 重组参数
	@SuppressLint("NewApi")
	@Override
	public byte[] getBody() throws AuthFailureError {
		// TODO Auto-generated method stub
		return paramsString == null ? super.getBody() : paramsString
				.getBytes(Charset.forName("utf-8"));
	}

	/**
	 * 设置Content-Type  当我们getbody（）时 json设置Content-Type
	 * **/
	@Override
	public String getBodyContentType() {
		// TODO Auto-generated method stub
		return PROTOCOL_CONTENT_TYPE;
	}

	// 設置響應時間
	@Override
	public RetryPolicy getRetryPolicy() {
		// 默认25s
		RetryPolicy policy = new DefaultRetryPolicy(EhaiVolleyFactory.TIME_OUT,
				0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return policy;
	}

	/**
	 * 设置请求头
	 * 设置Content-Type 无效
	 * **/
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> header = new HashMap<String, String>();
		header.put("charset", "utf-8");
//		header.put("Content-Type", "application/json");
		// header.put("Accept-Language", "zh-CN,zh;q=0.8");
		// header.put("Accept-Encoding", "gzip");
		header.put("Authorization",
				MD5.getMD5Str(EhaiVolleyFactory.md5Key + paramsString));
		// header.put("Authorization", "7FBCC6121C4448BAB7774FDFA018A45A");
		return header;
	}

	private ParameterizedType GsonType(final Class rawClass, final Type args) {
		return new ParameterizedType() {

			@Override
			public Type getRawType() {
				return rawClass;
			}

			@Override
			public Type getOwnerType() {
				return args;
			}

			@Override
			public Type[] getActualTypeArguments() {
				return null;
			}
		};
	}
}
