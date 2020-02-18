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

package uk.co.telegraph.switchboard.repositories;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;

public class ApplicationContentGenerator {

  private ApplicationContentGenerator() {
  }

  public static List<Application> generateApplications() {
    return List.of(
        generateApplication(
            "fd528ad1-6072-3d96-b5af-ff901ac93818",
            "Editorial Dashboard",
            "VC3MciBHBRlyMio0u6kuVoeq9mwasmQbEBAnSHu7MmE43ssjclE3stB3LM7ed7Pm",
            null,
            Set.of(Context.DEFAULT_KEY)
        ),
        generateApplication(
            "d1cbbea4-67a3-3b70-9ab5-81da0af46238",
            "Authoring Frontend",
            "mC5XPkAZBrW4sG7gmi7VdV1ajX0CV5d4nVKq6Jg54v5YjrasjNWcC0haV4kEWVln",
            "Nam at purus justo. Donec lacinia justo eu nisi vestibulum fermentum.",
            Set.of("development", "staging", "production")
        ),
        generateApplication(
            "4e6aea3a-506c-3b7b-a3c3-74e442d1dfa4",
            "Mobile Application",
            "EU8ig9DWP0O41IZO420QwumGXC1sqJYE5lwvpGzUpuMoC9oj3tDeN63qBcZCBY3h",
            "Nullam ipsum orci, suscipit a nibh vel, tempus iaculis nibh.",
            Set.of(Context.DEFAULT_KEY)
        ),
        generateApplication(
            "e2a3ab66-3a1d-3256-9499-1d0c96961405",
            "Mobile Authoring",
            "QjVpgDl7C7j9q09GMHlE5qYVsOU1VDG5ZQSgVAvmf8lhrY7HnKAzNtXeZS1fPtcS",
            null,
            Set.of("development", "staging", "production")
        ),
        generateApplication(
            "e6211ab1-8838-38ec-a10f-d36f673bb84f",
            "Payment application",
            "um1TGPBGErS2g4Woqj23VXIHYTqgJr56qXuQpu8oHS4uMAkFgi8pZc40ENXbkRcW",
            "Cras sagittis nisi nec neque eleifend, non tempor erat finibus.",
            Set.of(Context.DEFAULT_KEY)
        )
    );
  }

  public static Application generateApplication(
      String id,
      String name,
      String secret,
      String description,
      Set<String> contextKeys
  ) {
    Application application = new Application(
        id,
        name,
        secret,
        description
    );

    application.setContexts(generateContexts(contextKeys));

    return application;
  }

  public static Set<Context> generateContexts(Set<String> contextKeys) {
    return contextKeys.stream()
        .map(contextKey -> new Context(contextKey))
        .collect(Collectors.toSet());
  }
}
