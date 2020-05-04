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

import static uk.co.telegraph.switchboard.generators.DataUtils.fileAsString;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import uk.co.telegraph.switchboard.infrastructure.repository.dao.ApplicationDao;

public final class ApplicationDaoContentGenerator {

  private static final String APPLICATIONS_JSON = "applications.json";
  private static final ObjectMapper objectMapper = new ObjectMapper();


  public static List<ApplicationDao> generateList() throws IOException {
    String applicationsJson = fileAsString("data/applications.json");
    return objectMapper.readValue(applicationsJson,
        new TypeReference<List<ApplicationDao>>() {});
  }

  public static ApplicationDao generate() throws IOException {
    return generateList().get(0);
  }

  private ApplicationDaoContentGenerator() {}
}
