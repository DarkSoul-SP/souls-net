package ua.darksoul.testprojects.soulsnet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.darksoul.testprojects.soulsnet.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
