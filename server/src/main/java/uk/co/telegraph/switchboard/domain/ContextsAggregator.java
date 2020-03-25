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

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Context aggregator.
 */
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ContextsAggregator {

  @Id
  @OneToOne
  @EqualsAndHashCode.Include
  private Application application;

  @OneToMany
  private List<Context> contexts;

  ContextsAggregator() {
  }

  public ContextsAggregator(Application application) {
    this.application = application;
  }

  public void addContext(String name) {
    throw new UnsupportedOperationException();
  }

  public void removeContext(String name) {
    throw new UnsupportedOperationException();
  }

  public Context getContext(String name) {
    throw new UnsupportedOperationException();
  }

  public Application getApplication() {
    throw new UnsupportedOperationException();
  }

  void setApplication(Application application) {
    throw new UnsupportedOperationException();
  }

  public List<Context> getContexts() {
    throw new UnsupportedOperationException();
  }

  void setContexts(List<Context> contexts) {
    throw new UnsupportedOperationException();
  }
}
