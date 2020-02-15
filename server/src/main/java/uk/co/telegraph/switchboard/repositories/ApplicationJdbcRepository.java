/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.repositories;

import java.awt.print.Pageable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.co.telegraph.switchboard.domain.Application;

@Repository
@Slf4j
public class ApplicationJdbcRepository implements ApplicationRepository {

  private final JdbcTemplate jdbcTemplate;

  public ApplicationJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Application> findByKey(String key) {
    Application application = null;

    try {
      application = jdbcTemplate.queryForObject(
          "SELECT * FROM application WHERE key = ?",
          ApplicationJdbcRepository::mapRowToApplication,
          key
      );
    } catch (EmptyResultDataAccessException emptyResult) {
      log.warn("Could not find application: {}", key);
    }

    return Optional.ofNullable(application);
  }

  @Override
  public boolean existsByKey(String key) {
    Integer result = jdbcTemplate
        .queryForObject("SELECT count(*) FROM application WHERE key = ?", Integer.class, key);
    return result != 0;
  }

  @Override
  public long count() {
    return jdbcTemplate
        .queryForObject("SELECT count(*) FROM application", Long.class);
  }

  @Override
  public void create(Application application) {
    jdbcTemplate.update(
        "INSERT INTO application(key, name, secret, description) VALUES (?, ?, ?, ?)",
        application.getKey(),
        application.getName(),
        application.getSecret(),
        application.getDescription());
  }

  @Override
  public void updateByKey(Application application, String key) {
    jdbcTemplate.update(
        "UPDATE application SET key = ?, name = ?, secret = ?, description = ? WHERE key = ?",
        application.getKey(),
        application.getName(),
        application.getSecret(),
        application.getDescription(),
        key);
  }

  @Override
  public void update(Application application) {
    updateByKey(application, application.getKey());
  }

  @Override
  public void deleteByKey(String key) {
    jdbcTemplate.update("DELETE FROM application WHERE key = ?", key);
  }

  @Override
  public List<Application> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM application ORDER BY name ASC",
        ApplicationJdbcRepository::mapRowToApplication);
  }

  @Override
  public Page<Application> findAll(Pageable pageable) {
    return null;
  }

  private static Application mapRowToApplication(ResultSet rs, int rowNum) throws SQLException {
    Application application = new Application(rs.getString("key"));
    application.setName(rs.getString("name"));
    application.setSecret(rs.getString("secret"));
    application.setDescription(rs.getString("description"));
    return application;
  }
}
