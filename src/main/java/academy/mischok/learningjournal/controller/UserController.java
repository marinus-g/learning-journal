package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                            UserDto userDto,
                           @RequestParam(defaultValue = "profile") String oldPath) {
        this.userService.updateUser(id, userDto);
        return "redirect:/" + oldPath;
    }
}