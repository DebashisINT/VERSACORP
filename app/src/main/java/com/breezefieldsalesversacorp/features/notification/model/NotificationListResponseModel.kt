package com.breezefieldsalesversacorp.features.notification.model

import com.breezefieldsalesversacorp.base.BaseResponse

/**
 * Created by Saikat on 06-03-2019.
 */
class NotificationListResponseModel : BaseResponse() {
    var notification_list: ArrayList<NotificationListDataModel>? = null
}