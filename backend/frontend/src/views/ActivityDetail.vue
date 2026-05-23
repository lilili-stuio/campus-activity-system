<template>
  <div class="container">
    <div v-if="loading" style="text-align:center;padding:60px">加载中...</div>
    <div v-else-if="activity" class="card">
      <h2 style="margin-bottom:12px">{{ activity.title }}</h2>
      <div class="meta" style="margin-bottom:16px">
        <div>地点：{{ activity.location || '待定' }}</div>
        <div>时间：{{ formatDate(activity.startTime) }} ~ {{ formatDate(activity.endTime) }}</div>
        <div>报名：{{ activity.currentNum }} / {{ activity.maxCapacity }}
          <span class="status-badge" :class="'status-' + activity.status" style="margin-left:8px">
            {{ statusMap[activity.status] }}
          </span>
        </div>
        <div v-if="user.user?.role === 'admin'">签到码：{{ activity.qrCodeKey }}</div>
      </div>
      <p style="line-height:1.8;margin:16px 0;white-space:pre-wrap">{{ activity.description }}</p>

      <div v-if="activity.status === 1" style="margin-top:20px">
        <button class="btn btn-primary" @click="handleRegister" :disabled="registered">
          {{ registered ? '已报名' : '立即报名' }}
        </button>
        <p style="color:red;font-size:13px;margin-top:6px" v-if="error">{{ error }}</p>
      </div>

      <div v-if="activity.status === 1 && registered" style="margin-top:16px;padding-top:16px;border-top:1px solid #eee">
        <div class="form-group">
          <label>签到码</label>
          <input v-model="qrCodeKey" placeholder="请输入签到码" />
        </div>
        <button class="btn btn-success" @click="handleCheckin">签到</button>
        <p style="margin-top:6px;font-size:13px" :style="{color: checkinMsg.includes('成功') ? 'green' : 'red'}" v-if="checkinMsg">{{ checkinMsg }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getActivity, registerActivity, checkin } from '../api'
import { useUserStore } from '../stores/user'

const route = useRoute()
const user = useUserStore()
const activity = ref(null)
const loading = ref(true)
const registered = ref(false)
const error = ref('')
const qrCodeKey = ref('')
const checkinMsg = ref('')

function formatDate(s) {
  return s ? s.replace('T', ' ').substring(0, 16) : ''
}

const statusMap = { 0: '未开始', 1: '报名中', 2: '已结束' }

onMounted(async () => {
  try {
    const [res, myRes] = await Promise.all([
      getActivity(route.params.id),
      import('../api').then(m => m.getMyRegistrations())
    ])
    activity.value = res.data.data
    const myRegs = myRes.data.data
    registered.value = myRegs.some(r => r.activityId === Number(route.params.id))
  } finally {
    loading.value = false
  }
})

async function handleRegister() {
  error.value = ''
  try {
    await registerActivity(activity.value.id)
    registered.value = true
    activity.value.currentNum++
  } catch (e) {
    error.value = e.response?.data?.message || '报名失败'
  }
}

async function handleCheckin() {
  checkinMsg.value = ''
  try {
    const res = await checkin(activity.value.id, qrCodeKey.value)
    checkinMsg.value = res.data.data
  } catch (e) {
    checkinMsg.value = e.response?.data?.message || '签到失败'
  }
}
</script>
