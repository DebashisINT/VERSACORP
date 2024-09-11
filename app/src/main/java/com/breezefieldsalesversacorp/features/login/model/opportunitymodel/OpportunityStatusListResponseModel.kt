package com.breezefieldsalesversacorp.features.login.model.opportunitymodel

import com.breezefieldsalesversacorp.app.domain.OpportunityStatusEntity
import com.breezefieldsalesversacorp.app.domain.ProductListEntity
import com.breezefieldsalesversacorp.base.BaseResponse

/**
 * Created by Puja on 30.05.2024
 */
class OpportunityStatusListResponseModel : BaseResponse() {
    var status_list: ArrayList<OpportunityStatusEntity>? = null
}