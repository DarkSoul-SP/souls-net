<template>
    <v-container class="container-authorization">
        You need to autorize through
        <a href="/login">Google</a>
    </v-container>
</template>

<script>
    export default {
        name: "Auth"
    }
</script>

<style scoped>
    .container-authorization {
        font-size: 130%
    }
</style>