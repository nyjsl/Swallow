package org.nyjsl.swallow.http.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class OKHttpManager {

    private OkHttpClient client = null;

    private static OKHttpManager INSTANCE = null;

    private static MediaType FORM_DATA = MediaType.parse("multipart/form-data; charset=utf-8");

    private Call call = null ;

    private RequestListener listener = null;
    /**
     * request fininshed or canceled
     */
    private boolean canceledOrFinished = false;
    /**
     * default connect timeout 15 seconds
     */
    private static final int DEF_CONN_TIMEOUT = 15;

    /**
     * default read timeout 40 seconds
     */
    private static final int DEF_READ_TIMEOUT = 40;
    /**
     * default write timeout 40 seconds
     */
    private static final int DEF_WRITE_TIMEOUT = 40;

    /**
     * constructor
     */
    private OKHttpManager(){
        if(null == client){
            client = new OkHttpClient();
            client.setConnectTimeout(DEF_CONN_TIMEOUT, TimeUnit.SECONDS);
            client.setReadTimeout(DEF_READ_TIMEOUT, TimeUnit.SECONDS);
            client.setWriteTimeout(DEF_WRITE_TIMEOUT, TimeUnit.SECONDS);
        }

    }

    /**
     * SINGLETON
     * @return
     */
    public static OKHttpManager getInstance(){
        if (null == INSTANCE ) {
            synchronized (OKHttpManager.class) {

                if (null == INSTANCE) {
                    INSTANCE = new OKHttpManager();
                }

            }
        }
        return INSTANCE;
    }

    /**
     * send post requset
     * @param url
     * @param params
     * @throws IOException
     */
    public void post(String url,HashMap<String,String> params, RequestListener listener){

        RequestBody requestBody = buildRequestBodyFromMap(params);
        request(url, listener, requestBody);

    }



    /**
     * multipart upload
     */
    public void upload(String url,HashMap<String,String> params,File[] files, RequestListener listener){
        url +=buildEncodeUrlPairsFromMap(params);
        RequestBody requestBody = buildRequestBodyFromFiles(files);
        request(url, listener, requestBody);

    }

    /**
     * create and send request to url and listern for result
     * @param url
     * @param listener
     * @param requestBody
     */
    private void request(String url, RequestListener listener, RequestBody requestBody) {
        this.listener = listener;
        try {
            Request request = new Request.Builder().url(url).post(requestBody).build();
            call = client.newCall(request);
            handler.obtainMessage(RequestListener.START).sendToTarget();
            canceledOrFinished = false;
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    canceledOrFinished = true;
                    Message msg = new Message();
                    msg.what = RequestListener.FAILURE;
                    msg.obj = e.toString();
                    handler.sendMessage(msg);

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    canceledOrFinished = true;
                    Message msg = new Message();
                    if (null != response && response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        String result = responseBody.string();
                        msg.what = RequestListener.SUCCESS;
                        msg.obj = result;
                    } else {
                        msg.what = RequestListener.FAILURE;
                        msg.obj = "error:" + response.code();
                    }
                    handler.sendMessage(msg);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = RequestListener.FAILURE;
            msg.obj = e.toString();
            handler.sendMessage(msg);
        }

    }

    private Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RequestListener.START:
                    listener.onStart();
                    break;
                case RequestListener.FAILURE:
                    String errors = (String) msg.obj;
                    listener.onFailure(errors);
                    break;
                case RequestListener.SUCCESS:
                    String result = (String) msg.obj;
                    listener.onSuccess(result);
                    break;
                case RequestListener.CANCELED:
                    listener.onCanceled();
                    break;
                default:
                    break;
            }
        }
    };

    public interface  RequestListener {

        int START = 0;
        int FAILURE = 1;
        int SUCCESS = 2;
        int CANCELED = 3;

        void onStart();
        void onFailure(String errors);
        void onSuccess(String result);
        void onCanceled();
    }

    /**
     * cancel requset
     */
    public void cancel(){
        if(null != call && !canceledOrFinished){
            canceledOrFinished = true;
            call.cancel();
            handler.obtainMessage(RequestListener.CANCELED).sendToTarget();
        }
    }

    /**
     * build parameters   application/x-www-form-urlencoded; charset=utf-8
     * @param params
     * @return
     */
    private RequestBody buildRequestBodyFromMap(HashMap<String, String> params){

        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if (params != null && params.size() > 0) {
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> next = iterator.next();
                formEncodingBuilder.add(next.getKey(), next.getValue());
            }
        }
        return  formEncodingBuilder.build();
    }

    /**
     * build params in url
     * @param params
     * @return
     */
    private  String buildEncodeUrlPairsFromMap(HashMap<String, String> params){
        String result = "";
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder("?");
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> next = iterator.next();

                sb.append(next.getKey())
                .append("=")
                .append(next.getValue());
                if(iterator.hasNext()){
                    sb.append("&");
                }
            }
            try {
                result =   URLEncoder.encode(sb.toString(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * build parameters multipart/form-data ; charset=utf-8
     * @param files
     * @return
     */
    private RequestBody buildRequestBodyFromFiles(File[] files){
        MultipartBuilder multipartBuilder = new MultipartBuilder();
        if(null == files || files.length>0){
            for(File f:files){
                multipartBuilder.addFormDataPart("img", f.getName(), RequestBody.create(FORM_DATA, f));
            }
        }
        return multipartBuilder.build();
    }
}
