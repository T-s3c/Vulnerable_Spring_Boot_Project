package tk.tutorial.security.requestvuln.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.tutorial.security.requestvuln.model.Commentary;

@Repository
public interface CommentaryRepository extends CrudRepository<Commentary, Long> {
}
