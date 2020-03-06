/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.services;

import static org.apache.commons.lang3.Conversion.intToByteArray;

import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

@Service
public class IdGeneratorImpl implements IdGenerator {

  private final byte[] randomSeed;

  public IdGeneratorImpl() {
    Random random = new Random(System.currentTimeMillis());

    randomSeed = random.ints(32)
        .mapToObj(i -> {
          byte[] result = new byte[4];
          return intToByteArray(i, 0, result, 0, 4);
        })
        .reduce(ArrayUtils::addAll)
        .orElseThrow();
  }

  @Override
  public String generateId(String name) {
    byte[] nameBytes = name.getBytes();
    byte[] timestampBytes = String.valueOf(System.nanoTime()).getBytes();
    byte[] uuidSeed = ArrayUtils.addAll(nameBytes, timestampBytes);

    return UUID.nameUUIDFromBytes(uuidSeed)
        .toString();
  }
}
