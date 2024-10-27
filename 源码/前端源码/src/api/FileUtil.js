import request from './request';

export default class FileUtil {
  static upload = (data) => {
    return request({
      url: '/blade-bus/file/upload',
      method: 'post',
      data: data
    })
  };
}
