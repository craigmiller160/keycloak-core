package io.craigmiller160.keycloak.core.service

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.flatMap
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.nimbusds.jwt.proc.JWTProcessor
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.function.flatMapCatch

class KeycloakTokenValidationService(
    private val config: KeycloakConfig,
    private val jwkService: KeycloakJwkService
) {

    fun validateToken(token: String) {
        jwkService.getAndCacheJWKSet(config)
            .flatMap { jwkSet -> either.eager {
                val jwt = Either.catch { SignedJWT.parse(token) }.bind()
                jwt to jwkSet
            } }
            .map { (jwt, jwkSet) -> jwt to createJwtProcessor(jwt, jwkSet) }
            .flatMapCatch { (jwt, jwtProcessor) -> jwtProcessor.process(jwt, null) }
    }

    private fun createJwtProcessor(jwt: SignedJWT, jwkSet: JWKSet): JWTProcessor<SecurityContext> {
        val keySource = ImmutableJWKSet<SecurityContext>(jwkSet)
        val keySelector = JWSVerificationKeySelector(jwt.header.algorithm, keySource)
        return DefaultJWTProcessor<SecurityContext>().apply {
            jwsKeySelector = keySelector
        }
    }
}