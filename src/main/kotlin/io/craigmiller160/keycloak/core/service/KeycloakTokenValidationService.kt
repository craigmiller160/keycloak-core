package io.craigmiller160.keycloak.core.service

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.getOrHandle
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.function.TryEither
import io.craigmiller160.keycloak.core.function.flatMapCatch

class KeycloakTokenValidationService(
    config: KeycloakConfig,
    operationsService: KeycloakOperationService
) {
    // TODO not inject-able, so cannot be tested
    private val jwkSetEither: TryEither<JWKSet> = operationsService.getJWKSet(config)

    fun validateToken(token: String) {
        either.eager {
            val jwt = Either.catch { SignedJWT.parse(token) }.bind()
            val jwkSet = jwkSetEither.bind()
            Pair(jwt, jwkSet)
        }
            .map { (jwt, jwkSet) ->
                val jwtProcessor = DefaultJWTProcessor<SecurityContext>()
                val keySource = ImmutableJWKSet<SecurityContext>(jwkSet)
                val keySelector = JWSVerificationKeySelector(jwt.header.algorithm, keySource)
                jwtProcessor.jwsKeySelector = keySelector
                Pair(jwt, jwtProcessor)
            }
            .flatMapCatch { (jwt, jwtProcessor) -> jwtProcessor.process(jwt, null) }
    }
}