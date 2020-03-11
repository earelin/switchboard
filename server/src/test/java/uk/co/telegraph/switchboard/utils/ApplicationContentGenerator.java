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

package uk.co.telegraph.switchboard.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.domain.Application;

public class ApplicationContentGenerator {

  private static final Gson gson = new Gson();
  private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

  public static List<Application> getApplicationList() throws FileNotFoundException {
    String fileName = classLoader.getResource("data/applications.json").getFile();
    FileReader fileReader = new FileReader(fileName);
    return gson.fromJson(fileReader, new TypeToken<List<Application>>() {}.getType());
  }

  public static List<ApplicationDto> getApplicationDtoList() throws FileNotFoundException {
    String fileName = classLoader.getResource("data/applications.json").getFile();
    FileReader fileReader = new FileReader(fileName);
    return gson.fromJson(fileReader, new TypeToken<List<ApplicationDto>>() {}.getType());
  }

}
