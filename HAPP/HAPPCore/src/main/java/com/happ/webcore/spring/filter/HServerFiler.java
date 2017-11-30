package com.happ.webcore.spring.filter;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.happ.webcore.base.HResponse;
import com.happ.webcore.base.conf.HAPPConstance;
import com.happ.webcore.base.exception.HException;
import com.happ.webcore.base.filter.annotation.HFilterServerAfter;
import com.happ.webcore.base.filter.annotation.HFilterServerBefore;

@Aspect
@Component
public class HServerFiler {

	@Around("execution(* com.happ.**.server.**.*(..))")
	public Object doFilterServer(ProceedingJoinPoint pjp) throws Throwable {
		Signature ss = pjp.getSignature();
		if (ss instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) ss;
			if (methodSignature.getReturnType() != HResponse.class) {
				return pjp.proceed();
			}
			Method method = methodSignature.getMethod();
			HResponse response = doBefore(pjp, method);
			if (response != null) {
				return response;
			}
			Object retObj = pjp.proceed();
			response = doAfter(retObj, method);
			if (response != null) {
				return response;
			}
			return retObj;

		} else {
			return pjp.proceed();
		}
	}

	/***
	 * 处理执行结果
	 * 
	 * @param exeObj
	 * @param method
	 * @return
	 */
	private HResponse doAfter(Object exeObj, Method method) {
		HFilterServerAfter before = method.getAnnotation(HFilterServerAfter.class);
		if (before != null) {
			Class<?> filters[] = before.filter();
			try {
				for (Class<?> filter : filters) {
					com.happ.webcore.base.filter.HFilterServerAfter ff = (com.happ.webcore.base.filter.HFilterServerAfter) filter.newInstance();
					if (ff.doFilter(exeObj)) {
						break;
					}
				}
				return null;
			} catch (HException e) {
				return new HResponse(e.getCode(), e.getMsg());
			} catch (Exception e) {
				e.printStackTrace();
				return new HResponse(HAPPConstance.ERROR_UNKNOW.getCode(), HAPPConstance.ERROR_UNKNOW.getMsg());
			}
		}
		return null;
	}

	/***
	 * 处理执行前
	 * 
	 * @param pjp
	 * @param method
	 * @return
	 */
	private HResponse doBefore(ProceedingJoinPoint pjp, Method method) {
		HFilterServerBefore before = method.getAnnotation(HFilterServerBefore.class);
		if (before != null) {
			//
			Class<?> filters[] = before.filter();
			try {
				for (Class<?> filter : filters) {
					com.happ.webcore.base.filter.HFilterServerBefore ff = (com.happ.webcore.base.filter.HFilterServerBefore) filter.newInstance();
					if (ff.doFilter(pjp.getArgs())) {
						break;
					}
				}
				return null;
			} catch (HException e) {
				return new HResponse(e.getCode(), e.getMsg());
			} catch (Exception e) {
				e.printStackTrace();
				return new HResponse(HAPPConstance.ERROR_UNKNOW.getCode(), HAPPConstance.ERROR_UNKNOW.getMsg());
			}
		}
		return null;
	}

}
