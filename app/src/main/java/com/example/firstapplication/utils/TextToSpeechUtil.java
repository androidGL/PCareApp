package com.example.firstapplication.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * @Author: gl
 * @CreateDate: 2019/10/17
 * @Description:
 */
public class TextToSpeechUtil {
    private static TextToSpeech tts;

    private static TextToSpeech getTTS(Context context,String s) {
        if (null == tts)
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if (i == TextToSpeech.SUCCESS) {
                        tts.setSpeechRate(1.0f);
                        tts.setPitch(1.0f);
                        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
                        int result = tts.setLanguage(Locale.CHINESE);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            //不支持中文就将语言设置为英文
                            Toast.makeText(context, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });
        return tts;
    }


    public static void textToSpeak(Context context, String s) {
        getTTS(context,s).speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public static void destoty() {
        if(null != tts) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }
}
