package com.breezefieldsalesversacorp.features.orderList.model

import com.breezefieldsalesversacorp.base.BaseResponse

/**
 * Created by Puja on 01.06.2024
 */
class OpportunityListResponseModel : BaseResponse() {
    var user_id: String? = null
    var opportunity_list: ArrayList<OpportunityListDataModel>? = null
}