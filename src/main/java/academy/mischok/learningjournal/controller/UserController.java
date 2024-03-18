package academy.mischok.learningjournal.controller;

import academy.mischok.learningjournal.dto.UserDto;
import academy.mischok.learningjournal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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