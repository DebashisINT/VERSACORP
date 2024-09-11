package com.breezefieldsalesversacorp.features.viewAllOrder.interf

import com.breezefieldsalesversacorp.app.domain.NewOrderGenderEntity
import com.breezefieldsalesversacorp.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}