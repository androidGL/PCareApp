package com.example.firstapplication.base;

import java.lang.ref.WeakReference;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:连接Model和View层的桥梁
 */
public abstract class BasePresenter<V extends BaseActivity,M extends BaseModel,CONTRACT>  {
    protected M m;
    private WeakReference<V> vWeakReference;

    public BasePresenter(){
        m = getModel();
    }

    /**
     * 绑定view
     * @param v
     */
    public void attachView(V v){
        vWeakReference = new WeakReference<>(v);
    }

    /**
     * 解绑view
     */
    public void detachView(){
        if(null != vWeakReference){
            vWeakReference.clear();
            vWeakReference = null;
            System.gc();
        }
    }

    //获取View
    public V getView(){
        if(null != vWeakReference){
            return vWeakReference.get();
        }
        return  null;
    }

    //获取子类具体契约（Model层和View层协商的共同业务）
    public abstract CONTRACT getContract();

    protected abstract M getModel();

}
