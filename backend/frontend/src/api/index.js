import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  res => res,
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      window.location.href = '/login'
    }
    return Promise.reject(err)
  }
)

export function login(studentId, password) {
  return api.post('/auth/login', { studentId, password })
}

export function getActivities() {
  return api.get('/activities')
}

export function getActivity(id) {
  return api.get(`/activities/${id}`)
}

export function createActivity(data) {
  return api.post('/activities', data)
}

export function updateActivity(id, data) {
  return api.put(`/activities/${id}`, data)
}

export function updateActivityStatus(id, status) {
  return api.put(`/activities/${id}/status?status=${status}`)
}

export function registerActivity(id) {
  return api.post(`/activities/${id}/register`)
}

export function getMyRegistrations() {
  return api.get('/activities/my/registrations')
}

export function checkin(activityId, qrCodeKey) {
  return api.post('/checkin', null, { params: { activityId, qrCodeKey } })
}

export default api
