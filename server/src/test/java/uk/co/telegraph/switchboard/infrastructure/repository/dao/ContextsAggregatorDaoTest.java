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

package uk.co.telegraph.switchboard.infrastructure.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContextsAggregatorDaoTest {

  public static final String APPLICATION_ID = "1dc58fb4-d63f-47c7-882e-8ac02daf2fd5";
  public static final Set<String> CONTEXTS = Sets.newHashSet("production", "testing");

  public static final String APPLICATION_ID_ALT = "e612252f-51f7-4ad3-84b7-dca29a33571e";
  public static final Set<String> CONTEXTS_ALT = Sets.newHashSet("development", "staging");

  private ContextsAggregatorDao contextsAggregatorDao;

  @BeforeEach
  void setUp() {
    contextsAggregatorDao = new ContextsAggregatorDao();
    contextsAggregatorDao.setId(APPLICATION_ID);
    contextsAggregatorDao.setContexts(CONTEXTS);
  }

  @Test
  void should_set_and_return_id() {
    contextsAggregatorDao.setId(APPLICATION_ID_ALT);

    assertThat(contextsAggregatorDao.getId())
        .isEqualTo(APPLICATION_ID_ALT);
  }

  @Test
  void should_set_and_return_contexts() {
    contextsAggregatorDao.setContexts(CONTEXTS_ALT);

    assertThat(contextsAggregatorDao.getContexts())
        .isEqualTo(CONTEXTS_ALT);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(contextsAggregatorDao.equals(contextsAggregatorDao))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    ContextsAggregatorDao compareObject = new ContextsAggregatorDao();
    compareObject.setId(APPLICATION_ID);

    assertThat(contextsAggregatorDao.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(contextsAggregatorDao.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(contextsAggregatorDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    ContextsAggregatorDao compareObject = new ContextsAggregatorDao();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(contextsAggregatorDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    ContextsAggregatorDao compareObject = new ContextsAggregatorDao();
    compareObject.setId(APPLICATION_ID);

    assertThat(contextsAggregatorDao.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_ID() {
    ContextsAggregatorDao compareObject = new ContextsAggregatorDao();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(contextsAggregatorDao.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_a_string_representation() throws IOException {
    assertThat(contextsAggregatorDao.toString())
        .contains(
            "ContextsAggregatorDao",
            APPLICATION_ID,
            CONTEXTS.toString());
  }
}
