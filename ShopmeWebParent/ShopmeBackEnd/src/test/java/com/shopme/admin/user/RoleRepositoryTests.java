package com.shopme.admin.user;

import com.shopme.core.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role role = new Role("Admin","manage everything");
        Role savedRole = roleRepository.save(role);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRole() {
        Role roleSalesPerson = new Role("SalesPerson","manage products price, customers, " +
                " shipping, orders and sale reports");
        Role roleEditor = new Role("Editor","manage categories, brands, " +
                " products,articles and menus");
        Role roleShipper = new Role("Shipper","view products, view orders " +
                "and update order status");
        Role roleAssistant = new Role("Assistant","manage questions and reviews");
        roleRepository.saveAll(List.of(roleSalesPerson,roleEditor,roleShipper,roleAssistant));
    }
}
