package ru.cardinalnsk.springjavajuniortest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user_entity")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate birthDate;
    String phoneNumber;
    String password;
    BigDecimal balance;

    @OneToMany
    List<Payment> paymentHistory = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    Set<UserRole> role = new HashSet<>();

}
