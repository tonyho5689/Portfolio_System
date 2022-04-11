package com.example.portfolio_system;

import com.example.portfolio_system.entity.Department;
import com.example.portfolio_system.entity.Securities;
import com.example.portfolio_system.service.DepartmentService;
import com.example.portfolio_system.service.SecuritiesService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PortfolioSystemApplicationTests {
    @Autowired
    private SecuritiesService securitiesService;
    @Autowired
    private DepartmentService departmentService;


//    @Test
//    void contextLoads() {
//    }

    //    @Test
//    @Order(1)
//    void testSecurities() {
//        securitiesService.createTicker(1, "TSLA");
//    }
    @Test
    void testSave() {
        Department department = new Department();
        department.setDepartmentCode("001");
        department.setDepartmentAddress("Address");
        department.setDepartmentName("Name");
        departmentService.saveDepartment(department);
    }
}
