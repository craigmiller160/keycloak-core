package io.craigmiller160.keycloak.core.extension

import com.nimbusds.jwt.JWTClaimsSet
import java.util.UUID

fun JWTClaimsSet.getUUIDClaim(name: String): UUID = getStringClaim(name).let(UUID::fromString)
