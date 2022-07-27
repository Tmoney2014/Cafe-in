package com.team2.cafein.repository;

import com.team2.cafein.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {




    List<Bookmark> findAllByUserId(Long userId);
    List<Bookmark> findAllByUserIdOrderBy(Long userId);
    //
}
