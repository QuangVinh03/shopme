package com.shopme.admin.user;

import com.shopme.core.entity.Role;
import com.shopme.core.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role role = entityManager.find(Role.class, 1);
        User user = new User("nguyenvanA@gmail.com", "12345", "Nguyen", "A");
        user.addRole(role);
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();

    }
    @Test
    public void testCreateUserWithTwoRoles() {
        Role roleEditor = entityManager.find(Role.class, 3);
        Role roleAssistant = entityManager.find(Role.class, 5);
        User user = new User("namhoak@gmail.com", "namhoak", "Nam", "Hoa");
        user.addRole(roleAssistant);
        user.addRole(roleEditor);
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
    }

    @Test
    public void testListAllUsers(){
        userRepository.findAll().forEach(System.out::println);
        assertThat(userRepository.findAll()).isNotNull();
    }

    @Test
    public void testFindUserById(){
        User user = userRepository.findById(1).orElse(null);
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User user = userRepository.findById(1).orElse(null);
        assert user != null;
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Test
    public void testUpdateUserRoles(){
        User user = userRepository.findById(2).orElse(null);
        Role roleEditor = new Role(3);
        Role roleSalePerson = new Role(2);
        assert user != null;
        user.getRoles().remove(roleEditor);
        user.addRole(roleSalePerson);
        userRepository.save(user);
    }

    @Test
    public void testDeleteUser(){
        userRepository.deleteById(2);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "abc@gmail.com";
        userRepository.getUserByEmail(email);
        assertThat(userRepository.getUserByEmail(email)).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        long count = userRepository.countById(id);
        assertThat(count).isGreaterThan(0);
        System.out.println(count);
    }

    @Test
    public void testDisabledUser(){
        Integer id = 1;
        boolean enabled = false;
        userRepository.updateEnabledStatus(id,enabled);

    }
}
