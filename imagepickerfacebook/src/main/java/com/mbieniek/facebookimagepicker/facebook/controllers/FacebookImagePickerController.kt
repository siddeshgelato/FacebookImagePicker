package com.mbieniek.facebookimagepicker.facebook.controllers

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.facebook.AccessToken
import com.mbieniek.facebookimagepicker.facebook.adapters.FacebookImageAdapter
import com.mbieniek.facebookimagepicker.facebook.data.FacebookDataManager
import com.mbieniek.facebookimagepicker.facebook.models.FacebookPicture
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by michaelbieniek on 3/24/18.
 */
class FacebookImagePickerController(val albumId: Long, imageSelectedListener: FacebookImageAdapter.ImageSelectedListener) {

    var facebookImageAdapter : FacebookImageAdapter = FacebookImageAdapter(imageSelectedListener)
    lateinit var facebookImageRecyclerView: RecyclerView
    var facebookDataManager : FacebookDataManager = FacebookDataManager

    fun bindView(recyclerView: RecyclerView) {
        facebookImageRecyclerView = recyclerView
        recyclerView.adapter = facebookImageAdapter
    }

    fun loadPictures() {
        facebookDataManager.requestPictures(albumId, getAccessToken()!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ imageList ->
                    facebookImageAdapter.addImageList(imageList)
                }, { throwable ->
                    Log.e("FACEBOOKIMAGEPICKER", "Error requesting images from Facebook", throwable)
                })
    }

    fun getSelectedFacebookPictures() : List<FacebookPicture> {
        return facebookImageAdapter.selectedImageList
    }


    fun getSelectedFacebookPicture() : FacebookPicture {
        return facebookImageAdapter.selectedImage!!
    }

    fun getAccessToken() : AccessToken? {
        return AccessToken.getCurrentAccessToken()
    }
}