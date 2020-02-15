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

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Context {
  public static final String DEFAULT_KEY = "default";

  public static Context buildDefault() {
    return new Context(DEFAULT_KEY);
  }

  @EqualsAndHashCode.Include
  @NotBlank @Size(max = 64)
  private final String key;

  public Context(String key) {
    this.key = key;
  }

  public boolean isDefault() {
    return Objects.equals(key, DEFAULT_KEY);
  }
}
