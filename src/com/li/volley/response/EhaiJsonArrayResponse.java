package com.li.volley.response;

import java.io.Serializable;
import java.util.List;

/**
 * 一嗨请求返回数组数据
 * 
 * @author 18834
 * **/
public class EhaiJsonArrayResponse<T> implements Serializable{


	private EhaiResult result;
	
	private List<T> data;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		data = data;
	}


	public EhaiResult getResult() {
		return result;
	}

	public void setResult(EhaiResult result) {
		result = result;
	}


}
