package com.breezefieldsalesversacorp.features.menuBeat

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.breezefieldsalesversacorp.app.FileUtils
import com.breezefieldsalesversacorp.app.Pref
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.addshop.model.*
import com.breezefieldsalesversacorp.features.addshop.model.assigntopplist.AddShopUploadImg
import com.breezefieldsalesversacorp.features.addshop.model.assigntopplist.AddshopImageMultiReqbody1
import com.breezefieldsalesversacorp.features.addshop.presentation.ShopListSubmitResponse
import com.breezefieldsalesversacorp.features.addshop.presentation.multiContactRequestData
import com.breezefieldsalesversacorp.features.beatCustom.BeatGetStatusModel
import com.breezefieldsalesversacorp.features.dashboard.presentation.DashboardActivity
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by saheli on 16-12-2023.
 */
class MenuBeatRepository(val apiService: MenuBeatApi) {

    fun currentTabMenubeat(sessiontoken: String, user_id: String, beat_id: String): Observable<MenuBeatResponse> {
        return apiService.getCurrentTabData(user_id,sessiontoken,beat_id)
    }
    fun hirerchyTabMenubeat(sessiontoken: String, user_id: String): Observable<MenuBeatAreaRouteResponse> {
        return apiService.getHirerchyTabData(user_id,sessiontoken)
    }

}