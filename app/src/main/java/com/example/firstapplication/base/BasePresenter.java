package com.example.firstapplication.base;

import java.lang.ref.WeakReference;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:连接Model和View层的桥梁
 */
public abstract class BasePresenter<V extends IView> implements IPresenter  {
    private WeakReference<V> mWeakActivity;

    public BasePresenter(V view){
        attachView(view);
    }

    /**
     * 绑定view
     * @param v
     */
    public void attachView(V v){
        mWeakActivity = new WeakReference<V>(v);
    }

    /**
     * 解绑view
     */
    public void detachView(){
        if(null != mWeakActivity){
            mWeakActivity.clear();
            mWeakActivity = null;
            System.gc();
        }
    }

    //获取View
    public V getView(){
        if(null != mWeakActivity){
            return mWeakActivity.get();
        }
        return  null;
    }

    protected boolean isViewAttach(){
        return null != mWeakActivity && null != mWeakActivity.get();
    }

}
