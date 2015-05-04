package com.li.volley.webservice;

import java.util.Map;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.li.volley.utils.MyLogger;

/**
 * 一嗨网络请求工具类
 * 
 * @author 18834
 * **/
public class EhaiVolleyFactory {

	private EhaiVolleyFactory(Context context) {
		queue = Volley.newRequestQueue(context);
	};

	private RequestQueue queue;

	/***
	 * 接口回调接口
	 * 
	 * @param <T>
	 *            为EhaiJsonResponse<T> 或者EhaiJsonArrayResponse<T>
	 * **/
	public interface EhaiResponseListener<T> {
		void onEhaiResponse(int type, T response);
	};

	private Dialog progressDialog;

	public final static int HTTP_POST = Method.POST;
	public final static int HTTP_GET = Method.GET;
	public final static int HTTP_PUT = Method.PUT;
	public final static int HTTP_DELETE = Method.DELETE;
	public final static int TIME_OUT = 25000;

	public static String md5Key = "MD-ANDROID121953";

	private String Tag = "Ehai";

	private MyLogger logger = MyLogger.getLogger(getClass().getSimpleName());

	private static EhaiVolleyFactory ehaiVolleyFactory = null;

	public static EhaiVolleyFactory getVolleyFactory(Context context) {
		if (ehaiVolleyFactory == null) {
			ehaiVolleyFactory = new EhaiVolleyFactory(context);
		}
		return ehaiVolleyFactory;
	}

