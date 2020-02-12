package uk.co.telegraph.switcher.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.telegraph.switcher.domain.Application;
import uk.co.telegraph.switcher.repositories.ApplicationRepository;

@Service
@Transactional(readOnly = true)
public class ApplicationServiceImp implements ApplicationService {

  private final ApplicationRepository applicationRepository;

  public ApplicationServiceImp(ApplicationRepository applicationRepository) {
    this.applicationRepository = applicationRepository;
  }

  @Override
  public Application createWithDefaultEnvironment(Application application) {
    return null;
  }

  @Override
  public Application createWithEnvironments(Application application, Set<String> environments) {
    return null;
  }

  @Override
  public Application update(Application application) {
    return null;
  }

  @Override
  public Optional<Application> find(String key) {
    return Optional.empty();
  }

  @Override
  public List<Application> findAll() {
    return null;
  }

  @Override
  public Page<Application> findAllPaginated(Pageable pageable) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }

  @Override
  public void addEnvironment(Application application, String environmentKey) {

  }

  @Override
  public void deleteEnvironment(Application application, String environmentKey) {

  }

  @Override
  public void updateEnvironment(Application application, String environmentKey,
      String newEnvironmentKey) {

  }
}
