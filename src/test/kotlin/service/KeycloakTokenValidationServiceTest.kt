package service

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jwt.JWTClaimsSet
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.model.request.HttpRequest
import io.craigmiller160.keycloak.core.service.KeycloakJwkService
import io.craigmiller160.keycloak.core.service.KeycloakTokenValidationService
import io.kotest.assertions.arrow.core.shouldBeRight
import java.io.InputStream
import org.assertj.core.api.Assertions.assertThat
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
  private lateinit var jwtClaims: JWTClaimsSet
  private lateinit var jwkSet: JWKSet

  @BeforeEach
  fun setup() {
    tokenValidationService = KeycloakTokenValidationService(config, jwkService)
    jwkSet = openResourceStream("keycloak-jwk.json").let { JWKSet.load(it) }
    jwtClaims =
      openResourceStream("keycloak-token.json").reader().readText().let { JWTClaimsSet.parse(it) }
  }

  private fun openResourceStream(path: String): InputStream =
    KeycloakTokenValidationServiceTest::class.java.classLoader.getResourceAsStream(path)!!

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
    val request =
      object : HttpRequest {
        override val requestUri: String = INSECURE_PATH
        override fun getHeaderValue(headerName: String): String? = null
      }

    val response = tokenValidationService.validateToken(request).shouldBeRight()
    assertThat(response)
      .hasFieldOrPropertyWithValue("isSecureUri", false)
      .hasFieldOrPropertyWithValue("token", null)
  }
}