	private ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	};

	/**
	 * 数据中有对象的其他请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param Httptype
	 *            http请求方式 EhaiVolleyFactory.HTTP_POST
	 * @param params
	 *            接口請求參數
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonRequstDiolag(Context context, final int type,
			int Httptype, String params,
			final EhaiResponseListener<T> listener, Class responseClass) {
		progressDialog = getDialog(context);
		EhaiGsonRequest<T> ehaiGsonRequest = new EhaiGsonRequest<T>(Httptype,
				getResponseUrl(type), params, new Listener<T>() {

					@Override
					public void onResponse(T response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						listener.onEhaiResponse(type, response);
					}
				}, errorListener, responseClass) {
		};
		try {
			Map<String, String> heard = ehaiGsonRequest.getHeaders();
		} catch (AuthFailureError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ehaiGsonRequest.setTag(Tag);
		logger.d(ehaiGsonRequest.getRetryPolicy().getCurrentTimeout());
		queue.add(ehaiGsonRequest);
		queue.start();
		logger.d(System.currentTimeMillis());
		// queue.add(new ClearCacheRequest((Cache)
		// ehaiGsonRequest.getCacheEntry(), null));
	}

	/**
	 * 数据中有对象的其他请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param Httptype
	 *            http请求方式 EhaiVolleyFactory.HTTP_POST
	 * @param params
	 *            接口請求參數
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonRequstNo(final int type, int Httptype,
			String params, final EhaiResponseListener<T> listener,
			Class responseClass) {
		EhaiGsonRequest<T> ehaiGsonRequest = new EhaiGsonRequest<T>(Httptype,
				getResponseUrl(type), params, new Listener<T>() {

					@Override
					public void onResponse(T response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						listener.onEhaiResponse(type, response);
					}
				}, errorListener, responseClass) {
		};
		ehaiGsonRequest.setTag(Tag);
		queue.add(ehaiGsonRequest);

	}

	public void EhaiSendStringRequstDiolag(Context context, final int type,
			int Httptype, String params,
			final EhaiResponseListener<String> listener) {
		progressDialog = getDialog(context);
		EhaiStringRequest ehaiStringRequest = new EhaiStringRequest(Httptype,
				getResponseUrl(type), params, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						listener.onEhaiResponse(type, response);
					}
				}, errorListener);
		ehaiStringRequest.setTag(Tag);
		queue.add(ehaiStringRequest);

	}

	public void EhaiSendStringRequstNo(final int type, int Httptype,
			String params, final EhaiResponseListener<String> listener) {
		EhaiStringRequest ehaiStringRequest = new EhaiStringRequest(Httptype,
				getResponseUrl(type), params, new Listener<String>() {

					@Override
					public void onResponse(String response) {
						listener.onEhaiResponse(type, response);
					}
				}, errorListener);
		ehaiStringRequest.setTag(Tag);
		queue.add(ehaiStringRequest);
	}

	/**
	 * 数据中是数组的其他请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param Httptype
	 *            http请求方式 EhaiVolleyFactory.HTTP_POST
	 * @param params
	 *            接口請求參數
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonArrayRequstNo( final int type,
			int Httptype, String params,
			final EhaiResponseListener<T> listener, Class responseClass) {

		EhaiGsonArrayRequest<T> ehaiGsonArrayRequest = new EhaiGsonArrayRequest<T>(
				Httptype, getResponseUrl(type), params, new Listener<T>() {

					@Override
					public void onResponse(T response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						listener.onEhaiResponse(type, response);

					}
				}, errorListener, responseClass);
		ehaiGsonArrayRequest.setTag(Tag);
		queue.add(ehaiGsonArrayRequest);
	}

	/**
	 * 数据中有对象数组的其他请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param Httptype
	 *            http请求方式 EhaiVolleyFactory.HTTP_POST
	 * @param params
	 *            接口請求參數
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonArrayRequstDiolag(final int type, int Httptype,
			String params, final EhaiResponseListener<T> listener,
			Class responseClass) {
		EhaiGsonArrayRequest<T> ehaiGsonArrayRequest = new EhaiGsonArrayRequest<T>(
				Httptype, getResponseUrl(type), params, new Listener<T>() {

					@Override
					public void onResponse(T response) {
						listener.onEhaiResponse(type, response);

					}
				}, errorListener, responseClass);
		ehaiGsonArrayRequest.setTag(Tag);
		queue.add(ehaiGsonArrayRequest);
	}

	/**
	 * 数据中有对象的GeET请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonRequstNo(final int type,
			final EhaiResponseListener<T> listener, Class responseClass) {
		this.EhaiSendJsonRequstNo(type, Method.GET, null, listener,
				responseClass);
	}

	/**
	 * 数据中有数组的GET请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonRequstDiolag(Context context, final int type,
			final EhaiResponseListener<T> listener, Class responseClass) {
		this.EhaiSendJsonRequstDiolag(context, type, Method.GET, null,
				listener, responseClass);
	}

	/**
	 * 数据中有数组的GET请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数组数据的对象
	 * ***/
	public <T> void EhaiSendJsonArrayRequstNo(final int type,
			final EhaiResponseListener<T> listener, Class responseClass) {
		this.EhaiSendJsonArrayRequstNo(type, Method.GET, null, listener,
				responseClass);
	}

	/**
	 * 数据中有数组的GET请求方法
	 * 
	 * @param type
	 *            请求方法type
	 * @param listener
	 *            数据返回的回调监听
	 * @param responseClass
	 *            返回数组数据的对象
	 * ***/
	public <T> void EhaiSendJsonArrayRequstDiolag(final int type,
			final EhaiResponseListener<T> listener, Class responseClass) {
		this.EhaiSendJsonArrayRequstDiolag(type, Method.GET, null, listener,
				responseClass);
	}

	public void canalReq(Object tag) {
		queue.cancelAll(tag);
	}

	public void canalAllReq() {
		queue.cancelAll(Tag);
	}

	public <T> void addReq(Request<T> req) {
		queue.add(req);
	}

	private String getResponseUrl(int type) {
		// StringBuffer url = new StringBuffer(UrlFactory.BASE_URL);
		StringBuffer url = new StringBuffer(
				"http://192.168.5.145/service/OrderInfoList");

		switch (type) {
		case 0:
			// url.append("service/user");
			break;

		default:
			break;
		}
		logger.d(url.toString());
		return url.toString();
	}

	private Dialog getDialog(Context context) {
		Dialog dialog = null;
		try {
//			dialog = DialogUtil.progressDialog(context);
			dialog = new ProgressDialog(context);
		} catch (Exception e) {
			if (progressDialog != null)
				progressDialog.dismiss();
			e.printStackTrace();
		}
		if (dialog != null)
			dialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {

					if (keyCode == KeyEvent.KEYCODE_BACK
							&& event.getAction() == KeyEvent.ACTION_DOWN) {
						canalAllReq();
						dialog.dismiss();
					}
					return false;
				}
			});
		return dialog;
	}
}
