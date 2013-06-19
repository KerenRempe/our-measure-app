package com.measure;

import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Leo on 13-5-21.
 */
public class Angle extends Activity {

	private double[] temp = new double[3];
	private double[] start = new double[3];
	private TextView tv1, tv2, tv3;
	private Button bt;
	SensorEvent se;
	SensorManager sense;
	
	private int count = 0;
	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			se = event;
		

			if (count > 10) {
				if (Math.abs(event.values[0] - temp[0]) > 0) {
					temp[0] = event.values[0];
					
					if(Math.abs(se.values[0] - start[0])>180){
						tv1.setText("value0::" + (360-Math.abs(se.values[0] - start[0])));
					}else
					tv1.setText("value0::" + Math.abs(se.values[0] - start[0]));
					
					
				}

				if (Math.abs(event.values[1] - temp[1]) > 0) {
					temp[1] = event.values[1];
					tv2.setText("value0::" + Math.abs(se.values[1] - start[1]));
				}

				if (Math.abs(event.values[2] - temp[2]) > 0) {
					temp[2] = event.values[2];
					tv3.setText("value0::" + Math.abs(se.values[2] - start[2]));
				}
				count=0;
			}
			
			count++;

		}

	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.angle);

		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		// sense = new MySense(this);

		sense = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sense.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		sense.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);

		bt = (Button) findViewById(R.id.button1);

		bt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// float y=sense.getAcceleration(1);
				// float z=sense.getAcceleration(2);
				//
				//
				// tv.setText(Math.toDegrees(Math.atan(z/y))+"");
				start[0] = se.values[0];
				start[1] = se.values[1];
				start[2] = se.values[2];

				temp[0] = start[0];
				temp[1] = start[1];
				temp[2] = start[2];

				tv1.setText("value0::0");
				tv2.setText("value0::0");
				tv3.setText("value0::0");
				Log.i("start[0]", start[0]+"");
				Log.i("start[1]", start[1]+"");
				Log.i("start[2]", start[2]+"");

			}
		});

	}

}
