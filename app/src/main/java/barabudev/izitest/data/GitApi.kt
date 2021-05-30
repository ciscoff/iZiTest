package barabudev.izitest.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {

    @GET("users/{user}/repos")
    fun repoList(@Path("user") user: String): Response<List<String>>
}