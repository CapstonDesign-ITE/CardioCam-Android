package org.gradproj.CardioCheckIn.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.gradproj.CardioCheckIn.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_sign_in.setOnClickListener {
            val intentCamera = Intent(this, CameraActivity::class.java)
            this.startActivity(intentCamera)
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
            this.finish()
        }

        btn_sign_up.setOnClickListener {
            val intentInfo = Intent(this, InfoActivity::class.java)
            this.startActivity(intentInfo)
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        }
    }
}