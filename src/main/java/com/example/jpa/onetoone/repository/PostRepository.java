/*
 * Copyright (c) Jordy Coder
 */

package com.example.jpa.onetoone.repository;

import com.example.jpa.onetoone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
