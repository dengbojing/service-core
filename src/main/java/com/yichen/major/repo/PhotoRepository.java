package com.yichen.major.repo;

import com.yichen.major.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author dengbojing
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {
}
