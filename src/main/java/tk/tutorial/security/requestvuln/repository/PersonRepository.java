package tk.tutorial.security.requestvuln.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tutorial.security.requestvuln.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT password from Person p where p.id = id")
    String findPasswordById(Long id);

    @Query("SELECT password from Person p where p.username = username")
    String findPasswordByUsername(String username);
}
