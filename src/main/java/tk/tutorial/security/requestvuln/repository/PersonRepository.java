package tk.tutorial.security.requestvuln.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.tutorial.security.requestvuln.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

	// Test if @Query is vulnerable to injection
	@Query("SELECT p from Person p where p.username = :username")
	Person findPersonFromUsername(@Param("username") String username);

}
