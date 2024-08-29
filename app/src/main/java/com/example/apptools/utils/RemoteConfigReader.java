package com.example.apptools.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteConfigReader extends AsyncTask<String, Void, String> {

    private Context mContext;
    public  RemoteConfigReader(Context context){
        this.mContext=context;
    }

    private static final String TAG = RemoteConfigReader.class.getSimpleName();

    @Override
    protected String doInBackground(String... urls) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append(",");
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading remote config file: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        // 在这里处理读取到的配置文件数据
        Log.d(TAG, "Remote config file content: " + result);
        XDataUtil.setXDataValue(mContext,"result",result);
        XDataUtil.showToast(mContext,result);
    }
}
