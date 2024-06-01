package com.shopme.admin.user;

import com.shopme.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User getUserByEmail(String username);
    public User getUserById(Integer id);
    public Long countById(Integer id);

    @Modifying
    @Transactional
    @Query("update User u  set  u.enabled = ?2 where u.id = ?1")
    public void updateEnabledStatus(Integer id, boolean enabled);
}
