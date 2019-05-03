package ua.darksoul.testprojects.soulsnet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.darksoul.testprojects.soulsnet.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
