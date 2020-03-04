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

package uk.co.telegraph.switchboard.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {

  private static final String APPLICATION_ID = "5cfd7920-1f9f-4fb3-a975-0393fccaa068";
  private static final String APPLICATION_NAME = "Authoring";
  private static final String APPLICATION_DESCRIPTION = "A great authoring application";

  private static final String FEATURE_FLAG_1_ID = "embed.youtube.videos";
  private static final String FEATURE_FLAG_2_ID = "tag.suggestions";

  private Application application;

  @Mock
  private FeatureFlag featureFlag1;

  @Mock
  private FeatureFlag featureFlag2;

  @Mock
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    application = new Application(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setDescription(APPLICATION_DESCRIPTION);
  }

  @Test
  void constructor_should_set_application_id() {
    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID);
  }

  @Test
  void should_set_and_get_name() {
    assertThat(application.getName())
        .isEqualTo(APPLICATION_NAME);
  }

  @Test
  void should_set_and_get_description() {
    assertThat(application.getDescription())
        .isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void should_add_feature_flags_and_get_it_by_id() {
    when(featureFlag1.getId()).thenReturn(FEATURE_FLAG_1_ID);

    application.addFeatureFlag(featureFlag1);

    assertThat(application.getFeatureFlagById(FEATURE_FLAG_1_ID))
        .get()
        .isEqualTo(featureFlag1);
  }

  @Test
  void should_remove_a_feature_flag() {
    when(featureFlag1.getId()).thenReturn(FEATURE_FLAG_1_ID);

    application.addFeatureFlag(featureFlag1);
    application.removeFeatureFlag(featureFlag1);

    assertThat(application.getFeatureFlagById(FEATURE_FLAG_1_ID))
        .isNotPresent();
  }

  @Test
  void should_return_the_set_of_feature_flags() {
    when(featureFlag1.getId()).thenReturn(FEATURE_FLAG_1_ID);
    when(featureFlag2.getId()).thenReturn(FEATURE_FLAG_2_ID);

    application.addFeatureFlag(featureFlag1);
    application.addFeatureFlag(featureFlag2);

    assertThat(application.getFeatureFlags())
        .contains(featureFlag1, featureFlag2);
  }

  @Test
  void should_return_a_list_of_feature_status_for_a_client() {
    when(featureFlag1.getId()).thenReturn(FEATURE_FLAG_1_ID);
    when(featureFlag2.getId()).thenReturn(FEATURE_FLAG_2_ID);
    when(featureFlag1.isEnabledForClient(any())).thenReturn(true);
    when(featureFlag2.isEnabledForClient(any())).thenReturn(false);

    application.addFeatureFlag(featureFlag1);
    application.addFeatureFlag(featureFlag2);

    assertThat(application.getFeatureStatusesForClient(clientInfo))
        .contains(
            entry(FEATURE_FLAG_1_ID, true),
            entry(FEATURE_FLAG_2_ID, false)
        );
  }

  @Test
  void should_can_equal_to_itself() {
    assertThat(application.canEqual(application))
        .isTrue();
  }

  @Test
  void should_can_equal_to_same_class() {
    Application compareObject = new Application();

    assertThat(application.canEqual(compareObject))
        .isTrue();
  }

  @Test
  void should_cannot_equal_to_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(application.canEqual(compareObject))
        .isFalse();
  }

  @Test
  void should_cannot_equal_to_null() {
    assertThat(application.canEqual(null))
        .isFalse();
  }

}
