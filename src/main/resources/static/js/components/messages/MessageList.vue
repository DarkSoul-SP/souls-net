<template>
    <v-layout align-space-around justify-start column fill-height>
    <message-form :messages="messages" :messageAttr="message" />
    <message-row v-for="message in sortedMessages"
                 :key="message.id"
                 :message="message"
                 :messages="messages"
                 :deleteMessage="deleteMessage"
                 :editMessage="editMessage" />
    </v-layout>
</template>

<script>
    import MessageRow from 'components/messages/MessageRow.vue'
    import MessageForm from 'components/messages/MessageForm.vue'
    export default {
        props: ['messages'],
        data() {
            return {
                message: null
            }
        },
        computed: {
            sortedMessages() {
                return this.messages.sort((a, b) => - (a.id - b.id))
            }
        },
        components: {
            MessageRow,
            MessageForm
        },
        methods: {
            editMessage(message) {
                this.message = message
            },
            deleteMessage(message) {
                this.$resource('/message{/id}').remove({id: message.id}).then(result => {
                    if (result.ok) {
                        this.messages.splice(this.messages.indexOf(message), 1)
                    }
                })
            }
        }
    }
</script>

<style>

</style>