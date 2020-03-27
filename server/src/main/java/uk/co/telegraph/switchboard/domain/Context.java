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

package uk.co.telegraph.switchboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Context {

  @Id
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne
  @JoinColumn(name = "application", referencedColumnName = "application_id")
  private ContextsAggregator contextsAggregator;

  @Column(length = 32)
  private String name;

  Context() {
  }

  public Context(ContextsAggregator contextsAggregator, String name) {
    this.contextsAggregator = contextsAggregator;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  void setId(Long id) {
    this.id = id;
  }

  public ContextsAggregator getContextsAggregator() {
    return contextsAggregator;
  }

  void setContextsAggregator(ContextsAggregator contextsAggregator) {
    this.contextsAggregator = contextsAggregator;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Context name cannot be empty or null");
    }

    if (name.length() > 32) {
      throw new IllegalArgumentException("Context name cannot be longer than 64 characters");
    }

    this.name = name;
  }
}
