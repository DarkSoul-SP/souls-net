package ua.darksoul.testprojects.soulsnet.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.darksoul.testprojects.soulsnet.domain.User;
import ua.darksoul.testprojects.soulsnet.domain.UserSubscription;
import ua.darksoul.testprojects.soulsnet.domain.Views;
import ua.darksoul.testprojects.soulsnet.service.ProfileService;
import ua.darksoul.testprojects.soulsnet.service.UserSubscriptionService;

import java.util.List;

/**
 * Created by DarkSoul on 28.03.2021
 **/

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserSubscriptionService userSubscriptionService;

    @Autowired
    public ProfileController(ProfileService profileService, UserSubscriptionService userSubscriptionService) {
        this.profileService = profileService;
        this.userSubscriptionService = userSubscriptionService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User getOne(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(@AuthenticationPrincipal User subscriber, @PathVariable("channelId") User channel) {
        if(subscriber.equals(channel)) {
            return channel;
        } else {
            return profileService.changeSubscription(channel, subscriber);
        }
    }

    @GetMapping("get-subscribes/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(@PathVariable("channelId") User channel) {
        return userSubscriptionService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.FullProfile.class)
    public UserSubscription changeSubscriptionStatus(@AuthenticationPrincipal User channel, @PathVariable("subscriberId") User subscriber) {
        return userSubscriptionService.changeSubscriptionStatus(channel, subscriber);
    }
}
