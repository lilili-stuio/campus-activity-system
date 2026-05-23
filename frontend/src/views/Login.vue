<template>
  <div class="login-box">
    <h2>校园活动管理系统</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label>学号</label>
        <input v-model="form.studentId" placeholder="请输入学号" required />
      </div>
      <div class="form-group">
        <label>密码</label>
        <input v-model="form.password" type="password" placeholder="请输入密码" required />
      </div>
      <p class="error-msg" v-if="error">{{ error }}</p>
      <button class="btn btn-primary" style="width:100%;margin-top:12px" type="submit">登录</button>
    </form>
    <p style="text-align:center;margin-top:16px;font-size:12px;color:#999">
      测试账号：admin / 123456 或 stu001 / 123456
    </p>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { login } from '../api'

const router = useRouter()
const userStore = useUserStore()
const form = reactive({ studentId: '', password: '' })
const error = ref('')

async function handleLogin() {
  error.value = ''
  try {
    const res = await login(form.studentId, form.password)
    userStore.setLogin(res.data.data)
    router.push('/')
  } catch (e) {
    error.value = e.response?.data?.message || '登录失败'
  }
}
</script>
