import Axios from "axios";
import {Message} from 'element-ui'

const request = Axios.create({
    baseURL: "/api",
    timeout: 30000,
    headers: {"Content-Type":"application/json"}
});

request.interceptors.response.use((config)=>{
    if (config.data.code === 400 && config.data.msg === "用户未登录") {
        Message.warning("权限失效， 请重新登录");
        setTimeout(() => {
            window.location.href = "/#/login"
        }, 1000)
        return new Promise(()=>{})
    }
    if (config.data.code === 400) {
        Message.warning(config.data.msg);
        return new Promise(()=>{})
    }
    return config
},(error)=>{
    console.log(error);
    return Promise.reject(error)
})

export default request;
