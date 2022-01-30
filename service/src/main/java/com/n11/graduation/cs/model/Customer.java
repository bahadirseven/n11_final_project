package com.n11.graduation.cs.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "n11_customer")
public class Customer extends AbstractBaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identity_number", length = 11, unique = true, nullable = false)
    private String identityNumber;

    @Column(name = "fullname", length = 50, nullable = false)
    private String fullname;

    @Column(name = "phone_number", length = 10, unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "income", nullable = false)
    private BigDecimal income;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Application> applicationList;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CreditScore creditScore;
}
