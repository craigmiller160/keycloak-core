package io.craigmiller160.keycloak.core.config

interface KeycloakConfig {
    val clientId: String
    val clientSecret: String
    val keycloakHost: String
}