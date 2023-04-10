// meet
package com.example.project13application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SubscriberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = "abc@d.com" // user id, to be retrieved from the parent activity in extras
        val type = SubscriberType.CAREGIVER // caregiver or family member, to be retrieved from the parent activity in extras

        val toList = Intent(this, PatientListActivity::class.java)
        toList.putExtra("userId", userId)
        toList.putExtra("type", type.toString())
        this.startActivity(toList)
    }
}