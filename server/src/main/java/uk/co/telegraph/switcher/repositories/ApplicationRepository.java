package uk.co.telegraph.switcher.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import uk.co.telegraph.switcher.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, String> {
  List<Application> findAllByOrderByName();

  Optional<Application> findBySecretKey(String key);
}