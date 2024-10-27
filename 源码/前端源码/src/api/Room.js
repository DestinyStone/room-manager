import request from './request';

export default class Room{
  static userLog = (params) => {
    return request({
      url: '/blade-bus/room/in/user/log',
      method: 'get',
      params: {...params}
    })
  };
  static save = (data) => {
    return request({
      url: '/blade-bus/room/save',
      method: 'post',
      data: data
    })
  };
  static deletes = (ids) => {
    return request({
      url: '/blade-bus/room/delete',
      method: 'get',
      params: {ids}
    })
  };
  static update = (id, data) => {
    return request({
      url: `/blade-bus/room/update/${id}`,
      method: 'post',
      data: data
    })
  };
  static detail = (id) => {
    return request({
      url: '/blade-bus/room/detail',
      method: 'get',
      params: {id}
    })
  };
  static list = (params) => {
    return request({
      url: '/blade-bus/room/list',
      method: 'get',
      params: {
        ...params,
      }
    })
  };
  static page = (current, size, params) => {
    return request({
      url: '/blade-bus/room/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
  };
}
