package com.example.anoopmohanan.bluetoothscanner

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.anoopmohanan.bluetoothscanner.Adapters.DevicesAdapter
import com.example.anoopmohanan.bluetoothscanner.Constants.Constants
import com.example.anoopmohanan.bluetoothscanner.Model.BLEDevice
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BLUIUpdateInterface, AppCompatActivity() {
    override fun showMessage(message: String) {

        Toast.makeText(this,"$message...",Toast.LENGTH_LONG).show()
    }

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var scanner:BluetoothUtility? = null
    private var deviceListData:ArrayList<BLEDevice>? = null

    var devicesArray     = arrayListOf<BLEDevice>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!isBLEAvailable()){

            Toast.makeText(this,"NO BLE Available",Toast.LENGTH_LONG).show()
        }else{

            Toast.makeText(this,"BLE Available Initializing...",Toast.LENGTH_LONG).show()
            // Initializes Bluetooth adapter.
            val bluetoothManager = applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

            mBluetoothAdapter = bluetoothManager.getAdapter();

            scanner = BluetoothUtility(this,this,7500, -75,bluetoothManager)
        }


    }

    fun startScan(view: View){
        Toast.makeText(this,"Start scanning...",Toast.LENGTH_LONG).show()
        scanner?.startScanning()
    }

    fun stopScan(view: View){
        scanner?.stopScanning()
    }


    override fun initializeWith(data: ArrayList<BLEDevice>){

        deviceListView.invalidate()
        var layoutManager = LinearLayoutManager(this)
        deviceListView.layoutManager = layoutManager
        deviceListView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        deviceListData = data
        val adapter = DevicesAdapter(data)
        deviceListView.adapter = adapter
    }

    fun isBLEAvailable(): Boolean{

        var isBLEPresent = true
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            isBLEPresent = false
        }
        return isBLEPresent
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Constants.REQUEST_ENABLE_BT){

            if (resultCode == Activity.RESULT_OK){

            }else if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }

}
