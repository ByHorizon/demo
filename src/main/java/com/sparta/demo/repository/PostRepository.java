package com.sparta.demo.repository;

import com.sparta.demo.domain.Post;
import com.sparta.demo.domain.PostRequestDto;
import com.sparta.demo.domain.PostUpdateRequestDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class PostRepository {
    @PersistenceContext
    private EntityManager em;

    public Post save(Post post){
        em.persist(post);
        return post;
    }
    public List<Post> findAll(){
        List<Post> findPosts = em.createQuery("select p from Post p order by p.id desc ", Post.class)
                .getResultList();

        return findPosts;
    }
    public Post findOne(Long id){
        Post findPost = em.find(Post.class, id);
        return findPost;
    }
    public Post findByUsername(String username){
        Post findPost = em.find(Post.class, username);
        return findPost;
    }
    public Post updatePost(PostUpdateRequestDto requestDto, Long id){
        Post findPost = em.find(Post.class, id);
        if(!findPost.getPassword().equals(requestDto.getCheckPassword())){
            throw new IllegalArgumentException("패스워드가 다릅니다.");
        }else {
            findPost.setTitle(requestDto.getTitle());
            findPost.setContents(requestDto.getContents());
            findPost.setUsername(requestDto.getUsername());
            findPost.setPassword(requestDto.getPassword());

            return findPost;
        }
    }
    public String deletePost(Long id, PostRequestDto requestDto){
        Post findPost = em.find(Post.class, id);
        if(findPost.getPassword().equals(requestDto.getPassword())){
            em.remove(findPost);
        } else{
            throw new IllegalArgumentException("패스워드가 다릅니다.");
        }
        return new String("성공적으로 삭제했습니다.");
    }
}
