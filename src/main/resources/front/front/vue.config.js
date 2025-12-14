module.exports = {
  lintOnSave: false,
  devServer: {
    port: 8081,
    proxy: {
      '/study_room': {
        target: 'http://localhost:8082',  // 后端端口
        changeOrigin: true
      }
    }
  }
}