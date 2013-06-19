//package com.z;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.measure.R;
//import com.measure.R.id;
//import com.measure.R.layout;
//
///**
// * 通过GPS定位获取值
// * @author android_ls
// *
// */
//public class MainActivity extends Activity {
//   
//	private static final String TAG = "BaiduMapLoactionActivity";
//	
//	private LocationClient mLocationClient;
//	
//	private MyBDLocationListener mBDLocationListener;
//	
//	private TextView mContent;
//	
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//   //     setContentView(R.layout.activity_main);
//        
//        mLocationClient = new LocationClient(this.getApplicationContext());
//        
//        mBDLocationListener = new MyBDLocationListener();
//        mLocationClient.registerLocationListener(mBDLocationListener);
//        
//        LocationClientOption option = new LocationClientOption();
//       
//        // 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
//        option.setAddrType("all");
//        // 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。 
//        option.setPoiExtraInfo(true);
//        
//        // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。 
//        option.setProdName("通过GPS定位我当前的位置");
//        
//        // 打开GPS，使用gps前提是用户硬件打开gps。默认是不打开gps的。 
//        option.setOpenGps(true);
//        
//        // 定位的时间间隔，单位：ms
//        // 当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。
//        // option.setScanSpan(1000);
//        
//        // 查询范围，默认值为500，即以当前定位位置为中心的半径大小。
//        option.setPoiDistance(500);
//        // 禁用启用缓存定位数据
//        option.disableCache(true);
//        
//        // 坐标系类型，百度手机地图对外接口中的坐标系默认是bd09ll
//        option.setCoorType("bd09ll");
//        
//        // 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
//        option.setPoiNumber(3);
//        
//        // 设置定位方式的优先级。
//        // 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
//        option.setPriority(LocationClientOption.GpsFirst);
//        
//        mLocationClient.setLocOption(option);
//      
//        mContent = (TextView) this.findViewById(R.id.textView1);
//        
//        // 发起定位请求
//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				 mLocationClient.start();
//			}
//		});
//        
//        
//        // 重新定位
//         findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//				if (mLocationClient != null && mLocationClient.isStarted())
//				  mLocationClient.requestLocation();
//			}
//		});
//        
//         // 发起查询请求
//       //  findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
// 			
// 			@Override
// 			public void onClick(View v) {
// 				
// 				if (mLocationClient != null && mLocationClient.isStarted())
// 					mLocationClient.requestPoi();
// 			}
// 		}); 
//        
//    }
//    
//    
//    final class MyBDLocationListener implements BDLocationListener{
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			Log.e(TAG, "---------onReceiveLocation()---------");
//			
//			if(location == null){
//				Log.e(TAG, "---------onReceiveLocation------location is NULL----");
//				return;
//			}
//			
//			int type = location.getLocType();
//			Log.i(TAG, "当前定位采用的类型是：type = " + type);
//			
//			String coorType = location.getCoorType();
//			Log.i(TAG, "坐标系类型:coorType = " + coorType);
//			
//			   // 判断是否有定位精度半径
//            if(location.hasRadius()){
//            	// 获取定位精度半径，单位是米
//            	float accuracy = location.getRadius();
//            	Log.i(TAG, "accuracy = " + accuracy);
//            }
//			
//            if(location.hasAddr()){
//				// 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
//				String address = location.getAddrStr();
//				Log.i(TAG, "address = " + address);
//			}
//            
//            String province = location.getProvince();  // 获取省份信息
//            String city = location.getCity();  // 获取城市信息
//            String district = location.getDistrict(); // 获取区县信息
//            
//            Log.i(TAG, "province = " + province);
//            Log.i(TAG, "city = " + city);
//            Log.i(TAG, "district = " + district);
//            
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            Log.i(TAG, "latitude = " + latitude);
//            Log.i(TAG, "longitude = " + longitude);
//			
//            
//            StringBuffer sb = new StringBuffer(256);
//    		sb.append("time : ");
//    		sb.append(location.getTime());
//    		sb.append("\nerror code : ");
//    		sb.append(location.getLocType());
//    		sb.append("\nlatitude : ");
//    		sb.append(location.getLatitude());
//    		sb.append("\nlontitude : ");
//    		sb.append(location.getLongitude());
//    		sb.append("\nradius : ");
//    		sb.append(location.getRadius());
//    		if (location.getLocType() == BDLocation.TypeGpsLocation){
//    			sb.append("\nspeed : ");
//    			sb.append(location.getSpeed());
//    			sb.append("\nsatellite : ");
//    			sb.append(location.getSatelliteNumber());
//    		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//    			sb.append("\naddr : ");
//    			sb.append(location.getAddrStr());
//    		} 
//     
//            
//    		mContent.setText(sb);
//		}
//
//		@Override
//		public void onReceivePoi(BDLocation poiLocation) {
//			
//			Log.e(TAG, "---------onReceivePoi()---------");
//			
//			if(poiLocation == null){
//				Log.e(TAG, "---------onReceivePoi------location is NULL----");
//				return;
//			}
//			
//			if(poiLocation.hasPoi()){
//				String poiStr = poiLocation.getPoi();
//				Log.i(TAG, "poiStr = " + poiStr);
//				
//			}
//			
//			if(poiLocation.hasAddr()){
//				// 获取反地理编码。 只有使用网络定位的情况下，才能获取当前位置的反地理编码描述。
//				String address = poiLocation.getAddrStr();
//				Log.i(TAG, "address = " + address);
//			}
//			
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("Poi time : ");
//			sb.append(poiLocation.getTime());
//			sb.append("\nerror code : ");
//			sb.append(poiLocation.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(poiLocation.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(poiLocation.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(poiLocation.getRadius());
//			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\naddr : ");
//				sb.append(poiLocation.getAddrStr());
//			} 
//			if(poiLocation.hasPoi()){
//				sb.append("\nPoi:");
//				sb.append(poiLocation.getPoi());
//			}else{				
//				sb.append("noPoi information");
//			}
//			
//			mContent.setText(sb);
//			
//		}
//    	
//    }
//    
//    @Override
//    protected void onDestroy() {
//    	if(mLocationClient != null && mLocationClient.isStarted()){
//    		if(mBDLocationListener != null){
//        		mLocationClient.unRegisterLocationListener(mBDLocationListener);
//        	}
//    		
//        	mLocationClient.stop();
//        	mLocationClient = null;
//    	}
//    	
//        super.onDestroy();
//    }
//    
//    
//}
//
//
