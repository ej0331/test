package com.edu.foodfun.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.edu.foodfun.R
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import okhttp3.*
import org.json.JSONObject

class MapFragment : Fragment() {

    private lateinit var locationManager: LocationManager
    private var locationPermissionGranted = false
    private lateinit var mLocationProviderClient: FusedLocationProviderClient
    private lateinit var deviceCoordinate: LatLng
    private lateinit var googleMap: GoogleMap
    private lateinit var currentMarker : Marker
    private lateinit var currentPolyline : Polyline

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
//        val nutc = LatLng(24.149704520502556, 120.6838030129633)
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this.requireActivity().applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            googleMap.isMyLocationEnabled = true
            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 5000
            mLocationProviderClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        locationResult ?: return
                        if(!::deviceCoordinate.isInitialized){
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(locationResult.lastLocation.latitude,locationResult.lastLocation.longitude),14f))
                        }
                        deviceCoordinate = LatLng(locationResult.lastLocation.latitude,locationResult.lastLocation.longitude)
                    }
                }, null
            )
        }
        else{
            requireLocationPermission()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    fun switchMarker(coordinate:LatLng, markerName:String){
        if(::currentMarker.isInitialized){
            currentMarker.remove()
        }
        currentMarker = googleMap.addMarker(MarkerOptions().position(coordinate).title(markerName))!!
    }

    fun redirectToMarker(){
        if(!::currentMarker.isInitialized){
            return
        }
        val apiKey ="AIzaSyB0qXFi__1d1zC35p0Vmj4gP3IR2Gyesyc"
        val destUri = "https://maps.googleapis.com/maps/api/directions/json?origin=${deviceCoordinate.latitude},${deviceCoordinate.longitude}&destination=${currentMarker.position.latitude},${currentMarker.position.longitude}&key=${apiKey}"
        val okHttpClient = OkHttpClient().newBuilder().build()
        val request: Request = Request.Builder().url(destUri).get().build()
        val call = okHttpClient.newCall(request)
        val path: MutableList<List<LatLng>> = ArrayList()
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                Log.d("getDirectionJSON", "onFailure: $e")
            }
            override fun onResponse(call: Call, response: Response) {
                val jsonResponse = JSONObject(response.body?.string())
                val routes = jsonResponse.getJSONArray("routes")
                val points = routes.getJSONObject(0).getJSONObject("overview_polyline").getString("points")
                var LatLngList = PolyUtil.decode(points)
                activity?.runOnUiThread(Runnable {
                    kotlin.run {
                        if(::currentPolyline.isInitialized){
                            currentPolyline.remove()
                        }
                        currentPolyline = googleMap?.addPolyline(PolylineOptions().addAll(LatLngList).color(Color.BLUE))
                    }
                })
            }
        })
    }

    fun getDeviceCoordinate(): LatLng?{
        return deviceCoordinate
    }

    private fun requireLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
        {
            AlertDialog.Builder(this.requireActivity())
                .setMessage("此應用程式，需要位置權限才能正常使用")
                .setPositiveButton("確定") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        1
                    )
                }
                .setNegativeButton("取消") { _, _ -> requireLocationPermission() }
                .show()
        }
        else
        {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
}