package io.craigmiller160.keycloak.core.exception

class KeycloakTokenValidationException(msg: String, cause: Throwable? = null) :
  RuntimeException(msg, cause)
