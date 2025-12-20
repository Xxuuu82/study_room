import axios from 'axios'
import router from '@/router/router-static'
import storage from '@/utils/storage'

const http = axios.create({
    timeout: 1000 * 86400,
    withCredentials: true,
    // 核心修改：补充后端IP+端口（localhost:8080），保留上下文路径/study_room
    baseURL: 'http://localhost:8082/study_room',
    //baseURL:''
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})

// 请求拦截：给请求头加Token，逻辑不变
http.interceptors.request.use(config => {
    config.headers['Token'] = storage.get('Token') // 请求头带上token
    return config
}, error => {
    return Promise.reject(error)
})

// 响应拦截：处理401 token失效，优化错误提示（可选，提升体验）
http.interceptors.response.use(response => {
    if (response.data && response.data.code === 401) { // 401, token失效
        router.push({ name: 'login' })
    }
    return response
}, error => {
    // 新增：打印错误详情，方便排查（可根据需要保留/删除）
    console.error('请求异常：', {
        url: error.config?.url,
        method: error.config?.method,
        message: error.message
    })
    // 统一提示请求失败（和你的ranking.vue中的提示呼应）
    if (window.ElMessage) {
        window.ElMessage.error('请求失败：' + (error.message || '网络异常'))
    }
    return Promise.reject(error)
})

export default http