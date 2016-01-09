
package com.wells.fastframe.ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.app.Activity;
import android.view.View;

public class InjectUtils {
    @SuppressWarnings("unused")
    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FINDVIEW_BY_ID  = "findViewById";

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);

    }

   
    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                   
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String methodName = eventBase.callBackMethod();
                        //                        OnClick onClick = (OnClick)annotation;
                        //                        onClick.value();
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            int[] viewIds = (int[]) valueMethod.invoke(annotation, null);
                            
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(methodName, method);
                           
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class<?>[] { listenerType }, handler);
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                setter.invoke(view, listener);

                            }
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        }
    }

 
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        int layoutId = contentView.value();
        activity.setContentView(layoutId);
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                try {
                    Method method = clazz.getMethod(METHOD_FINDVIEW_BY_ID, int.class);
                    Object view = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
