package io.craigmiller160.keycloak.core.service

object KeycloakUtils {
    fun getJwkEndpointForRealm(realmName: String): String =
        "/realms/$realmName/protocol/openid-connect/certs"
}