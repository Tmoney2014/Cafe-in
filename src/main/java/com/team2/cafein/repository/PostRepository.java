package com.team2.cafein.repository;

import com.team2.cafein.model.Post;
import com.team2.cafein.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long postId);

    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findByUserOrderByCreatedAtDesc(User user);

}
