<template>
    <section class="w-full flex justify-between items-center opacity-80">
        <div
            class="flex bg-gray-200 w-96 items-center rounded-lg shadow-md pl-2"
        >
            <fa class="px-2" icon="search" />
            <input
                v-model="searchTerm"
                @input="emitSearch"
                class="w-full h-12 bg-gray-200 px-2 outline-none rounded-lg"
                placeholder="Search..."
            />
        </div>
    </section>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits()

const searchTerm = ref('')

const emitSearch = () => {
    emit('update-search', searchTerm.value)
    console.log(searchTerm.value)
}
</script>