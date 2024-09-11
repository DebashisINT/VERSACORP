package com.breezefieldsalesversacorp.features.viewAllOrder.orderOptimized

import com.breezefieldsalesversacorp.app.domain.ProductOnlineRateTempEntity
import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}