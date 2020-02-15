/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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

import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class ClientInfo {
  private final String applicationKey;
  private final String contextKey;
  private final String instance;
  private final String user;
  private final ZonedDateTime dateTime;

  ClientInfo(String applicationKey, String contextKey, String instance, String user,
      ZonedDateTime dateTime) {
    this.applicationKey = applicationKey;
    this.contextKey = contextKey;
    this.instance = instance;
    this.user = user;
    this.dateTime = dateTime;
  }

  public Context getContext() {
    return new Context(contextKey);
  }

  public static ClientInfoBuilder builder() {
    return new ClientInfoBuilder();
  }

  public static class ClientInfoBuilder {

    private String applicationKey;
    private String contextKey = Context.DEFAULT_KEY;
    private String instance;
    private String user;
    private ZonedDateTime dateTime;

    ClientInfoBuilder() {
    }

    public ClientInfoBuilder applicationKey(String applicationKey) {
      this.applicationKey = applicationKey;
      return this;
    }

    public ClientInfoBuilder contextKey(String contextKey) {
      this.contextKey = contextKey;
      return this;
    }

    public ClientInfoBuilder instance(String instance) {
      this.instance = instance;
      return this;
    }

    public ClientInfoBuilder user(String user) {
      this.user = user;
      return this;
    }

    public ClientInfoBuilder dateTime(ZonedDateTime dateTime) {
      this.dateTime = dateTime;
      return this;
    }

    public ClientInfo build() {
      return new ClientInfo(applicationKey, contextKey, instance, user, dateTime);
    }
  }
}
