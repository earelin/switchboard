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

package uk.co.telegraph.switchboard.application.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.telegraph.switchboard.domain.ContextsAggregator;

class ContextMapperTest {

  private ContextsAggregator contextsAggregator;

  private ContextMapper contextMapper;

  @BeforeEach
  void setUp() {
    contextMapper = Mappers.getMapper(ContextMapper.class);
    contextsAggregator = new ContextsAggregator(getApplication());
  }

//  @Test
//  void should_map_from_domain_map_to_dto() {
//    Map<String, Context> contextMap = Map.of(
//        "staging", new Context( "staging"),
//        "production", new Context( "production")
//    );
//
//    assertThat(contextMapper.domainMapToDto(contextMap))
//        .containsExactly("production", "staging");
//  }

  @Test
  void should_return_empty_mapping_null_domain_map_to_dto() {
    assertThat(contextMapper.domainMapToDto(null))
        .isEmpty();
  }

  @Test
  void should_return_empty_mapping_empty_domain_map_to_dto() {
    assertThat(contextMapper.domainMapToDto(Collections.emptyMap()))
        .isEmpty();
  }
}
