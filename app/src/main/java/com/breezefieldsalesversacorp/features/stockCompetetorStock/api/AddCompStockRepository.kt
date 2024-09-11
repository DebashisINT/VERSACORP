package com.breezefieldsalesversacorp.features.stockCompetetorStock.api

import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.orderList.model.NewOrderListResponseModel
import com.breezefieldsalesversacorp.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.breezefieldsalesversacorp.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class AddCompStockRepository(val apiService:AddCompStockApi){

    fun addCompStock(shopAddCompetetorStockRequest: ShopAddCompetetorStockRequest): Observable<BaseResponse> {
        return apiService.submShopCompStock(shopAddCompetetorStockRequest)
    }

    fun getCompStockList(sessiontoken: String, user_id: String, date: String): Observable<CompetetorStockGetData> {
        return apiService.getCompStockList(sessiontoken, user_id, date)
    }
}