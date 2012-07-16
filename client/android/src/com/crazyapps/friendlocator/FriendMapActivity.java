package com.crazyapps.friendlocator;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class FriendMapActivity extends MapActivity implements LocationListener {

	private MapView				mapView;
	private LocationManager		locationManager;
	private MapController		mapController;
	private MyLocationOverlay	myLocation;
	private double				lat	= 0;
	private double				lng	= 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.friendmap);

		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		myLocation = new MyLocationOverlay(getApplicationContext(), mapView);
		myLocation.runOnFirstFix(new Runnable() {
			public void run() {
				mapController.animateTo(myLocation.getMyLocation());
				mapController.setZoom(17);
			}
		});
		myLocation.enableMyLocation();
		myLocation.enableCompass();

		mapView.getOverlays().add(myLocation);

		locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);

		mapController = mapView.getController();
		mapController.setZoom(15);

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLocationChanged(Location location) {
		lat = location.getLatitude();
		lng = location.getLongitude();
		Toast.makeText(getBaseContext(), "Location change to : Latitude = " + lat + " Longitude = " + lng,
				Toast.LENGTH_SHORT).show();

		GeoPoint p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		mapController.animateTo(p);
		mapController.setCenter(p);
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}