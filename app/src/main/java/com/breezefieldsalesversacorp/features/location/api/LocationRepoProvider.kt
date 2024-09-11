package com.breezefieldsalesversacorp.features.location.api

import com.breezefieldsalesversacorp.features.location.shopdurationapi.ShopDurationApi
import com.breezefieldsalesversacorp.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}