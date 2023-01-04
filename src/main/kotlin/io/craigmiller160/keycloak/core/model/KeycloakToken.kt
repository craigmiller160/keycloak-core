package io.craigmiller160.keycloak.core.model

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
)
