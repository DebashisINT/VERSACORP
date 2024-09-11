package com.breezefieldsalesversacorp.features.stockAddCurrentStock.api

import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.location.model.ShopRevisitStatusRequest
import com.breezefieldsalesversacorp.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.breezefieldsalesversacorp.features.stockAddCurrentStock.ShopAddCurrentStockRequest
import com.breezefieldsalesversacorp.features.stockAddCurrentStock.model.CurrentStockGetData
import com.breezefieldsalesversacorp.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class ShopAddStockRepository (val apiService : ShopAddStockApi){
    fun shopAddStock(shopAddCurrentStockRequest: ShopAddCurrentStockRequest?): Observable<BaseResponse> {
        return apiService.submShopAddStock(shopAddCurrentStockRequest)
    }

    fun getCurrStockList(sessiontoken: String, user_id: String, date: String): Observable<CurrentStockGetData> {
        return apiService.getCurrStockListApi(sessiontoken, user_id, date)
    }

}