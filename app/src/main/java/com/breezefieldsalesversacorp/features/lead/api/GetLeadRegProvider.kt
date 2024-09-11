package com.breezefieldsalesversacorp.features.lead.api

import com.breezefieldsalesversacorp.features.NewQuotation.api.GetQuotListRegRepository
import com.breezefieldsalesversacorp.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}