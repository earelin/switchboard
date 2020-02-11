package uk.co.telegraph.switcher.services;

import java.util.List;
import java.util.Optional;
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
  @Transactional
  public Application create(Application application) {
    return applicationRepository.save(application);
  }

  @Override
  @Transactional
  public Application update(Application application) {
    return null;
  }

  @Override
  public Optional<Application> find(String key) {
    return Optional.empty();
  }

  @Override
  public List<Application> findAll() {
    return applicationRepository.findAllByOrderByName();
  }

  @Override
  @Transactional
  public void delete(Long id) {
    applicationRepository.deleteById(id);
  }
}
