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

package github4s.free.domain

case class Project(
    owner_url: String,
    url: String,
    html_url: String,
    columns_url: String,
    id: Int,
    name: String,
    body: String,
    number: Int,
    state: String,
    creator: User,
    created_at: String,
    updated_at: String)

case class Column(
    id: Int,
    name: String,
    url: String,
    project_url: String, // parse out project Id?
    cards_url: String,
    created_at: String,
    updated_at: String)

case class Card(
    url: String,
    column_url: String, // parse out columnId? or make rich helper things?
    content_url: Option[String], // parse out issueId?
    id: Int,
    note: Option[String],
    creator: User,
    created_at: String,
    updated_at: String)
