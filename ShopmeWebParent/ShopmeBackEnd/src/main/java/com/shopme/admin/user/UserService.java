package com.shopme.admin.user;

import com.shopme.core.entity.Role;
import com.shopme.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public void save(User user) {
        User existedUser = userRepository.getUserById(user.getId());
        // kiem tra che do edit va pass co trong khong
        if(user.getId()!=null && user.getPassword().isEmpty()) {
            user.setPassword(existedUser.getPassword());
        }else {
            encodePassword(user);
        }
        userRepository.save(user);
    }
    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);
        // kiem tra mode edit(co id) va email hien tai
        if(id!=null && id.equals(userByEmail.getId())) return true;
        return userByEmail == null;
    }

    public User get(Integer id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("Couldn't find user by id "+id));
    }

    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countUsers = userRepository.countById(id);
        if(countUsers==null||countUsers==0) {throw new UserNotFoundException("Couldn't find user by id "+id);}
        userRepository.deleteById(id);
   }

   public void updateUserEnabledStatus(Integer id, boolean enabled) throws UserNotFoundException {
        userRepository.updateEnabledStatus(id, enabled);
   }
}
