package uk.co.telegraph.switcher.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.co.telegraph.switcher.domain.Application;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=validate"
})
class ApplicationRepositoryTest {

  private static final String APPLICATION_KEY = "newsroom-dashboard";
  private static final String APPLICATION_NAME = "Newsroom Dashboard";
  private static final String APPLICATION_SECRET = "K2I1JPxYp1pCWprzf4QaReiwntZXxmu4";
  private static final String APPLICATION_DESCRIPTION
      = "An amazing application to be feature flagged";

  @Autowired
  private ApplicationRepository applicationRepository;

  @Test
  void shouldCreateAnApplication() {
    Application application = createApplication();

    Application createdApplication = applicationRepository.save(application);

    assertThat(createdApplication.getId())
        .isNotNull()
        .isGreaterThan(0);
    assertThat(createdApplication.getName())
        .isEqualTo(APPLICATION_NAME);
    assertThat(createdApplication.getKey())
        .isEqualTo(APPLICATION_KEY);
    assertThat(createdApplication.getSecret())
        .isEqualTo(APPLICATION_SECRET);
    assertThat(createdApplication.getDescription())
        .isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void shouldUpdateOneApplication() {
    Application application = createApplication();
    Application createdApplication = applicationRepository.save(application);
    Long applicationId = createdApplication.getId();

    Application updatedApplication = new Application();
    updatedApplication.setId(applicationId);
    updatedApplication.setName("Authoring frontend");
    updatedApplication.setKey("authoring-frontend");
    updatedApplication.setSecret("4grtw453gfahgdfsret=");
    updatedApplication.setDescription("Another application description");

    applicationRepository.save(updatedApplication);

    Optional<Application> foundApplication = applicationRepository.findById(applicationId);

    assertThat(foundApplication)
        .isPresent();
    assertThat(foundApplication.get().getId())
        .isEqualTo(applicationId);
    assertThat(foundApplication.get().getName())
        .isEqualTo("Authoring frontend");
    assertThat(foundApplication.get().getKey())
        .isEqualTo("authoring-frontend");
    assertThat(foundApplication.get().getSecret())
        .isEqualTo("4grtw453gfahgdfsret=");
    assertThat(foundApplication.get().getDescription())
        .isEqualTo("Another application description");
  }

  @Test
  void shouldFindAnApplicationById() {
    Application application = createApplication();
    Application createdApplication = applicationRepository.save(application);
    Long applicationId = createdApplication.getId();

    Optional<Application> foundApplication = applicationRepository.findById(applicationId);

    assertThat(foundApplication)
        .isPresent();
    assertThat(foundApplication.get().getId())
        .isEqualTo(applicationId);
    assertThat(foundApplication.get().getName())
        .isEqualTo(APPLICATION_NAME);
    assertThat(foundApplication.get().getKey())
        .isEqualTo(APPLICATION_KEY);
    assertThat(foundApplication.get().getSecret())
        .isEqualTo(APPLICATION_SECRET);
    assertThat(foundApplication.get().getDescription())
        .isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void shouldReturnEmptyWhenFindByIdANotExistingApplication() {
    assertThat(applicationRepository.findById(1234567890L))
        .isNotPresent();
  }

  @Test
  void shouldDeleteAnApplicationById() {
    Application application = createApplication();
    Application createdApplication = applicationRepository.save(application);
    Long applicationId = createdApplication.getId();

    applicationRepository.deleteById(applicationId);

    assertThat(applicationRepository.findById(applicationId))
        .isNotPresent();
  }

  @Test
  void shouldFindAnApplicationByKey() {
    Application application = createApplication();
    applicationRepository.save(application);

    Optional<Application> foundApplication = applicationRepository.findByKey(APPLICATION_KEY);

    assertThat(foundApplication)
        .isPresent();
    assertThat(foundApplication.get().getId())
        .isNotNull()
        .isGreaterThan(0);
    assertThat(foundApplication.get().getName())
        .isEqualTo(APPLICATION_NAME);
    assertThat(foundApplication.get().getKey())
        .isEqualTo(APPLICATION_KEY);
    assertThat(foundApplication.get().getSecret())
        .isEqualTo(APPLICATION_SECRET);
    assertThat(foundApplication.get().getDescription())
        .isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void shouldFindAllOrderByName() {
    Application application1 = createApplication();

    Application application2 = createApplication();
    application2.setName("Trello Web Hooks");
    application2.setKey("trello-web-hooks");

    Application application3 = createApplication();
    application3.setName("Editorial Dashboard");
    application3.setKey("editorial-dashboard");

    applicationRepository.saveAll(Arrays.asList(application1, application2, application3));

    List<Application> applications = applicationRepository.findAllByOrderByName();

    assertThat(applications)
        .hasSize(3)
        .containsExactly(application3, application1, application2);
  }

  private Application createApplication() {
    Application application = new Application();
    application.setName(APPLICATION_NAME);
    application.setKey(APPLICATION_KEY);
    application.setSecret(APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);

    return application;
  }
}
