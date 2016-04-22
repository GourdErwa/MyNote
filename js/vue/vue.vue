<!-- MyComponent.vue -->

<!-- css -->
<style>
    .message {
        color: red;
    }
</style>

<!-- template -->
<template>
    <div class="message">{{ message }}</div>
</template>

<!-- js -->
<script>
    export default {
        props: ['message'],
        created() {
            console.log('MyComponent created!')
        }
    }
</script>
