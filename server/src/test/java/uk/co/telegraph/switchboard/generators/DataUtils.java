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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public final class ContentGenerator {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static Map<String, Object> jsonFileToMap(String resourceFilePath) throws IOException {
    String json = fileAsString(resourceFilePath);
    return mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
  }

  public static String fileAsString(String resourceFilePath) throws IOException {
    return IOUtils.toString(
        Objects.requireNonNull(
            ContentGenerator.class.getClassLoader().getResource(resourceFilePath)),
        StandardCharsets.UTF_8);
  }
}
