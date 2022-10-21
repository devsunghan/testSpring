package com.example.springboard.post;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

import com.example.springboard.answer.Answer;
import com.example.springboard.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

// Jpaを使うためには"@Entity"アノテーションを使い、Entityであることを示す必要があります。
@Getter
@Setter
@Entity
public class Post {

//    @GeneratedValueアノテーションにより、Idを指定しなくても自動的違う数字を入力します。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

//    ポスト1つにつき、複数のコメントをつけることはできますが、複数のポストに同じ番号のコメントを１つ付けることはできません。
//    @OneToManyアノテーションを付けることで、ポストからコメントに接近することができ、ポストを削除すれば関連するコメントも削除するようにしました。
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

//　　 一人のユーザーが複数のポストを投稿することができるので、@ManyToOneアノテーションを適用しました。
    @ManyToOne
    private SiteUser author;

}
