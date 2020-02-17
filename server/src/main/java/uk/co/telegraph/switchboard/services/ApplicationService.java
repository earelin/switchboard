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

package uk.co.telegraph.switchboard.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import uk.co.telegraph.switchboard.domain.Application;

public interface ApplicationService {
  Application createFrom(String name, String description);
  Application createFrom(String name, String description, Set<String> context);
  void delete(Application application);
  Application updateNameAndDescription(Application application, String name, String description);
  Application addContext(Application application, String contextKey);
  Application removeContext(Application application, String contextKey);
  Optional<Application> findByKey(String key);
  List<Application> findAll();
  List<Application> findAll(Pageable pageable);
}
