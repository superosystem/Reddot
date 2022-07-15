package com.gusrylmubarok.reddit.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotBlank(message = "username is required!")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "password is required!")
    private String password;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "email is required!")
    private String email;

    @Column(name = "created")
    private Instant createdAt;

    @Column(name = "enabled")
    private boolean enabled;

//    @OneToOne(mappedBy = "userId")
//    private VerificationToken verificationToken;

//    @OneToMany(mappedBy = "user")
//    private List<Vote> votes;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Post> posts;

//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments;

//    @OneToMany(mappedBy = "user")
//    private List<Subreddit> subreddits;
}
