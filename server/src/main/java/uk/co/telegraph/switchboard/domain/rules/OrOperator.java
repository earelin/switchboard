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

package uk.co.telegraph.switchboard.domain.rules;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import uk.co.telegraph.switchboard.domain.ClientInfo;

public class OrOperator implements Rule {

  private final Set<Rule> rules;

  public OrOperator(Rule...rules) {
    this.rules = new HashSet<>(Arrays.asList(rules));
  }

  @Override
  public boolean isEnabledForClient(ClientInfo clientInfo) {
    return rules.stream()
        .map(rule -> rule.isEnabledForClient(clientInfo))
        .reduce(Boolean::logicalOr)
        .orElse(false);
  }
}
