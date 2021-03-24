package ua.darksoul.testprojects.soulsnet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.darksoul.testprojects.soulsnet.domain.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
