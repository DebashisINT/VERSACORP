package com.breezefieldsalesversacorp.features.survey.api

import com.breezefieldsalesversacorp.features.photoReg.api.GetUserListPhotoRegApi
import com.breezefieldsalesversacorp.features.photoReg.api.GetUserListPhotoRegRepository

object SurveyDataProvider{

    fun provideSurveyQ(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.create())
    }

    fun provideSurveyQMultiP(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.createImage())
    }
}