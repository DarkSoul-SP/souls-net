<template>
    <v-container>
        <v-layout justify-space-around>
            <v-list v-if="subscriptions.length > 0">
                <v-list-tile v-for="item in subscriptions">
                    <user-link :user="item.channel" size="28"></user-link>
                </v-list-tile>
            </v-list>
            <div v-else style="font-size: 24px">You have not subscriptions yet</div>
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
                subscriptions: []
            }
        },
        async beforeMount() {
            const res = await profileApi.subscriptionList(this.$store.state.profile.id)
            this.subscriptions = await res.json()
        }
}
</script>

<style scoped>

</style>