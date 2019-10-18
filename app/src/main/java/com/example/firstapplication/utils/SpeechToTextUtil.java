package com.example.firstapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:
 */
public class SpeechToTextUtil {
    private Activity context;
    private final String TAG = "SpeechToTextUtil";
    private final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 0;
    private SpeechRecognizer speechRecognizer;
    private Callback iCallback;


    private SpeechRecognizer init(){
        if(speechRecognizer==null){
            requestPermission();
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            speechRecognizer.setRecognitionListener(new SampleRecognitionListener());
        }
        return speechRecognizer;
    }

    public void startSpeak(Activity context,Callback iCallback){
        this.context = context;
        this.iCallback=iCallback;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.CHINESE.toString());
        init().startListening(intent);
    }
    public void stopSpeak(){
        speechRecognizer.stopListening();
    }
    public void destotySpeechToSpeak(){
        if(null == speechRecognizer)
            return;
        speechRecognizer.cancel();
        speechRecognizer.destroy();
    }
    private class SampleRecognitionListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d(TAG, "onReadyForSpeech Start");
            Log.d(TAG, "onReadyForSpeech End");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech Start");
            Log.d(TAG, "onBeginningOfSpeech End");
        }

        @Override
        public void onRmsChanged(float v) {
            Log.d(TAG, "onRmsChanged Start");
            Log.d(TAG, "onRmsChanged End");
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            Log.d(TAG, "onBufferReceived Start");
            Log.d(TAG, "onBufferReceived End");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech Start");
            Log.d(TAG, "onEndOfSpeech End");

        }

        @Override
        public void onError(int error) {
            Log.d(TAG, "onError Start");

            switch (error) {
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    resetText("网络链接超时");
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    resetText("网络错误或者没有权限");
                    break;
                case SpeechRecognizer.ERROR_AUDIO:
                    resetText("音频发生错误");
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    resetText("连接出错");
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    resetText("服务器出错");
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    resetText("什么也没有听到");
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    resetText("没有匹配到合适的结果");
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    resetText("RecognitionService已经启动,请稍后");
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    resetText("请赋予APP权限,另请（Android6.0以上）确认动态申请权限");
                    break;
                default:
                    break;

            }
            Log.d(TAG, "onError End");
        }
        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "onResults Start");
            String key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList mResult = results.getStringArrayList(key);

            String[] result = new String[0];
            if (mResult != null) {
                result = new String[mResult.size()];
            }
            if (mResult != null) {
                mResult.toArray(result);
            }
            Log.d(TAG, "Recognize Result:" + result);
            iCallback.success(result[0]);
            Log.d(TAG, "onResults End");
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d(TAG, "onPartialResults Start");
            Log.d(TAG, "onPartialResults End");

        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            Log.d(TAG, "onEvent Start");
            Log.d(TAG, "onEvent End");

        }
    }
    //记得6.0以后动态请求权限,不然会一直提示网络错误
    private void requestPermission() {
        Log.d(TAG, "requestPermission");
        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
    }

    //给EditText 设置 Text
    private void resetText(String text) {
        Log.d(TAG, text);
    }

    public interface Callback{
        public void success(String result);
    }

}
