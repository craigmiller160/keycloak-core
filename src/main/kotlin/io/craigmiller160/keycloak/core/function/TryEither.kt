package io.craigmiller160.keycloak.core.function

import arrow.core.Either

typealias TryEither<T> = Either<Throwable, T>