<template>
    <v-container>
        <v-layout justify-space-around>
            <v-flex :xs9="!$vuetify.breakpoint.xsOnly">
                <div v-if="isMyProfile" class="title mb-3">My profile</div>
                <v-layout row justify-space-between>
                    <v-flex class="px-1">
                        <v-img :src="profile.userpic"></v-img>
                    </v-flex>
                    <v-flex class="px-3">
                        <v-layout column class="profile-data">
                            <v-flex>Username: {{profile.name}}</v-flex>
                            <v-flex v-if="profile.gender">Gender: {{profile.gender}}</v-flex>
                            <v-flex>Last visit: {{profile.lastVisit}}</v-flex>
                            <router-link v-if="isMyProfile" :to="`/subscriptions/${profile.id}`">
                                Subscriptions: {{profile.subscriptions && profile.subscriptions.length}}
                            </router-link>
                            <v-flex v-else>Subscriptions: {{profile.subscriptions && profile.subscriptions.length}}</v-flex>
                            <router-link v-if="profile.subscribers && profile.subscribers.length > 0" :to="`/subscribers/${profile.id}`">
                                Subscribers: {{profile.subscribers.length}}
                            </router-link>
                            <v-flex v-else>Subscribers: 0</v-flex>
                            <v-checkbox
                                v-if="isMyProfile"
                                v-model="profile.autoApprove"
                                label="Enable auto-approval for subscribers"
                                color="red"
                                @change="changeAutoApproval"
                            ></v-checkbox>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-btn v-if="!isMyProfile" class="mt-3" style="margin-left: 2.5em;"
                       @click="changeSubscription"
                       :style="{ backgroundColor: activeColor }">
                  {{ isISubscribed ? 'Unsubscribe' : 'Subscribe'}}
                </v-btn>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import profileApi from 'api/profile'
    import {mapActions} from "vuex";

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
            ...mapActions(['updateProfileAction']),
          async changeSubscription() {
              const data = await profileApi.changeSubscription(this.profile.id)
              this.profile = await data.json()
          },
          async updateProfile() {
              const id = this.$route.params.id || this.$store.state.profile.id
              const data = await profileApi.get(id)
              this.profile = await data.json()

              this.$forceUpdate()
          },
          changeAutoApproval() {
              this.updateProfileAction(this.profile)
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