package tk.tutorial.security.requestvuln.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.tutorial.security.requestvuln.model.Person;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT password from Person p where p.id = id")
    String findPasswordById(Long id);

    @Query("SELECT p from Person p where p.username = :username")
    Person findPersonFromUsername(@Param("username") String username);

    @Query("SELECT p FROM Person p WHERE p.username = :username")
    List<Person> getAllPersonWithSameUsername(String username);
}
