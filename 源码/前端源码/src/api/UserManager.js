import request from './request';

export default class UserManager {
  static save(data) {
    return request({
      url: '/user/save',
      method: 'post',
      data: data,
    })
  }
  static delete(ids) {
    return request({
      url: '/user/delete',
      method: 'get',
      params: {ids}
    })
  }
  static update(id, data) {
    return request({
      url: `/user/update/${id}`,
      method: 'post',
      data: data,
    })
  }
  static list(param) {
    return request({
      url: `/user/list`,
      method: 'get',
      params: {...param}
    })
  }
  static detail(id) {
    return request({
      url: `/user/detail`,
      method: 'get',
      params: {id}
    })
  }
  static page(current, size, param) {
    return request({
      url: `/user/page`,
      method: 'get',
      params:{
        ...param,
        current,
        size,
      }
    })
  }
}
