package com.king.view.util;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @since 2017/3/28
 */

public final class PointUtils {


    private static final String POINTS = "points";
    private static final String POINT = "point";
    private static final String X = "x";
    private static final String Y = "y";

    private PointUtils(){
        throw new AssertionError();
    }

    public static List<Point> getListPointByResourceJson(Context context, String fileName) throws IOException {
        InputStream inputStream =  context.getClassLoader().getResourceAsStream(fileName);
        return getListPointByJson(inputStreamToString(inputStream));
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {

        if(inputStream!=null){

            StringBuilder sb = new StringBuilder();

            byte[] data = new byte[4096];
            int len;

            while ((len = inputStream.read(data))!=-1){
                sb.append(new String(data,0,len));
            }
            if(inputStream!=null){
                inputStream.close();
            }
            return sb.toString();

        }
        return null;
    }

    public static List<Point> getListPointByJsonInputStream(InputStream inputStream) throws IOException {
        return getListPointByJson(inputStreamToString(inputStream));
    }

    public static List<Point> getListPointByJson(String json){
        if(json!=null){
            try {
                List<Point> list = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONObject(POINTS).getJSONArray(POINT);

                int size = jsonArray.length();
                for(int i=0;i<size;i++){
                    JSONObject jsonPoint = jsonArray.getJSONObject(i);
                    Point point = new Point(jsonPoint.getInt(X),jsonPoint.getInt(Y));
                    list.add(point);
                }

                return list;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
