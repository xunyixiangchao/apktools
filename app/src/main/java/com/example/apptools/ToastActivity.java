package com.example.apptools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.apptools.utils.LogToFile;


public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
//        Toast.makeText(this,"Toast",Toast.LENGTH_LONG).show();
        String[] list = new String[]{"Item1", "Item2"};
        toastMessage("Toast");
//        new MaterialAlertDialogBuilder(this).setTitle("Dialog").setItems(list, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        }).show();


        //这个转有smail代码如下
        String t1 = "Toast1";
        String t2 = "Toast1";
        LogToFile.write(t1 + "->" + t2);
        //####
//        new-instance v1, Ljava/lang/StringBuilder;
//
//        invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V
//
//        invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
//
//        const-string v2, "->"
//
//        invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
//
//        invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
//
//        invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
//
//        move-result-object v1
//        invoke-static {v1}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V

//        dex文件地址：https://wwrz.lanzout.com/iaiZT13p795g
//
//        使用方式：v1是要导出的内容
//        invoke-static {v1}, Lcom/example/apptools/utils/LogToFile;->write(Ljava/lang/String;)V

    }

    public void toastMessage(String message) {
        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show();

    }
}