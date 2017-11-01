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

package github4s.api

import github4s.GithubResponses.GHResponse
import github4s.{GithubApiUrls, HttpClient, HttpRequestBuilderExtension}
import github4s.free.domain._
import github4s.free.interpreters.Capture
import github4s.util.URLEncoder
import io.circe.syntax._
import io.circe.generic.auto._

/** Factory to encapsulate calls related to Projects operations  */
class Projects[C, M[_]](
    implicit urls: GithubApiUrls,
    C: Capture[M],
    httpClientImpl: HttpRequestBuilderExtension[C, M]) {

  val httpClient = new HttpClient[C, M]

  /**
   * List projects for a repository
   *
   * @param accessToken to identify the authenticated user
   * @param headers optional user headers to include in the request
   * @param owner of the repo
   * @param repo name of the repo
   * @return a GHResponse with the project list.
   */
  def listRepoProjects(
      accessToken: Option[String] = None,
      headers: Map[String, String] = Map(),
      owner: String,
      repo: String): M[GHResponse[List[Project]]] =
    httpClient.get[List[Project]](accessToken, s"repos/$owner/$repo/projects", headers)

  /**
   * List projects for an organization
   *
   * @param accessToken to identify the authenticated user
   * @param headers optional user headers to include in the request
   * @param org name of the organization
   * @return a GHResponse with the project list.
   */
  def listOrgProjects(
      accessToken: Option[String] = None,
      headers: Map[String, String] = Map(),
      org: String): M[GHResponse[List[Project]]] =
    httpClient.get[List[Project]](accessToken, s"orgs/$org/projects", headers)

  /**
   * List columns for a project
   *
   * @param accessToken to identify the authenticated user
   * @param headers optional user headers to include in the request
   * @param projectId id of the project
   * @return a GHResponse with the project list.
   */
  def listProjectColumns(
      accessToken: Option[String] = None,
      headers: Map[String, String] = Map(),
      projectId: Int): M[GHResponse[List[Column]]] =
    httpClient.get[List[Column]](accessToken, s"projects/$projectId/columns", headers)

  /**
   * List cards for a project
   *
   * @param accessToken to identify the authenticated user
   * @param headers optional user headers to include in the request
   * @param columnId id of the project
   * @return a GHResponse with the project list.
   */
  def listProjectCards(
      accessToken: Option[String] = None,
      headers: Map[String, String] = Map(),
      columnId: Int): M[GHResponse[List[Card]]] =
    httpClient.get[List[Card]](accessToken, s"projects/columns/$columnId/cards", headers)

}
