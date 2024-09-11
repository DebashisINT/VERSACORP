package com.breezefieldsalesversacorp.features.viewAllOrder.interf

import com.breezefieldsalesversacorp.app.domain.NewOrderGenderEntity
import com.breezefieldsalesversacorp.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}