package com.breezefieldsalesversacorp.features.weather.api

import com.breezefieldsalesversacorp.base.BaseResponse
import com.breezefieldsalesversacorp.features.task.api.TaskApi
import com.breezefieldsalesversacorp.features.task.model.AddTaskInputModel
import com.breezefieldsalesversacorp.features.weather.model.ForeCastAPIResponse
import com.breezefieldsalesversacorp.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}