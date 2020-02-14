package uk.co.telegraph.switchboard.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.co.telegraph.switchboard.domain.Application;

@Repository
public class ApplicationJdbcRepository implements ApplicationRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ApplicationJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Application> findByKey(String key) {
    return Optional.empty();
  }

  @Override
  public boolean existsByKey(String key) {
    return false;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void updateByKey(Application application, String key) {

  }

  @Override
  public void update(Application application) {

  }

  @Override
  public void deleteByKey(String key) {

  }

  @Override
  public List<Application> findAll() {
    return null;
  }

  @Override
  public Page<Application> findAll(Pageable pageable) {
    return null;
  }
}
