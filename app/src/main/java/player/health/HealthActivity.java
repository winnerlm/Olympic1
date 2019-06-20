package player.health;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.ImageProcessing;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import player.PlayerActivity;
import player.health.personInformation.InformationActivity;
import player.health.personInformation.Information_SQL_adapter;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static java.lang.System.currentTimeMillis;

/**
 * Created by 刘明 on 2019/5/9.
 */
public class HealthActivity extends Activity {
    private Button btn;
    //曲线
    private Timer timer = new Timer();
    private TimerTask task;
    private static int gx;
    private static int j;
    //心率
    private static int beatsAvg;
    //心率总次数
    private static int beatsum = 0;
    //执行的次数
    private static int beatrate = 0;
    //平均心率
    private static int rate = 0;
    //保存的系统时间
    private static long saveTime;
    //提示手盖住的次数
    private int outrate = 0;
    //数据库执行方法
    private Information_SQL_adapter SQL;
    //摄像头开始闲置
    private int exitTime = 0;

    private static double flag = 1;
    private Handler handler;
    private String title = "pulse";
    private XYSeries series;

    private XYMultipleSeriesDataset mDataset;
    private GraphicalView chart;
    private XYMultipleSeriesRenderer renderer;
    private Context context;
    private int addX = -1;
    double addY;
    int[] xv = new int[300];
    int[] yv = new int[300];
    int[] hua = new int[]{9, 10, 11, 12, 13, 14, 13, 12, 11, 10, 9, 8, 7, 6, 7, 8, 9, 10, 11, 10, 10};

