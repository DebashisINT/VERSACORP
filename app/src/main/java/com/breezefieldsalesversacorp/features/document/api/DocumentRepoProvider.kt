package com.breezefieldsalesversacorp.features.document.api

import com.breezefieldsalesversacorp.features.dymanicSection.api.DynamicApi
import com.breezefieldsalesversacorp.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}