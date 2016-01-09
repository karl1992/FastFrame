
package com.wells.fastframe.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;


@Target (ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter="setOnClickListener",listenerType=View.OnClickListener.class,callBackMethod="onClick")
public @interface OnClick {
    int [] value();

}
