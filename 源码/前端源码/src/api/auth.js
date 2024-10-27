import request from './request';
export default class Auth {
    static updateInfo = (data) => {
        return request({
            url: '/info/update',
            method: 'post',
            data: data
        })
    };
    static passwordUpdate = (data) => {
        return request({
            url: '/password/update',
            method: 'post',
            data: data
        })
    };
    static login = (data) => {
        return request({
            url: '/auth',
            method: 'post',
            data: data
        })
    };
    static register = (data) => {
        return request({
            url: '/register',
            method: 'post',
            data: data
        })
    };
    static loginOut = () => {
      return request({
        url: '/loginOut',
        method: 'get',
      })
    }
}
