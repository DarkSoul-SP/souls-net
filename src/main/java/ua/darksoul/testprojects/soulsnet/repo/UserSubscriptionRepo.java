package ua.darksoul.testprojects.soulsnet.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.UserSubscription;
import ua.darksoul.testprojects.soulsnet.domain.UserSubscriptionId;

import java.util.List;

/**
 * Created by DarkSoul on 30.03.2021
 **/

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
    List<UserSubscription> findByChannel(User channel);
    UserSubscription findByChannelAndSubscriber(User channel, User subscriber);
}
