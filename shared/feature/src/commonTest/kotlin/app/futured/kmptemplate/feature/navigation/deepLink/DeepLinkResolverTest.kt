package app.futured.kmptemplate.feature.navigation.deepLink

import kotlin.test.Test
import kotlin.test.assertEquals

class DeepLinkResolverTest {

    private val deepLinkResolver: DeepLinkResolver = DeepLinkResolverImpl()

    @Test
    fun `test unknown deep link`() {
        assertEquals(
            expected = null,
            actual = deepLinkResolver.resolve("kmptemplate://stub"),
        )
    }

    @Test
    fun `test home deep link`() {
        assertEquals(
            expected = DeepLinkDestination.HomeTab,
            actual = deepLinkResolver.resolve("kmptemplate://home"),
        )
    }

    @Test
    fun `test profile deep link`() {
        assertEquals(
            expected = DeepLinkDestination.ProfileTab,
            actual = deepLinkResolver.resolve("kmptemplate://profile"),
        )
    }

    @Test
    fun `test secondScreen deep link`() {
        assertEquals(
            expected = DeepLinkDestination.SecondScreen,
            actual = deepLinkResolver.resolve("kmptemplate://home/second"),
        )
    }

    @Test
    fun `test thirdScreen deep link - no params`() {
        assertEquals(
            expected = null,
            actual = deepLinkResolver.resolve("kmptemplate://home/third"),
        )
    }

    @Test
    fun `test thirdScreen deep link - with path param`() {
        assertEquals(
            expected = DeepLinkDestination.ThirdScreen("ARGUMENT"),
            actual = deepLinkResolver.resolve("kmptemplate://home/third/ARGUMENT"),
        )
    }

    @Test
    fun `test thirdScreen deep link - with query param`() {
        assertEquals(
            expected = DeepLinkDestination.ThirdScreen("ARGUMENT"),
            actual = deepLinkResolver.resolve("kmptemplate://home/third?arg=ARGUMENT"),
        )
    }
}
