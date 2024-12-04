package com.i18n.controller;

import com.i18n.entity.User;
import com.i18n.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Map<String, Object>> getUsers(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        List<User> users = userService.getAllUsers();

        String id = messageSource.getMessage("user.id", null, locale);
        String name = messageSource.getMessage("user.name", null, locale);
        String email = messageSource.getMessage("user.email", null, locale);

        return users.stream().map(user -> {
            Map<String, Object> translatedUser = new HashMap<>();
            translatedUser.put(id, user.getId());
            translatedUser.put(name, user.getName());
            translatedUser.put(email, user.getEmail());
            return translatedUser;
        }).toList();
    }
}
