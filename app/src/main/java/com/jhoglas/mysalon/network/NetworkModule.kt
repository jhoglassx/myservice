package com.jhoglas.mysalon.network

import com.jhoglas.mysalon.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.TimeZone
import java.util.UUID
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val REQUEST_TRACE_ID = "X-Request-Trace-Id"
    private const val AUTHORIZATION = "Authorization"
    private const val TIME_OUT = 15L

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ) = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
        .create(APIConsumer::class.java)

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }
    }

    @Provides
    fun providerOkhttpService(
        providesHeaderInterceptor: Interceptor,
        provideLoggingInterceptor: HttpLoggingInterceptor,
        provideAuthorizationInterceptor: AuthorizationInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(provideLoggingInterceptor)
            .addInterceptor(provideAuthorizationInterceptor)
            .addInterceptor(providesHeaderInterceptor)

        return builder.build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun providesHeaderInterceptor(): Interceptor =
        Interceptor { interceptor ->
            interceptor.proceed(
                interceptor.request().newBuilder()
                    .header(REQUEST_TRACE_ID, UUID.randomUUID().toString())
                    .header(AUTHORIZATION, "Bearer adcione o token aqui")
                    .header("Content-Type", "application/json")
                    .build(),
            )
        }

    @Provides
    fun provideAuthorizationInterceptor(): AuthorizationInterceptor = AuthorizationInterceptor(
        publicKey = "publicKey",
        privateKey = "privateKey",
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    )
}
