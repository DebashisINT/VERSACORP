package com.breezefieldsalesversacorp.features.mylearning.apiCall

import com.breezefieldsalesversacorp.features.login.api.opportunity.OpportunityListApi
import com.breezefieldsalesversacorp.features.login.api.opportunity.OpportunityListRepo

object LMSRepoProvider {
    fun getTopicList(): LMSRepo {
        return LMSRepo(LMSApi.create())
    }
}