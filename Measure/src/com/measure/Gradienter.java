package com.measure;import com.measure.view.GradienterView;import android.graphics.Typeface;import android.hardware.Sensor;import android.hardware.SensorEvent;import android.hardware.SensorEventListener;import android.hardware.SensorListener;import android.hardware.SensorManager;import android.os.Bundle;import android.app.Activity;import android.content.Context;import android.content.Intent;import android.util.Log;import android.view.Gravity;import android.view.LayoutInflater;import android.view.Menu;import android.view.View;import android.view.Window;import android.view.WindowManager;import android.view.View.OnClickListener;import android.widget.Button;import android.widget.PopupWindow;import android.widget.TextView;@SuppressWarnings("deprecation")public class Gradienter extends Activity {	int k = 90; // 灵敏度	GradienterView mv;	SensorManager mySensorManager;	private View root;	private PopupWindow popupWindow;	Sensor sensor ;	private static  float[] values;	private TextView textView,title;	@Override	protected void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		requestWindowFeature(Window.FEATURE_NO_TITLE);		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,				WindowManager.LayoutParams.FLAG_FULLSCREEN);		setContentView(R.layout.gradienter);		mv = (GradienterView) findViewById(R.id.gradienterView);		mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);				LayoutInflater li = (LayoutInflater) getBaseContext().getSystemService(				LAYOUT_INFLATER_SERVICE);		 root = (View) li.inflate(R.layout.gradienter_popup, null);		 popupWindow = new PopupWindow(root, 300, 200);		 textView = (TextView)root.findViewById(R.id.textView1);		 		 sensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY); 		 title=(TextView)findViewById(R.id.title);						textView = (TextView) root.findViewById(R.id.textView1);			Typeface fontFace = Typeface.createFromAsset(getAssets(),					"fonts/LithosPro-Regular.ttf");			textView.setTypeface(fontFace);			title.setTypeface(fontFace);			title.setText("LENGTH");			Button home = (Button)findViewById(R.id.bt_home);			home.setOnClickListener(new OnClickListener() {								@Override				public void onClick(View v) {					Intent intent = new Intent();					intent.setClass(Gradienter.this, Main.class);					Gradienter.this.finish();					Gradienter.this.startActivity(intent);									}			});	}	private SensorListener mSensorLisener = new SensorListener() {		@Override		public void onAccuracyChanged(int sensor, int accuracy) {		}		public boolean isContain(int x, int y) {// 判断点是否在圆内			int tempx = (int) (x + mv.zhongBitmap2.getWidth() / 2.0);			int tempy = (int) (y + mv.zhongBitmap2.getWidth() / 2.0);			int ox = (int) (mv.zhong1_X + mv.zhongBitmap1.getWidth() / 2.0);			int oy = (int) (mv.zhong1_X + mv.zhongBitmap1.getWidth() / 2.0);			if (Math.sqrt((tempx - ox) * (tempx - ox) + (tempy - oy)					* (tempy - oy))					+ mv.zhongBitmap2.getWidth() / 2 > (mv.zhongBitmap1					.getWidth() / 2.0 - mv.zhongBitmap2.getWidth() / 2.0)) {				// 不在圆内				return false;			} else {				// 在圆内时				return true;			}		}		@Override		public void onSensorChanged(int sensor, float[] values) {			if (sensor == SensorManager.SENSOR_ORIENTATION) {				Gradienter.values=values;				double pitch = values[SensorManager.DATA_Y];				double roll = values[SensorManager.DATA_Z];				int x = 0;				int y = 0;// 临时变量，算中间水泡坐标时用												// 气泡不在边缘时				if (Math.abs(roll) <= k) {					mv.shang2_X = mv.shang1_X // 上面的							+ (int) (((mv.shangBitmap1.getWidth() - mv.shangBitmap2									.getWidth()) / 2.0) * (1 - roll / k));					x = mv.zhong1_X // 中间的							+ (int) (((mv.zhongBitmap1.getWidth() - mv.zhongBitmap2									.getWidth()) / 2.0) * (1 - roll / k));				}				// 气泡在边缘				else if (roll > k) {					mv.shang2_X = mv.shang1_X;					x = mv.zhong1_X;				} else {					mv.shang2_X = mv.shang1_X + mv.shangBitmap1.getWidth()							- mv.shangBitmap2.getWidth();					x = mv.zhong1_X + mv.zhongBitmap1.getWidth()							- mv.zhongBitmap2.getWidth();				}				// 开始调整y 的值				if (Math.abs(pitch) <= k) {					mv.zuo2_Y = mv.zuo1_Y // 左面的							+ (int) (((mv.zuoBitmap1.getHeight() - mv.zuoBitmap2									.getHeight()) / 2.0) * (1 + pitch / k));					y = mv.zhong1_Y + // 中间的							(int) (((mv.zhongBitmap1.getHeight() - mv.zhongBitmap2									.getHeight()) / 2.0) * (1 + pitch / k));				} else if (pitch > k) {					mv.zuo2_Y = mv.zuo1_Y + mv.zuoBitmap1.getHeight()							- mv.zuoBitmap2.getHeight();					y = mv.zhong1_Y + mv.zhongBitmap1.getHeight()							- mv.zhongBitmap2.getHeight();				} else {					mv.zuo2_Y = mv.zuo1_Y;					y = mv.zhong1_Y;				}				if (isContain(x, y)) {// 中间的水泡在圆内才改变坐标					mv.zhong2_X = x;					mv.zhong2_Y = y;				} else {					int x0, y0;//圆上的点，即所求点					//int tempx = (int) (x + mv.zhongBitmap2.getWidth() / 2.0);					//int tempy = (int) (y + mv.zhongBitmap2.getWidth() / 2.0);					int ox = (int) (mv.zhong1_X + mv.zhongBitmap1.getWidth() / 2.0);					int oy = (int) (mv.zhong1_X + mv.zhongBitmap1.getWidth() / 2.0);					double l = Math.sqrt( (ox-x)*(ox-x)+(oy-y)*(oy-y))+ mv.zhongBitmap2.getWidth()/2;					double r = mv.zhongBitmap1.getWidth() / 2.0 - mv.zhongBitmap2.getWidth() / 2.0;					x0 = (int) (r*Math.abs(ox-x)/l);					y0 = (int) (r*Math.abs(y-oy)/l);					if(x > ox)						x0 = ox - x0;					else {						x0 = ox + x0;					}					if(y > oy)						y0 = oy + y0;					else {						y0 = oy - y0;					}										mv.zhong2_X = x0;					mv.zhong2_Y = y0;				}				mv.postInvalidate();// 重绘MainView			}		}	};	private SensorListener proximityListener = new SensorListener() {		public void onAccuracyChanged(int sensor, int accuracy) {		}		@Override		public void onSensorChanged(int sensor, float[] values) {			Log.i("haha",values[0]+"");						if((int)values[0]==0){				popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style); 				popupWindow.showAtLocation(root, Gravity.CENTER, 0,0);				mySensorManager.unregisterListener(mSensorLisener);											}			else{				mySensorManager.registerListener(mSensorLisener,						SensorManager.SENSOR_ORIENTATION);				popupWindow.dismiss();			}			}	};		private SensorEventListener senseListener = new SensorEventListener() {		@Override		public void onAccuracyChanged(Sensor sensor, int accuracy) {			// TODO Auto-generated method stub		}		@Override		public void onSensorChanged(SensorEvent event) {			Log.i("haha",event.values[0]+"");						if((int)event.values[0]==0){				popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style); 				popupWindow.showAtLocation(root, Gravity.CENTER, 20,20);				mySensorManager.unregisterListener(mSensorLisener);				Log.i("!!!","!!!!");				Log.i("values", values[0]+"   "+values[1]+"   "+values[2]);				if(Math.abs(values[1])<1 && Math.abs(values[2])>1)				{					textView.setText("2水平");				}else if(Math.abs(values[1])>1 && Math.abs(values[2])<1){					textView.setText("1水平");				}else if(Math.abs(values[1])>1 && Math.abs(values[2])>1){					textView.setText("都不水平");				}else if(Math.abs(values[1])<1 && Math.abs(values[2])<1){					textView.setText("全水平");				}			}			else{				popupWindow.dismiss();				mySensorManager.registerListener(mSensorLisener,						SensorManager.SENSOR_ORIENTATION);			}					//	Log.i("value1",acceleration+"");								}	};			@Override	protected void onResume() { // 添加监听		mySensorManager.registerListener(mSensorLisener,				SensorManager.SENSOR_ORIENTATION);		mySensorManager.registerListener(senseListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);	//	mySensorManager.registerListener(proximityListener, SensorManager.SENSOR_PROXIMITY);				super.onResume();	}	@Override	protected void onPause() { // 取消监听		mySensorManager.unregisterListener(mSensorLisener);	//	mySensorManager.unregisterListener(proximityListener);		mySensorManager.unregisterListener(senseListener);		super.onPause();	}}