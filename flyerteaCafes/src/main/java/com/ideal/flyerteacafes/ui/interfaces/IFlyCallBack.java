package com.ideal.flyerteacafes.ui.interfaces;

/**
 * 请求数据返回接口
 *
 * @author fly
 */
public interface IFlyCallBack<T> {

     void flyStart();

     void flySuccess(T result) ;

     void flyError();

     void flyCancelled() ;

     void flyFinished() ;
}
