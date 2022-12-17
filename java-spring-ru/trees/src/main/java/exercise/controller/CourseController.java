package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private static String EMPTY_STRING = "";
    private static String DOT_STRING = "\\.";
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    @GetMapping(path = "/{id}/previous")
    public Iterable<Course> getPreviousCourses(@PathVariable long id) {
        Course parentCourse = courseRepository.findById(id);

        return courseRepository.findCourseByIds(getParentIdsByPath(parentCourse.getPath()));
    }

    private List<Long> getParentIdsByPath(String path) {
        if (path != null && !EMPTY_STRING.equals(path)) {
            return Arrays.stream(path.split(DOT_STRING))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }

        return List.of();
    }
    // END

}
