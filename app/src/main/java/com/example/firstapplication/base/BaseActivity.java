package com.example.firstapplication.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author gelan
 * @date 2019/10/9
 * Description:activity基类，负责view层
 */
public abstract class BaseActivity<P extends BasePresenter,CONTRACT> extends Activity {
    protected P p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //弱引用：可以避免P层对V层的持有，当activity finish时释放内存
        p = getPresenter();
        p.attachView(this);
    }
    public abstract int getLayoutId();

    //从子类中获取具体的契约
    protected abstract P getPresenter();
    //让P层做什么需求
    public abstract CONTRACT getContract();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        p.detachView();
    }
}
