package uk.co.telegraph.switcher.services;

import java.util.List;
import java.util.Optional;
import uk.co.telegraph.switcher.entities.Application;

public interface ApplicationService {
  Application create(Application application);

  Application update(Application application);

  Optional<Application> findById(Long id);

  Optional<Application> findByKey(String key);

  List<Application> findAll();

  void delete(Long id);
}
