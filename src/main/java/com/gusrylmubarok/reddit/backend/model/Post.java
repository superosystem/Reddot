package com.gusrylmubarok.reddit.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_name")
    @NotBlank(message = "post name cannot be empty or null!")
    private String postName;

    @Column(name = "url")
    @Nullable
    private String url;

    @Column(name = "description")
    @Nullable
    @Lob
    private String description;

    @Column(name = "vote_count")
    private int voteCount = 0;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
    private Subreddit subreddit;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "post")
//    private List<Vote> votes;

}
