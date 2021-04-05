package ua.darksoul.testprojects.soulsnet.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.darksoul.testprojects.soulsnet.domain.User;

import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = { "subscriptions", "subscribers"})
    Optional<User> findById(String s);
}
