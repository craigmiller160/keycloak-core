package io.craigmiller160.keycloak.core.model.request

interface HttpRequest {
  val requestUri: String
  fun getHeaderValue(headerName: String): String?
}
