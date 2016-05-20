package com.fortysevendeg.github4s.free.interpreters

import cats.{ Eval, Monad, MonadError }
import scalaz.concurrent.Task

trait TaskInstances {
  implicit val taskMonad: Monad[Task] with MonadError[Task, Throwable] = new Monad[Task] with MonadError[Task, Throwable] {

    def pure[A](x: A): Task[A] = Task.now(x)

    override def map[A, B](fa: Task[A])(f: A ⇒ B): Task[B] =
      fa map f

    override def flatMap[A, B](fa: Task[A])(f: A ⇒ Task[B]): Task[B] =
      fa flatMap f

    override def pureEval[A](x: Eval[A]): Task[A] =
      Task.fork(Task.delay(x.value))

    override def raiseError[A](e: Throwable): Task[A] =
      Task.fail(e)

    override def handleErrorWith[A](fa: Task[A])(f: Throwable ⇒ Task[A]): Task[A] =
      fa.handleWith({ case x ⇒ f(x) })
  }
}

/** Implicit implementation of the interpreters with Task as target monad   */
object TaskInterpreters extends Interpreters[Task] with TaskInstances