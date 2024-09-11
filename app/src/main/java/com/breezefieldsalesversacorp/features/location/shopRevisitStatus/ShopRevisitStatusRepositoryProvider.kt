package com.breezefieldsalesversacorp.features.location.shopRevisitStatus

import com.breezefieldsalesversacorp.features.location.shopdurationapi.ShopDurationApi
import com.breezefieldsalesversacorp.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}