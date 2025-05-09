<template>
  <div class="container">
    <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:20px">
      <h2 style="font-size:20px">活动列表</h2>
    </div>
    <div v-if="loading" style="text-align:center;padding:60px;color:#999">加载中...</div>
    <div v-else>
      <div v-for="act in activities" :key="act.id" class="card">
        <h3><router-link :to="'/activity/' + act.id" style="color:#1a73e8;text-decoration:none">{{ act.title }}</router-link></h3>
        <div class="meta">
          {{ act.location }} | {{ formatDate(act.startTime) }} ~ {{ formatDate(act.endTime) }}
        </div>
        <div class="meta">
          已报名 {{ act.currentNum }} / {{ act.maxCapacity }} 人
          <span class="status-badge" :class="'status-' + act.status" style="margin-left:8px">
            {{ statusMap[act.status] }}
          </span>
        </div>
        <p style="font-size:14px;color:#555;margin:8px 0">{{ act.description?.substring(0, 100) }}{{ act.description?.length > 100 ? '...' : '' }}</p>
      </div>
      <div v-if="activities.length === 0" style="text-align:center;padding:60px;color:#999">暂无活动</div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getActivities } from '../api'

const activities = ref([])
const loading = ref(true)
const statusMap = { 0: '未开始', 1: '报名中', 2: '已结束' }

function formatDate(s) {
  return s ? s.replace('T', ' ').substring(0, 16) : ''
}

onMounted(async () => {
  try {
    const res = await getActivities()
    activities.value = res.data.data
  } finally {
    loading.value = false
  }
})
</script>
