package com.foodies.customer.GoogleMapWork;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.foodies.customer.Constants.AllConstants;
import com.foodies.customer.Constants.ApiRequest;
import com.foodies.customer.Constants.Callback;
import com.foodies.customer.Constants.PreferenceClass;
import com.foodies.customer.Models.ImageSliderModel;
import com.foodies.customer.Adapters.SlidingImageAdapterTrackingStatus;
import com.foodies.customer.Constants.Config;
import com.foodies.customer.R;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rd.PageIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends AppCompatActivity implements OnMapReadyCallback, android.location.LocationListener,
        RoutingListener{


    ImageView close;
    SharedPreferences sPref;
    String user_id, order_id;
    String map_change = "1";
    String order_status;
    private SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;

    String rider_f_name, rider_l_name, rider_phone_number;
    String user_lat, user_long, rest_lat, rest_long, rider_lat, rider_long;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSION_DATA_ACCESS_CODE = 2;
    private boolean mLocationPermissionGranted;


    int height = 140;
    int width = 140;

    DatabaseReference mDatabase;
    DatabaseReference mDatebaseTracking;
    FirebaseDatabase firebaseDatabase;

    LatLng origin;
    LatLng destination;
    private List<Polyline> polylines;



    Bitmap mMarkerIcon;
    Marker marker;

    public static boolean user_;


    public static String rider_id;
    private ArrayList<ImageSliderModel> ImagesArray;
    private static ViewPager mPager;
    private PageIndicatorView pageIndicatorView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);


        mPager = (ViewPager)findViewById(R.id.image_slider_pager);
        sPref = getSharedPreferences(PreferenceClass.user, MODE_PRIVATE);
        user_id = sPref.getString(PreferenceClass.pre_user_id, "");
        order_id = sPref.getString(PreferenceClass.ORDER_ID, "");
        pageIndicatorView = findViewById(R.id.pageIndicatorView);

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child("tracking_status");
        mDatebaseTracking = firebaseDatabase.getReference().child(AllConstants.TRACKING);

        setupMapIfNeeded();
        mapFragment.setRetainInstance(true);

        polylines = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }



        showRiderLocationAgainstOrder();
        getStatus();

        mMarkerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.rider_pin);

    }

   @SuppressWarnings("deprecation")
    public void showRiderLocationAgainstOrder() {

        ImagesArray = new ArrayList<ImageSliderModel>();
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("order_id", order_id);
            jsonObject1.put("map_change", map_change);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(this, Config.SHOW_RIDER_LOCATION_AGAINST_LATLONG, jsonObject1, new Callback() {
            @Override
            public void Responce(String resp) {

                map_change = "0";
                try {
                    JSONObject jsonObjectmain = new JSONObject(resp);
                    int code = Integer.parseInt(jsonObjectmain.getString("code"));
                    if (code == 200) {

                        JSONArray msgArray = jsonObjectmain.getJSONArray("msg");


                        for (int i = 0; i < msgArray.length(); i++) {

                            JSONObject jsonObject = msgArray.getJSONObject(i);
                            JSONObject RiderOrder = jsonObject.getJSONObject("RiderOrder");
                            JSONObject RiderLocation = RiderOrder.getJSONObject("RiderLocation");
                            JSONArray jsonArray = RiderLocation.getJSONArray("status");
                            rider_lat = RiderLocation.optString("lat");
                            rider_long = RiderLocation.optString("long");
                            rider_id = RiderOrder.optString("rider_user_id");

                            ImagesArray.clear();

                            for (int j = 0; j < jsonArray.length(); j++) {

                                JSONObject statusJsonObject = jsonArray.getJSONObject(j);

                                order_status = statusJsonObject.optString("order_status");
                                map_change = statusJsonObject.optString("map_change");

                                ImageSliderModel imageSliderModel = new ImageSliderModel();
                                imageSliderModel.setSliderImageUrl(statusJsonObject.optString("order_status"));

                                ImagesArray.add(imageSliderModel);

                            }

                            pageIndicatorView.setCount(ImagesArray.size());
                            mPager.setAdapter(new SlidingImageAdapterTrackingStatus(TrackingActivity.this, ImagesArray));

                            JSONObject Rider = jsonObject.getJSONObject("Rider");
                            rider_f_name = Rider.optString("first_name");
                            rider_l_name = Rider.optString("last_name");
                            rider_phone_number = Rider.optString("phone");

                            JSONObject UserLocation = jsonObject.getJSONObject("UserLocation");
                            user_lat = UserLocation.optString("lat");
                            user_long = UserLocation.optString("long");

                            JSONObject RestaurantLocation = jsonObject.getJSONObject("RestaurantLocation");

                            rest_lat = RestaurantLocation.optString("lat");
                            rest_long = RestaurantLocation.optString("long");

                            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {

                                    marker.showInfoWindow();
                                    mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                        @Override
                                        public void onInfoWindowClick(Marker marker) {
                                            phoneCall();
                                        }
                                    });

                                    return false;
                                }
                            });


                            if ((rider_lat.equalsIgnoreCase("") && rider_long.equalsIgnoreCase("")) && (user_lat.equalsIgnoreCase("") && user_long.equalsIgnoreCase(""))) {
                                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.hotel_pin);
                                Bitmap b = bitmapdraw.getBitmap();
                                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                                setUpPinWithLatLong(rest_lat, rest_long, "Hotel", smallMarker);
                            } else if (user_lat.equalsIgnoreCase("") && user_long.equalsIgnoreCase("")) {

                                if (map_change.equalsIgnoreCase("1")) {

                                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.rider_pin);
                                    Bitmap b = bitmapdraw.getBitmap();
                                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);


                                    //  setUpPinWithLatLong(rider_lat, rider_long, "Hotel",smallMarker);
                                    //  mapChangeAnimation(rider_lat,rider_long,rest_lat,rest_long,R.drawable.rider_pin,R.drawable.user_pin);
                                    // mapChangeAnimation();

                                    user_ = false;


                                    if(marker ==null) {
                                        marker = mGoogleMap.addMarker(new MarkerOptions()
                                                .position(
                                                        new LatLng(Double.parseDouble(rider_lat), Double.parseDouble(rider_long)))
                                                .draggable(true).visible(true).title(rider_f_name+" "+rider_l_name).snippet(rider_phone_number).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                        pathDraw(rider_lat, rider_long, rest_lat, rest_long, R.drawable.hotel_pin);


                                    }
                                    mapChangeAnimation(user_);


                                }
                            } else if (rest_lat.equalsIgnoreCase("") && rest_long.equalsIgnoreCase("")) {
                                if (map_change.equalsIgnoreCase("1")) {

                                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.rider_pin);
                                    Bitmap b = bitmapdraw.getBitmap();
                                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                                    // mapChangeAnimation(rider_lat,rider_long,user_lat,user_long,R.drawable.rider_pin,R.drawable.user_pin);
                                    //  setUpPinWithLatLong(rider_lat, rider_long, "Hotel",smallMarker);
                                    user_ = true;

                                    if(marker ==null) {
                                        marker = mGoogleMap.addMarker(new MarkerOptions()
                                                .position(
                                                        new LatLng(Double.parseDouble(rider_lat), Double.parseDouble(rider_long)))
                                                .draggable(true).visible(true).title(rider_f_name+" "+rider_l_name).snippet(rider_phone_number).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                        pathDraw(rider_lat, rider_long, user_lat, user_long, R.drawable.user_pin);
                                    }
                                    mapChangeAnimation(user_);


                                }
                            }

                        }


                    } else {
                        Toast.makeText(TrackingActivity.this, resp, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    public void statusChange(){
        ImagesArray = new ArrayList<ImageSliderModel>();
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("order_id", order_id);
            jsonObject1.put("map_change", map_change);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(this, Config.SHOW_RIDER_LOCATION_AGAINST_LATLONG, jsonObject1, new Callback() {
            @Override
            public void Responce(String resp) {

                map_change = "0";
                try {
                    JSONObject jsonObjectmain = new JSONObject(resp);
                    int code = Integer.parseInt(jsonObjectmain.getString("code"));
                    if (code == 200) {

                        JSONArray msgArray = jsonObjectmain.getJSONArray("msg");

                        for (int i = 0; i < msgArray.length(); i++) {

                            JSONObject jsonObject = msgArray.getJSONObject(i);
                            JSONObject RiderOrder = jsonObject.getJSONObject("RiderOrder");
                            JSONObject RiderLocation = RiderOrder.getJSONObject("RiderLocation");
                            JSONArray jsonArray = RiderLocation.getJSONArray("status");
                            rider_lat = RiderLocation.optString("lat");
                            rider_long = RiderLocation.optString("long");
                            rider_id = RiderOrder.optString("rider_user_id");

                            ImagesArray.clear();

                            for (int j = 0; j < jsonArray.length(); j++) {

                                JSONObject statusJsonObject = jsonArray.getJSONObject(j);

                                order_status = statusJsonObject.optString("order_status");
                                map_change = statusJsonObject.optString("map_change");

                                ImageSliderModel imageSliderModel = new ImageSliderModel();
                                imageSliderModel.setSliderImageUrl(statusJsonObject.optString("order_status"));

                                ImagesArray.add(imageSliderModel);

                            }

                            pageIndicatorView.setCount(ImagesArray.size());
                            mPager.setAdapter(new SlidingImageAdapterTrackingStatus(TrackingActivity.this,ImagesArray));

                        }
                    }


                    else {
                        Toast.makeText(TrackingActivity.this, resp, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private void setupMapIfNeeded() {

        // Build the map.
        if(mGoogleMap==null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            try {
                ActivityCompat.requestPermissions(TrackingActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            catch (Exception e){
                e.getMessage();
            }

        }
    }

    public void pathDraw(String startLat,String startLon,String endLat,String endLon, int desTinationDrawable){

        DecimalFormat decimalFormat = new DecimalFormat("#.#######");
       origin = new LatLng(Double.parseDouble(decimalFormat.format(Double.valueOf(startLat))), Double.valueOf(decimalFormat.format(Double.valueOf(startLon))));
       destination = new LatLng(Double.parseDouble(endLat), Double.parseDouble(endLon));


        if(origin==null || destination==null)
        {
            if(origin==null)
            {

                    Toast.makeText(this,"Please choose a starting point.",Toast.LENGTH_SHORT).show();

            }
            if(destination==null)
            {

                    Toast.makeText(this,"Please choose a destination.",Toast.LENGTH_SHORT).show();

            }
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(TrackingActivity.this)
                    .alternativeRoutes(false)
                    .waypoints(origin, destination)
                    .build();
            routing.execute();

           // midPoint(Double.parseDouble(startLat),Double.parseDouble(startLon),Double.parseDouble(endLat),Double.parseDouble(endLon));
        /*   double centerLat = (Double.parseDouble(startLat))+(Double.parseDouble(startLon))/2;
             double centerLon = (Double.parseDouble(endLat))+(Double.parseDouble(endLon))/2;
             centerPoint = new LatLng(centerLat, centerLon);*/


          CameraUpdate center = CameraUpdateFactory.newLatLng(destination);
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

            mGoogleMap.moveCamera(center);
            mGoogleMap.animateCamera(zoom);



/*

            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(origin)
                    .include(destination).build();
            Point displaySize = new Point();
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
            getWindowManager().getDefaultDisplay().getSize(displaySize);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 1000, 16));
            mGoogleMap.animateCamera(zoom);
*/



/*// Save current zoom
            float originalZoom = mGoogleMap.getCameraPosition().zoom;

            // Move temporarily camera zoom
            mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(16));

            Point pointInScreen = mGoogleMap.getProjection().toScreenLocation(origin);

            Point newPoint = new Point();
            newPoint.x = pointInScreen.x + newPoint.x;
            newPoint.y = pointInScreen.y + newPoint.y;

            LatLng newCenterLatLng = mGoogleMap.getProjection().fromScreenLocation(newPoint);

            // Restore original zoom
            mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(originalZoom));

            // Animate a camera with new latlng center and required zoom.
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newCenterLatLng, 16));*/

        }




   /*    DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mGoogleMap);

        DrawMarker.getInstance(this).draw(mGoogleMap, destination, desTinationDrawable, "Destination Location");
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 1000, 16));*/

    }
   public void setUpPinWithLatLong(String lat,String lon,String pinTitle, Bitmap icon){

        if (mGoogleMap != null) {
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lon))).title(pinTitle)).setIcon(BitmapDescriptorFactory.fromBitmap(icon));
            if (ActivityCompat.checkSelfPermission(getApplicationContext()
                    , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext()
                    , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            LatLng coordinate = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon)); //Store these lat lng values somewhere. These should be constant.
            CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                    coordinate, 15);
            mGoogleMap.animateCamera(location);
       /*     mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);*/

        }

    }

    public void getStatus(){
        mDatabase.keepSynced(true);
        DatabaseReference query2 = mDatabase.child(order_id).child("order_status");

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            statusChange();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    public void mapChangeAnimation(final boolean user){
        mDatebaseTracking.keepSynced(true);
        DatabaseReference query = mDatebaseTracking.child(rider_id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                rider_lat = ""+dataSnapshot.child("rider_lat").getValue();
                rider_long = ""+dataSnapshot.child("rider_long").getValue();
                String rider_previous_lat = ""+dataSnapshot.child("rider_previous_lat").getValue();
                String rider_previous_long = ""+dataSnapshot.child("rider_previous_long").getValue();
                LatLng latlongLatest = new LatLng(Double.parseDouble(rider_lat),Double.parseDouble(rider_previous_long));


                if(!user) {

                    if (!rider_lat.equalsIgnoreCase(rider_previous_lat) && !rider_long.equalsIgnoreCase(rider_previous_long)) {



                        Routing routing = new Routing.Builder()
                                .travelMode(AbstractRouting.TravelMode.DRIVING)
                                .withListener(TrackingActivity.this)
                                .alternativeRoutes(false)
                                .waypoints(latlongLatest, destination)
                                .build();
                        routing.execute();

                    }


                }
                else {

                    if (!rider_lat.equalsIgnoreCase(rider_previous_lat) && !rider_long.equalsIgnoreCase(rider_previous_long)) {


                      Routing routing = new Routing.Builder()
                                .travelMode(AbstractRouting.TravelMode.DRIVING)
                                .withListener(TrackingActivity.this)
                                .alternativeRoutes(false)
                                .waypoints(latlongLatest, destination)
                                .build();
                        routing.execute();


                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }







     @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    //updateLocationUI();
                }
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
       /* LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mGoogleMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(TrackingActivity.this);*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }


    public void phoneCall() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(TrackingActivity.this);
        builder1.setMessage("Cal to rider from you phone.");
        builder1.setTitle("Make a cal!");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Call",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        onCall();

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }


    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+ rider_phone_number)));
        }
    }



    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();

        for (int i = 0; i <route.size(); i++) {


            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(ContextCompat.getColor(this,R.color.colorRed));
            polyOptions.width(7);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mGoogleMap.addPolyline(polyOptions);
            polylines.add(polyline);

        }


       MarkerOptions options;

        options = new MarkerOptions();
        options.position(destination);
        if(!user_) {
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.hotel_pin));
        }
        else {
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_pin));
        }
        mGoogleMap.addMarker(options);
    }

    @Override
    public void onRoutingCancelled() {
        Log.i("Rout TAG", "Routing was cancelled.");
    }


}
