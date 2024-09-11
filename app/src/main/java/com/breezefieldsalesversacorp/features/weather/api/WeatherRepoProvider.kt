package com.breezefieldsalesversacorp.features.weather.api

import com.breezefieldsalesversacorp.features.task.api.TaskApi
import com.breezefieldsalesversacorp.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}