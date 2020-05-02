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

import lombok.ToString;
import uk.co.telegraph.switchboard.domain.rules.DefaultRule;
import uk.co.telegraph.switchboard.domain.rules.Rule;

@ToString
public class FeatureFlag {

  private String id;
  private Rule rule;

  public FeatureFlag(String id) {
    this.id = id;
    this.rule = new DefaultRule(false);
  }

  public FeatureFlag(String id, Rule rule) {
    this.id = id;
    this.rule = rule;
  }

  boolean isEnabledForClient(ClientInfo clientInfo) {
    return rule.isEnabledForClient(clientInfo);
  }

  public String getId() {
    return id;
  }

  public Rule getRule() {
    return rule;
  }

  public void setRule(Rule rule) {
    this.rule = rule;
  }
}
