package com.bwie.wangyanmin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 *@auther:王彦敏
 *@Date: 2019/11/26
 *@Time:14:03
 *@Description:
 * */
public class NetUtils {
    //1.实现私有的静态的对象
    private static NetUtils netUtils=new NetUtils();
    //2.实现静态方法
    private NetUtils(){

    }
    //单例模式
    public static NetUtils getInstance() {
        return netUtils;
    }
    //判断网络
    public static boolean net(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            return true;
        }else {
            return false;
        }

    }
    //判断wifi
    public static boolean isWifi(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
            return true;
        }else {
            return false;
        }

    }
    //判断是否又移动网络
    public static boolean isMoble(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
            return true;
        }else {
            return false;
        }

    }

    //获取字符串
    @SuppressLint("StaticFieldLeak")
    public void getData(final String httpUrl, final MyCallBack myCallBack){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                Log.e("e",s);
                myCallBack.onGson(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                String json ="";
                InputStream inputStream =null;

                try {
                    //子线程获取数据
                    URL url = new URL(httpUrl);
                    HttpURLConnection httpurlConnection = (HttpURLConnection) url.openConnection();
                    httpurlConnection.setRequestMethod("GET");
                    httpurlConnection.setReadTimeout(5000);
                    httpurlConnection.setConnectTimeout(5000);
                    //启动
                    httpurlConnection.connect();
                    //判断网络
                    if (httpurlConnection.getResponseCode()==200){
                        //有网直接获取流
                        inputStream= httpurlConnection.getInputStream();
                        json =io2String(inputStream);

                    }else {

                        Log.e("code","获取网络失败");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                return json;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    //流转字符串
    public String io2String(InputStream inputStream) throws IOException {
        //三大工具类
        byte[] bytes=new byte[1024];
        int len=-1;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        while ((len=inputStream.read(bytes))!=-1){
            byteArrayOutputStream.write(bytes,0,len);
        }
        byte[] bytes1 = byteArrayOutputStream.toByteArray();
        String json = new String(bytes1);
        return json;
    }

    //获取图片
    @SuppressLint("StaticFieldLeak")
    public void getPhoto(final String httpUrl, final ImageView imageView){
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                //主线程
                imageView.setImageBitmap(bitmap);
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap bitmap=null;
                InputStream inputStream =null;

                try {
                    //子线程获取数据
                    URL url = new URL(httpUrl);
                    HttpURLConnection httpurlConnection = (HttpURLConnection) url.openConnection();
                    httpurlConnection.setRequestMethod("GET");
                    httpurlConnection.setReadTimeout(5000);
                    httpurlConnection.setConnectTimeout(5000);
                    //启动
                    httpurlConnection.connect();
                    //判断网络
                    if (httpurlConnection.getResponseCode()==200){
                        //有网直接获取流
                        inputStream= httpurlConnection.getInputStream();
                        bitmap= io2Bitmap(inputStream);

                    }else {

                        Log.e("code","获取网络失败");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                return bitmap;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



    }

    //流转图片
    public Bitmap io2Bitmap(InputStream inputStream){
        return BitmapFactory.decodeStream(inputStream);
    }


    //判断网络



    //返回接口
    public interface MyCallBack{
        void onGson(String json);
    }
}
