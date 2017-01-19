package com.jaipurpinkpanthers.android.util;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Jay on 27-01-2016.
 */
public class InternetOperations {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();
    //public static String SERVER_URL = "http://192.168.1.103/jppbackend/index.php/json/";
    /*public static String SERVER_UPLOADS_URL = "http://192.168.0.124/jppbackend/uploads/";
    public static String SERVER_THUMB_URL = "http://192.168.0.124/admin/index.php/image/index?name=";*/
    //String local_url="http://192.168.1.105/jppbackend/index.php/json/";
    public static String SERVER_URL = "http://admin.jaipurpinkpanthers.com/index.php/json/";
    public static String SERVER_UPLOADS_URL = "http://admin.jaipurpinkpanthers.com/uploads/";
    public static String SERVER_THUMB_URL = "http://admin.jaipurpinkpanthers.com/index.php/image/index?name=";
    public static String SERVER_WIDTH_250 = "&width=250";
    public static String SERVER_WIDTH_400 = "&width=400";

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postBlank(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                //.post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static boolean checkIsOnlineViaIP() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            //Process ipProcess = runtime.exec("/system/bin/ping -c 1 192.168.0.124");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return false;
    }
}
