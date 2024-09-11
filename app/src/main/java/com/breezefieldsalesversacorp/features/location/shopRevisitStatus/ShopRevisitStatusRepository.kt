package com.breezefieldsalesversacorp.features.location.shopRevisitStatus

import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.location.model.ShopDurationRequest
import com.breezefieldsalesversacorp.features.location.model.ShopRevisitStatusRequest
import io.reactivex.Observable

class ShopRevisitStatusRepository(val apiService : ShopRevisitStatusApi) {
    fun shopRevisitStatus(shopRevisitStatus: ShopRevisitStatusRequest?): Observable<BaseResponse> {
        return apiService.submShopRevisitStatus(shopRevisitStatus)
    }
}