package uk.co.telegraph.switcher.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.co.telegraph.switcher.domain.Application;

public interface ApplicationService {
  Application createWithDefaultEnvironment(Application application);

  Application createWithEnvironments(Application application, Set<String> environments);

  Application update(Application application);

  Optional<Application> find(String key);

  List<Application> findAll();

  Page<Application> findAllPaginated(Pageable pageable);

  void delete(Long id);

  void addEnvironment(Application application, String environmentKey);

  void deleteEnvironment(Application application, String environmentKey);

  void updateEnvironment(Application application, String environmentKey, String newEnvironmentKey);
}
