package com.breezefieldsalesversacorp.features.dashboard.presentation.api.dayStartEnd

import com.breezefieldsalesversacorp.features.stockCompetetorStock.api.AddCompStockApi
import com.breezefieldsalesversacorp.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}