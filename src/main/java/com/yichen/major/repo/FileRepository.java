package com.yichen.major.repo;

import com.yichen.major.entity.FileMartial;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dengbojing
 */
public interface FileRepository extends JpaRepository<FileMartial, String> {

}
