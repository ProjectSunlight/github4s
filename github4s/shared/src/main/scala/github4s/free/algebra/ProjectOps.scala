/*
 * Copyright 2016-2017 47 Degrees, LLC. <http://www.47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github4s.free.algebra

import cats.InjectK
import cats.free.Free
import github4s.GithubResponses._
import github4s.free.domain.{Card, Column, Project}

/**
 * Project ops ADT
 */
sealed trait ProjectOp[A]

final case class ListRepoProjects(
    owner: String,
    repo: String,
    accessToken: Option[String] = None
) extends ProjectOp[GHResponse[List[Project]]]

final case class ListOrgProjects(
    org: String,
    accessToken: Option[String] = None
) extends ProjectOp[GHResponse[List[Project]]]

final case class ListProjectColumns(
    id: Int,
    accessToken: Option[String] = None
) extends ProjectOp[GHResponse[List[Column]]]

final case class ListProjectCards(
    id: Int,
    accessToken: Option[String] = None
) extends ProjectOp[GHResponse[List[Card]]]

/**
 * Exposes Project operations as a Free monadic algebra that may be combined with other Algebras via
 * Coproduct
 */
class ProjectOps[F[_]](implicit I: InjectK[ProjectOp, F]) {

  def listRepoProjects(
      owner: String,
      repo: String,
      accessToken: Option[String] = None
  ): Free[F, GHResponse[List[Project]]] =
    Free.inject[ProjectOp, F](ListRepoProjects(owner, repo, accessToken))

  def listOrgProjects(
      org: String,
      accessToken: Option[String] = None
  ): Free[F, GHResponse[List[Project]]] =
    Free.inject[ProjectOp, F](ListOrgProjects(org, accessToken))

  def listProjectColumns(
      id: Int,
      accessToken: Option[String] = None
  ): Free[F, GHResponse[List[Column]]] =
    Free.inject[ProjectOp, F](ListProjectColumns(id, accessToken))

  def listProjectCards(
      id: Int,
      accessToken: Option[String] = None
  ): Free[F, GHResponse[List[Card]]] =
    Free.inject[ProjectOp, F](ListProjectCards(id, accessToken))
}

/**
 * Default implicit based DI factory from which instances of the ProjectOps may be obtained
 */
object ProjectOps {

  implicit def instance[F[_]](implicit I: InjectK[ProjectOp, F]): ProjectOps[F] = new ProjectOps[F]

}
