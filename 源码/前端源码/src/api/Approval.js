import request from './request';

export default class Approval{
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

  static repairSuccess = (id) => {
    return request({
      url: '/blade-bus/room/repair/handler/success',
      method: 'get',
      params: {id}
    })
  }
  static repairPage = (current, size, params) => {
    return request({
      url: '/blade-bus/room/repair/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
  };
  static repairPass = (id, userId) => {
    return request({
      url: '/blade-bus/room/repair/pass',
      method: 'get',
      params: {
        id,
        userId
      }
    })
  };
  static repairReject = (id, cause) => {
    return request({
      url: '/blade-bus/room/repair/reject',
      method: 'get',
      params: {
        id,
        cause
      }
    })
  };


  static cleanSuccess = (id) => {
    return request({
      url: '/blade-bus/room/clean/handler/success',
      method: 'get',
      params: {id}
    })
  }
  static cleanPage = (current, size, params) => {
    return request({
      url: '/blade-bus/room/clean/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
  };
  static cleanPass = (id, userId) => {
    return request({
      url: '/blade-bus/room/clean/pass',
      method: 'get',
      params: {
        id,
        userId
      }
    })
  };
  static cleanReject = (id, cause) => {
    return request({
      url: '/blade-bus/room/clean/reject',
      method: 'get',
      params: {
        id,
        cause
      }
    })
  };


  static outPage = (current, size, params) => {
    return request({
      url: '/blade-bus/room/out/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
  };
  static page = (current, size, params) => {
    return request({
      url: '/blade-bus/room/in/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
  };
  static outPass = (id) => {
    return request({
      url: '/blade-bus/room/out/pass',
      method: 'get',
      params: {
        id
      }
    })
  };
  static outReject = (id, cause) => {
    return request({
      url: '/blade-bus/room/out/reject',
      method: 'get',
      params: {
        id,
        cause
      }
    })
  };
  static pass = (id) => {
    return request({
      url: '/blade-bus/room/in/pass',
      method: 'get',
      params: {
        id
      }
    })
  };
  static reject = (id, cause) => {
    return request({
      url: '/blade-bus/room/in/reject',
      method: 'get',
      params: {
        id,
        cause
      }
    })
  };
  static addDay = (id) => {
    return request({
      url: '/blade-bus/room/in/add/day',
      method: 'get',
      params: {
        id
      }
    })
  };
}
