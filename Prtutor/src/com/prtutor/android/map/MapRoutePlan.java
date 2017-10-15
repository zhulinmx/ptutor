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

	Button mBtnDrive = null; // �ݳ�����
	Button mBtnTransit = null; // ��������
	Button mBtnWalk = null; // ��������

	MapView mMapView = null; // ��ͼView
	MKSearch mSearch = null; // ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��

	LocationListener mLocationListener = null;// createʱע���listener��Destroyʱ��ҪRemove
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
		// ��仰����
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BaiduApiTools.MyGeneralListener());
		}
		app.mBMapMan.start();
		// ���ʹ�õ�ͼSDK�����ʼ����ͼActivity
		super.initMapActivity(app.mBMapMan);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
//		// ���������Ŷ���������Ҳ��ʾoverlay,Ĭ��Ϊ������
//		mMapView.setDrawOverlayWhenZooming(true);
		mMapView.getController().setZoom(16);

		// ע�ᶨλ�¼�
		mLocationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					//��λ����ǰλ��
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

		// ��ʼ������ģ�飬ע���¼�����
		mSearch = new MKSearch();
		mSearch.init(app.mBMapMan, new MKSearchListener() {

			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
				// progress.setVisibility(View.GONE);
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					Toast.makeText(MapRoutePlan.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(MapRoutePlan.this, mMapView);
				// �˴���չʾһ��������Ϊʾ��
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				//mMapView.getOverlays().clear();//5.24�¸�
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
					Toast.makeText(MapRoutePlan.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				TransitOverlay routeOverlay = new TransitOverlay(MapRoutePlan.this, mMapView);
				// �˴���չʾһ��������Ϊʾ��
				routeOverlay.setData(res.getPlan(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();

				// mMapView.getController().animateTo(res.getStart().pt);
				mMapView.getController().animateTo(new GeoPoint(31652582, 120252930));
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
				if (error != 0 || res == null) {

					Toast.makeText(MapRoutePlan.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(MapRoutePlan.this, mMapView);
				// �˴���չʾһ��������Ϊʾ��
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

		// �趨������ť����Ӧ
		mBtnDrive = (Button) findViewById(R.id.drive);

		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				SearchButtonProcess(v);//���ʱʵ������
			}
		};
		mBtnDrive.setOnClickListener(clickListener);//���õ���¼�
	}

	private void routePlan() {
		// ������յ��name���и�ֵ��Ҳ����ֱ�Ӷ����긳ֵ����ֵ�����򽫸��������������
		MKPlanNode stNode = new MKPlanNode();
		// stNode.name = editSt.getText().toString();
		// location.getLongitude(), location.getLatitude()
		GeoPoint stPoint = new GeoPoint(myLat, myLong);
		stNode.pt = stPoint;
		MKPlanNode enNode = new MKPlanNode();
		// enNode.name = "���̻����22¥";
		enNode.pt = new GeoPoint(myLat, myLong1);

		// ʵ��ʹ�����������յ���н�����ȷ���趨
		mSearch.drivingSearch("", stNode, "����", enNode);
	}

	void SearchButtonProcess(View v) {
		MKPlanNode start = new MKPlanNode();  
		start.pt = new GeoPoint(myLat, myLong);
		MKPlanNode end = new MKPlanNode();  
		//end.name = "���̻����22¥";
		//end.pt = new GeoPoint(30902594,121933025);// ���üݳ�·���������ԣ�ʱ�����ȡ��������ٻ�������  
		end.pt = new GeoPoint(destLat,destLong);
		mSearch.drivingSearch( null, start,null, end);  
	}

	@Override
	protected void onPause() {
		super.onPause();
		BaiduApiTools app = (BaiduApiTools) this.getApplication();
		// �Ƴ�listener
		app.mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		app.mBMapMan.stop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		BaiduApiTools app = (BaiduApiTools) this.getApplication();
		// ע��Listener
		app.mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
		app.mBMapMan.start();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
