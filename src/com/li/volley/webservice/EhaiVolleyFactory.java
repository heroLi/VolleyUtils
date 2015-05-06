package com.li.volley.webservice;

import java.util.Map;

import android.R;
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
import com.li.volley.response.EhaiJsonArrayResponse;
import com.li.volley.response.EhaiJsonResponse;
import com.li.volley.response.UrlRequest;
import com.li.volley.utils.MyLogger;
import com.li.volley.utils.NetUtils;
import com.li.volley.utils.ToastUtils;

/**
 * 一嗨网络请求工具类
 * 
 * @author 18834
 * **/
public class EhaiVolleyFactory {

	private EhaiVolleyFactory(Context context) {
		mContext = context;
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
	
	private Context mContext;

	private MyLogger logger = MyLogger.getLogger(getClass().getSimpleName());

	private static EhaiVolleyFactory ehaiVolleyFactory = null;

	public static EhaiVolleyFactory getVolleyFactory(Context context) {
		if (ehaiVolleyFactory == null) {
			synchronized (EhaiVolleyFactory.class) {
				if(ehaiVolleyFactory == null){
					
					ehaiVolleyFactory = new EhaiVolleyFactory(context);
				}
			}
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
	 * @param request
	 *            请求对象
	 * @param listener
	 *            数据返回的回调监听   T  EhaiJsonResponse<responseClass>
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonRequstDiolag(Context context,
			final UrlRequest request, final EhaiResponseListener<T> listener,
			Class responseClass) {
		if(!checkNetWork()){
			return ;
		}
		
		progressDialog = getDialog(context);
		logger.d(request.toString());
		EhaiGsonRequest<T> ehaiGsonRequest = new EhaiGsonRequest<T>(
				request.getHttpType(), request.getUrl(), request.getParams(),
				new Listener<T>() {

					@Override
					public void onResponse(T response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						listener.onEhaiResponse(request.getRequestType(),
								response);
					}
				}, errorListener, responseClass) {
		};
		ehaiGsonRequest.setTag(Tag);
		queue.add(ehaiGsonRequest);
	}

	/**
	 * 数据中有对象的其他请求方法
	 * 
	 * @param request
	 *            请求对象
	 * @param listener
	 *            数据返回的回调监听  T  EhaiJsonResponse<responseClass>
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonRequstNo(final UrlRequest request,
			final EhaiResponseListener<T> listener, Class responseClass) {
		if(!checkNetWork()){
			return ;
		}
		logger.d(request.toString());
		EhaiGsonRequest<T> ehaiGsonRequest = new EhaiGsonRequest<T>(
				request.getHttpType(), request.getUrl(), request.getParams(),
				new Listener<T>() {

					@Override
					public void onResponse(T response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						listener.onEhaiResponse(request.getRequestType(),
								response);
					}
				}, errorListener, responseClass) {
		};
		ehaiGsonRequest.setTag(Tag);
		queue.add(ehaiGsonRequest);

	}

	public void EhaiSendStringRequstDiolag(Context context,
			final UrlRequest request,
			final EhaiResponseListener<String> listener) {
		if(!checkNetWork()){
			return ;
		}
		logger.d(request.toString());
		progressDialog = getDialog(context);
		EhaiStringRequest ehaiStringRequest = new EhaiStringRequest(
				request.getHttpType(), request.getUrl(), request.getParams(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						listener.onEhaiResponse(request.getRequestType(),
								response);
					}
				}, errorListener);
		ehaiStringRequest.setTag(Tag);
		queue.add(ehaiStringRequest);

	}

	public void EhaiSendStringRequstNo(final UrlRequest request,
			final EhaiResponseListener<String> listener) {
		if(!checkNetWork()){
			return ;
		}
		logger.d(request.toString());
		EhaiStringRequest ehaiStringRequest = new EhaiStringRequest(
				request.getHttpType(), request.getUrl(), request.getParams(),
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						listener.onEhaiResponse(request.getRequestType(),
								response);
					}
				}, errorListener);
		ehaiStringRequest.setTag(Tag);
		queue.add(ehaiStringRequest);
	}

	/**
	 * 数据中是数组的其他请求方法
	 * 
	 * @param request
	 *            请求对象
	 * @param listener
	 *            数据返回的回调监听  T  EhaiJsonArrayResponse<responseClass>
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonArrayRequstNo(final UrlRequest request,
			final EhaiResponseListener<T> listener, Class responseClass) {
		if(!checkNetWork()){
			return ;
		}
		logger.d(request.toString());
		EhaiGsonArrayRequest<T> ehaiGsonArrayRequest = new EhaiGsonArrayRequest<T>(
				request.getHttpType(), request.getUrl(), request.getParams(),
				new Listener<T>() {

					@Override
					public void onResponse(T response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
						listener.onEhaiResponse(request.getRequestType(),
								response);

					}
				}, errorListener, responseClass);
		ehaiGsonArrayRequest.setTag(Tag);
		queue.add(ehaiGsonArrayRequest);
	}

	/**
	 * 数据中有对象数组的其他请求方法
	 * 
	 * @param request
	 *            请求对象
	 * @param listener
	 *            数据返回的回调监听  T  EhaiJsonArrayResponse<responseClass>
	 * @param responseClass
	 *            返回数据的对象
	 * */
	public <T> void EhaiSendJsonArrayRequstDiolag(final UrlRequest request,
			final EhaiResponseListener<T> listener, Class responseClass) {
		if(!checkNetWork()){
			return ;
		}
		logger.d(request.toString());
		
		EhaiGsonArrayRequest<T> ehaiGsonArrayRequest = new EhaiGsonArrayRequest<T>(
				request.getHttpType(), request.getUrl(), request.getParams(),
				new Listener<T>() {

					@Override
					public void onResponse(T response) {
						listener.onEhaiResponse(request.getRequestType(),
								response);

					}
				}, errorListener, responseClass);
		ehaiGsonArrayRequest.setTag(Tag);
		queue.add(ehaiGsonArrayRequest);
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
			// dialog = DialogUtil.progressDialog(context);
			dialog = new ProgressDialog(context,
					R.style.Theme_DeviceDefault_Dialog);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
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
	
	private boolean  checkNetWork() {
		if(NetUtils.NETWORK_NO==NetUtils.getNetWorkStatus(mContext)){
			ToastUtils.showText(mContext, "请连接网络");
			return false;
		}
		return true;
	}
}
