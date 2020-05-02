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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;
import static uk.co.telegraph.switchboard.generators.ContextsAggregatorContentGenerator.getContextsAggregator;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.telegraph.switchboard.Integration;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;
import uk.co.telegraph.switchboard.repositories.ContextsAggregatorRepository;

@Integration
@WebMvcTest(ContextController.class)
class ContextControllerTest {

  private static final String NOT_EXISTING_APPLICATION_ID = "431a907c-ae02-4f70-9434-3d92316db79f";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ContextsAggregatorRepository contextsAggregatorRepository;

  @MockBean
  private ApplicationRepository applicationRepository;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  @Disabled
  void should_return_contexts_list_for_an_existing_application_context() {
    Application application = getApplication();
    when(contextsAggregatorRepository.getByApplicationId(application.getId()))
        .thenReturn(Optional.of(getContextsAggregator()));

    given()
        .contentType(ContentType.JSON)
      .when()
        .get("/rest/v1/application/{applicationId}/context", application.getId())
      .then()
        .status(HttpStatus.OK)
        .body("$", contains("production", "staging"));
  }

  @Test
  @Disabled
  void should_return_empty_contexts_list_for_an_existing_application_without_contexts() {
    Application application = getApplication();
    when(applicationRepository.getById(application.getId()))
        .thenReturn(Optional.of(application));
    when(contextsAggregatorRepository.getByApplicationId(application.getId()))
        .thenReturn(Optional.empty());

    given()
        .contentType(ContentType.JSON)
      .when()
        .get("/rest/v1/application/{applicationId}/context", application.getId())
      .then()
        .status(HttpStatus.OK)
        .body("$", emptyIterable());
  }

  @Test
  @Disabled
  void should_return_not_found_if_a_not_existing_application_context_list_is_requested() {
    when(applicationRepository.getById(NOT_EXISTING_APPLICATION_ID))
        .thenReturn(Optional.empty());
    when(contextsAggregatorRepository.getByApplicationId(NOT_EXISTING_APPLICATION_ID))
        .thenReturn(Optional.empty());

    given()
        .contentType(ContentType.JSON)
      .when()
        .get("/rest/v1/application/{applicationId}/context",
            NOT_EXISTING_APPLICATION_ID)
      .then()
        .status(HttpStatus.NOT_FOUND);
  }

  @Test
  void should_create_a_context_for_an_existing_application() {
    Application application = getApplication();
    when(applicationRepository.getById(application.getId()))
        .thenReturn(Optional.of(application));
    when(contextsAggregatorRepository.getByApplicationId(application.getId()))
        .thenReturn(Optional.of(getContextsAggregator()));
    when(contextsAggregatorRepository.save(any()))
        .then(returnsFirstArg());

    given()
        .contentType(ContentType.JSON)
        .body("testing")
      .when()
        .post("/rest/v1/application/{applicationId}/context", application.getId())
      .then()
        .status(HttpStatus.CREATED)
        .body(is("testing"));
  }

  @Test
  void should_create_a_context_for_an_existing_application_without_aggregator() {
    Application application = getApplication();
    when(applicationRepository.getById(application.getId()))
        .thenReturn(Optional.of(application));
    when(contextsAggregatorRepository.getByApplicationId(application.getId()))
        .thenReturn(Optional.of(getContextsAggregator()));
    when(contextsAggregatorRepository.save(any()))
        .then(returnsFirstArg());

    given()
        .contentType(ContentType.JSON)
        .body("testing")
      .when()
        .post("/rest/v1/application/{applicationId}/context", application.getId())
      .then()
        .status(HttpStatus.CREATED)
        .body(is("testing"));
  }

  @Test
  void should_return_not_found_if_request_create_context_for_a_not_existing_application() {
    when(applicationRepository.getById(NOT_EXISTING_APPLICATION_ID))
        .thenReturn(Optional.empty());

    given()
        .contentType(ContentType.JSON)
        .body("testing")
      .when()
        .post("/rest/v1/application/{applicationId}/context", NOT_EXISTING_APPLICATION_ID)
      .then()
        .status(HttpStatus.NOT_FOUND);
  }

  @Test
  void should_delete_a_context() {
    Application application = getApplication();
    when(applicationRepository.getById(application.getId()))
        .thenReturn(Optional.of(application));
    when(contextsAggregatorRepository.getByApplicationId(application.getId()))
        .thenReturn(Optional.of(getContextsAggregator()));
    when(contextsAggregatorRepository.save(any()))
        .then(returnsFirstArg());

    given()
        .contentType(ContentType.JSON)
      .when()
        .delete("/rest/v1/application/{applicationId}/context/{contextName}",
            application.getId(), "staging")
      .then()
        .status(HttpStatus.OK);
  }

  @Test
  void should_return_not_found_if_try_to_delete_a_not_existing_context() {
    Application application = getApplication();
    when(applicationRepository.getById(application.getId()))
        .thenReturn(Optional.of(application));
    when(contextsAggregatorRepository.getByApplicationId(application.getId()))
        .thenReturn(Optional.of(getContextsAggregator()));
    when(contextsAggregatorRepository.save(any()))
        .then(returnsFirstArg());

    given()
        .contentType(ContentType.JSON)
      .when()
        .delete("/rest/v1/application/{applicationId}/context/{contextName}",
            application.getId(), "testing")
      .then()
        .status(HttpStatus.NOT_FOUND);
  }

  @Test
  void should_return_not_found_if_try_to_delete_a_context_for_a_not_existing_application() {
    Application application = getApplication();
    when(applicationRepository.getById(NOT_EXISTING_APPLICATION_ID))
        .thenReturn(Optional.empty());

    given()
        .contentType(ContentType.JSON)
      .when()
        .delete("/rest/v1/application/{applicationId}/context/{contextName}",
            NOT_EXISTING_APPLICATION_ID, "staging")
      .then()
        .status(HttpStatus.NOT_FOUND);
  }
}
