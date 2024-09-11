package com.breezefieldsalesversacorp.features.login.model.alarmconfigmodel

import com.breezefieldsalesversacorp.base.BaseResponse

/**
 * Created by Saikat on 19-02-2019.
 */
class AlarmConfigResponseModel : BaseResponse() {
    var alarm_settings_list: ArrayList<AlarmConfigDataModel>? = null
}