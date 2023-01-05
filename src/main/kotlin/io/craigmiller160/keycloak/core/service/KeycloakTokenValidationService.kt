package io.craigmiller160.keycloak.core.service

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.leftIfNull
import arrow.core.flatMap
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.SignedJWT
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.nimbusds.jwt.proc.JWTProcessor
import io.craigmiller160.keycloak.core.config.KeycloakConfig
import io.craigmiller160.keycloak.core.exception.KeycloakTokenValidationException
import io.craigmiller160.keycloak.core.function.TryEither
import io.craigmiller160.keycloak.core.function.flatMapCatch
import io.craigmiller160.keycloak.core.function.leftIfNull
import io.craigmiller160.keycloak.core.model.keycloak.KeycloakToken
import io.craigmiller160.keycloak.core.model.request.HttpRequest

class KeycloakTokenValidationService(
    private val config: KeycloakConfig,
    private val jwkService: KeycloakJwkService
) {
    companion object {
        private const val ACCESS_ROLE_NAME = "access"
    }

    // TODO need to take in RequestWrapper to pull out the token
    // TODO need to support insecure path evaluation
    fun validateToken(request: HttpRequest): TryEither<KeycloakToken> =
        getTokenFromRequest(request)
            .flatMap { token -> either.eager {
                val jwkSet = jwkService.getAndCacheJWKSet(config).bind()
                val jwt = Either.catch { SignedJWT.parse(token) }.bind()
                jwt to jwkSet
            } }
            .map { (jwt, jwkSet) -> jwt to createJwtProcessor(jwt, jwkSet) }
            .flatMapCatch { (jwt, jwtProcessor) -> jwtProcessor.process(jwt, null) }
            .map { KeycloakToken.fromClaimsSet(it) }
            .flatMap { validateTokenClaims(it) }

    private fun getTokenFromRequest(request: HttpRequest): TryEither<String> =
        request.getHeaderValue("Authorization")
            ?.replace("Bearer ", "")
            .leftIfNull { KeycloakTokenValidationException("No bearer token in request") }

    private fun validateTokenClaims(token: KeycloakToken): TryEither<KeycloakToken> {
        if (token.resourceAccess[config.clientId]?.roles?.contains(ACCESS_ROLE_NAME) == true) {
            TODO()
        }
        TODO()
    }

    private fun createJwtProcessor(jwt: SignedJWT, jwkSet: JWKSet): JWTProcessor<SecurityContext> {
        val keySource = ImmutableJWKSet<SecurityContext>(jwkSet)
        val keySelector = JWSVerificationKeySelector(jwt.header.algorithm, keySource)
        return DefaultJWTProcessor<SecurityContext>().apply {
            jwsKeySelector = keySelector
        }
    }
}