package player.health.personInformation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenhaibo.olympic.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 作者：Created by 高宇 on 2016/11/20.
 * 邮箱：741208260@qq.com
 */

public class Information_list_adapter extends BaseAdapter {
   private  Information_SQL_adapter Sql;
    private Context context;
    private List<Person> persons;


    public Information_list_adapter(Context context) {

        this.context = context;
        Sql = new Information_SQL_adapter(context);
        persons = Sql.findAll();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.e("gaoyu_information","打印了");
        //得到某位置对应的person对象
        Person person = persons.get(position);
        int rate = Integer.parseInt(person.getName());
        view = View.inflate(context, R.layout.person_information_item, null);
        TextView tv_data_information = (TextView) view.findViewById(R.id.tv_data_information);
        TextView tv_rate_information = (TextView) view.findViewById(R.id.tv_rate_information);
        ImageView iv_remind_information  = (ImageView)view.findViewById(R.id.iv_remind_information);
        //日期转换成标准格式
        long dateTime = Long.parseLong(person.getNumber());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
        Date date = new Date(dateTime);
        String time  =dateFormat.format(date);
        tv_data_information.setText("测试日期："+time);
        tv_rate_information.setText("测试结果："+rate+"次/分钟");
        if (rate>60&&rate<90){

        }else{
            iv_remind_information.setImageResource(R.drawable.select_jinggao);
        }
        return view;
    }
}
