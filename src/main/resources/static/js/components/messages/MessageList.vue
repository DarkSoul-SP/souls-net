<template>
    <v-layout align-space-around justify-start column fill-height>
    <message-form :messageAttr="message" />
    <message-row v-for="message in sortedMessages"
                 :key="message.id"
                 :message="message"
                 :editMessage="editMessage" />
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    import { mapGetters } from 'vuex';

    export default {
        data() {
            return {
                message: null
            }
        },
        components: {
            MessageRow,
            MessageForm
        },
        computed: mapGetters(['sortedMessages']),
        methods: {
            editMessage(message) {
                this.message = message
            }
        }
    }
</script>

<style>

</style>