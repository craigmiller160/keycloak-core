package io.craigmiller160.keycloak.core.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.nimbusds.jwt.JWTClaimsSet
import io.craigmiller160.keycloak.core.model.keycloak.KeycloakToken
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class KeycloakTokenTest {
  @Test
  fun `converts claims to token object`() {
    val rawClaimsType = jacksonTypeRef<Map<String, Any>>()
    val rawClaims =
      KeycloakTokenTest::class.java.classLoader.getResourceAsStream("keycloak-token.json").let {
        jacksonObjectMapper().readValue(it, rawClaimsType)
      }
    val claimsSet = JWTClaimsSet.parse(rawClaims)

    val token = KeycloakToken.fromClaimsSet(claimsSet)
    assertThat(token)
      .hasFieldOrPropertyWithValue("exp", 1672957697L)
      .hasFieldOrPropertyWithValue("iat", 1672957397)
      .hasFieldOrPropertyWithValue("authTime", 1672957397)
      .hasFieldOrPropertyWithValue("jti", UUID.fromString("c861cad8-80a4-4251-96cb-282a1e2a44a1"))
      .hasFieldOrPropertyWithValue("iss", "http://127.0.0.1:8080/realms/my-realm")
      .hasFieldOrPropertyWithValue("aud", "account")
      .hasFieldOrPropertyWithValue("sub", UUID.fromString("12cf53ba-962e-4e68-a1ce-c61f6da7d759"))
      .hasFieldOrPropertyWithValue("typ", "Bearer")
      .hasFieldOrPropertyWithValue("azp", "test-client")
      .hasFieldOrPropertyWithValue("nonce", UUID.fromString("9504bd8f-6d04-4c14-aea1-9f1c8ae58bf2"))
      .hasFieldOrPropertyWithValue(
        "sessionState", UUID.fromString("f1532c46-5d48-41ef-bd46-1bfd6105dad1"))
      .hasFieldOrPropertyWithValue("acr", "1")
      .hasFieldOrPropertyWithValue("allowedOrigins", listOf("http://localhost:3003"))
      .hasFieldOrPropertyWithValue("scope", "openid profile email")
      .hasFieldOrPropertyWithValue("sid", UUID.fromString("f1532c46-5d48-41ef-bd46-1bfd6105dad1"))
      .hasFieldOrPropertyWithValue("emailVerified", true)
      .hasFieldOrPropertyWithValue("name", "Test User")
      .hasFieldOrPropertyWithValue("preferredUsername", "test-user")
      .hasFieldOrPropertyWithValue("givenName", "Test")
      .hasFieldOrPropertyWithValue("familyName", "User")
      .hasFieldOrPropertyWithValue("email", "john@gmail.com")

    assertThat(token.realmAccess.roles)
      .hasSize(3)
      .contains("offline_access", "uma_authorization", "default-roles-my-realm")
    assertThat(token.resourceAccess).hasSize(1).containsKeys("account")
    assertThat(token.resourceAccess["account"]?.roles)
      .isNotNull
      .hasSize(3)
      .contains("manage-account", "manage-account-links", "view-profile")
  }
}
