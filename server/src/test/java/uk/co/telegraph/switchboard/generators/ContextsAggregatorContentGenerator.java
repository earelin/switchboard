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

package uk.co.telegraph.switchboard.generators;

import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.ContextsAggregator;

public final class ContextsAggregatorContentGenerator {

  public static final String CONTEXT_PRODUCTION_NAME = "production";
  public static final String CONTEXT_STAGING_NAME = "staging";

  public static ContextsAggregator getContextsAggregator(Application application) {
    ContextsAggregator contextsAggregator = new ContextsAggregator(application);
    contextsAggregator.addContext(CONTEXT_PRODUCTION_NAME);
    contextsAggregator.addContext(CONTEXT_STAGING_NAME);
    return contextsAggregator;
  }

  private ContextsAggregatorContentGenerator() {
  }
}
