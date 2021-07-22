/*
 * Copyright (c) Jordy Coder
 */
package com.example.jpa.onetoone.controller;

import com.example.jpa.onetoone.exception.ResourceNotFoundException;
import com.example.jpa.onetoone.model.CommentModel;
import com.example.jpa.onetoone.repository.CommentRepository;
import com.example.jpa.onetoone.repository.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    public Page<CommentModel> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId,
                                                     Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentModel createComment(@PathVariable (value = "postId") Long postId,
                                 @Valid @RequestBody CommentModel comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentModel updateComment(@PathVariable (value = "postId") Long postId,
                                 @PathVariable (value = "commentId") Long commentId,
                                 @Valid @RequestBody CommentModel commentRequest) {
        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
                                           @PathVariable (value = "commentId") Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }
}
