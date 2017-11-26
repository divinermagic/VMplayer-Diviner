package a.itcast.mobileplayer95.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Diviner
 */
public class AboutActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        //设置标题栏 为 ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("关于");
        //显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //展开时的颜色
        collapsing.setExpandedTitleColor(Color.WHITE);
        //折叠后的颜色
        collapsing.setCollapsedTitleTextColor(Color.WHITE);
    }

    @Override
    /**
     * 判断如果是返回按钮的话 就关闭当前页面
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
