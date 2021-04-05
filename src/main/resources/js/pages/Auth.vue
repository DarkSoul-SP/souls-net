<template>
    <v-container class="container-authorization">
        You need to authorize through
        <a href="/login"> <img src="https://img.icons8.com/fluent/30/000000/google-logo.png" alt="Google"/></a>
    </v-container>
</template>

<script>
    export default {
        name: "Auth"
    }
</script>

<style scoped>
    .container-authorization {
        font-size: 160%
    }
</style>