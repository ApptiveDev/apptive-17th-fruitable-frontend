package com.fruitable.Fruitable.di

import android.content.Context
import com.fruitable.Fruitable.app.data.network.api.SalesApi
import com.fruitable.Fruitable.app.data.network.api.UserApi
import com.fruitable.Fruitable.app.data.repository.SaleRepositoryImpl
import com.fruitable.Fruitable.app.data.repository.UserRepositoryImpl
import com.fruitable.Fruitable.app.data.storage.CookieStorage
import com.fruitable.Fruitable.app.domain.repository.SaleRepository
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import com.fruitable.Fruitable.app.domain.utils.log
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl("http://3.34.197.243:8080") // baseURL
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
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
    fun provideUserRepository(cookieStore: CookieStorage, api: UserApi): UserRepository {
        return UserRepositoryImpl(cookieStore, api)
    }
    // sale api 제공
    @Provides
    @Singleton
    fun provideSalesApi(retrofit: Retrofit): SalesApi {
        return retrofit.create(SalesApi::class.java)
    }
    // sale repository 제공
    @Provides
    @Singleton
    fun provideSalesRepository(api: SalesApi): SaleRepository {
        return SaleRepositoryImpl(api)
    }
}
