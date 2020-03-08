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

package uk.co.telegraph.switchboard.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.telegraph.switchboard.ApplicationIntegration;
import uk.co.telegraph.switchboard.domain.Application;

@ApplicationIntegration
class ApplicationRepositoryJpaImplTest {

  private static final String APPLICATION_ID = "0420d644-1cd3-4c77-aadf-0c70677ce041";
  private static final String APPLICATION_NAME = "Pulse";
  private static final String APPLICATION_DESCRIPTION = "Wonderful statistics application";
  private static final String APPLICATION_SECRET = "VqMpFsVvPHmtd7XL";

  @Autowired
  private ApplicationRepositoryJpaImpl applicationRepository;

  @Test
  void should_store_and_return_an_application() {

  }

  private Application applicationGenerator() {
    Application application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);
    return application;
  }

}
