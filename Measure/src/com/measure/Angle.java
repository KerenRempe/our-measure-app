package com.measure;

import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Leo on 13-5-21.
 */
public class Angle extends Activity {

	private double[] temp = new double[3];
	private double[] start = new double[3];
	// private TextView tv1, tv2, tv3;
	private TextView textView, title;
	private Button bt;
	SensorEvent se;
	SensorManager sense;
	private View root;
	private PopupWindow popupWindow;
	private int type = 0;
	private int count = 0;
	DecimalFormat df;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.angle);

		type = this.getIntent().getExtras().getInt("type");
		// tv1 = (TextView) findViewById(R.id.textView1);
		// tv2 = (TextView) findViewById(R.id.textView2);
		// tv3 = (TextView) findViewById(R.id.textView3);
		// sense = new MySense(this);
		df = new DecimalFormat(".##");
		sense = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sense.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sense.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

		bt = (Button) findViewById(R.id.button2);

		
		
		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// float y=sense.getAcceleration(1);
				// float z=sense.getAcceleration(2);
				//
				//
				// tv.setText(Math.toDegrees(Math.atan(z/y))+"");
				if (popupWindow != null) {
					popupWindow.dismiss();
					popupWindow = null;
				}

				if (popupWindow == null) {
					popupWindow = new PopupWindow(root, 300, 200);
					backButton();
					popupWindow
							.setAnimationStyle(R.style.mypopwindow_anim_style);
					popupWindow.setFocusable(true);
					popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);

					start[0] = se.values[0];
					start[1] = se.values[1];
					start[2] = se.values[2];

					temp[0] = start[0];
					temp[1] = start[1];
					temp[2] = start[2];

					// tv1.setText("value0::0");
					// tv2.setText("value0::0");
					// tv3.setText("value0::0");
					Log.i("start[0]", start[0] + "");
					Log.i("start[1]", start[1] + "");
					Log.i("start[2]", start[2] + "");
				}

			}
		});
		LayoutInflater li = (LayoutInflater) getBaseContext().getSystemService(
				LAYOUT_INFLATER_SERVICE);
		root = (View) li.inflate(R.layout.gradienter_popup, null);

		title = (TextView) findViewById(R.id.title);

		textView = (TextView) root.findViewById(R.id.textView1);

		Typeface fontFace = Typeface.createFromAsset(getAssets(),
				"fonts/LithosPro-Regular.ttf");
		textView.setTypeface(fontFace);
		title.setTypeface(fontFace);
		title.setText("ANGLE");

		Button home = (Button)findViewById(R.id.bt_home);
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Angle.this, Main.class);
				Angle.this.finish();
				Angle.this.startActivity(intent);
				
			}
		});
		
	}

	public void backButton() {
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.setFocusable(true);
		popupWindow.setFocusable(true);

		root.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					Log.v("keyCode", "/" + keyCode);
					if (popupWindow != null) {
						popupWindow.dismiss();
						popupWindow = null;
					}
				}
				return false;
			}
		});
	}

	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			se = event;

			if (count > 10) {

				if (type == 0) {
					if (Math.abs(event.values[0] - temp[0]) > 0) {
						temp[0] = event.values[0];

						if (Math.abs(se.values[0] - start[0]) > 180) {
							
							String length = df.format((360 - Math.abs(se.values[0] - start[0])));
							textView.setText(length+"°" );
						} else {
							
							
							String length = df.format(Math.abs(se.values[0] - start[0]));
							textView.setText(length+"°" );
						}
					}
				}
				if (type == 1) {
					if (Math.abs(event.values[1] - temp[1]) > 0) {
						
						
						temp[1] = event.values[1];
						String length = df.format(Math.abs(se.values[1] - start[1]));
						textView.setText(length+"°" );
					}
				}

				if (type == 2) {
					if (Math.abs(event.values[2] - temp[2]) > 0) {
						temp[2] = event.values[2];
						String length = df.format(Math.abs(se.values[2] - start[2]));
						textView.setText(length+"°" );
						
					}
				}

				count = 0;
			}

			count++;

		}

	};
}
