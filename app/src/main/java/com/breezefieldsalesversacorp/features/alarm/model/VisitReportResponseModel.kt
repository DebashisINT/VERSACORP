package com.breezefieldsalesversacorp.features.alarm.model

import com.breezefieldsalesversacorp.base.BaseResponse

/**
 * Created by Saikat on 21-02-2019.
 */
class VisitReportResponseModel : BaseResponse() {
    var visit_report_list: ArrayList<VisitReportDataModel>? = null
}