package a.itcast.mobileplayer95.http;


import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：Magic on 2017/9/7 05:31
 * 邮箱：bonian1852@163.com
 */

    public abstract class BaseCallBack<T> {

    // TODO: 2017/9/19 从第19行 到 第30行 不用去深刻理解它 知道它的效果直接来用就可以
    // TODO: 2017/9/19 getSuperclassTypeParameter 它的作用就是:Gson转换需要一个泛型的类, 每次我们new BaseCallBack的时候,就会给Type赋值,赋的值就是我们的泛型

    Type type;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public BaseCallBack()
    {
        type = getSuperclassTypeParameter(getClass());
    }


    public abstract void onFailure(int code ,Exception e) ;

    // TODO: 2017/9/7 onResponse 改个名字 onSuccess [result]:结果
    public abstract void onSuccess(T t);

}
