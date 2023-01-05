package service

import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.service.JwkDownloader
import io.craigmiller160.keycloak.core.service.KeycloakJwkService
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
  @Mock private lateinit var downloader: JwkDownloader
  @Mock private lateinit var config: KeycloakConfig
  @Test
  fun getAndCacheJWKSet() {
    val stream =
      KeycloakJwkServiceTest::class.java.classLoader.getResourceAsStream("keycloak-jwk.json")
    whenever(config.keycloakHost).thenReturn(HOST)
    whenever(config.realmName).thenReturn(REALM)
    whenever(downloader).thenReturn { stream }

    val service = KeycloakJwkService(downloader)
    TODO()
  }
}
