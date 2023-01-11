package com.fruitable.Fruitable.di

import android.content.Context
import com.fruitable.Fruitable.app.data.network.api.UserApi
import com.fruitable.Fruitable.app.data.repository.UserRepositoryImpl
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import com.fruitable.Fruitable.app.domain.utils.log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        @ApplicationContext appContext: Context,
    ): Interceptor = Interceptor { chain ->
        val newRequest = chain
            .request()
            .newBuilder()
            .build()
        return@Interceptor chain.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor() { it.log() }.setLevel(HttpLoggingInterceptor.Level.BODY)


    // client 제공
    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // Retrofit 제공
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val okHttpClient = client.newBuilder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .build()
        return Retrofit.Builder()
            .baseUrl("http://3.34.197.243:8080") // baseURL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    // user api 제공
    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
    // user repository 제공
    @Provides
    @Singleton
    fun provideUserRepository(api: UserApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}
