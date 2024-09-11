package com.breezefieldsalesversacorp.features.location.api

import com.breezefieldsalesversacorp.app.Pref
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.location.model.AppInfoInputModel
import com.breezefieldsalesversacorp.features.location.model.AppInfoResponseModel
import com.breezefieldsalesversacorp.features.location.model.GpsNetInputModel
import com.breezefieldsalesversacorp.features.location.model.ShopDurationRequest
import com.breezefieldsalesversacorp.features.location.shopdurationapi.ShopDurationApi
import io.reactivex.Observable

/**
 * Created by Saikat on 17-Aug-20.
 */
class LocationRepo(val apiService: LocationApi) {
    fun appInfo(appInfo: AppInfoInputModel?): Observable<BaseResponse> {
        return apiService.submitAppInfo(appInfo)
    }

    fun getAppInfo(): Observable<AppInfoResponseModel> {
        return apiService.getAppInfo(Pref.session_token!!, Pref.user_id!!)
    }

    fun gpsNetInfo(appInfo: GpsNetInputModel?): Observable<BaseResponse> {
        return apiService.submitGpsNetInfo(appInfo)
    }
}