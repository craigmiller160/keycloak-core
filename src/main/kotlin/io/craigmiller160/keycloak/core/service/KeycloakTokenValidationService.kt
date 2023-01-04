package io.craigmiller160.keycloak.core.service

import arrow.core.getOrHandle
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.function.TryEither

class KeycloakTokenValidationService(
    config: KeycloakConfig,
    operationsService: KeycloakOperationService
) {
    // TODO not inject-able, so cannot be tested
    private val jwkSet: TryEither<JWKSet> = operationsService.getJWKSet(config)

    fun validateToken(token: String) {
        val theJwkSet = jwkSet.getOrHandle { throw it }

        val jwt = SignedJWT.parse(token)

        val jwtProcessor = DefaultJWTProcessor<SecurityContext>()
        val keySource = ImmutableJWKSet<SecurityContext>(theJwkSet)
        val keySelector = JWSVerificationKeySelector(jwt.header.algorithm, keySource)
        jwtProcessor.jwsKeySelector = keySelector

        val claims = jwtProcessor.process(jwt, null)
    }
}