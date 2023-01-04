package io.craigmiller160.keycloak.core.model

data class KeycloakResourceAccess(
    override val roles: List<String>
) : KeycloakAccessEntity
