package uk.co.telegraph.switchboard.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import uk.co.telegraph.switchboard.domain.Application;

public interface ApplicationService {
  Application createWithDefaultEnvironment(Application application);

  Application createWithEnvironments(Application application, Set<String> environments);

  Application update(Application application);

  Optional<Application> find(String key);

  List<Application> findAll();

//  Page<Application> findAllPaginated(Pageable pageable);

  void delete(Long id);

  void addEnvironment(Application application, String environmentKey);

  void deleteEnvironment(Application application, String environmentKey);

  void updateEnvironment(Application application, String environmentKey, String newEnvironmentKey);
}
