package io.craigmiller160.keycloak.core.service

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.getOrHandle
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWT
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.nimbusds.jwt.proc.JWTProcessor
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.function.TryEither
import io.craigmiller160.keycloak.core.function.flatMapCatch

class KeycloakTokenValidationService(
    config: KeycloakConfig,
    operationsService: KeycloakOperationService
) {
    // TODO not efficient
    private val jwkSetEither: TryEither<JWKSet> = operationsService.getJWKSet(config)

    fun validateToken(token: String) {
        either.eager {
            val jwt = Either.catch { SignedJWT.parse(token) }.bind()
            val jwkSet = jwkSetEither.bind()
            Pair(jwt, jwkSet)
        }
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