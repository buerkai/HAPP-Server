package com.happ.webcore.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.exception.HException;
import com.happ.webcore.base.log.HLog;

@ControllerAdvice
public class HRestExceptionHandler  {
	protected HLog log = new HLog(getClass());
	
	//400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public HResponse requestNotReadable(HttpMessageNotReadableException ex){
        System.out.println("400..requestNotReadable");
        ex.printStackTrace();
        return new HResponse(HAPPConstance.ERROR_UNKNOW);
    }
    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    public HResponse requestTypeMismatch(TypeMismatchException ex){
        System.out.println("400..TypeMismatchException");
        ex.printStackTrace();
        return new HResponse(HAPPConstance.ERROR_UNKNOW);
    }
    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public HResponse requestMissingServletRequest(MissingServletRequestParameterException ex){
        System.out.println("400..MissingServletRequest");
        ex.printStackTrace();
        return new HResponse(HAPPConstance.ERROR_UNKNOW);
    }
    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public HResponse request405(){
        System.out.println("405...");
        return new HResponse(HAPPConstance.ERROR_UNKNOW);
    }
    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public HResponse request406(){
        System.out.println("404...");
        return new HResponse(HAPPConstance.ERROR_UNKNOW);
    }
    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class,HttpMessageNotWritableException.class})
    public HResponse server500(RuntimeException runtimeException){
        System.out.println("500...");
        return new HResponse(HAPPConstance.ERROR_UNKNOW);
    }
    
    
    
	
    
}
