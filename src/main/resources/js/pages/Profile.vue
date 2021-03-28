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
                            <v-flex>Email: {{profile.email}}</v-flex>
                            <v-flex v-if="profile.gender">Gender: {{profile.gender}}</v-flex>
                            <v-flex>Locale: {{profile.locale}}</v-flex>
                            <v-flex>Last visit: {{profile.lastVisit}}</v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: "Profile",
        computed: mapState(['profile'])
    }
</script>

<style scoped>
    img {
        max-width: 100%;
        height: auto;
    }
    .profile-data {
        font-size: 125%
    }
</style>