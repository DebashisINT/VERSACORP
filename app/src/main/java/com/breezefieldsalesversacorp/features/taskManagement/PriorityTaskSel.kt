package com.breezefieldsalesversacorp.features.taskManagement

import com.breezefieldsalesversacorp.base.BaseResponse


data class PriorityTaskSel(var task_priority_list:ArrayList<TaskPriorityResponse> = ArrayList()): BaseResponse()

data class TaskPriorityResponse (var task_priority_id:String = "",
                                     var task_priority_name:String = "")

