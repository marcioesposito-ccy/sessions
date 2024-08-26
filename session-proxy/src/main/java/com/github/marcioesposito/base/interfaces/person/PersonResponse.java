package com.github.marcioesposito.base.interfaces.person;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonResponse implements Serializable {

  @Serial private static final long serialVersionUID = -4849793337837031698L;

  @NonNull private Integer id;

  @NonNull private String name;

  @NonNull private Integer number;

  @NonNull private Instant createdAt;

  @NonNull private Instant updatedAt;
}
