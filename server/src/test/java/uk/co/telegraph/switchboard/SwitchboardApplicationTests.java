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

package uk.co.telegraph.switchboard;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static uk.co.telegraph.switchboard.Definitions.TEST_INTEGRATION_TAG;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Tag(TEST_INTEGRATION_TAG)
class SwitchboardApplicationTests {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void initialiseRestAssuredMockMvcWebApplicationContext() {
    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
  }

  @Test
  void checkHealth() {
    given()
      .when()
        .get("/actuator/health")
      .then()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalTo("UP"));
  }

}
