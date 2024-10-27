import request from './request';

export default class Group{
  static save = (data) => {
    return request({
      url: '/blade-bus/group/save',
      method: 'post',
      data: data
    })
  };
  static deletes = (ids) => {
    return request({
      url: '/blade-bus/group/delete',
      method: 'get',
      params: {ids}
    })
  };
  static update = (id, data) => {
    return request({
      url: `/blade-bus/group/update/${id}`,
      method: 'post',
      data: data
    })
  };
  static detail = (id) => {
    return request({
      url: '/blade-bus/group/detail',
      method: 'get',
      params: {id}
    })
  };
  static list = (params) => {
    return request({
      url: '/blade-bus/group/list',
      method: 'get',
      params: {
        ...params,
      }
    })
  };
  static page = (current, size, params) => {
    return request({
      url: '/blade-bus/group/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
  };
}
