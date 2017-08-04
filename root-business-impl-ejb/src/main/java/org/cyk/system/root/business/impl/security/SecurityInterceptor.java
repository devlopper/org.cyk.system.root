package org.cyk.system.root.business.impl.security;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.cyk.system.root.business.impl.UserSessionBusiness;

@Secure @Interceptor @Priority(Interceptor.Priority.APPLICATION)
public class SecurityInterceptor implements Serializable {

	private static final long serialVersionUID = 4559361895142168000L;

	@Inject private UserSessionBusiness userSessionData;
	
	@AroundInvoke
    public Object invoke(final InvocationContext context) throws Exception {
        System.out.println("SecurityInterceptor.invoke() : "+context.getMethod().getName()+" by "+userSessionData.getUserAccount().getCredentials().getUsername());
        return context.proceed();
    }

}