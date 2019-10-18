package com.example.firstapplication.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:
 */
public class TextToSpeechUtil implements TextToSpeech.OnInitListener {
    private Context context;
    private static TextToSpeech tts;

    private TextToSpeech getTTS() {
        if (null == tts)
            synchronized (TextToSpeechUtil.class) {
                if (null == tts ) {
                    tts = new TextToSpeech(context, this);
                }
                return tts;
            }
        else
            return tts;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //不支持中文就将语言设置为英文

            }
        }
    }

    public void textToSpeak(Context context,String s){
        this.context = context;
        getTTS().speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }
    public void destoty(){
        context = null;
        tts = null;
    }

}
