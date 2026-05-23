<template>
  <div class="container">
    <h2 style="margin-bottom:20px">我的报名</h2>
    <div v-if="loading" style="text-align:center;padding:60px">加载中...</div>
    <div v-else>
      <div v-for="item in list" :key="item.id" class="card">
        <h3>{{ item.activityId }}</h3>
        <div class="meta">报名时间：{{ formatDate(item.registeredAt) }}</div>
        <router-link :to="'/activity/' + item.activityId" class="btn btn-outline">查看详情</router-link>
      </div>
      <div v-if="list.length === 0" style="text-align:center;padding:60px;color:#999">暂无报名记录</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyRegistrations } from '../api'

const list = ref([])
const loading = ref(true)

function formatDate(s) {
  return s ? s.replace('T', ' ').substring(0, 16) : ''
}

onMounted(async () => {
  try {
    const res = await getMyRegistrations()
    list.value = res.data.data
  } finally {
    loading.value = false
  }
})
</script>
