package ut.ee.cs.sensorwork

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,SensorEventListener{
     lateinit var mSensorManager:SensorManager
    override fun onAccuracyChanged(event: Sensor?, p1: Int) {


    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        var x = event.values[0]
        var y = event.values[1]
        var  z = event.values[2]
        leftxx.setBackgroundColor(Color.WHITE)
        centerxx.setBackgroundColor(Color.WHITE)
        rightxx.setBackgroundColor(Color.WHITE)
        when{
            (x<=-4)-> rightxx.setBackgroundColor(Color.BLUE)
            x>-4 && x<4 -> centerxx.setBackgroundColor(Color.BLUE)
            else->leftxx.setBackgroundColor(Color.BLUE)
        }

        Log.i("Acceloremer","Values is $x")


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

       val  mAccelerometer= mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        mSensorManager!!.registerListener(this, mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        mSensorManager.unregisterListener(this)
    }
}
