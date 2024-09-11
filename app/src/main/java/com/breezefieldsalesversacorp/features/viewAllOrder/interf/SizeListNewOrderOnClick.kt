package com.breezefieldsalesversacorp.features.viewAllOrder.interf

import com.breezefieldsalesversacorp.app.domain.NewOrderProductEntity
import com.breezefieldsalesversacorp.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}