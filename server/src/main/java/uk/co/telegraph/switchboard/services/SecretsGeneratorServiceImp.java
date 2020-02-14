package uk.co.telegraph.switchboard.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class SecretsGeneratorServiceImp implements SecretsGeneratorService {
  @Override
  public String generateSecret(int size) {
    return RandomStringUtils.randomAlphanumeric(size);
  }
}
