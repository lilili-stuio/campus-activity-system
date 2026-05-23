<template>
  <div class="container">
    <h2 style="margin-bottom:20px">活动管理</h2>

    <div v-if="showForm" class="card" style="margin-bottom:24px">
      <h3 style="margin-bottom:16px">{{ editing ? '编辑活动' : '新建活动' }}</h3>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label>活动名称</label>
          <input v-model="form.title" required />
        </div>
        <div class="form-group">
          <label>活动描述</label>
          <textarea v-model="form.description"></textarea>
        </div>
        <div class="form-group">
          <label>活动地点</label>
          <input v-model="form.location" />
        </div>
        <div style="display:flex;gap:16px">
          <div class="form-group" style="flex:1">
            <label>开始时间</label>
            <input type="datetime-local" v-model="form.startTime" required />
          </div>
          <div class="form-group" style="flex:1">
            <label>结束时间</label>
            <input type="datetime-local" v-model="form.endTime" required />
          </div>
        </div>
        <div class="form-group">
          <label>最大报名人数</label>
          <input type="number" v-model.number="form.maxCapacity" min="1" required />
        </div>
        <div style="display:flex;gap:12px">
          <button class="btn btn-primary" type="submit">{{ editing ? '更新' : '创建' }}</button>
          <button class="btn btn-outline" type="button" @click="showForm = false">取消</button>
        </div>
      </form>
    </div>

    <button v-if="!showForm" class="btn btn-primary" style="margin-bottom:16px" @click="openCreate">+ 新建活动</button>

    <div v-for="act in activities" :key="act.id" class="card">
      <div style="display:flex;justify-content:space-between;align-items:center">
        <div>
          <h3>{{ act.title }}</h3>
          <div class="meta">
            {{ statusMap[act.status] }} | {{ act.currentNum }}/{{ act.maxCapacity }} 人
            | 签到码: {{ act.qrCodeKey }}
          </div>
        </div>
        <div style="display:flex;gap:8px">
          <button class="btn btn-outline" @click="openEdit(act)">编辑</button>
          <button v-if="act.status === 1" class="btn btn-danger" @click="changeStatus(act.id, 2)">结束</button>
          <button v-if="act.status === 2" class="btn btn-outline" @click="changeStatus(act.id, 1)">重开</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { getActivities, createActivity, updateActivity, updateActivityStatus } from '../api'

const activities = ref([])
const showForm = ref(false)
const editing = ref(null)
const statusMap = { 0: '未开始', 1: '报名中', 2: '已结束' }

const form = reactive({
  title: '', description: '', location: '',
  startTime: '', endTime: '', maxCapacity: 50
})

function formatLocal(s) {
  if (!s) return ''
  return s.replace(' ', 'T').substring(0, 16)
}

function openCreate() {
  editing.value = null
  Object.assign(form, { title: '', description: '', location: '', startTime: '', endTime: '', maxCapacity: 50 })
  showForm.value = true
}

function openEdit(act) {
  editing.value = act.id
  form.title = act.title
  form.description = act.description || ''
  form.location = act.location || ''
  form.startTime = formatLocal(act.startTime)
  form.endTime = formatLocal(act.endTime)
  form.maxCapacity = act.maxCapacity
  showForm.value = true
}

async function handleSubmit() {
  const payload = {
    title: form.title,
    description: form.description,
    location: form.location,
    startTime: form.startTime,
    endTime: form.endTime,
    maxCapacity: form.maxCapacity
  }
  if (editing.value) {
    await updateActivity(editing.value, payload)
  } else {
    await createActivity(payload)
  }
  showForm.value = false
  loadActivities()
}

async function changeStatus(id, status) {
  await updateActivityStatus(id, status)
  loadActivities()
}

async function loadActivities() {
  const res = await getActivities()
  activities.value = res.data.data
}

onMounted(loadActivities)
</script>
