import Vue from 'vue'

const profile = Vue.resource('/profile{/id}')

export default {
    get: id => profile.get({id}),
    update: myProfile => profile.update({id: myProfile.id}, myProfile),
    changeSubscription: channelId => Vue.http.post(`/profile/change-subscription/${channelId}`),
    subscriptionList: subscriberId => Vue.http.get(`/profile/get-subscriptions/${subscriberId}`),
    subscriberList: channelId => Vue.http.get(`/profile/get-subscribes/${channelId}`),
    changeSubscriptionStatus: subscriberId => Vue.http.post(`/profile/change-status/${subscriberId}`)
}