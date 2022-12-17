package exercise.repository;

import exercise.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findById(long id);

    @Query(value = "SELECT * FROM courses c WHERE c.id IN ?1 group by c.id", nativeQuery = true)
    List<Course> findCourseByIds(List<Long> ids);
}
