package com.itheima.vmplayer.util;

import com.itheima.vmplayer.bean.LyricBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wschun on 2016/10/3.
 */

public class LoadLyricUtil {

    public static List<LyricBean> loadLyricFromFile(File file){
        List<LyricBean> lyricBeanList=new ArrayList<>();
        if (file==null || !file.exists()){
            lyricBeanList.add(new LyricBean("未成功加载歌词",0));
            return  lyricBeanList;
        }

        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            String readLine = bufferedReader.readLine();
            while (readLine!=null){
                List<LyricBean> lyricBeanLists=formatterLyricFromReadLine(readLine);
                lyricBeanList.addAll(lyricBeanLists);
                readLine=bufferedReader.readLine();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return lyricBeanList;
    }

    private static List<LyricBean> formatterLyricFromReadLine(String readLine) {
        List<LyricBean> lyricBeanList=new ArrayList<>();
//        [00:23.47]在爱情里面进退 最多被消费
        String[] lyarcArray = readLine.split("]");
        for (int i = 0; i < lyarcArray.length - 1; i++) {
            String content=lyarcArray[lyarcArray.length-1];
            int startPoint=parseTime(lyarcArray[i]);
            lyricBeanList.add(new LyricBean(content,startPoint));
        }
        return lyricBeanList;
    }

    private static int parseTime(String s) {
//        [00:23.47
        String[] array = s.split(":");
        String min=array[0].substring(1);
        array = array[1].split("\\.");
        String con=array[0];
        String hmiu=array[1];


        int time=Integer.parseInt(min)*60*1000+Integer.parseInt(con)*1000+Integer.parseInt(hmiu)*10;


        return time;
    }

}
