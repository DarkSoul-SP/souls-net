package ua.darksoul.testprojects.soulsnet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.darksoul.testprojects.soulsnet.domain.Message;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.UserSubscription;
import ua.darksoul.testprojects.soulsnet.dto.EventType;
import ua.darksoul.testprojects.soulsnet.repo.UserDetailsRepo;
import ua.darksoul.testprojects.soulsnet.repo.UserSubscriptionRepo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DarkSoul on 28.03.2021
 **/

@Service
public class ProfileService {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if(subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            subscription.setActive(channel.isAutoApprove());
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subscriptions);
        }

        return userDetailsRepo.save(channel);
    }

    public User update(User userFromDB) {
        if(userFromDB == null) {
            return null;
        }

        userFromDB.setAutoApprove(!userFromDB.isAutoApprove());
        User updatedUser = userDetailsRepo.save(userFromDB);
//        wsSender.accept(EventType.UPDATE, updatedMessage);

        return updatedUser;
    }
}
