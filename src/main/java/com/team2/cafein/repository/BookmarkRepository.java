package com.team2.cafein.repository;

import com.team2.cafein.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {




    List<Bookmark> findAllByUserId(Long userId);

    Long deleteByPostId(Long postId);

    List<Long> findPostIdsByUserId(@Param("userId") Long userId);

    void deleteAllByPostId( Long postId);

    List<Bookmark> findByUserId(Long userId);

    List<Bookmark> findByUserIdAndPostId(Long userId, Long postId);
//    List<Bookmark> findAllByUserIdOrderBy(Long userId);
    //
}
