package io.craigmiller160.keycloak.core.model

data class KeycloakRealmAccess(
    override val roles: List<String>
) : KeycloakAccessEntity