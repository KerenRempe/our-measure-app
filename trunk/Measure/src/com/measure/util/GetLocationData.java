package com.measure.util;


import android.app.Application;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
public class GetLocationData
{
	
	
    private Application application=null;
	private LocationData locData = null;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationClient mLocClient = null;
    LocationClientOption option=null;
    private double Longitude;
    private double Latitude;
    public  GetLocationData(Application application)
    {
    	this.application=application;
    	this.mLocClient = new LocationClient(application.getApplicationContext());
    	mLocClient = new LocationClient( application.getApplicationContext() );
        mLocClient.registerLocationListener( myListener );
        option = new LocationClientOption();
        option.setOpenGps(true);          //打开gps
        option.setPriority(LocationClientOption.GpsFirst);//网络优先
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(1000);
        
        mLocClient.setLocOption(option);
        mLocClient.start();
        locData = new LocationData();
    	
    }
 public void stop()
 {
	if(mLocClient != null &&mLocClient.isStarted())
	{
		 mLocClient.unRegisterLocationListener(myListener);
	     mLocClient.stop();
	}
	 
 }
    public double getLatitude()
    {    
    	return this.Latitude;
    }
    public double getLongitude()
    {
    	return this.Longitude;
    }
   
    public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            Log.i("location", location.getLatitude()+" "+location.getLongitude());
            setLatitude(locData.latitude );
            setLongitude(locData.longitude);
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
}


