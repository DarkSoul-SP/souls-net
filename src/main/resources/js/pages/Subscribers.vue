<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list v-if="subscribers.length > 0">
                <v-list-tile v-for="item in subscribers">
                    <user-link :user="item.subscriber" size="28"></user-link>

                    <v-btn v-if="isMyProfile" @click="changeSubscriptionStatus(item.subscriber.id)">
                        {{item.active ? 'Dismiss' : 'Approve'}}
                    </v-btn>
                </v-list-tile>
            </v-list>
            <div v-else style="font-size: 24px">Have not subscribers yet</div>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile'
    import UserLink from 'components/UserLink.vue';

    export default {
        name: "Subscriptions",
        components: { UserLink },
        data() {
            return {
                subscribers: []
            }
        },
        computed: {
            isMyProfile() {
                return !this.$route.params.id || this.$route.params.id === this.$store.state.profile.id
            },
        },
        methods: {
            async changeSubscriptionStatus(subscriberId) {
                await profileApi.changeSubscriptionStatus(subscriberId)

                const subscriptionIndex = this.subscribers.findIndex(
                    item => item.subscriber.id === subscriberId
                )

                const subscription = this.subscribers[subscriptionIndex]

                this.subscribers = [
                    ...this.subscribers.slice(0, subscriptionIndex),
                    {
                        ...subscription,
                        active: !subscription.active
                    },
                    ...this.subscribers.slice(subscriptionIndex + 1)
                ]
            }
        },
        async beforeMount() {
            const res = await profileApi.subscriberList(this.$route.params.id)
            this.subscribers = await res.json()
        }
}
</script>

<style scoped>

</style>