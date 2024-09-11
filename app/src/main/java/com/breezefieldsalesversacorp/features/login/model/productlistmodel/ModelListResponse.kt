package com.breezefieldsalesversacorp.features.login.model.productlistmodel

import com.breezefieldsalesversacorp.app.domain.ModelEntity
import com.breezefieldsalesversacorp.app.domain.ProductListEntity
import com.breezefieldsalesversacorp.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}