package com.sparta.demo.controller;

import com.sparta.demo.domain.Post;
import com.sparta.demo.domain.PostRequestDto;
import com.sparta.demo.domain.PostResponseDto;
import com.sparta.demo.domain.PostUpdateRequestDto;
import com.sparta.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    @Autowired PostRepository postRepository;

    @PostMapping("/api/save")
    public PostResponseDto save(@RequestBody PostRequestDto requestDto){
        Post post = new Post(requestDto);
        post.setLocalDateTime(LocalDateTime.now());
        postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }
    @GetMapping("/api/findAll")
    public List<PostResponseDto> findAll(){
        List<Post> findPosts = postRepository.findAll();
        ArrayList<PostResponseDto> postResponseDtos1 = new ArrayList<>();
        for(Post a : findPosts){
            PostResponseDto postResponseDto = new PostResponseDto(a);
            postResponseDtos1.add(postResponseDto);
        }
        return postResponseDtos1;
    }
    @GetMapping("/api/{id}/findOne")
    public PostResponseDto findOne(@PathVariable Long id){
        Post findPost = postRepository.findOne(id);
        System.out.println("findPost = " + findPost);
        PostResponseDto postResponseDto = new PostResponseDto(findPost);
        return postResponseDto;
    }
    @PutMapping("/api/{id}/update")
        public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto){
        Post findPost = postRepository.findOne(id);
        if(findPost != null){
            postRepository.updatePost(requestDto,id);
            PostResponseDto postResponseDto = new PostResponseDto(findPost);
            return postResponseDto;
        } else{
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }
    @DeleteMapping("/api/{id}/delete")
    public String deletePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
      postRepository.deletePost(id, requestDto);
      return new String("성공적으로 삭제하였습니다.");
    }

}
