package com.fullStack.to_do.list.repository;

import com.fullStack.to_do.list.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories c WHERE c.user_id = :userId ",
            nativeQuery = true)
    Page<Category> getAllCategoryForSpecificUser(@Param("userId") long userId, Pageable pageable);



}
