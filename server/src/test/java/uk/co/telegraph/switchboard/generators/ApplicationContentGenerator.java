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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.infrastructure.repository.dao.ApplicationDao;

public final class ApplicationContentGenerator {

  public static final String APPLICATION_ID = "0420d644-1cd3-4c77-aadf-0c70677ce041";
  public static final String APPLICATION_NAME = "Pulse";
  public static final String APPLICATION_DESCRIPTION = "Wonderful statistics application";
  public static final String APPLICATION_SECRET = "VqMpFsVvPHmtd7XL";

  private static final Gson gson = new Gson();
  private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

  public static List<Application> generateApplicationList() throws FileNotFoundException {
    return gson.fromJson(
        fileReaderFromResource("data/applications.json"),
        new TypeToken<List<Application>>() {}.getType());
  }

  public static List<ApplicationDto> generateApplicationDtoList() throws FileNotFoundException {
    return gson.fromJson(
        fileReaderFromResource("data/applications.json"),
        new TypeToken<List<ApplicationDto>>() {}.getType());
  }

  public static List<ApplicationDao> generateApplicationDaoList() throws IOException {
    return gson.fromJson(
        fileReaderFromResource("data/applications.json"),
        new TypeToken<List<ApplicationDao>>() {}.getType());
  }

  public static ApplicationDao generateApplicationDao() {
    ApplicationDao applicationDao = new ApplicationDao();
    applicationDao.setId(APPLICATION_ID);
    applicationDao.setName(APPLICATION_NAME);
    applicationDao.setDescription(APPLICATION_DESCRIPTION);
    applicationDao.setSecret(APPLICATION_SECRET);
    return applicationDao;
  }

  public static Application generateApplication() {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    return application;
  }

  private static FileReader fileReaderFromResource(String resourcePath) throws FileNotFoundException {
    String fileName = classLoader.getResource(resourcePath).getFile();
    return new FileReader(fileName);
  }

  private ApplicationContentGenerator() {
  }
}
