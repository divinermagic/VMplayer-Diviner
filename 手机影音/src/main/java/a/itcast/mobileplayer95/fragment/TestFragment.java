package a.itcast.mobileplayer95.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a.itcast.mobileplayer95.R;

/**
 * 作者：Magic on 2017/9/5 19:28
 * 邮箱：bonian1852@163.com
 */

public class TestFragment extends Fragment {

    private TextView tv_text;

    /**
     * 获取 Fragment 对象
     * // TODO: 2017/9/5 去学习下 setArguments 和 getArguments
     * @param content
     * @return
     */
    // TODO: 2017/9/5 getInstance:获得实例  Arguments:参数
    public static TestFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString("content",content);


        TestFragment testFragment = new TestFragment();
        testFragment.setArguments(arguments);

        return testFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test,null);
        tv_text = (TextView) view.findViewById(R.id.tv_text);

        //获取初始化参数
        Bundle arguments = getArguments();
        String content = arguments.getString("content");
        tv_text.setText(content);

        return view;

    }
}
