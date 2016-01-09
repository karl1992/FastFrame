
package com.wells.fastframe.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;


public class ListenerInvocationHandler implements InvocationHandler {

    
    private Object target;
    
    private HashMap<String, Method> methodMap = new HashMap<String, Method>();

    /**
     * Setter method for property <tt>target</tt>.
     * 
     * @param tagret value to be assigned to property target
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    public ListenerInvocationHandler(Object target) {
        this.target = target;

    }

    /** 
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target!=null){
            String methodName = method.getName();
            method =  methodMap.get(methodName);
            if(method!=null){
                return method.invoke(target, args);
            }
            
        }
        return null;
    }
    
    
    public void addMethod(String name,Method method){
        methodMap.put(name, method);
    }

}
