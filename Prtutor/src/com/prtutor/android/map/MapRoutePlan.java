package com.prtutor.android.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.RouteOverlay;
import com.baidu.mapapi.TransitOverlay;

import com.google.prtutor.R;

public class MapRoutePlan extends MapActivity {

	Button mBtnDrive = null; // 驾车搜索
	Button mBtnTransit = null; // 公交搜索
	Button mBtnWalk = null; // 步行搜索

	MapView mMapView = null; // 地图View
	MKSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	LocationListener mLocationListener = null;// create时注册此listener，Destroy时需要Remove
	int myLong = 39989954;
	int myLat = 116323062;
	int myLong1 = 39989954;
	int destLong,destLat;
	Location mLocation = null;
	boolean isFirst = true;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_introduce_test);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		destLong = bundle.getInt("destLong");
		destLat = bundle.getInt("destLat");	
		BaiduApiTools app = (BaiduApiTools) this.getApplication();
		// 这句话报错
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BaiduApiTools.MyGeneralListener());
		}
		app.mBMapMan.start();
		// 如果使用地图SDK，请初始化地图Activity
		super.initMapActivity(app.mBMapMan);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
//		// 设置在缩放动画过程中也显示overlay,默认为不绘制
//		mMapView.setDrawOverlayWhenZooming(true);
		mMapView.getController().setZoom(16);

		// 注册定位事件
		mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					//定位到当前位置
					myLong = (int) (location.getLongitude() * 10e5);
					myLong1 = (int) ((location.getLongitude() + 0.1) * 10e5);
					myLat = (int) (location.getLatitude() * 10e5);
					mLocation = location;
					if (isFirst) {
						//routePlan();
						isFirst = false;
					}
				}
			}
		};

		// 初始化搜索模块，注册事件监听
		mSearch = new MKSearch();
		mSearch.init(app.mBMapMan, new MKSearchListener() {

			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
				// progress.setVisibility(View.GONE);
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(MapRoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(MapRoutePlan.this, mMapView);
				// 此处仅展示一个方案作为示例
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				//mMapView.getOverlays().clear();//5.24新改
				mMapView.getOverlays().add(routeOverlay);
				//mMapView.invalidate();
				mMapView.getController().setZoom(16);
				Toast.makeText(MapRoutePlan.this, "MapRoutePlan", Toast.LENGTH_SHORT).show();
				// GeoPoint: Latitude: 39989954, Longitude: 116323062
				mMapView.getController().animateTo(res.getStart().pt);
				// mMapView.getController().animateTo(
				// new GeoPoint(31552582, 120252930));
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
				if (error != 0 || res == null) {
					Toast.makeText(MapRoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				TransitOverlay routeOverlay = new TransitOverlay(MapRoutePlan.this, mMapView);
				// 此处仅展示一个方案作为示例
				routeOverlay.setData(res.getPlan(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();

				// mMapView.getController().animateTo(res.getStart().pt);
				mMapView.getController().animateTo(new GeoPoint(31652582, 120252930));
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
				if (error != 0 || res == null) {

					Toast.makeText(MapRoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(MapRoutePlan.this, mMapView);
				// 此处仅展示一个方案作为示例
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.getController().setZoom(16);
				mMapView.invalidate();
				mMapView.getController().animateTo(res.getStart().pt);

			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
			}

			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onGetRGCShareUrlResult(String arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});

		// 设定搜索按钮的响应
		mBtnDrive = (Button) findViewById(R.id.drive);

		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				SearchButtonProcess(v);//点击时实现搜索
			}
		};
		mBtnDrive.setOnClickListener(clickListener);//设置点击事件
	}

	private void routePlan() {
		// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
		MKPlanNode stNode = new MKPlanNode();
		// stNode.name = editSt.getText().toString();
		// location.getLongitude(), location.getLatitude()
		GeoPoint stPoint = new GeoPoint(myLat, myLong);
		stNode.pt = stPoint;
		MKPlanNode enNode = new MKPlanNode();
		// enNode.name = "总商会大厦22楼";
		enNode.pt = new GeoPoint(myLat, myLong1);

		// 实际使用中请对起点终点城市进行正确的设定
		mSearch.drivingSearch("", stNode, "温岭", enNode);
	}

	void SearchButtonProcess(View v) {
		MKPlanNode start = new MKPlanNode();  
		start.pt = new GeoPoint(myLat, myLong);
		MKPlanNode end = new MKPlanNode();  
		//end.name = "总商会大厦22楼";
		//end.pt = new GeoPoint(30902594,121933025);// 设置驾车路线搜索策略，时间优先、费用最少或距离最短  
		end.pt = new GeoPoint(destLat,destLong);
		mSearch.drivingSearch( null, start,null, end);  
	}

	@Override
	protected void onPause() {
		super.onPause();
		BaiduApiTools app = (BaiduApiTools) this.getApplication();
		// 移除listener
		app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		app.mBMapMan.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		BaiduApiTools app = (BaiduApiTools) this.getApplication();
		// 注册Listener
		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		app.mBMapMan.start();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
