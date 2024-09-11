package com.breezefieldsalesversacorp.features.viewAllOrder.interf

import com.breezefieldsalesversacorp.app.domain.NewOrderGenderEntity
import com.breezefieldsalesversacorp.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}