package io.craigmiller160.keycloak.core.model.keycloak

import com.nimbusds.jwt.JWTClaimsSet
import io.craigmiller160.keycloak.core.extension.getUUIDClaim
import io.craigmiller160.keycloak.core.extension.getZDTFromDateClaim
import io.craigmiller160.keycloak.core.extension.getZDTFromMillisClaim
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

data class KeycloakToken(
  val exp: ZonedDateTime,
  val iat: ZonedDateTime,
  val authTime: ZonedDateTime,
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
  val resourceAccess: KeycloakResourcesAccess,
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
        exp = claims.getZDTFromDateClaim("exp"),
        iat = claims.getZDTFromDateClaim("iat"),
        authTime = claims.getZDTFromMillisClaim("auth_time"),
        jti = claims.getUUIDClaim("jti"),
        iss = claims.getStringClaim("iss"),
        aud = claims.getStringListClaim("aud").first(),
        sub = claims.getUUIDClaim("sub"),
        typ = claims.getStringClaim("typ"),
        azp = claims.getStringClaim("azp"),
        nonce = claims.getUUIDClaim("nonce"),
        sessionState = claims.getUUIDClaim("session_state"),
        acr = claims.getStringClaim("acr"),
        allowedOrigins = claims.getStringListClaim("allowed-origins"),
        realmAccess = KeycloakRealmAccess.fromClaims(claims.getJSONObjectClaim("realm_access")),
        resourceAccess =
          KeycloakResourceAccess.fromAllResourcesClaims(
            claims.getJSONObjectClaim("resource_access")),
        scope = claims.getStringClaim("scope"),
        sid = claims.getUUIDClaim("sid"),
        emailVerified = claims.getBooleanClaim("email_verified"),
        name = claims.getStringClaim("name"),
        preferredUsername = claims.getStringClaim("preferred_username"),
        givenName = claims.getStringClaim("given_name"),
        familyName = claims.getStringClaim("family_name"),
        email = claims.getStringClaim("email"))
  }
}

fun foo() {
  val foo = Instant.ofEpochMilli(0).atZone(ZoneId.systemDefault())
}
