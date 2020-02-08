package uk.co.telegraph.switcher.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import javax.crypto.KeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.telegraph.switcher.domain.Application;
import uk.co.telegraph.switcher.repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationServiceImp implements ApplicationService {

  private final ApplicationRepository applicationRepository;

  public ApplicationServiceImp(ApplicationRepository applicationRepository) {
    this.applicationRepository = applicationRepository;
  }

  @Override
  public void create(Application application) {

  }

  @Override
  public Optional<Application> findById(String id) {
    return Optional.empty();
  }

  @Override
  public Optional<Application> findByKey(String key) {
    return Optional.empty();
  }

  @Override
  public List<Application> list() {
    return null;
  }

  @Override
  public void remove(String id) {

  }
}
