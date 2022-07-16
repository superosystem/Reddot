package com.gusrylmubarok.reddit.backend.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
}
