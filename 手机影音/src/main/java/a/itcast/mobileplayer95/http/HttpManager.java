package a.itcast.mobileplayer95.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：Magic on 2017/9/7 05:07
 * 邮箱：bonian1852@163.com
 */

public class HttpManager {

    private static final String TAG = "HttpManager";

    private OkHttpClient okHttpClient;

    private static HttpManager httpManager;
    // TODO: 2017/9/19 Handler 将HttpManager请求到的数据发送到主线程[OkHttp将数据结果返回到主线程]
    private Handler mHandler;

    // TODO: 2017/9/7 把 public HttpManager() 改成 private HttpManager() 改成单例模式
    private HttpManager() {
        this.okHttpClient = new OkHttpClient();
        // TODO: 2017/9/19 mHandler = new Handler(Looper.getMainLooper()); 不管在哪里创建这个 HttpManager 这个Handler永远是关联到主线程的
        // TODO: 2017/9/19 然后构建 HttpManager 的时候给 Handler赋个值
        mHandler = new Handler(Looper.getMainLooper());
    }

    // TODO: 2017/9/7 获取HttpManager的单例对象
    public static HttpManager getInstance(){
        if (httpManager == null){
            httpManager = new HttpManager();
        }

        return httpManager;
    }
    // TODO: 2017/9/7 单例对象中不是所有的方法都是静态的
    // TODO: 2017/9/7 发起get请求 在子线程发起网络请求
    public void get(String url,BaseCallBack baseCallBack){

        // TODO: 2017/9/7 Request requset = new Request.Builder().url(url).build(); 创建请求参数
        Request requset = new Request.Builder().url(url).build();
        // TODO: 2017/9/19 发起异步的请求
        doRequest(requset,baseCallBack);
    }

    // TODO: 2017/9/7 post 在子线程发起post请求
    public void post(String url, Map<String,String> params,BaseCallBack baseCallBack) {

        // TODO: 2017/9/7 FormBody.Builder builder = new FormBody.Builder(); 创建表单 表单请求体
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        // TODO: 2017/9/7 遍历表单里面的 key和Value [entry]:条目 [params]:参数个数 [entrySet]:条目集
        for (Map.Entry<String,String> entry :params.entrySet()){
            bodyBuilder.add(entry.getKey(),entry.getValue());
        }

        // TODO: 2017/9/7 RequestBody body = bodyBuilder.build(); 创建请求体
        RequestBody body = bodyBuilder.build();

        // TODO: 2017/9/7 Request request = new Request.Builder().url(url).post(body).build(); 创建请求参数
        Request request = new Request.Builder().url(url).post(body).build();
        doRequest(request,baseCallBack);


    }

    // TODO: 2017/9/7 doRequest 做的请求
    private void doRequest(Request request, final BaseCallBack baseCallBack) {
        // TODO: 2017/9/7 Call call = okHttpClient.newCall(requset); 创建请求对象 [requset]:请求
        Call call = okHttpClient.newCall(request);

        // TODO: 2017/9/7 call.enqueue(new Callback() 发起异步的请求 [enqueue]:排队
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //这里面没有错误码 直接给个-1
               mHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       baseCallBack.onFailure(-1,e);
                   }
               });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //a.itcast.mobileplayer95.utils.LogUtils.e(TAG,"HttpManager.onResponse,当前线程="+Thread.currentThread());
                // TODO: 2017/9/7 String result = response.body().string(); 获取请求的服务器数据
                // TODO: 2017/9/19 [response]:响应 [body];内容
                // TODO: 2017/9/19 final String result = response.body().string(); 在子线程读取服务器数据
                final String result = response.body().string();
                // TODO: 2017/9/19 在主线程更新界面
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()){
                            //String result = response.body().string();//这里的内容 要放到外面来
                            // TODO: 2017/9/19 根据 baseCallBack.type 类型的不同,做不同的数据解析
                            // TODO: 2017/9/19 有可能某些请求 **返回的不是 JSON 数据**，有可能是String类型的数据,也有可能是bitmap图片,或多媒体
                            if (baseCallBack.type == String.class){
                                //String数据 直接返回String类型的数据
                                baseCallBack.onSuccess(result);
                            }else {
                                try {
                                    // TODO: 2017/9/19 Gson转换的时,就根据baseCallBack的泛型的类型,转换下Json对象
                                    // TODO: 2017/9/19 指定了一个Bean,直接进行 Json 转换
                                    Object obj = new Gson().fromJson(result, baseCallBack.type);
                                    baseCallBack.onSuccess(obj);
                                } catch (Exception e) {
                                    // TODO: 2017/9/19 或者返回的 **JSON 解析出错**，可以返回出错的原因
                                    baseCallBack.onFailure(-1,new RuntimeException("Json解析出错:"+result));
                                    e.printStackTrace();
                                }
                            }

                            // LogUtils.e(TAG,"OkHttpTestActivity.postInChildThread,result:\"+result");
                        }else {
                            baseCallBack.onFailure(response.code(),new RuntimeException("获取到服务器的错误状态"));
                        }
                    }
                });
            }
        });
    }

}
