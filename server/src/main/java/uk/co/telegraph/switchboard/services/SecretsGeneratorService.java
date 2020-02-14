package uk.co.telegraph.switchboard.services;

public interface SecretsGeneratorService {
  String generateSecret(int size);
}
