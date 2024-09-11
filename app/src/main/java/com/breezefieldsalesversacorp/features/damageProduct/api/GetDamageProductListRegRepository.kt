package com.breezefieldsalesversacorp.features.damageProduct.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.breezefieldsalesversacorp.app.FileUtils
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.NewQuotation.model.*
import com.breezefieldsalesversacorp.features.addshop.model.AddShopRequestData
import com.breezefieldsalesversacorp.features.addshop.model.AddShopResponse
import com.breezefieldsalesversacorp.features.damageProduct.model.DamageProductResponseModel
import com.breezefieldsalesversacorp.features.damageProduct.model.delBreakageReq
import com.breezefieldsalesversacorp.features.damageProduct.model.viewAllBreakageReq
import com.breezefieldsalesversacorp.features.login.model.userconfig.UserConfigResponseModel
import com.breezefieldsalesversacorp.features.myjobs.model.WIPImageSubmit
import com.breezefieldsalesversacorp.features.photoReg.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GetDamageProductListRegRepository(val apiService : GetDamageProductListApi) {

    fun viewBreakage(req: viewAllBreakageReq): Observable<DamageProductResponseModel> {
        return apiService.viewBreakage(req)
    }

    fun delBreakage(req: delBreakageReq): Observable<BaseResponse>{
        return apiService.BreakageDel(req.user_id!!,req.breakage_number!!,req.session_token!!)
    }

}