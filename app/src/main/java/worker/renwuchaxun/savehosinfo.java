package worker.renwuchaxun;

/**
 * Created by 刘明 on 2018/11/30.
 */
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by yeyongfei on 18-10-6.
 */

public class savehosinfo {
    public static boolean saveHospitalInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("hospitalNames",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("河北北方学院附属第一医院", "http://www.hbbfyfy.com/index.html");
        edit.putString("中国人民解放军第二五一医院", "http://www.251yy.com.cn/");
        edit.putString("张家口市第一医院", "http://www.zjkdyyy.com/");
        edit.putString("张家口市妇幼保健院", "http://www.zjksfybjy.com/cn/index.asp");
        edit.putString("张家口市第二医院", "http://deyy.zjknews.com/");
        edit.putString("张家口市第四医院", "http://www.eye123.com/");
        edit.commit();
        return true;
    }
    //从hospitalNames.xml文件中获取存储医院网址
    public static String getHospitalInfo(Context context, String hospitalname) {
        SharedPreferences sp = context.getSharedPreferences("hospitalNames",
                Context.MODE_PRIVATE);
        String hospitalName = sp.getString(hospitalname, null);
        return hospitalName;
    }
}

