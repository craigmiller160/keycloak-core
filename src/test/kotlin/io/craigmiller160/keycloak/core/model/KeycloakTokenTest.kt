package io.craigmiller160.keycloak.core.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import com.nimbusds.jwt.JWTClaimsSet
import io.craigmiller160.keycloak.core.model.keycloak.KeycloakToken
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
    TODO("Validate the token")
  }
}
