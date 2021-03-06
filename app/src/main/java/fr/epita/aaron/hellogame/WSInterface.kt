package fr.epita.aaron.hellogame

import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("game/list")
    fun listGames(): retrofit2.Call<List<Games>>

    @GET("game/details")
    fun DescGame(@Query("game_id") id: Int): retrofit2.Call<Games>
}