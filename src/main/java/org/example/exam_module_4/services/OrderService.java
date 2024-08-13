package org.example.exam_module_4.services;

import org.example.exam_module_4.models.Category;
import org.example.exam_module_4.models.Order;
import org.example.exam_module_4.repositories.ICategoryRepository;
import org.example.exam_module_4.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Order> findAllOrder(){
        return orderRepository.findAll();
    }
    public Page<Order> getOrdersByDateRange(Date startDate, Date endDate, Pageable pageable) {
        return orderRepository.findByOrderDateBetween(startDate, endDate,pageable);
    }
    public List<Order> getTopOrders(int topCount) {
        return orderRepository.findTopOrders(PageRequest.of(0, topCount));
    }

    public Page<Order> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1,pageSize);
        return orderRepository.findAll(pageable);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
