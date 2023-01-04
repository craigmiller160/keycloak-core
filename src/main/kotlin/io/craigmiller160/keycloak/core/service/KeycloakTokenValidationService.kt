package io.craigmiller160.keycloak.core.service

import com.nimbusds.jose.jwk.JWKSet
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.function.TryEither

class KeycloakTokenValidationService(
    private val config: KeycloakConfig
) {
    private val jwkSet: TryEither<JWKSet> = KeycloakOperationService.getJWKSet(config)

    fun validateToken(token: String) {

    }
}