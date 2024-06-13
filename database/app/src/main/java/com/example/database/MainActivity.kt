package com.example.database

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.khalti.widget.Config
import com.khalti.widget.KhaltiButton
import com.khalti.widget.PaymentPreference
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    private lateinit var khaltiButton: KhaltiButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        khaltiButton = findViewById(R.id.khalti_button)

        // Build Config
        val map = HashMap<String, Any>()
        map["merchant_extra"] = "This is extra data"

        val builder = Config.Builder(Constant.pub, "Product ID", "Main", 1100L, object : OnCheckOutListener {
            override fun onError(action: String, errorMap: Map<String, String>) {
                Log.i(action, errorMap.toString())
            }

            override fun onSuccess(data: Map<String, Any>) {
                Log.i("success", data.toString())
            }
        })
            .paymentPreferences(
                ArrayList<PaymentPreference>().apply {
                    add(PaymentPreference.KHALTI)
                    add(PaymentPreference.EBANKING)
                    add(PaymentPreference.MOBILE_BANKING)
                    add(PaymentPreference.CONNECT_IPS)
                    add(PaymentPreference.SCT)
                }
            )
            .additionalData(map)
            .productUrl("http://example.com/product")
            .mobile("9800000000")

        val config = builder.build()

        // Set Config
        khaltiButton.setCheckOutConfig(config)
    }
}
