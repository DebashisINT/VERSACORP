package com.breezefieldsalesversacorp.features.nearbyuserlist.api

import com.breezefieldsalesversacorp.app.Pref
import com.breezefieldsalesversacorp.features.nearbyuserlist.model.NearbyUserResponseModel
import com.breezefieldsalesversacorp.features.newcollection.model.NewCollectionListResponseModel
import com.breezefieldsalesversacorp.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}