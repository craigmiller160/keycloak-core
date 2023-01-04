package io.craigmiller160.keycloak.core.service

import arrow.core.Either
import com.nimbusds.jose.jwk.JWKSet
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.function.TryEither
import java.net.URL

class KeycloakOperationService {
    private fun getJwkEndpointForRealm(realmName: String): String =
        "/realms/$realmName/protocol/openid-connect/certs"

    fun getJWKSet(config: KeycloakConfig): TryEither<JWKSet> = Either.catch {
        val uri = getJwkEndpointForRealm(config.realmName)
        JWKSet.load(URL("${config.keycloakHost}$uri"))
    }
}