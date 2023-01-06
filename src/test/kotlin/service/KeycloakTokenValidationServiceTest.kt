package service

import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.service.KeycloakJwkService
import io.craigmiller160.keycloak.core.service.KeycloakTokenValidationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class KeycloakTokenValidationServiceTest {
  companion object {
    private const val CLIENT_ID = "my-client"
    private const val REALM_NAME = "my-realm"
    private const val HOST = "https://keycloak.com"
    private const val INSECURE_PATH = "/my-insecure/path"
  }
  @Mock private lateinit var jwkService: KeycloakJwkService
  private val config: KeycloakConfig =
    object : KeycloakConfig {
      override val clientId: String = CLIENT_ID
      override val clientSecret: String = ""
      override val realmName: String = REALM_NAME
      override val keycloakHost: String = HOST
      override val insecurePaths: List<String> = listOf(INSECURE_PATH)
    }
  private lateinit var tokenValidationService: KeycloakTokenValidationService

  @BeforeEach
  fun setup() {
    tokenValidationService = KeycloakTokenValidationService(config, jwkService)
  }

  @Test
  fun `validateToken - token is valid`() {
    TODO()
  }

  @Test
  fun `validateToken - token is expired`() {
    TODO()
  }

  @Test
  fun `validateToken - token does not have client access`() {
    TODO()
  }

  @Test
  fun `validateToken - path is insecure`() {
    TODO()
  }
}
