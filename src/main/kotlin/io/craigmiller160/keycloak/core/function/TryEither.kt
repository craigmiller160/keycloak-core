package io.craigmiller160.keycloak.core.function

import arrow.core.Either
import arrow.core.flatMap

internal typealias TryEither<T> = Either<Throwable, T>

internal fun <A, B> TryEither<A>.flatMapCatch(fn: (A) -> B): TryEither<B> = flatMap { a ->
  Either.catch { fn(a) }
}

internal fun <A> A?.leftIfNull(fn: () -> Throwable): TryEither<A> =
  this?.let { Either.Right(it) } ?: Either.Left(fn())
