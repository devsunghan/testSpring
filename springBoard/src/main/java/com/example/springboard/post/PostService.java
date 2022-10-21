package com.example.springboard.post;

import com.example.springboard.DataNotFoundException;
    import com.example.springboard.post.Post;
import com.example.springboard.post.PostRepository;
import com.example.springboard.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ControllerとServiceを分けた理由は、メソッドをmodule化することで再利用性を上げ、より容易く保守できるようにするためです。
@RequiredArgsConstructor
@Service
public class PostService {


    private final PostRepository postRepository;

//    データベースにあるポストを全部リストに入れ、returnします。
    public List<Post> getList() {
        return this.postRepository.findAll();
    }

//    idをパラメータでもらい、一致するIdのポストをReturnします。
    public Post getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

//    パラメータでもらった情報を基にポストを作り、データベースに入れます。
    public void create(String subject, String content, SiteUser user) {
        Post p = new Post();
        p.setSubject(subject);
        p.setContent(content);
        p.setCreateDate(LocalDateTime.now());
        p.setAuthor(user);
        this.postRepository.save(p);
    }

//    すでにポスト状態のインスタンスを貰ったら、そのままデータベースに入れます。
    public void save(Post q) {
        this.postRepository.save(q);
    }


//    Pagination : ポストなどを一定数の数に分け、ページ化したもの
//    Pageable : Jpaで提供するオブジェクトでPageableタイプの変数をJpaに渡すと、JpaがDBに接近し、自動的にLimitの条件を入れ、データを探します。
//    Page : Pageableをパラメータにし、貰った結果のデータ型
//    PageRequest : ページの番号、ページのサイズ、整列(Sorting)のオプションなど指定し、Pageableを作る方法

//    getListは作成日時で整列されたポストをページでreturnするメソッドです。
//    パラメータで貰った番号のページを変換しますが、defaultは0ですので、ただlistを要請すれば、一番最近作られたポストのページをreturnします。
    public Page<Post> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }

//    テストデータを作るためのメソッドです。
    public void createTest() {
        for (int i = 1; i <= 300; i++) {
            Post p = new Post();
            p.setSubject(String.format("test data:[%03d]", i));
            p.setContent("test");
            p.setCreateDate(LocalDateTime.now());
            this.postRepository.save(p);
        }
    }

//    ポストを修正するためのメソッドです。
    public void modify(Post post, String subject, String content) {
        post.setSubject(subject);
        post.setContent(content);
        this.postRepository.save(post);
    }

//    ポストを削除するためのメソッドです。
    public void delete(Post post) {
        this.postRepository.delete(post);
    }
}
