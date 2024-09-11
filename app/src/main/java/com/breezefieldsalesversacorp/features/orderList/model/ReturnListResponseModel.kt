package com.breezefieldsalesversacorp.features.orderList.model

import com.breezefieldsalesversacorp.base.BaseResponse


class ReturnListResponseModel: BaseResponse() {
    var return_list: ArrayList<ReturnDataModel>? = null
}