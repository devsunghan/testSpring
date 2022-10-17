package com.example.springboard.answer;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.springboard.post.Post;
import com.example.springboard.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Post post;

    @ManyToOne
    private SiteUser author;
}
