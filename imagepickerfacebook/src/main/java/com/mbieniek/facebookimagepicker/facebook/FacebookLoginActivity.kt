package com.mbieniek.facebookimagepicker.facebook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.mbieniek.facebookimagepicker.R
import com.mbieniek.facebookimagepicker.facebook.models.FacebookPicture

const val KEY_FACEBOOK_RESULT = "KEY_FACEBOOK_RESULT"
const val KEY_REQUEST_CODE = "request_code"
const val REQUEST_CODE_FACEBOOK = 1000

class FacebookLoginActivity : AppCompatActivity() {

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_facebook)
        initResultLauncher()
        findViewById<Button>(R.id.buttonLoginFacebook)?.setOnClickListener {
            resultLauncher?.launch(
                Intent(
                    this@FacebookLoginActivity,
                    FacebookAlbumPickerActivity::class.java
                )
            )
        }
    }


    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data =
                        result.data?.getParcelableExtra(FACEBOOK_IMAGE_RESULT_KEY) as? FacebookPicture
                    val resultIntent = Intent();
                    resultIntent.putExtra(KEY_FACEBOOK_RESULT, data?.sourceUrl)
                    resultIntent.putExtra(KEY_REQUEST_CODE, REQUEST_CODE_FACEBOOK)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }
    }

}