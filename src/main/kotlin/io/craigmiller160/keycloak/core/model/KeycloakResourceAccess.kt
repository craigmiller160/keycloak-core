package io.craigmiller160.keycloak.core.model

data class KeycloakResourceAccess(
    override val roles: List<String>
) : KeycloakAccessEntity {
    companion object {
        @JvmStatic
        fun fromClaims(claims: Map<String,Any>): KeycloakResourceAccess =
            KeycloakResourceAccess(
                roles = claims["roles"] as List<String>
            )

        @JvmStatic
        fun fromAllResourcesClaims(claims: Map<String, Any>): KeycloakResourcesAccess =
            claims
                .map { (key, value) -> key to fromClaims(value as Map<String,Any>) }
                .toMap()
    }
}

typealias KeycloakResourcesAccess = Map<String, KeycloakResourceAccess>