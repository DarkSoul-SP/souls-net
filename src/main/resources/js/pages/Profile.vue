<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex :xs9="!$vuetify.breakpoint.xsOnly">
                <div class="title mb-3">My profile</div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex class="px-3">
                        <v-layout column class="profile-data">
                            <v-flex>Username: {{profile.name}}</v-flex>
<!--                            <v-flex v-if="isMyProfile">Email: {{profile.email}}</v-flex>-->
                            <v-flex v-if="profile.gender">Gender: {{profile.gender}}</v-flex>
<!--                            <v-flex>Locale: {{profile.locale}}</v-flex>-->
                            <v-flex>Last visit: {{profile.lastVisit}}</v-flex>
                            <v-flex>Subscriptions: {{profile.subscriptions && profile.subscriptions.length}}</v-flex>
                            <router-link v-if="isMyProfile" :to="`/subscriptions/${profile.id}`">
                                Subscribers: {{profile.subscribers && profile.subscribers.length}}
                            </router-link>
                            <v-flex v-else>Subscribers: {{profile.subscribers && profile.subscribers.length}}</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-btn v-if="!isMyProfile"
                       @click="changeSubscription" class="subscribed"
                       :style="{ backgroundColor: activeColor }">
                  {{ isISubscribed ? 'Unsubscribe' : 'Subscribe'}}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile'

    export default {
        name: "Profile",
        data() {
          return {
            profile: {}
          }
        },
        computed: {
          isMyProfile() {
              return !this.$route.params.id || this.$route.params.id === this.$store.state.profile.id
          },
          isISubscribed() {
              return this.profile.subscribers && this.profile.subscribers.find(subscription => {
                  return subscription.subscriber === this.$store.state.profile.id
              })
          },
          activeColor() {
            return this.isISubscribed ? 'white' : 'orangered'
          }
        },
        watch: {
            '$route'() {
                this.updateProfile()
            }
        },
        methods: {
          async changeSubscription() {
              const data = await profileApi.changeSubscription(this.profile.id)
              this.profile = await data.json()
          },
          async updateProfile() {
              const id = this.$route.params.id || this.$store.state.profile.id
              const data = await profileApi.get(id)
              this.profile = await data.json()

              this.$forceUpdate()
          }
        },
        beforeMount() {
            this.updateProfile()
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
    }
    img {
        max-width: 100%;
        height: auto;
    }
    .profile-data {
        font-size: 125%;
    }
</style>