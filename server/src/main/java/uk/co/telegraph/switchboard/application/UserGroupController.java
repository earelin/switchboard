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

import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.telegraph.switchboard.domain.model.UserGroup;
import uk.co.telegraph.switchboard.domain.repositories.ApplicationRepository;
import uk.co.telegraph.switchboard.domain.repositories.UserGroupAggregatorRepository;

/**
 * User group controller.
 */
@RestController
@RequestMapping(
    value = "/rest/v1/application/{applicationId}/user-group",
    produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserGroupController {

  private final ApplicationRepository applicationRepository;
  private final UserGroupAggregatorRepository userGroupAggregatorRepository;

  public UserGroupController(
      ApplicationRepository applicationRepository,
      UserGroupAggregatorRepository userGroupAggregatorRepository) {
    this.applicationRepository = applicationRepository;
    this.userGroupAggregatorRepository = userGroupAggregatorRepository;
  }

  /**
   * Get application user groups.
   * @param applicationId Application ID.
   * @return Set of application user groups.
   */
  @GetMapping
  public Set<UserGroup> getUserGroups(@PathVariable String applicationId) {
    throw new UnsupportedOperationException();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserGroup createUserGroup(@PathVariable String applicationId) {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/{userGroupId}")
  public Optional<UserGroup> getUserGroup(
      @PathVariable String applicationId,
      @PathVariable Long userGroupId) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{userGroupId}")
  public void deleteUserGroup(
      @PathVariable String applicationId,
      @PathVariable Long userGroupId) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/{userGroupId}")
  public UserGroup updateUserGroup(
      @PathVariable String applicationId,
      @PathVariable Long userGroupId) {
    throw new UnsupportedOperationException();
  }

  @PostMapping("/{userGroupId}/user")
  public UserGroup addUserToGroup(
      @PathVariable String applicationId,
      @PathVariable Long userGroupId) {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/{userGroupId}/user")
  public Set<String> getUsersFromGroup(
      @PathVariable String applicationId,
      @PathVariable Long userGroupId) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{userGroupId}/user/{userId}")
  public UserGroup removeUserFromGroup(
      @PathVariable String applicationId,
      @PathVariable String userGroupId,
      @PathVariable Long userId) {
    throw new UnsupportedOperationException();
  }
}
