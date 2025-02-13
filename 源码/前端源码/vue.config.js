module.exports =  {
  lintOnSave: false,
  devServer: {
    proxy: {
      '/api': {// 匹配所有以 '/api1'开头的请求路径
        target: 'http://localhost:9999',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/api': ''}
      },
    }
  }
};
