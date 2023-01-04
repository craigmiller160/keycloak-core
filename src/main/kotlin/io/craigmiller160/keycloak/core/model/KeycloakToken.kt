package io.craigmiller160.keycloak.core.model

import com.nimbusds.jwt.JWTClaimsSet
import io.craigmiller160.keycloak.core.extension.getUUIDClaim
import java.util.UUID

data class KeycloakToken(
    val exp: Long,
    val iat: Long,
    val authTime: Long,
    val jti: UUID,
    val iss: String,
    val aud: String,
    val sub: UUID,
    val typ: String,
    val azp: String,
    val nonce: UUID,
    val sessionState: UUID,
    val acr: String,
    val allowedOrigins: List<String>,
    val realmAccess: KeycloakRealmAccess,
    val resourceAccess: KeycloakResourceAccess,
    val scope: String,
    val sid: UUID,
    val emailVerified: Boolean,
    val name: String,
    val preferredUsername: String,
    val givenName: String,
    val familyName: String,
    val email: String
) {
    companion object {
        @JvmStatic
        fun fromClaimsSet(claims: JWTClaimsSet): KeycloakToken =
            KeycloakToken(
                exp = claims.getLongClaim("exp"),
                iat = claims.getLongClaim("iat"),
                authTime = claims.getLongClaim("auth_time"),
                jti = claims.getUUIDClaim("jti"),
                iss = claims.getStringClaim("iss"),
                aud = claims.getStringClaim("aud"),
                sub = claims.getUUIDClaim("sub"),
                typ = claims.getStringClaim("typ"),
                azp = claims.getStringClaim("azp"),
                nonce = claims.getUUIDClaim("nonce"),
                sessionState = claims.getUUIDClaim("session_state"),
                acr = claims.getStringClaim("acr"),
                allowedOrigins = claims.getStringListClaim("allowed-origins"),
                realmAccess = KeycloakRealmAccess.fromClaims(claims.getJSONObjectClaim("realm_access"))
            )
    }
}
