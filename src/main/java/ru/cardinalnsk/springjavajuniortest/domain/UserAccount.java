package ru.cardinalnsk.springjavajuniortest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user_entity")
public class UserAccount extends BaseEntity {

    String username;
    String firstName;
    String lastName;
    String email;
    @Column(unique = true, nullable = false)
    String phoneNumber;
    String password;
    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate birthDate;

    @Builder.Default
    BigDecimal balance = BigDecimal.valueOf(1000);


    @OneToMany(fetch = FetchType.LAZY)
    List<Payment> paymentHistory = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Builder.Default
    Set<UserRole> role = new HashSet<>();

    @CreationTimestamp
    Instant createdAt;

}
