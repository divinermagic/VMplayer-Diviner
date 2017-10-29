package a.itcast.mobileplayer95.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import a.itcast.mobileplayer95.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = "WebViewActivity";
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.webview)
    WebView webview;

//    @Bind(R.id.webview)
//    WebView webview;
//    @Bind(R.id.progress_bar)
//    ProgressBar progressBar;
//    @Bind(R.id.tool_bar)
//    Toolbar toolBar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // android.R.id.home == getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        //初始化标题
        setSupportActionBar(toolBar);//把我们的ToolBar设置为标题栏 并且把系统的去掉 不然会崩溃
        getSupportActionBar().setTitle("VMPlayer");//修改标题文字
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示标题栏自带 返回 按钮


        //初始化 WebView
        WebSettings webSettings = webview.getSettings();
        webSettings.setSupportMultipleWindows(true);//支持多个窗口设置
        webSettings.setAllowFileAccess(true);//设置允许访问文件
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);//为了显示网页动态效果 需要打开JavaScript开关
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN : 适应屏幕，内容将自动缩放
         */
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);

        //获取网页的加载进度
        webview.setWebChromeClient(new WebChromeClient() {
            // TODO: 2017/9/21 复写方法 快捷键 Windows系统 ctrl + o  Mac系统 control + o
            @Override
            // TODO: 2017/9/21 onProgressChanged 当网页的加载进度时发生变化时被调用
            public void onProgressChanged(WebView view, int newProgress) {
                //此时[曾经] 网页加载的进度条暂时完成 但是网页还是显示不出来 因为这个网页有特效我们用这个特效需要打开一个开关
                progressBar.setProgress(newProgress);
            }
        });

        //现在 网页可以正常加载了
        webview.setWebViewClient(new WebViewClient() {
            @Override
            // TODO: 2017/9/23 onPageStarted 开始加载界面
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //让进度条加载可见
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            // TODO: 2017/9/23 onPageFinished 界面加载结束了
            public void onPageFinished(WebView view, String url) {
                //让进度条加载不可见
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
