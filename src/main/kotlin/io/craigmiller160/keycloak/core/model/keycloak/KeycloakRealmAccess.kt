package io.craigmiller160.keycloak.core.model.keycloak

data class KeycloakRealmAccess(
    override val roles: List<String>
) : KeycloakAccessEntity {
    companion object {
        @JvmStatic
        fun fromClaims(claims: Map<String,Any>): KeycloakRealmAccess =
            KeycloakRealmAccess(
                roles = claims["roles"] as List<String>
            )
    }
}