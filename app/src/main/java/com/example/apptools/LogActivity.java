package com.example.apptools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.apptools.utils.XDataUtil;
import com.example.apptools.utils.XOkHttpUtil;
import cn.soul.android.component.DiceFingerMsg;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import cn.soulapp.lib.basic.app.MartianApp;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Log.e("tag","message");
        Map<String, String> map = new HashMap<>();
        map.put("key1","value1");
        XDataUtil.getXDataIntValue(this,4,map);

        XOkHttpUtil.soulInterceptor(null,null);

        int nextInt = new SecureRandom().nextInt(3) + 1;
        if(XDataUtil.isChecked(MartianApp.b())){
            nextInt=XDataUtil.getXDataIntValue(MartianApp.b(),1);
        }
        DiceFingerMsg diceFingerMsg = new DiceFingerMsg(nextInt);


    }
}