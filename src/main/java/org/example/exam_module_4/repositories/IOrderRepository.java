package org.example.exam_module_4.repositories;

import org.example.exam_module_4.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findByOrderDateBetween(Date startDate, Date endDate, Pageable pageable);
    @Query("SELECT o FROM Order o JOIN o.product p " +
            "GROUP BY o.id ORDER BY SUM(o.quantity * p.price) DESC")
    List<Order> findTopOrders(Pageable pageable);
}
