package com.shadan.userservice.service;

import com.shadan.userservice.config.JwtFilter;
import com.shadan.userservice.dao.UserDao;
import com.shadan.userservice.feign.ExpenseInterface;
import com.shadan.userservice.model.ChangePassword;
import com.shadan.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private ExpenseInterface expenseInterface;

    @Autowired
    private UserDao userDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public String addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return "User added successfully";
    }

    public String deleteUser() {
        String username = jwtFilter.getUsernameLocal();
        String fullHeader = jwtFilter.getFullHeader();
        expenseInterface.deleteAllExpenses(fullHeader);
        userDao.deleteByUsername(username);
        return "User deleted successfully";
    }

    public String changePassword(ChangePassword changePassword) {
        String username = jwtFilter.getUsernameLocal();
        User user = userDao.findByUsername(username);
        if (!bCryptPasswordEncoder.matches(changePassword.getOldPassword(), user.getPassword()))
            return "Old password is incorrect";
        user.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
        userDao.save(user);
        return "Password changed successfully";

    }

    public boolean getUser() {
        String username = jwtFilter.getUsernameLocal();
        User user =  userDao.findById(username).orElse(null);
        return user!=null;
    }
}
