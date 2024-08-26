package com.github.marcioesposito.base.domain.person;

import com.github.marcioesposito.base.domain.person.Person.UniqueKeys;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.NaturalId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
    name = "people",
    uniqueConstraints = @UniqueConstraint(name = UniqueKeys.UK1, columnNames = "name"))
public class Person implements Serializable {

  @Serial private static final long serialVersionUID = -3890705509152469912L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NaturalId
  @Column(name = "name", nullable = false, length = 127)
  private String name;

  @NonNull
  @Setter(AccessLevel.PACKAGE)
  @Column(name = "number", nullable = false)
  private Integer number;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @Builder(access = AccessLevel.PACKAGE, toBuilder = true)
  public Person(@NonNull final String name, @NonNull final Integer number) {
    final var now = Instant.now();
    this.name = name;
    this.number = number;
    this.createdAt = now;
    this.updatedAt = now;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return getName().equals(person.getName());
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @UtilityClass
  public static class UniqueKeys {

    public final String UK1 = "people_uk1";
  }
}
