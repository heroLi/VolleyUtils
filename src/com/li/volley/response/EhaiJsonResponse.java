package com.li.volley.response;

import java.io.Serializable;
import java.util.List;

/**
 * 一嗨接口返回json对象数据
 * 
 * 返回数据格式{ status: false ,message : "", data:""}
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

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public EhaiResult getResult() {
		return result;
	}

	public void setResult(EhaiResult result) {
		result = result;
	}

}
