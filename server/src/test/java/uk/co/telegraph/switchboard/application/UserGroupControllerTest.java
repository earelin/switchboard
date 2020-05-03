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

package uk.co.telegraph.switchboard.application;

import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.telegraph.switchboard.Integration;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.domain.repositories.ApplicationRepository;
import uk.co.telegraph.switchboard.domain.repositories.UserGroupAggregatorRepository;

@Integration
@WebMvcTest(UserGroupController.class)
class UserGroupControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ApplicationRepository applicationRepository;

  @MockBean
  private UserGroupAggregatorRepository userGroupAggregatorRepository;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  void should_return_an_existing_application_user_groups() {
    Application application = getApplication();

  }

}
