package com.breezefieldsalesversacorp.features.viewAllOrder.model

import com.breezefieldsalesversacorp.app.domain.NewOrderColorEntity
import com.breezefieldsalesversacorp.app.domain.NewOrderGenderEntity
import com.breezefieldsalesversacorp.app.domain.NewOrderProductEntity
import com.breezefieldsalesversacorp.app.domain.NewOrderSizeEntity
import com.breezefieldsalesversacorp.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

