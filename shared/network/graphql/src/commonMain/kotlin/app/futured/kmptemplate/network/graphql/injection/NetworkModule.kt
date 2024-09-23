package app.futured.kmptemplate.network.graphql.injection

import app.futured.kmptemplate.network.graphql.client.ApolloClientFactory
import com.apollographql.apollo.ApolloClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("app.futured.kmptemplate.network.graphql")
class NetworkGraphqlModule {
    @Single
    fun apolloClient(factory: ApolloClientFactory): ApolloClient = factory.create()
}
