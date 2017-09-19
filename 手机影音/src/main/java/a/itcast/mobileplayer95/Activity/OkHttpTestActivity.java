package a.itcast.mobileplayer95.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.util.List;

import a.itcast.mobileplayer95.R;
import a.itcast.mobileplayer95.bean.AreaBean;
import a.itcast.mobileplayer95.http.BaseCallBack;
import a.itcast.mobileplayer95.http.HttpManager;
import a.itcast.mobileplayer95.utils.URLProviderUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTestActivity extends AppCompatActivity {

    private static final String TAG = "OkHttpTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);

        loadData();
    }

    // TODO: 2017/9/7 loadData 加载数据
    private void loadData() {
        //String url = "192.168.78.21:8080/1";
        // TODO: 2017/9/7 URLProviderUtil.getMainPageUrl(int offset,int size); [offset]:偏移量 [size]:大小
        //String url = URLProviderUtil.getMainPageUrl(0,10);
        //LogUtils.e(TAG,"OkHttpTestActivity.loadData,url:"+url);
        String url = URLProviderUtil.getMVareaUrl();
        a.itcast.mobileplayer95.utils.LogUtils.e(TAG,"OkHttpTestActivity.loadData,url="+url);
        // TODO: 2017/9/19 有可能某些请求 **返回的不是 JSON 数据**，或者返回的 **JSON 解析出错**，都在可以在封装的框架里处理
        HttpManager.getInstance().get(url, new BaseCallBack<List<AreaBean>>() {
            @Override
            public void onFailure(int code, Exception e) {
                a.itcast.mobileplayer95.utils.LogUtils.e(TAG,"OkHttpTestActivity.onFailure,e="+e);
            }

//            @Override
//            public void onSuccess(String s) {
//                a.itcast.mobileplayer95.utils.LogUtils.e(TAG,"OkHttpTestActivity.onSuccess,s="+s);
//            }

            @Override
            public void onSuccess(List<AreaBean> areaBeen) {
                a.itcast.mobileplayer95.utils.LogUtils.e(TAG,"OkHttpTestActivity.onSuccess,areaBean="+areaBeen.size());
                Toast.makeText(OkHttpTestActivity.this, "我获取到数据了", Toast.LENGTH_SHORT).show();
            }


        });
    }

    // TODO: 2017/9/7 postInChildThread 在子线程发起post请求
    private void postInChildThread(String url) {
        // TODO: 2017/9/7 OkHttpClient okHttpClient = new OkHttpClient(); 创建请求客户端 [相当于打开浏览器]
        OkHttpClient okHttpClient = new OkHttpClient();

        // TODO: 2017/9/7 FormBody.Builder builder = new FormBody.Builder(); 创建表单 表单请求体
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("offset","0");
        bodyBuilder.add("size","10");

        // TODO: 2017/9/7 RequestBody body = bodyBuilder.build(); 创建请求体
        RequestBody body = bodyBuilder.build();

        // TODO: 2017/9/7 Request request = new Request.Builder().url(url).post(body).build(); 创建请求参数
        Request request = new Request.Builder().url(url).post(body).build();

        // TODO: 2017/9/7 Call call = okHttpClient.newCall(requset); 创建请求对象 [requset]:请求
        Call call = okHttpClient.newCall(request);

        // TODO: 2017/9/7 call.enqueue(new Callback() 发起异步的请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    // TODO: 2017/9/7 String result = response.body().string(); 获取请求的服务器数据
                    String result = response.body().string();
                    LogUtils.e(TAG,"OkHttpTestActivity.postInChildThread,result:"+result);
                }
            }
        });

    }

    // TODO: 2017/9/7 getInChildThread 在子线程发起网络请求
    private void getInChildThread(String url) {
        // TODO: 2017/9/7 OkHttpClient okHttpClient = new OkHttpClient(); 创建请求客户端 [相当于打开浏览器]
        OkHttpClient okHttpClient = new OkHttpClient();

        // TODO: 2017/9/7 Request requset = new Request.Builder().url(url).build(); 创建请求参数
        Request requset = new Request.Builder().url(url).build();

        // TODO: 2017/9/7 Call call = okHttpClient.newCall(requset); 创建请求对象 [requset]:请求
        Call call = okHttpClient.newCall(requset);

        // TODO: 2017/9/7 call.enqueue(new Callback() 发起异步的请求
        call.enqueue(new Callback() {
            @Override
            // TODO: 2017/9/7 call.enqueue(new Callback()-onFailure 请求发生异常
            public void onFailure(Call call, IOException e) {

            }

            @Override
            // TODO: 2017/9/7 call.enqueue(new Callback()-onResponse 获取到服务器数据了,即使是404等错误状态 也是获取到服务器数据
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    // TODO: 2017/9/7 String result = response.body().string(); 获取请求的服务器数据
                    String result = response.body().string();
                    LogUtils.e(TAG,"OkHttpTestActivity.getInChildThread,result:"+result);
                }
            }
        });
    }

    private void getMethod(final String url) {
        new Thread(){
            @Override
            public void run() {
                try {
                    // TODO: 2017/9/7 OkHttpClient okHttpClient = new OkHttpClient(); 创建请求客户端 [相当于打开浏览器]
                    OkHttpClient okHttpClient = new OkHttpClient();

                    // TODO: 2017/9/7 Request requset = new Request.Builder().url(url).build(); 创建请求参数
                    Request requset = new Request.Builder().url(url).build();

                    // TODO: 2017/9/7 Call call = okHttpClient.newCall(requset); 创建请求对象 [requset]:请求
                    Call call = okHttpClient.newCall(requset);

                    // TODO: 2017/9/7 Response response = call.execute(); 执行请求,获取服务器响应 [execute]:执行 [response]:响应
                    Response response = call.execute();

                    // TODO: 2017/9/7 response.isSuccessful() 只有在请求成功的时候才会获取数据
                    if (response.isSuccessful())
                    {
                        // TODO: 2017/9/7 String result = response.body().string(); 获取请求的服务器数据
                        String result = response.body().string();

                        LogUtils.e(TAG,"OkHttpTestActivity.getMethod,result:"+result);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
