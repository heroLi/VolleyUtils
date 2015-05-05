package com.li.volley.response;

import java.io.Serializable;

import com.li.volley.webservice.EhaiVolleyFactory;

/**
 * 构建请求对象
 * 
 * url  请求url
 * params 请求参数
 * requestType  请求接口类型
 *	httpType http请求类型
 * **/
public class UrlRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url = "";

	private String params;

	private int requestType = 0;
	
	private int httpType=EhaiVolleyFactory.HTTP_GET;
	
	

	public int getHttpType() {
		return httpType;
	}

	public void setHttpType(int httpType) {
		this.httpType = httpType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	
	@Override
	public String toString() {
		
		StringBuffer buffer = new StringBuffer() ;
		buffer.append("UrlRequest=="+"\turl="+url+"\tparams="+params+"\trequestType="+requestType);
		return buffer.toString();
	}

}
