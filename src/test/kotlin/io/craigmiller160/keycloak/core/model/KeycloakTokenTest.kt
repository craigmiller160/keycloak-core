package io.craigmiller160.keycloak.core.model

import org.junit.jupiter.api.Test
import java.util.UUID

class KeycloakTokenTest {
    private val rawClaims = mapOf<String,Any>(
        "exp" to 1,
        "iat" to 2,
        "auth_time" to 3,
        "jti" to UUID.randomUUID().toString()
    )
    @Test
    fun `converts claims to token object`() {
        TODO()
    }
}