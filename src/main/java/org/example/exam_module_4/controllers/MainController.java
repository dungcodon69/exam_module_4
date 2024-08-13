package org.example.exam_module_4.controllers;

import org.example.exam_module_4.models.Category;
import org.example.exam_module_4.models.Order;
import org.example.exam_module_4.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private OrderService orderService;


    @GetMapping
    public String home(Model model,@RequestParam(value = "page", defaultValue = "1")int pageNo){
        int pageSize = 5;
//        List<Order> orders = orderService.findAllOrder();
        Page<Order> pages = orderService.findPaginated(pageNo,pageSize);
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPage",pages.getTotalPages());
        model.addAttribute("totalItems",pages.getTotalElements());
        model.addAttribute("orders",pages.getContent());
        return "index";
    }

    @GetMapping("/add-new")
    public String addNew(){
        return "add_new";
    }

    @GetMapping("list-by-date")
    public String listByDate(@RequestParam("startDate") String startDateStr,
                             @RequestParam("endDate") String endDateStr, RedirectAttributes redirectAttributes,Model model,
    @RequestParam(value = "page", defaultValue = "1") int pageNo,
    @RequestParam(value = "size", defaultValue = "5") int pageSize
    ) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);
        if (endDate.before(startDate)) {
            redirectAttributes.addFlashAttribute("listByDateMessage","Ngày nhập khong hop le");
            return "redirect:/";
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Order> pages = orderService.getOrdersByDateRange(startDate,endDate, pageable);
        model.addAttribute("orders", pages.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", pages.getTotalPages());
        model.addAttribute("totalItems", pages.getTotalElements());
        model.addAttribute("startDate", startDateStr);
        model.addAttribute("endDate", endDateStr);
        return "index";
    }

    @PostMapping("list-by-top")
    public String listByTop(@RequestParam("top") int top,Model model){
        List<Order> orders = orderService.getTopOrders(top);
        model.addAttribute("orders", orders);
        return "index";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,Model model){
        Optional<Order> option = orderService.findById(id);
        if (option.isPresent()){
            Order order = option.get();
            List<Category> categories = orderService.getAllCategory();
            model.addAttribute("categories",categories);
            model.addAttribute("order",order);
        }
        else {
            return "redirect:/";
        }
        return "add_new";
    }


}
