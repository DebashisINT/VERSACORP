package com.breezefieldsalesversacorp.features.newcollectionreport

import com.breezefieldsalesversacorp.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}