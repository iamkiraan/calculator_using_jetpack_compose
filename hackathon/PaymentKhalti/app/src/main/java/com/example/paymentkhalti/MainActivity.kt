package com.example.paymentkhalti

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.khalti.checkout.helper.Config
import com.khalti.checkout.helper.KhaltiCheckOut
import com.khalti.checkout.helper.OnCheckOutListener
import com.khalti.checkout.helper.PaymentPreference
import com.khalti.widget.KhaltiButton
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val khalti_button = findViewById<KhaltiButton>(R.id.khalti_button)


        val config = Config.Builder(
            "test_public_key_1640bf6cb7b7434aa7b7c1db86d96e6d",
            "product_id",
            "Futsal payment",
            1000L,
            object : OnCheckOutListener {
                override fun onError(action: String, errorMap: Map<String, String>) {
                    Log.i(action, errorMap.toString())
                    Toast.makeText(this@MainActivity, "Error: $errorMap", Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(data: Map<String, Any>) {
                    Log.i("success", data.toString())
                    Toast.makeText(this@MainActivity, "Success: $data", Toast.LENGTH_SHORT).show()
                }
            }
        )
            .paymentPreferences(Arrays.asList(PaymentPreference.KHALTI, PaymentPreference.EBANKING, PaymentPreference.MOBILE_BANKING, PaymentPreference.CONNECT_IPS, PaymentPreference.SCT))
            .additionalData(mapOf("merchant_extra" to "This is extra data"))
            .productUrl("http://example.com/product")
            .mobile("9863930225")
            .build()

        khalti_button.setCheckOutConfig(config)
        khalti_button.setOnClickListener {
            val khaltiCheckOut = KhaltiCheckOut(this@MainActivity, config)
            khaltiCheckOut.show()
        }

    }
}
