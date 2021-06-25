package barabudev.izitest.di

import android.content.Context
import barabudev.izitest.R
import barabudev.izitest.data.GitApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun gitHubEndPoint(okHttpClient: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.url_github))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun gitApi(retrofit: Retrofit): GitApi {
        return retrofit.create(GitApi::class.java)
    }

    /**
     * Добавить заголовок "Content-Type":"application/json"
     */
    private val interceptor: Interceptor
        get() = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.header("Content-Type", "application/json")
                return chain.proceed(requestBuilder.build())
            }
        }
}