package service

import com.nimbusds.jose.jwk.JWKSet
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.service.KeycloakJwkService
import io.kotest.assertions.arrow.core.shouldBeRight
import java.io.InputStream
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class KeycloakJwkServiceTest {
  companion object {
    private const val HOST = "https://keycloak.com"
    private const val REALM = "my-realm"
  }
  @Mock private lateinit var config: KeycloakConfig
  @Test
  fun getAndCacheJWKSet() {
    val stream =
      KeycloakJwkServiceTest::class.java.classLoader.getResourceAsStream("keycloak-jwk.json")!!
    whenever(config.keycloakHost).thenReturn(HOST)
    whenever(config.realmName).thenReturn(REALM)

    var url = ""
    var downloaderCount = 0
    val service = KeycloakJwkService {
      url = it
      downloaderCount++
      getJwkStream()
    }
    val jwkSet = service.getAndCacheJWKSet(config).shouldBeRight()
    assertEquals("$HOST${service.getJwkEndpointForRealm(REALM)}", url)

    val expected = JWKSet.load(getJwkStream())
    assertEquals(2, jwkSet.keys.size)
    assertEquals(expected.keys[0], jwkSet.keys[0])
    assertEquals(expected.keys[1], jwkSet.keys[1])

    service.getAndCacheJWKSet(config).shouldBeRight()
    assertEquals(1, downloaderCount)
  }

  private fun getJwkStream(): InputStream =
    KeycloakJwkServiceTest::class.java.classLoader.getResourceAsStream("keycloak-jwk.json")!!
}
