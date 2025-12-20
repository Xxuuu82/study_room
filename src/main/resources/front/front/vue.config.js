module.exports = {
  lintOnSave: false,
  devServer: {
    port: 8081,
    proxy: {
      '/study_room': {
        target: 'http://localhost:8080',  // 后端端口
        changeOrigin: true
      }
    }
  }
}