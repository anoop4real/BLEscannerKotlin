package com.example.anoopmohanan.bluetoothscanner

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.anoopmohanan.bluetoothscanner.Constants.Constants
import com.example.anoopmohanan.bluetoothscanner.Model.BLEDevice
import android.view.InputDevice.getDevice
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.bluetooth.le.ScanFilter
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.v4.app.ActivityCompat.requestPermissions
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat.startActivityForResult






/**
 * Created by anoopmohanan on 23/03/18.
 */

// TODO: Right now passing the Activity instance to Utility class as some of the fetaures like permission etc can be done only from Activity.
// TODO: Need to change the logic as passing activity is not a good idea
class BluetoothUtility(val activity:AppCompatActivity,val BLEInterface:BLUIUpdateInterface,val scnPeriod: Long,val signalStrength:Int,
                       val bluetoothManager: BluetoothManager) {


    var bleAdapter = bluetoothManager.adapter
    val bleUIInterface = BLEInterface
    val scanPeriod = scnPeriod
    val strength = signalStrength
    val bleMainActivity = activity
    var isScanning = false
    val handler = android.os.Handler()

    var filters: List<ScanFilter> = ArrayList()
    var settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
            .build()

    var devicesArray     = arrayListOf<BLEDevice>()
//    private val mLeScanCallback = LeScanCallback { device, rssi, scanRecord ->
//
//        handler.post((Runnable {
//
//            bleUIInterface.showMessage("Found Device With name ${device.name}")
//            bleUIInterface.showMessage("Found Device ${device.toString()}")
//            Log.i("onLeScan", device.toString())
//            val bleDevice = BLEDevice(device.address,device.name,rssi)
//            devicesArray.add(bleDevice)
//            bleUIInterface.initializeWith(devicesArray)
//
//        }))
//
//    }

    private val leScanCallback = BtleScanCallback()
    private inner class BtleScanCallback : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            addScanResult(result)
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            for (result in results) {
                addScanResult(result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            //Log.e(TAG, "BLE Scan Failed with code " + errorCode)
        }

        private fun addScanResult(result: ScanResult) {
            val device = result.getDevice()
            val deviceAddress = device.getAddress()

            //bleUIInterface.showMessage("Found Device With name ${device.name}")
            //bleUIInterface.showMessage("Found Device ${device.toString()}")
            Log.i("onLeScan", device.toString())
            val bleDevice = BLEDevice(device.address,device.name,result.rssi)
            devicesArray.add(bleDevice)
            bleUIInterface.initializeWith(devicesArray)

        }
    };

    private fun isBluetoothOn(bluetoothAdapter: BluetoothAdapter): Boolean{

        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled){
            return false
        }

        return true
    }


    public fun isScanningOn(): Boolean{

        return isScanning
    }

    public fun startScanning(){

        if (hasPermissions() && !isScanning){
            bleUIInterface.showMessage("Bluetooth not ON, requesting")
            requestBluetoothEnableFor(bleMainActivity)
        }else{

            scanBLE(true)
        }
    }

    public fun stopScanning(){
        scanBLE(false)
    }

    private fun scanBLE(shouldScan: Boolean){

        if (shouldScan){

            handler.postDelayed(Runnable {
                isScanning = false
                bleAdapter.bluetoothLeScanner.startScan(filters,settings,leScanCallback)
            }, scanPeriod)

            isScanning = true
            bleAdapter.bluetoothLeScanner.stopScan(leScanCallback)
        }else{

            isScanning = false
            bleAdapter.bluetoothLeScanner.stopScan(leScanCallback)
        }

    }


    private fun hasPermissions(): Boolean {
        if (bleAdapter == null || !bleAdapter.isEnabled()) {
            bleUIInterface.showMessage("Bluetooth not ON, requesting")
            requestBluetoothEnableFor(bleMainActivity)
            return false
        } else if (!hasLocationPermissions()) {
            requestLocationPermission()
            return false
        }
        return true
    }

    public fun requestBluetoothEnableFor(activity: Activity) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        activity.startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BT )
        Log.d("BLE", "Requested user enables Bluetooth. Try starting the scan again.")
    }

    private fun hasLocationPermissions(): Boolean {
        return bleMainActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {

        bleMainActivity.requestPermissions(arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_FINE_LOCATION)
    }

}