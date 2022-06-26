package com.edu.foodfun

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.edu.foodfun.fragment.DetailFragment
import com.edu.foodfun.fragment.FriendFragment
import com.edu.foodfun.fragment.ListFragment
import com.edu.foodfun.fragment.RecommendFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private var locationPermissionGranted = false
    private lateinit var bottomNavbar:BottomNavigationView
    private var willEatList: MutableList<SimpleRest> = mutableListOf()
    private var favoriteList: MutableList<SimpleRest> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listenerUserAboutData()

        bottomNavbar = findViewById(R.id.bottomNavigationView)
        bottomNavbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    if(isFragmentInBackstack(supportFragmentManager, ::RecommendFragment.javaClass.name)){
                        supportFragmentManager.popBackStackImmediate(::RecommendFragment.javaClass.name,0)
                    }
                    else{
                        supportFragmentManager.beginTransaction().replace(R.id.frameFragmentContainer, RecommendFragment()).addToBackStack(::RecommendFragment.javaClass.name).commit()
                    }
                }
                R.id.recomment -> {
                    if(isFragmentInBackstack(supportFragmentManager, ::RecommendFragment.javaClass.name)){
                        supportFragmentManager.popBackStackImmediate(::RecommendFragment.javaClass.name,0)
                    }
                    else{
                        supportFragmentManager.beginTransaction().replace(R.id.frameFragmentContainer, RecommendFragment()).addToBackStack(::RecommendFragment.javaClass.name).commit()
                    }
                }
                R.id.list -> {
                    if(isFragmentInBackstack(supportFragmentManager, ::ListFragment.javaClass.name)){
                        supportFragmentManager.popBackStackImmediate(::ListFragment.javaClass.name,0)
                    }
                    else{
                        supportFragmentManager.beginTransaction().replace(R.id.frameFragmentContainer, ListFragment(willEatList, favoriteList)).addToBackStack(::ListFragment.javaClass.name).commit()
                    }
                }
                R.id.friend -> {
                    if(isFragmentInBackstack(supportFragmentManager, FriendFragment().javaClass.name)){
                        supportFragmentManager.popBackStackImmediate(::FriendFragment.javaClass.name,0)
                    }
                    else{
                        supportFragmentManager.beginTransaction().replace(R.id.frameFragmentContainer, FriendFragment()).addToBackStack(::FriendFragment.javaClass.name).commit()
                    }
                }
                R.id.detail -> {
                    if(isFragmentInBackstack(supportFragmentManager, DetailFragment().javaClass.name)){
                        supportFragmentManager.popBackStackImmediate(::DetailFragment.javaClass.name,0)
                    }
                    else{
                        supportFragmentManager.beginTransaction().replace(R.id.frameFragmentContainer, DetailFragment()).addToBackStack(::DetailFragment.javaClass.name).commit()
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
        checkPermission()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        locationPermissionGranted = true //已獲取到權限
                    }
                    else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            AlertDialog.Builder(this)
                                .setTitle("開啟位置權限")
                                .setMessage("位置權限已被關閉，需開啟後才可正常使用")
                                .setPositiveButton("確定") { _, _ -> startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1) }
                                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                                .show()
                        }
                        else {
                            Toast.makeText(this, "位置權限被拒絕，功能將會無法正常使用", Toast.LENGTH_SHORT).show() //權限被拒絕
                            requestLocationPermission()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> { checkPermission() }
        }
    }

    private fun listenerUserAboutData(){
        willEatList.add(SimpleRest("阿鍋家-鍋燒煮義(一中店)", LatLng(24.152136,120.687786), "2mins"))
        willEatList.add(SimpleRest("阿豬媽아줌마韓式烤肉火鍋吃到飽 台中店", LatLng(24.1529067,120.684657), "2mins"))
        willEatList.add(SimpleRest("Mr.38", LatLng(24.1514695,120.678503), "4mins"))
        willEatList.add(SimpleRest("裊裊鍋物 一中店", LatLng(24.1521255,120.687783), "6mins"))
        willEatList.add(SimpleRest("偈亭泡菜鍋 雙十1店", LatLng(24.1481609,120.686669), "6mins"))
        willEatList.add(SimpleRest("本壽司", LatLng(24.1654767,120.679714), "2mins"))
        willEatList.add(SimpleRest("庫克燒烤BAR", LatLng(24.1666926,120.667347), "3mins"))
        willEatList.add(SimpleRest("登大郎鮮肉餐酒館", LatLng(24.154127,120.66602), "2mins"))
        willEatList.add(SimpleRest("果核豆花鄉", LatLng(24.1495718,120.680845), "5mins"))
//        for (i in 0..10) {
//            willEatList.add(SimpleRest("Tasty$i", LatLng(24.152136,120.687786), "${i}mins"))
//        }
    }

    private fun checkPermission() {
        //檢查權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true //已獲取到權限
        }
        else {
            requestLocationPermission() //要求獲取權限
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            AlertDialog.Builder(this).setMessage("此應用程式，需要位置權限才能正常使用")
                .setPositiveButton("確定") { _, _ -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1) }
                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                .show()
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun isFragmentInBackstack(fragmentManager: FragmentManager, fragmentTagName: String): Boolean {
        for (entry in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentTagName == fragmentManager.getBackStackEntryAt(entry).name) {
                return true
            }
        }
        return false
    }
}