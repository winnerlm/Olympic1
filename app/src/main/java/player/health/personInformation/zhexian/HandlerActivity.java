package player.health.personInformation.zhexian;

/**
 * Created by 刘明 on 2018/12/14.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.wenhaibo.olympic.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class HandlerActivity extends Activity {
    private Button submit;
    private Button reset;
    private EditText editText;
    private GraphicalView Gview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new SumbitClickListener());
        reset = (Button) findViewById(R.id.reset);
        editText = (EditText) findViewById(R.id.editOne);
        String testString = editText.getText().toString();
        //默认的数值
        double [] Ypoints = new double[]{5,4,6,3,5};;
        if(!"".equals(testString)&&testString!=null){
            String[] strings = testString.split(",");
            Ypoints = new double[strings.length];
            for(int i=0;i<strings.length;i++){
                Ypoints[i] = Integer.valueOf(strings[i]);
            }
        }
        lineView(Ypoints);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class SumbitClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            String tempString = editText.getText().toString();
            String[] tempStrings =null;
            if(tempString.contains(",")){
                tempStrings = tempString.split(",");
            }else if(tempString.contains("，")){
                tempStrings = tempString.split("，");
            }
            double []temppoints = new double[tempStrings.length];
            for(int i=0;i<tempStrings.length;i++){
                temppoints[i] = Integer.valueOf(tempStrings[i]);
            }
            lineView(temppoints);
        }

    }

    //折线图
    public void lineView(double [] Ypoints){
        //同样是需要数据dataset和视图渲染器renderer
        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("情况线");
        String[] strs = new String[Ypoints.length];
        for(int i=0;i<Ypoints.length;i++){
            series.add(i+1, Ypoints[i]);
            strs[i] =(i+1)+"天";
        }
        mDataset.addSeries(series);
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        //设置图表的X轴的当前方向
        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        mRenderer.setXTitle("日期");//设置为X轴的标题
        mRenderer.setYTitle("身体情况");//设置y轴的标题
        mRenderer.setAxisTitleTextSize(20);//设置轴标题文本大小
        mRenderer.setChartTitle("身体状态图");//设置图表标题
        mRenderer.setChartTitleTextSize(60);//设置图表标题文字的大小
        mRenderer.setLabelsTextSize(30);//设置标签的文字大小
        mRenderer.setLegendTextSize(30);//设置图例文本大小
        mRenderer.setPointSize(10f);//设置点的大小
        mRenderer.setYAxisMin(50);//设置y轴最小值是0
        mRenderer.setYAxisMax(200);
        mRenderer.setYLabels(10);//设置Y轴刻度个数（貌似不太准确）
        mRenderer.setXAxisMax(strs.length+1);
        mRenderer.setShowGrid(true);//显示网格
        //将x标签栏目显示如：1,2,3,4替换为显示1月，2月，3月，4月
        for(int i=0;i<strs.length;i++){
            mRenderer.addXTextLabel(i+1, strs[i]);
        }
        mRenderer.setXLabels(0);//设置只显示如1月，2月等替换后的东西，不显示1,2,3等
        mRenderer.setMargins(new int[] { 20, 30, 15, 20 });//设置视图位置

        XYSeriesRenderer r = new XYSeriesRenderer();//(类似于一条线对象)
        r.setColor(Color.BLUE);//设置颜色
        r.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        r.setFillPoints(true);//填充点（显示的点是空心还是实心）
        r.setDisplayChartValues(true);//将点的值显示出来
        r.setChartValuesSpacing(10);//显示的点的值与图的距离
        r.setChartValuesTextSize(25);//点的值的文字大小
        r.setLineWidth(3);//设置线宽
        mRenderer.addSeriesRenderer(r);

        LinearLayout layout = (LinearLayout) findViewById(R.id.lineChar);
        Gview = ChartFactory.getLineChartView(this, mDataset, mRenderer);
        Gview.setBackgroundColor(Color.BLACK);
        //移除原有的LinearLayout中的视图控件
        layout.removeAllViewsInLayout();
        layout.addView(Gview);
    }
}