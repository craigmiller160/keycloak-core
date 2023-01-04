package io.craigmiller160.keycloak.core.function

import arrow.core.Either
import arrow.core.flatMap

typealias TryEither<T> = Either<Throwable, T>

fun <A,B> TryEither<A>.flatMapCatch(fn: (A) -> B): TryEither<B> =
    flatMap { a -> Either.catch { fn(a) } }