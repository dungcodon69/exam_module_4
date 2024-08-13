package org.example.exam_module_4.repositories;

import org.example.exam_module_4.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
