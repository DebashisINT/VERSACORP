package com.breezefieldsalesversacorp.features.leaderboard.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.breezefieldsalesversacorp.app.FileUtils
import com.breezefieldsalesversacorp.app.Pref
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.addshop.model.AddLogReqData
import com.breezefieldsalesversacorp.features.addshop.model.AddShopRequestData
import com.breezefieldsalesversacorp.features.addshop.model.AddShopResponse
import com.breezefieldsalesversacorp.features.addshop.model.LogFileResponse
import com.breezefieldsalesversacorp.features.addshop.model.UpdateAddrReq
import com.breezefieldsalesversacorp.features.contacts.CallHisDtls
import com.breezefieldsalesversacorp.features.contacts.CompanyReqData
import com.breezefieldsalesversacorp.features.contacts.ContactMasterRes
import com.breezefieldsalesversacorp.features.contacts.SourceMasterRes
import com.breezefieldsalesversacorp.features.contacts.StageMasterRes
import com.breezefieldsalesversacorp.features.contacts.StatusMasterRes
import com.breezefieldsalesversacorp.features.contacts.TypeMasterRes
import com.breezefieldsalesversacorp.features.dashboard.presentation.DashboardActivity
import com.breezefieldsalesversacorp.features.login.model.WhatsappApiData
import com.breezefieldsalesversacorp.features.login.model.WhatsappApiFetchData
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Puja on 10-10-2024.
 */
class LeaderboardRepo(val apiService: LeaderboardApi) {

    fun branchlist(session_token: String): Observable<LeaderboardBranchData> {
        return apiService.branchList(session_token)
    }
    fun ownDatalist(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOwnData> {
        return apiService.ownDatalist(user_id,activitybased,branchwise,flag)
    }
    fun overAllAPI(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOverAllData> {
        return apiService.overAllDatalist(user_id,activitybased,branchwise,flag)
    }
}