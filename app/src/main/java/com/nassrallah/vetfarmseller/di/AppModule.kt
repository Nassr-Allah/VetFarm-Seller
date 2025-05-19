package com.nassrallah.vetfarmseller.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.AuthDataSource
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.CommuneDataSource
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.WilayaDataSource
import com.nassrallah.vetfarmseller.feature_auth.data.data_source.local.AppDataStore
import com.nassrallah.vetfarmseller.feature_auth.data.repository.AuthRepositoryImpl
import com.nassrallah.vetfarmseller.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.CreateSellerUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.GetAllWilayasUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.GetCommunesByWilayaUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveIdUseCase
import com.nassrallah.vetfarmseller.feature_auth.domain.use_case.RetrieveTokenUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.data.data_source.DashboardDataSource
import com.nassrallah.vetfarmseller.feature_dashboard.data.repository.DashboardRepository
import com.nassrallah.vetfarmseller.feature_dashboard.domain.repository.DashboardRepositoryImpl
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetOrdersCountUseCase
import com.nassrallah.vetfarmseller.feature_dashboard.domain.use_case.GetSellerIncomeUseCase
import com.nassrallah.vetfarmseller.feature_inventory.data.data_source.remote.InventoryDataSource
import com.nassrallah.vetfarmseller.feature_inventory.data.data_source.remote.VetRequestDataSource
import com.nassrallah.vetfarmseller.feature_inventory.data.repository.InventoryRepositoryImpl
import com.nassrallah.vetfarmseller.feature_inventory.domain.repository.InventoryRepository
import com.nassrallah.vetfarmseller.feature_inventory.domain.use_case.AddRequestUseCase
import com.nassrallah.vetfarmseller.feature_order.data.data_source.remote.OrderDataSource
import com.nassrallah.vetfarmseller.feature_order.data.repository.OrderRepositoryImpl
import com.nassrallah.vetfarmseller.feature_order.domain.repository.OrderRepository
import com.nassrallah.vetfarmseller.feature_order.domain.use_case.GetOrdersUseCase
import com.nassrallah.vetfarmseller.feature_profile.data.data_source.SellerDataSource
import com.nassrallah.vetfarmseller.feature_profile.data.repository.ProfileRepositoryImpl
import com.nassrallah.vetfarmseller.feature_profile.domain.repository.ProfileRepository
import com.nassrallah.vetfarmseller.feature_profile.domain.use_case.GetSellerByIdUseCase
import com.nassrallah.vetfarmseller.feature_profile.domain.use_case.UpdateSellerUseCase
import com.nassrallah.vetfarmseller.location.LocationClient
import com.nassrallah.vetfarmseller.location.LocationClientImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "seller_credentials")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient {
            install(Auth) {
                bearer {  }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideFusedLocationProvider(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context) = AppDataStore(context.dataStore)

    @Provides
    @Singleton
    fun provideOrderRepository(client: HttpClient): OrderRepository {
        return OrderRepositoryImpl(OrderDataSource(client))
    }

    @Provides
    @Singleton
    fun provideInventoryRepository(client: HttpClient): InventoryRepository {
        return InventoryRepositoryImpl(InventoryDataSource(client), VetRequestDataSource(client))
    }

    @Provides
    @Singleton
    fun provideAuthRepository(client: HttpClient, dataStore: AppDataStore): AuthRepository {
        return AuthRepositoryImpl(
            AuthDataSource(client),
            dataStore,
            WilayaDataSource(client),
            CommuneDataSource(client)
        )
    }

    @Provides
    @Singleton
    fun provideProfileRepository(client: HttpClient): ProfileRepository {
        return ProfileRepositoryImpl(SellerDataSource(client))
    }

    @Provides
    @Singleton
    fun provideDashboardRepository(client: HttpClient): DashboardRepository {
        return DashboardRepositoryImpl(SellerDataSource(client), DashboardDataSource(client))
    }

    @Provides
    @Singleton
    fun provideTokenUseCase(repository: AuthRepository) = RetrieveTokenUseCase(repository)

    @Provides
    @Singleton
    fun provideIdUseCase(repository: AuthRepository) = RetrieveIdUseCase(repository)

    @Provides
    @Singleton
    fun provideOrdersUseCase(repository: OrderRepository): GetOrdersUseCase {
        return GetOrdersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddRequestUseCase(
        repository: InventoryRepository,
        tokenUseCase: RetrieveTokenUseCase,
        idUseCase: RetrieveIdUseCase
    ) = AddRequestUseCase(repository, tokenUseCase, idUseCase)

    @Provides
    @Singleton
    fun provideGetSellerByIdUseCase(repository: ProfileRepository): GetSellerByIdUseCase {
        return GetSellerByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateSellerUseCase(repository: ProfileRepository): UpdateSellerUseCase {
        return UpdateSellerUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOrdersCountUseCase(
        repository: OrderRepository,
        retrieveIdUseCase: RetrieveIdUseCase,
        retrieveTokenUseCase: RetrieveTokenUseCase
    ): GetOrdersCountUseCase {
        return GetOrdersCountUseCase(repository, retrieveTokenUseCase, retrieveIdUseCase)
    }

    @Provides
    @Singleton
    fun provideGetSellerIncomeUseCase(
        repository: DashboardRepository,
        retrieveIdUseCase: RetrieveIdUseCase,
        retrieveTokenUseCase: RetrieveTokenUseCase
    ): GetSellerIncomeUseCase {
        return GetSellerIncomeUseCase(repository, retrieveIdUseCase, retrieveTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetAllWilayasUseCase(repository: AuthRepository): GetAllWilayasUseCase {
        return GetAllWilayasUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCommunesByWilayaUseCase(repository: AuthRepository): GetCommunesByWilayaUseCase {
        return GetCommunesByWilayaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLocationClient(@ApplicationContext context: Context, client: FusedLocationProviderClient): LocationClient {
        return LocationClientImpl(context, client)
    }

}