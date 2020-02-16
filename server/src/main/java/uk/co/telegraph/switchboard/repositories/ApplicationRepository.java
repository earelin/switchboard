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

package uk.co.telegraph.switchboard.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.co.telegraph.switchboard.domain.Application;

public interface ApplicationRepository {

  void create(Application application);

  void updateByKey(Application application, String key);

  void update(Application application);

  boolean existsByKey(String key);

  long count();

  void deleteByKey(String key);

  Optional<Application> findByKey(String key);

  List<Application> findAll();

  Page<Application> findAll(Pageable pageable);
}
