package uk.co.telegraph.switcher.repositories;

import org.springframework.data.repository.CrudRepository;
import uk.co.telegraph.switcher.domain.Application;

public interface ApplicationRepository extends CrudRepository<Application, String> {}
