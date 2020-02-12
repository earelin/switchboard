package uk.co.telegraph.switcher.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import uk.co.telegraph.switcher.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
  List<Application> findAllByOrderByName();

  Optional<Application> findByKey(String key);

  Page<Application> findAll(Pageable pageable);
}
