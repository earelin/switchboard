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

package uk.co.telegraph.switchboard.domain.validation;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.apache.commons.lang3.StringUtils;

public class StringFieldValidator extends FieldValidator<String> {

  public static StringFieldValidatorBuilder builder() {
    return new StringFieldValidatorBuilder();
  }

  protected StringFieldValidator(String name) {
    super(name);
  }

  public static class StringFieldValidatorBuilder {
    private String fieldName = null;
    private Function<String, String> validators = UnaryOperator.identity();

    public StringFieldValidator build() {
      StringFieldValidator stringFieldValidator = new StringFieldValidator(this.fieldName);
      stringFieldValidator.validators = this.validators;
      return stringFieldValidator;
    }

    public StringFieldValidatorBuilder fieldName(String fieldName) {
      this.fieldName = fieldName;
      return this;
    }

    public StringFieldValidatorBuilder shouldNotBeNull() {
      this.validators = this.validators.andThen(value -> {
        if (Objects.isNull(value)) {
          throw new ValidationException(String.format("%s must not be null.", this.fieldName));
        }
        return value;
      });
      return this;
    }

    public StringFieldValidatorBuilder shouldNotBeBlank() {
      this.validators = this.validators.andThen(value -> {
        if (Objects.nonNull(value) && StringUtils.isBlank(value)) {
          throw new ValidationException(String.format("%s must not be blank, value: %s", this.fieldName, value));
        }
        return value;
      });
      return this;
    }

    public StringFieldValidatorBuilder shouldNotBeLongerThan(int maxLength) {
      this.validators = this.validators.andThen(value -> {
        if (Objects.nonNull(value) && value.length() > maxLength) {
          throw new ValidationException(
              String.format("%s must not be longer than %d characters, current value is %s long",
                  this.fieldName, maxLength, value.length()));
        }
        return value;
      });
      return this;
    }

    public StringFieldValidatorBuilder withRegexValidator(String regex) {
      this.validators = this.validators.andThen(value -> {
        if (!value.matches(regex)) {
          throw new ValidationException(
              String.format("%s must match regular expression %s, current value: %s",
                  this.fieldName, regex, value));
        }
        return value;
      });
      return this;
    }

    public StringFieldValidatorBuilder withCustomValidator(Function<String, Boolean> validator, String message) {
      this.validators = this.validators.andThen(value -> {
        if (!validator.apply(value)) {
          throw new ValidationException(message);
        }
        return value;
      });
      return this;
    }
  }
}