    //	private static final String TAG = "HeartRateMonitor";
    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private static SurfaceView preview = null;
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;
    //	private static View image = null;
    private static TextView text = null;
    private static TextView text1 = null;
    private static TextView text2 = null;
    private static PowerManager.WakeLock wakeLock = null;
    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];

    public static enum TYPE {
        GREEN, RED
    }


    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    private static double beats = 0;
    private static long startTime = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        btn=(Button)findViewById(R.id.btn) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HealthActivity.this,InformationActivity.class);
                startActivity(intent);
            }
        });
        //显示动画

        //曲线
        context = getApplicationContext();
        //这里获得main界面上的布局，下面会把图表画在这个布局里面
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);

        //这个类用来放置曲线上所有的点，是一个点的集合，根据这些点画曲线
        series = new XYSeries(title);

        //创建一个数据集的实例，其中可以封装图表所需的数据，这个数据集将被用来创建图表
        mDataset = new XYMultipleSeriesDataset();

        //将点集添加到这个数据集中
        mDataset.addSeries(series);

        //以下都是曲线的样式和属性等等的设置，renderer相当于一个用来给图表做渲染的句柄
        int color = Color.GREEN;
        PointStyle style = PointStyle.CIRCLE;
        renderer = buildRenderer(color, style, true);

        //设置好图标的样式
        setChartSettings(renderer, "X", "Y", 0, 300, 4, 16, Color.WHITE, Color.WHITE);

        //生成图表
        chart = ChartFactory.getLineChartView(context, mDataset, renderer);

        //将图标加到布局中去
        layout.addView(chart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));


		/*	       thread = new Thread(){
               public void arrayList(int u) {
	    		   ArrayList arrayList = new ArrayList();
	    		   arrayList.add(HardwareControler.readADC());
	   		}
	       };*/

        //这里的Handler实例将配合下面的Timer实例，完成定时更新图表的功能
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //刷新图表
                updateChart();
                super.handleMessage(msg);
            }
        };

        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        //曲线
        timer.schedule(task, 1, 20);

        preview = (SurfaceView) findViewById(R.id.preview);
        //通过判断取景大小来判断是否该住摄像头
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        //		image = findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
    }


    //曲线
    @Override
    public void onDestroy() {
        //当结束程序时 关掉Timer
        timer.cancel();
        super.onDestroy();
    }


    protected XYMultipleSeriesRenderer buildRenderer(int color, PointStyle style, boolean fill) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

        //设置图表中曲线本身的样式，包括颜色，点的大小及线的粗细
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.RED);
//		r.setPointStyle(null);
//		r.setFillPoints(fill);
        r.setLineWidth(1);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    //renderer, "X", "Y", 0, 300, 4, 16, Color.WHITE, Color.WHITE
    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String xTitle, String yTitle,
                                    double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        //图标的渲染

        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        //坐标轴颜色
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
        //显示网格
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GREEN);
        //每个格的单位
        renderer.setXLabels(20);
        renderer.setYLabels(10);

        renderer.setXTitle("Time");
        renderer.setYTitle("mmHg");
        //坐标轴方向
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setPointSize((float) 3);
        renderer.setShowLegend(false);
    }

    private void updateChart() {

        //设置好下一个增加的节点
        //    	addX = 10;
        //addY = (int)(Math.random() * 90 + 50);
        //		addY = (int)(HardwareControler.readADC());
        //    	addY=10+addY;
        //    	if(addY>1400)
        //    		addY=10;
        if (flag == 1)
            addY = 10;
        else {
//			addY=250;
            flag = 1;
            if (gx < 200) {
                if (hua[20] > 1) {
                    if (outrate < 4) {
                        makeText(HealthActivity.this, "请用指尖盖住摄像头！谢谢配合！", LENGTH_SHORT).show();
                    }
                    outrate++;
                    if (outrate == 5) {
                        makeText(HealthActivity.this, "系统5秒后退出", LENGTH_SHORT).show();
                        //计时操作
                        //每隔100毫秒执行onTick中的方法一次
                        //直到执行完10000/100次为止，最后会执行onFinish()中的方法
                        CountDownTimer cdt = new CountDownTimer(10000, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                //显示exitTime++
                            }

                            @Override
                            public void onFinish() {
                                System.exit(0);
                            }
                        };
                        cdt.start();
                    }
                    hua[20] = 0;
                }
                hua[20]++;
                return;
            } else
                hua[20] = 10;
            j = 0;

        }
        if (j < 20) {
            addY = hua[j];
            j++;
        }

        //移除数据集中旧的点集
        mDataset.removeSeries(series);
        //判断当前点集中到底有多少个点，因为屏幕最多容纳100个，所以当超过100是长度永远还是100
        int length = series.getItemCount();
        int bz = 0;
        //		addX = length;
        if (length > 300) {
            length = 300;
            bz = 1;
        }
        addX = length;
        //将旧的点集中x和y的数值取出来放入backup中，并且将x的值加1，造成曲线向右平移的效果
        for (int i = 0; i < length; i++) {
            xv[i] = (int) series.getX(i) - bz;
            yv[i] = (int) series.getY(i);
        }

        //点集先清空，为了做成新的点集而准备
        series.clear();
        mDataset.addSeries(series);
        //将新产生的点首先加入到点集中，然后在循环体中将坐标变换后的一系列点都重新加入到点集中
        //这里可以试验一下把顺序颠倒过来是什么效果，即先运行循环体，再添加新产生的点
        series.add(addX, addY);
        for (int k = 0; k < length; k++) {
            series.add(xv[k], yv[k]);
        }


        //在数据集中添加新的点集
        //		mDataset.addSeries(series);

        //视图更新，没有这一步，曲线不会呈现动态
        //如果在非UI主线程中，需要调用postInvalidate()，具体参考api
        chart.invalidate();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        camera = Camera.open();
        startTime = currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (beatrate != 0) {
            rate = beatsum / beatrate;
        }
        Log.e("gaoyu_beat", "哈哈哈哈哈" + rate);
        wakeLock.release();
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    /**
     * 相机预览方法
     * 这个方法中实现动态更新界面UI的功能，
     * 通过获取手机摄像头的参数来实时动态计算平均像素值、脉冲数，从而实时动态计算心率值。
     */
    private static Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        //实时预览帧数据
        public void onPreviewFrame(byte[] data, Camera cam) {
            if (data == null)
                throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null)
                throw new NullPointerException();
            if (!processing.compareAndSet(false, true))
                return;
            int width = size.width;
            int height = size.height;

            //图像处理
            int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
            gx = imgAvg;
            //text1.setText("平均像素值是" + String.valueOf(imgAvg));
            //像素平均值imgAvg,日志
            //	Log.i(TAG, "imgAvg=" + imgAvg);
            text1.setText("平均像素值是"+ String.valueOf(imgAvg));
            //像素平均值imgAvg,日志
            //Log.i(TAG, "imgAvg=" + imgAvg);
            if (imgAvg == 0 || imgAvg == 255) {
                processing.set(false);
                return;
            }

            int averageArrayAvg = 0;
            int averageArrayCnt = 0;
            for (int i = 0; i < averageArray.length; i++) {
                if (averageArray[i] > 0) {
                    averageArrayAvg += averageArray[i];
                    averageArrayCnt++;
                }
            }

            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
            TYPE newType = currentType;
            if (imgAvg < rollingAverage) {
                newType = TYPE.RED;
                if (newType != currentType) {
                    beats++;
                    flag = 0;
                    text2.setText("脉冲数是  " + String.valueOf(beats));
                    //					Log.e(TAG, "BEAT!! beats=" + beats);
                }
            } else if (imgAvg > rollingAverage) {
                newType = TYPE.GREEN;
            }

            if (averageIndex == averageArraySize)
                averageIndex = 0;
            averageArray[averageIndex] = imgAvg;
            averageIndex++;

            // Transitioned from one state to another to the same
            if (newType != currentType) {
                currentType = newType;
                //				image.postInvalidate();
            }
            //获取系统结束时间（ms）
            long endTime = currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d;
            if (totalTimeInSecs >= 2) {
                double bps = (beats / totalTimeInSecs);
                int dpm = (int) (bps * 60d);
                if (dpm < 30 || dpm > 180 || imgAvg < 200) {
                    //获取系统开始时间（ms）
                    startTime = currentTimeMillis();
                    //beats心跳总数
                    beats = 0;
                    processing.set(false);
                    return;
                }
                //				Log.e(TAG, "totalTimeInSecs=" + totalTimeInSecs + " beats="+ beats);
                if (beatsIndex == beatsArraySize)
                    beatsIndex = 0;
                beatsArray[beatsIndex] = dpm;
                beatsIndex++;
                int beatsArrayAvg = 0;
                int beatsArrayCnt = 0;
                for (int i = 0; i < beatsArray.length; i++) {

                    if (beatsArray[i] > 0) {
                        beatsArrayAvg += beatsArray[i];
                        beatsArrayCnt++;
                    }
                }
                int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
                //累计心跳数
                beatsum = beatsum + beatsAvg;
                //统计次数
                beatrate++;
                Log.e("gaoyu_beat_sleep", "心跳总数" + beatsum);
                text.setText("实时心率是" + String.valueOf(beatsAvg) + "次/分钟");
                /*text.setText("亲的心率是" + String.valueOf(beatsAvg) + "  值:" + String.valueOf(beatsArray.length)
                        + "    " + String.valueOf(beatsIndex) + "    " + String.valueOf(beatsArrayAvg) + "    " + String.valueOf(beatsArrayCnt));*/
                //获取系统时间（ms）
                startTime = currentTimeMillis();
                Log.e("gaoyu_beat_sleep", "时间是" + startTime);

                beats = 0;

            }

            processing.set(false);
        }

    };
    /**
     * 预览回调接口
     */
    //回调方法
    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        //创建surface界面
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                //通过SurfaceView显示取景画面
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);

            } catch (Throwable t) {
                //				Log.e("PreviewDemo-surfaceCallback","Exception in setPreviewDisplay()", t);
            }
        }

        //屏幕改变，比如缩放，旋转屏幕等
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
                //				Log.d(TAG, "Using width=" + size.width + " height="	+ size.height);
            }
            camera.setParameters(parameters);
            camera.startPreview();
        }

        //退出
        public void surfaceDestroyed(SurfaceHolder holder) {
            // Ignore
        }
    };

    /**
     * 盖住相机就是最小尺寸
     * 获取相机最小的预览尺寸
     * @param width
     * @param height
     * @param parameters
     * @return
     */
    private static Camera.Size getSmallestPreviewSize(int width, int height,
                                                      Camera.Parameters parameters) {
        Camera.Size result = null;
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea)
                        result = size;
                }
            }
        }
        return result;
    }

    //退出保存
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //确认对话框  退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (beatrate != 0) {
                rate = beatsum / beatrate;
            }
            Log.e("gaoyu_beat", "退出时的数据" + rate);
            AlertDialog.Builder alerDialog = new AlertDialog.Builder(this);
            alerDialog.setTitle("本次测试结果是：" + rate + "次/分钟");
            alerDialog.setMessage("选择保存或退出");
            //获取系统时间
            saveTime = System.currentTimeMillis();
            alerDialog.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //将数据存进数据库
                    SQL = new Information_SQL_adapter(HealthActivity.this);
                    String valueRate = String.valueOf(rate);
                    String valueTime = String.valueOf(saveTime);
                    SQL.add(valueRate, valueTime);

                    makeText(HealthActivity.this, "数据已保存，在个人信息中查看！", LENGTH_SHORT).show();
                    Intent i2 = new Intent(HealthActivity.this, InformationActivity.class);
                    startActivity(i2);
                    finish();

                }
            });
            alerDialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i2 = new Intent(HealthActivity.this, PlayerActivity.class);
                    startActivity(i2);
                    finish();
                }
            });
            alerDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}