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
