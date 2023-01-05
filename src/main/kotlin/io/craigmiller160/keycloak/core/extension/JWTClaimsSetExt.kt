package io.craigmiller160.keycloak.core.extension

import com.nimbusds.jwt.JWTClaimsSet
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

fun JWTClaimsSet.getUUIDClaim(name: String): UUID = getStringClaim(name).let(UUID::fromString)

fun JWTClaimsSet.getZDTFromDateClaim(name: String): ZonedDateTime =
  getDateClaim(name).toInstant().atZone(ZoneId.of("UTC"))

fun JWTClaimsSet.getZDTFromMillisClaim(name: String): ZonedDateTime =
  Instant.ofEpochSecond(getLongClaim(name)).atZone(ZoneId.of("UTC"))
