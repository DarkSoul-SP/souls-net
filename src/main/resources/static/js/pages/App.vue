<template>
    <v-app>
        <v-toolbar app>
            <v-toolbar-title>SoulsNet</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="profile" class="toolbar-profile-name">{{profile.name}}</span>
            <v-btn v-if="profile" icon href="/logout">
                <v-icon>exit_to_app</v-icon>
            </v-btn>
        </v-toolbar>
        <v-content>
            <v-container v-if="!profile" class="container-authorization">You need to autorize through
                <a href="/login">Google</a>
            </v-container>
            <v-container v-if="profile">
                <messages-list />
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import MessagesList from 'components/messages/MessageList.vue'
    import { addHandler } from "util/ws"
    import { mapState, mapMutations } from 'vuex'

    export default {
        components: {
            MessagesList
        },
        computed: mapState(['profile']),
        methods: mapMutations(['addMessageMutation', 'updateMessageMutation', 'removeMessageMutation']),
        created() {
            addHandler(data => {
                if(data.objectType === 'MESSAGE') {
                    switch (data.eventType) {
                        case 'CREATE' :
                            this.addMessageMutation(data.body)
                            break
                        case 'UPDATE' :
                            this.updateMessageMutation(data.body)
                            break
                        case 'REMOVE' :
                            this.removeMessageMutation(data.body)
                            break
                        default :
                            console.error(`Event type of the message is unknown "${data.eventType}"`)
                    }
                } else {
                    console.error(`Object type of the message is unknown "${objectType}"`)
                }
            })
        }
    }
</script>

<style>
    v-toolbar-title {
        font-size: 140%
    }
    .container-authorization {
        font-size: 130%
    }
    .toolbar-profile-name {
        font-size: 120%
    }
</style>