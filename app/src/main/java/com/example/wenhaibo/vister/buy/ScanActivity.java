package com.example.wenhaibo.vister.buy;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wenhaibo.olympic.R;
import com.example.wenhaibo.vister.MipcaActivityCapture;
import com.example.wenhaibo.vister.buy.TeChan.TeChan0;
import com.example.wenhaibo.vister.buy.TeChan.TeChan1;
import com.example.wenhaibo.vister.buy.TeChan.TeChan2;
import com.example.wenhaibo.vister.buy.TeChan.TeChan3;
import com.example.wenhaibo.vister.buy.TeChan.TeChan4;
import com.example.wenhaibo.vister.buy.TeChan.TeChan5;
import com.example.wenhaibo.vister.buy.TeChan.TeChan6;
import com.example.wenhaibo.vister.buy.TeChan.TeChan7;
import com.example.wenhaibo.vister.buy.TeChan.TeChan8;
import com.example.wenhaibo.vister.buy.TeChan.TeChanActivity;

import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends Activity {

	//热搜
	private liushi mFlowLayout;
	private EditText name;
	private Dao dao;
	private ListView lv;
	private ArrayAdapter<String> adapter;
	private List<String> sel;
	private Button btn_clean;
	List<String> a=new ArrayList<>();

	//商品
	private Button btn;
	private Button btn2,btn3;
	//二维码
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private TextView mTextView ;
	private ImageView mImageView;
	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		btn=findViewById(R.id.btn_taobao);
		btn2=findViewById(R.id.btn_tianmao);
		btn3=findViewById(R.id.btn_taobaoshop);



		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"try",Toast.LENGTH_SHORT).show();
				//totaoBao();
				Intent intent =new Intent(ScanActivity.this,TeChanActivity.class);
				startActivity(intent);
			}
		});


		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
						Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse("https://www.taobao.com/markets/nanzhuang/2017new?spm=a21bo.2017.201867-main.2.5af911d9AFWTwt"));
                        startActivity(intent1);
				//totianmao("https://www.taobao.com/markets/nanzhuang/2017new?spm=a21bo.2017.201867-main.2.5af911d9AFWTwt");
			}
		});

		btn3.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				toshop("https://swarovski.tmall.com/shop/view_shop.htm?shop_id=126434207");
			}
		});


		//二维码
		mTextView = (TextView) findViewById(R.id.result);
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);

		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ScanActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});



		//热搜
		mFlowLayout = (liushi) findViewById(R.id.ls);

		name = (EditText) findViewById(R.id.name);
		lv = (ListView) findViewById(R.id.lv);
		btn_clean = (Button) findViewById(R.id.btn_clean);
		dao = new Dao(ScanActivity.this);
		sel = dao.sel();
		adapter = new ArrayAdapter<>(ScanActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sel);

		lv.setAdapter(adapter);

		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int d, long l) {
				AlertDialog.Builder ab=new AlertDialog.Builder(ScanActivity.this);
				ab.setTitle("是否删除");
				Log.d("aaa",sel.get(d).toString());
				ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						int delyi = dao.delyi(sel.get(d).toString());

						if (delyi==1){
							zhanshi();
						}
					}
				});
              /*  */
				ab.setNegativeButton("取消",null);

				ab.show();
				return false;
			}
		});

		if (sel.size()>0){
			btn_clean.setVisibility(View.VISIBLE);
		}else if(sel.size()==0)
		{
			btn_clean.setVisibility(View.INVISIBLE);
		}
		initChildViews();
	}



	void totaoBao(){

		String tbPath="https://www.xiangha.com/techan/zhangjiakoushi?xhdl=sogou_aladdin";
		Intent intent = new Intent();
		intent.setAction("Android.intent.action.VIEW");
		Uri uri = Uri.parse(tbPath); // 商品地址
		intent.setData(uri);
		//intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
		startActivity(intent);
	}

	/*void totianmao( String tbPath){
		Intent intent = new Intent();
		intent.setAction("Android.intent.action.VIEW");
		Uri uri = Uri.parse(tbPath); // 商品地址
		intent.setData(uri);
		intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
		startActivity(intent);
	}*/

	void toshop(String tbPath){

//        String tbPath="https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.3.45d07484uw9hPZ&id=565570128470";
		Intent intent = new Intent();
		intent.setAction("Android.intent.action.VIEW");
		Uri uri = Uri.parse(tbPath); // 商品地址
		intent.setData(uri);
		intent.setClassName("com.taobao.taobao", "com.taobao.android.shop.activity.ShopHomePageActivity");
		startActivity(intent);
	}


	public boolean checkPackage(String packageName)
	{
		if (packageName == null || "".equals(packageName))
			return false;
		try
		{
			this.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			return false;
		}
	}


	//二维码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case SCANNIN_GREQUEST_CODE:
				if(resultCode == RESULT_OK){
					Bundle bundle = data.getExtras();

					mTextView.setText(bundle.getString("result"));

					mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));

					String url=bundle.getString("result");
					Log.e("wen",url);
					Intent i1=new Intent();
					i1.setAction("android.intent.action.VIEW");
					Uri content_url=Uri.parse(url);
					i1.setData(content_url);
					startActivity(i1);
				}
				break;
		}
	}



	//热搜
	private void zhanshi() {
		List<String> sel4 = dao.sel();
		ArrayAdapter<String> ada = new ArrayAdapter<>(ScanActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sel4);
		lv.setAdapter(ada);
	}

	private String mNames[] = {
			"柴沟堡熏肉", "圪渣饼","西八里大蒜", "土山洼香瓜",
			"蔚州贡米","蔚县杏扁","牛奶葡萄","鹦哥绿豆","口蘑"
	};
	private void initChildViews() {
		ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = 10;
		lp.rightMargin = 10;
		lp.topMargin = 5;
		lp.bottomMargin = 5;
		for (int i = 0; i < mNames.length; i++) {
			TextView view = new TextView(this);
			view.setText(mNames[i]);
			view.setTextColor(Color.WHITE);

			final int finalI = i;
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					switch (finalI){
						case 0:{
							Intent intent =new Intent(ScanActivity.this,TeChan0.class);
							startActivity(intent);
							break;
					 	}
						case 1: {
							Intent intent =new Intent(ScanActivity.this,TeChan1.class);
							startActivity(intent);
							break;
						}
						case 2: {
							Intent intent =new Intent(ScanActivity.this,TeChan2.class);
							startActivity(intent);
							break;
						}
						case 3: {
							Intent intent =new Intent(ScanActivity.this,TeChan3.class);
							startActivity(intent);
							break;
						}
						case 4: {
							Intent intent =new Intent(ScanActivity.this,TeChan4.class);
							startActivity(intent);
							break;
						}
						case 5: {
							Intent intent =new Intent(ScanActivity.this,TeChan5.class);
							startActivity(intent);
							break;
						}
						case 6: {
							Intent intent =new Intent(ScanActivity.this,TeChan6.class);
							startActivity(intent);
							break;
						}
						case 7: {
							Intent intent =new Intent(ScanActivity.this,TeChan7.class);
							startActivity(intent);
							break;
						}
						case 8: {
							Intent intent =new Intent(ScanActivity.this,TeChan8.class);
							startActivity(intent);
							break;
						}
				}
					Toast.makeText(ScanActivity.this,mNames[finalI], Toast.LENGTH_SHORT).show();
				}
			});
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
			mFlowLayout.addView(view, lp);
		}
	}

	public void add(View view) {
		String n = name.getText().toString();
		int i = dao.insertJson(n);

		btn.setVisibility(View.VISIBLE);

		List<String> sel3 = dao.sel();
		a.add(0,n);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<>(ScanActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, a);
		lv.setAdapter(adapter3);
	}

	public void delall(View view) {
		dao.del();
		List<String> sel2 = dao.sel();
		ArrayAdapter<String> adapter2 = new ArrayAdapter<>(ScanActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sel2);

		lv.setAdapter(adapter2);

		Toast.makeText(ScanActivity.this,"清除成功",Toast.LENGTH_LONG).show();
		btn.setVisibility(View.INVISIBLE);
	}
}
