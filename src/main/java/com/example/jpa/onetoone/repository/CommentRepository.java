/*
 * Copyright (c) Jordy Coder
 */

package com.example.jpa.onetoone.repository;


import com.example.jpa.onetoone.model.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    Page<CommentModel> findByPostId(Long postId, Pageable pageable);
    Optional<CommentModel> findByIdAndPostId(Long id, Long postId);
}
