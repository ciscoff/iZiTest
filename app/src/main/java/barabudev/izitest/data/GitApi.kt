package barabudev.izitest.data

import barabudev.izitest.data.model.UserRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitApi {

    /**
     * https://docs.github.com/en/rest/reference/repos#list-repositories-for-a-user
     */
    @GET("users/{user}/repos")
    suspend fun repoList(@Path("user") user: String): Response<List<UserRepository>>
}