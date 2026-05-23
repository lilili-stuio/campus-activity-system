<template>
  <div>
    <nav v-if="user.token" class="navbar">
      <h2>校园活动管理</h2>
      <div class="nav-links">
        <router-link to="/">活动列表</router-link>
        <router-link to="/my">我的报名</router-link>
        <router-link v-if="user.user?.role === 'admin'" to="/admin">后台管理</router-link>
        <button @click="handleLogout">退出</button>
      </div>
    </nav>
    <router-view />
  </div>
</template>

<script setup>
import { useUserStore } from './stores/user'
import { useRouter } from 'vue-router'

const user = useUserStore()
const router = useRouter()

function handleLogout() {
  user.logout()
  router.push('/login')
}
</script>
