package uk.co.telegraph.switcher.services;

import java.util.List;
import java.util.Optional;
import uk.co.telegraph.switcher.domain.Application;

public interface ApplicationService {
  void create(Application application);
  Optional<Application> findById(String id);
  Optional<Application> findByKey(String key);
  List<Application> list();
  void remove(String id);
}
