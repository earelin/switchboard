package uk.co.telegraph.switcher.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class SecretsServiceImp implements SecretsService {

  private static final String KEY_GENERATOR = "AES";
  private static final int KEY_BIT_SIZE = 256;

  private final KeyGenerator keyGenerator;

  public SecretsServiceImp() throws NoSuchAlgorithmException {
    keyGenerator = KeyGenerator.getInstance(KEY_GENERATOR);
    SecureRandom secureRandom = new SecureRandom();
    keyGenerator.init(KEY_BIT_SIZE, secureRandom);
  }

  @Override
  public String generateSecret() {
    SecretKey secretKey = keyGenerator.generateKey();
    return Base64.getEncoder()
        .encodeToString(secretKey.getEncoded());
  }
}
