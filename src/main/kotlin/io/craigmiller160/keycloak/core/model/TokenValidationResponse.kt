package io.craigmiller160.keycloak.core.model

import io.craigmiller160.keycloak.core.model.keycloak.KeycloakToken

data class TokenValidationResponse(
    val isSecureUri: Boolean,
    val token: KeycloakToken? = null
)
