<template>
    <v-flex xs3 class="mt-2">
        <v-autocomplete
            :items="users"
            solo
            item-text="name"
            item-value="name"
            clearable
            placeholder="Search people"
            no-data-text="People not found"
            prepend-inner-icon="search"
        >
            <template
                slot="item"
                slot-scope="data"
            >
                <template >
                    <v-card-text primary-title>
                        <user-link
                            :user="data.item"
                            size="36">
                        </user-link>
                    </v-card-text>
                </template>
            </template>
        </v-autocomplete>
    </v-flex>
</template>

<script>
import UserLink from 'components/UserLink.vue'
import { mapState } from "vuex";

export default {
    name: "SearchPeople",
    components: { UserLink },
    computed: {
        ...mapState(['users'])
    }
}
</script>

<style scoped>

</style>