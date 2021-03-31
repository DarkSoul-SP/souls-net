package ua.darksoul.testprojects.soulsnet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.UserSubscription;
import ua.darksoul.testprojects.soulsnet.repo.UserSubscriptionRepo;

import java.util.List;

/**
 * Created by DarkSoul on 30.03.2021
 **/

@Service
public class UserSubscriptionService {
    private final UserSubscriptionRepo userSubscriptionRepo;

    @Autowired
    public UserSubscriptionService(UserSubscriptionRepo userSubscriptionRepo) {
        this.userSubscriptionRepo = userSubscriptionRepo;
    }

    public List<UserSubscription> getSubscriptions(User subscriber) {
        return userSubscriptionRepo.findBySubscriber(subscriber);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepo.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionRepo.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepo.save(subscription);
    }
}
