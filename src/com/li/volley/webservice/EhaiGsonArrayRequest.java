package com.li.volley.webservice;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
import com.li.volley.response.EhaiJsonArrayResponse;
import com.li.volley.utils.MD5;
import com.li.volley.utils.MyLogger;

/***
 * 一嗨Volley请求类 返回json数组对象EhaiJsonArrayResponse
 * 
 * @author 18834
 * @param EhaiJsonArrayResponse
 *            <T>
 * **/
public class EhaiGsonArrayRequest<T> extends Request<EhaiJsonArrayResponse<T>> {

	private final Listener<EhaiJsonArrayResponse<T>> mListener;

	private final Class clazz;

	private final String userAgent = "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

	private String parmas;

	private static MyLogger logger = MyLogger.getLogger("EhaiGsonArrayRequest");

	private String url = "";

	private static final String PROTOCOL_CHARSET = "utf-8";

	private static final String PROTOCOL_CONTENT_TYPE = String.format(
			"application/json; charset=%s", PROTOCOL_CHARSET);

	public EhaiGsonArrayRequest(String url, Listener<EhaiJsonArrayResponse<T>> listener,
			ErrorListener errorListener, Class rep) {
		this(Method.GET, url, null, listener, errorListener, rep);
	}

	public EhaiGsonArrayRequest(int method, String url, String par,
			Listener<EhaiJsonArrayResponse<T>> listener,
			ErrorListener errorListener, Class rep) {
		super(method, url, errorListener);
		mListener = listener;
		url = url;
		clazz = rep;
		parmas = par;
	}

	@Override
	protected Response<EhaiJsonArrayResponse<T>> parseNetworkResponse(
			NetworkResponse response) {
		Gson gson = new Gson();
		String data;
		try {
			data = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));

			Type type = gsonType(EhaiJsonArrayResponse.class, clazz);
			logger.d(data == null ? "" : data);
			return Response.success(
					(EhaiJsonArrayResponse<T>) gson.fromJson(data, type),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}

	}

	@Override
	protected void deliverResponse(EhaiJsonArrayResponse<T> response) {
		mListener.onResponse(response);
	}

	/**
	 * 设置Content-Type 当我们getbody（）时 json设置Content-Type
	 * **/
	@Override
	public String getBodyContentType() {
		// TODO Auto-generated method stub
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		return parmas == null ? super.getBody() : parmas.getBytes();
	}

	private static ParameterizedType gsonType(final Class raw,
			final Type... args) {

		return new ParameterizedType() {

			@Override
			public Type getRawType() {
				return raw;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}

			@Override
			public Type[] getActualTypeArguments() {
				return args;
			}
		};

	}

	// 设置请求头
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("charset", "UTF-8");
		// headers.put("Content-Type", "application/json");
		headers.put("User-Agent", userAgent);
		headers.put("Authorization",
				MD5.getMD5Str(EhaiVolleyFactory.md5Key + url));
		return headers;
	}

	// 设置响应超时
	@Override
	public RetryPolicy getRetryPolicy() {
		// TODO Auto-generated method stub
		RetryPolicy policy = new DefaultRetryPolicy(EhaiVolleyFactory.TIME_OUT,
				0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return policy;
	}
}
