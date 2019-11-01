package com.example.firstapplication.z_test;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author: gl
 * @CreateDate: 2019/10/31
 * @Description: RxAndroid是响应式编程
 */
public class RxAndroidTest {
//     public static void main(String[] args){
//         Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//             @Override
//             public void subscribe(ObservableEmitter<String> e) throws Exception {
//                 e.onNext("A");
//                 e.onNext("B");
//                 e.onNext("C");
//                 e.onComplete();
//             }
//         });
//         Observer<String> observer = new Observer<String>() {
//             @Override
//             public void onSubscribe(Disposable d) {
//                 showToast("onSubscribe");
//             }
//
//             @Override
//             public void onNext(String value) {
//                 showToast("onNext"+value);
//             }
//
//             @Override
//             public void onError(Throwable e) {
//                 showToast("onError");
//             }
//
//             @Override
//             public void onComplete() {
//                 showToast("onComplete");
//             }
//         };
//    }

    //    Observable:被观察者，用来处理事件的派发
    //Observer 观察者，观察目标为Observable


    private static void showToast(String msg){

        Log.i("RxAndroidTest",msg);
    }
}
