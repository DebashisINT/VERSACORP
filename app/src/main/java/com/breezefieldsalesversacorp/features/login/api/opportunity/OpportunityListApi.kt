package com.breezefieldsalesversacorp.features.login.api.opportunity

import com.breezefieldsalesversacorp.app.NetworkConstant
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.addshop.model.AudioFetchDataCLass
import com.breezefieldsalesversacorp.features.dashboard.presentation.DashboardActivity
import com.breezefieldsalesversacorp.features.login.model.opportunitymodel.OpportunityStatusListResponseModel
import com.breezefieldsalesversacorp.features.login.model.productlistmodel.ProductListResponseModel
import com.breezefieldsalesversacorp.features.orderITC.SyncDeleteOppt
import com.breezefieldsalesversacorp.features.orderITC.SyncEditOppt
import com.breezefieldsalesversacorp.features.orderITC.SyncOppt
import com.breezefieldsalesversacorp.features.orderITC.SyncOrd
import com.breezefieldsalesversacorp.features.orderList.model.OpportunityListResponseModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Saikat on 20-11-2018.
 */
interface OpportunityListApi {
    @FormUrlEncoded
    @POST("CRMOpportunityDetails/OpportunityStatusList")
    fun getOpportunityStatusList(@Field("session_token") session_token: String): Observable<OpportunityStatusListResponseModel>

    @POST("CRMOpportunityDetails/OpportunityDetailSave")
    fun saveOpportunity(@Body syncOppt: SyncOppt): Observable<BaseResponse>

    @POST("CRMOpportunityDetails/OpportunityDetailEdit")
    fun editOpportunity(@Body syncEditOppt: SyncEditOppt): Observable<BaseResponse>

    @POST("CRMOpportunityDetails/OpportunityDetailDelete")
    fun deleteOpportunity(@Body syncDeleteOppt: SyncDeleteOppt): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("CRMOpportunityDetails/OpportunityDetailLists")
    fun getOpportunityL(@Field("user_id") user_id: String): Observable<OpportunityListResponseModel>

    @FormUrlEncoded
    @POST("Shoplist/FetchShopRevisitAudio")
    fun getAudioLApi(@Field("user_id") user_id: String,@Field("data_limit_in_days") data_limit_in_days:String): Observable<AudioFetchDataCLass>

    @POST("LMSInfoDetails/UserWiseLMSModulesInfo")
    fun saveLMSModuleInfoApi(@Body obj: DashboardActivity.LMSModule): Observable<BaseResponse>
    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): OpportunityListApi {
            val retrofit = Retrofit.Builder()
                    .client(NetworkConstant.setTimeOut())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetworkConstant.BASE_URL)
                    .build()

            return retrofit.create(OpportunityListApi::class.java)
        }
    }
}