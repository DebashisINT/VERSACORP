package com.breezefieldsalesversacorp.features.photoReg.present

import com.breezefieldsalesversacorp.app.domain.ProspectEntity
import com.breezefieldsalesversacorp.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}