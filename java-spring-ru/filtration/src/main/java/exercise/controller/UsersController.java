package exercise.controller;

import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Зависимости для самостоятельной работы
// import org.springframework.data.querydsl.binding.QuerydslPredicate;
// import com.querydsl.core.types.Predicate;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
    @GetMapping(path = "")
    public Iterable<User> getUsers(
            @RequestParam(value="firstName", required = false) String firstName,
            @RequestParam(value="lastName", required = false) String lastName
    ) {
        if (null != lastName && null != firstName) {
            return this.userRepository.findAll(
                    QUser.user.lastName.likeIgnoreCase("%" + lastName+ "%").and(QUser.user.firstName.likeIgnoreCase("%"+ firstName + "%"))
            );
        }

        if (null != firstName) {
            return this.userRepository.findAll(QUser.user.firstName.likeIgnoreCase("%"+ firstName + "%"));
        }

        if (null != lastName) {
            return this.userRepository.findAll(QUser.user.lastName.likeIgnoreCase("%" + lastName+ "%"));
        }

        return this.userRepository.findAll();
    }
    // END
}

