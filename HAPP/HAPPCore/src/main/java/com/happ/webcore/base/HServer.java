package com.happ.webcore.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.exception.HException;
import com.happ.webcore.base.log.HLog;

@RestController
public class HServer {

	protected HLog log = new HLog(getClass());


	@ExceptionHandler(Throwable.class)
	public HResponse handleException(Throwable ex) {
		log.error("未知异常", ex);
		return new HResponse(HAPPConstance.ERROR_UNKNOW.getCode(), HAPPConstance.ERROR_UNKNOW.getMsg());
	}

	@ExceptionHandler(HException.class)
	public HResponse handleHException(HException ex) {
		return new HResponse(ex);
	}
	
	public HResponse buildOk(List<Object> data) {
		HResponse response = new HResponse();
		response.setData(data);
		return response;
	}
	/***
	 * 执行成功
	 * @param requestParams
	 * @return
	 */
	public HResponse buildOk(Map<String,Object> requestParams) {
		HResponse response = new HResponse();
		response.setPageIndex(requestParams.get(HAPPConstance.PAGE_INDEX));
		response.setPageSize(requestParams.get(HAPPConstance.PAGE_SIZE));
		response.setPageTotal(requestParams.get(HAPPConstance.PAGE_TOTAL));
		return response;
	}
	
	/***
	 * 执行成功
	 * @param data
	 * @param requestParams
	 * @return
	 */
	public HResponse buildOk(List<Object> data,Map<String,Object> requestParams) {
		HResponse response = new HResponse();
		response.setData(data);
		response.setPageIndex(requestParams.get(HAPPConstance.PAGE_INDEX));
		response.setPageSize(requestParams.get(HAPPConstance.PAGE_SIZE));
		response.setPageTotal(requestParams.get(HAPPConstance.PAGE_TOTAL));
		return response;
	}
	
	
	/***
	 * 检测输入参数
	 * @param requestParams
	 * @return
	 */
	public Map<String, Object> checkInputrequestParams(Map<String, Object> requestParams){
		if(requestParams==null) {
			requestParams=new HashMap<String, Object>();
		}
		if(!requestParams.containsKey(HAPPConstance.PAGE_INDEX)) {
			requestParams.put(HAPPConstance.PAGE_INDEX, Integer.valueOf(1));
		}
		if(!requestParams.containsKey(HAPPConstance.PAGE_SIZE)) {
			requestParams.put(HAPPConstance.PAGE_SIZE, Integer.valueOf(10));
		}
		return requestParams;
	}
	
	public void checkPramsNull(Map<String, Object> requestParams,String mapKey,int errorCode) {
		if(!requestParams.containsKey(mapKey)) {
			//TODO 增加读取错误信息
			throw new HException(errorCode, "");
		}
	}
}
