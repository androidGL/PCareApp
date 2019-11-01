package com.example.firstapplication.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:连接Model和View层的桥梁
 */
public abstract class BasePresenter<V extends IView> implements IPresenter  {
    private WeakReference<V> mWeakActivity;
    //Disposable容器，收集Disposable，主要用于内存泄漏管理
    private CompositeDisposable mDisposables;

    public BasePresenter(V view){
        attachView(view);
    }

    /**
     * 绑定view
     * @param v
     */
    public void attachView(V v){
        mWeakActivity = new WeakReference<V>(v);
        mDisposables = new CompositeDisposable();
    }

    /**
     * 解绑view
     */
    public void detachView(){
        if(null != mWeakActivity){
            mDisposables.clear();
            mDisposables = null;
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
    /**
     * @param disposable 添加Disposable到CompositeDisposable
     *                   通过解除disposable处理内存泄漏问题
     */
    protected boolean addDisposable(Disposable disposable) {
        if (isNullOrDisposed(disposable)) {
            return false;
        }
        return mDisposables.add(disposable);
    }
    /**
     * @param d 判断d是否为空或者dispose
     * @return true:一次任务未开始或者已结束
     */
    protected boolean isNullOrDisposed(Disposable d) {
        return d == null || d.isDisposed();
    }

    /**
     * @param d 判断d是否dispose
     * @return true:一次任务还未结束
     */
    protected boolean isNotDisposed(Disposable d) {
        return d != null && !d.isDisposed();
    }

}
