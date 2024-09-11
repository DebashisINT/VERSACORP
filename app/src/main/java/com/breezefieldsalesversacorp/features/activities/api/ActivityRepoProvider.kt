package com.breezefieldsalesversacorp.features.activities.api

import com.breezefieldsalesversacorp.features.member.api.TeamApi
import com.breezefieldsalesversacorp.features.member.api.TeamRepo

object ActivityRepoProvider {
    fun activityRepoProvider(): ActivityRepo {
        return ActivityRepo(ActivityApi.create())
    }

    fun activityImageRepoProvider(): ActivityRepo {
        return ActivityRepo(ActivityApi.createImage())
    }
}