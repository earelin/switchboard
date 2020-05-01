/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.infrastructure.jpa.entities;

import static uk.co.telegraph.switchboard.domain.Context.NAME_MAX_LENGTH;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("user_group")
public class UserGroupEntity {
  @Id
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne
  @ToString.Exclude
  private ApplicationEntity application;

  @Column(length = NAME_MAX_LENGTH)
  private String name;

  @ElementCollection
  @Column(name = "name")
  private Set<String> users = new HashSet<>();
}
