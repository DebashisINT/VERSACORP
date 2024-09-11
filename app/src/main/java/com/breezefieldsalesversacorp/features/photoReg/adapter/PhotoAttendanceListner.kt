package com.breezefieldsalesversacorp.features.photoReg.adapter

import com.breezefieldsalesversacorp.features.photoReg.model.UserListResponseModel

interface PhotoAttendanceListner {
    fun getUserInfoOnLick(obj: UserListResponseModel)
    fun getUserInfoAttendReportOnLick(obj: UserListResponseModel)
}