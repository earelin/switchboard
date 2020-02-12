package uk.co.telegraph.switcher.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switcher.domain.Application;
import uk.co.telegraph.switcher.repositories.ApplicationRepository;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImpTest {

  private static final String APPLICATION_NAME = "Newsroom Dashboard";
  private static final String APPLICATION_DESCRIPTION
      = "An amazing application to be feature flagged";

  private ApplicationServiceImp applicationService;

  @Mock
  private ApplicationRepository applicationRepository;

  @BeforeEach
  void setUp() {
    applicationService = new ApplicationServiceImp(applicationRepository);

    when(applicationRepository.save(any(Application.class)))
        .thenAnswer(invocation -> {
            Application application = (Application) invocation.getArguments()[0];
            application.setId(1L);
            return application;
        });
  }

  @Disabled
  @Test
  void shouldCreateApplication() {

  }

}
