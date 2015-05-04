package com.li.volley.response;

import java.io.Serializable;
import java.util.List;

/**
 * 一嗨接口返回json对象数据
 * 
 * @author 18834
 * 
 * **/
public class EhaiJsonResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3985813888772810769L;
	/***
	 * 
	 * {"Result":{"IsSuccess":false,"Message":"不存在的用户名"},"Data":null}
	 * **/

	private EhaiResult result;

	private Class<T> data;

	public Class<T> getData() {
		return data;
	}

	public void setData(Class<T> data) {
		data = data;
	}

	public EhaiResult getResult() {
		return result;
	}

	public void setResult(EhaiResult result) {
		result = result;
	}

}
