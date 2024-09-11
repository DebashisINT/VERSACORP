package com.breezefieldsalesversacorp.features.viewAllOrder.interf

import com.breezefieldsalesversacorp.app.domain.NewOrderColorEntity
import com.breezefieldsalesversacorp.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}