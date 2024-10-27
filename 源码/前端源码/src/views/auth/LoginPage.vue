<template>
    <div id="login-page" style="display: flex; justify-content: center; background: url('/image/img.png'); height: 100%;">
        <div style="width: 40%; min-width: 600px; margin-top: 100px;">
            <el-card class="box-card">
                <div style="text-align: center; font-weight: 700; font-size: 22px; line-height: 22px; height: 22px">酒店房务管理系统</div>
            </el-card>
            <el-card class="box-card" style="margin-top: 10px">
                <el-tabs v-model="activeName">
                    <el-tab-pane label="登录"  name="login">
                        <el-form ref="loginForm" :rules="rules"  :model="loginForm" label-width="120px" style="padding: 20px 60px 20px 0px">
                            <el-form-item label="用户名" prop="username">
                                <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
                            </el-form-item>
                            <el-form-item label="密码" prop="password">
                                <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
                            </el-form-item>
                          <el-form-item label="角色" prop="role">
                            <el-radio v-model="loginForm.role" label="宾馆入驻">宾馆入驻</el-radio>
                            <el-radio v-model="loginForm.role" label="管理员">管理员</el-radio>
                          </el-form-item>
                          <el-form-item label="验证码" prop="code">
                            <div style="display: flex">
                              <el-input style="width: 200px;" v-model="loginForm.code" placeholder="请输入验证码"></el-input>
                              <div style="margin-left: 30px;">
                                <img :src="codeSrc" id="authCodeImg" @click="changeCodeImg">
                              </div>
                            </div>
                          </el-form-item>
                        </el-form>
                        <div>
                            <el-button style="width: 100%;" type="primary" @click="login">登录</el-button>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="注册" name="register">
                      <el-form ref="registerForm" :rules="registerRules"  :model="registerForm" label-width="120px" style="padding: 20px 60px 20px 0px">
                        <el-row>
                          <el-col :span="12">
                            <el-form-item :label="registerForm.role === '用户' ? '昵称' : '宾馆名称'" prop="nick">
                              <el-input type="nick" v-model="registerForm.nick" :placeholder="`请输入${registerForm.role === '用户' ? '昵称' : '宾馆名称'}`"></el-input>
                            </el-form-item>
                            <el-form-item label="用户名" prop="username">
                              <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
                            </el-form-item>
                          </el-col>
                          <el-col :span="12">
                            <el-form-item label="头像" prop="avatar">
                              <el-upload
                                  :class="avatarClass"
                                  action="/api/blade-bus/file/upload"
                                  :show-file-list="false"
                                  :on-success="handleAvatarSuccess"
                              >
                                <img v-if="registerForm.avatar" :src="registerForm.avatar" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                              </el-upload>
                            </el-form-item>
                          </el-col>
                          <el-col :span="12">
                            <el-form-item label="密码" prop="password">
                              <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
                            </el-form-item>
                          </el-col>
                          <el-col :span="12">
                            <el-form-item label="确认密码" prop="confirmPassword">
                              <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请输入确认密码"></el-input>
                            </el-form-item>
                          </el-col>
                        </el-row>
                          <el-row>
                            <el-col :span="12">
                              <el-form-item label="联系电话" prop="phone">
                                <el-input v-model="registerForm.phone" placeholder="请输入联系电话"></el-input>
                              </el-form-item>
                            </el-col>
                            <el-col :span="12">
                              <el-form-item label="联系邮箱" prop="email">
                                <el-input v-model="registerForm.email" placeholder="请输入联系邮箱"></el-input>
                              </el-form-item>
                            </el-col>
                          </el-row>
                          <el-form-item v-if="registerForm.role === '宾馆入驻'" label="宾馆官网" prop="website">
                            <el-input v-model="registerForm.website" placeholder="请输入宾馆官网"></el-input>
                          </el-form-item>
                          <el-form-item v-if="registerForm.role === '宾馆入驻'" label="宾馆描述" prop="remark">
                            <el-input v-model="registerForm.remark" type="textarea" resize="none" :rows="4" placeholder="请输入宾馆描述"></el-input>
                          </el-form-item>
                        </el-form>
                        <div>
                            <el-button style="width: 100%;"  @click="register">注册</el-button>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </el-card>
        </div>
    </div>
</template>

<script>
    import Auth from "../../api/auth";

    export default {
        name: "LoginName",
        data() {
            return {
              codeSrc: "/api/auth/getAuthCodeImg?count=0",
                activeName: "login",
                loginForm: {
                  model: 0,
                  role: "用户"
                },
                registerForm: {
                  role: "宾馆入驻"
                },
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'},],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'},],
                },
                registerRules: {
                  username: [{required: true, message: '请输入用户名', trigger: 'blur'},],
                  password: [{required: true, message: '请输入密码', trigger: 'blur'},],
                  confirmPassword: [{required: true, message: '请输入确认密码', trigger: 'blur'},],
                  nick: [{required: true, message: '请输入姓名', trigger: 'blur'},],
                  avatar: [{required: true, message: '请上传头像', trigger: 'blur'},],
                  phone: [{required: true, message: '请输入联系电弧', trigger: 'blur'},],
                  email: [{required: true, message: '请输入邮箱', trigger: 'blur'},],
                }
            }
        },
      computed: {
          avatarClass() {
            if (this.registerForm.role === '用户')     {
              return "avatar-uploader"
            }
            return "avatar-room-uploader"
          }
      },
        methods: {
          changeCodeImg(){
            this.codeSrc = "/api/auth/getAuthCodeImg?count=1&timestamp="+new Date().getTime();
          },
          handleAvatarSuccess(uuid) {
            let url = `./api/blade-bus/file/download?uuid=${uuid}`;
            this.$set(this.registerForm, 'avatar', url);
          },
            login() {
                this.$refs["loginForm"].validate((valid) => {
                    if (!valid) {
                        return;
                    }
                    this.handlerLogin();
                });
            },
            handlerLogin() {
                Auth.login(this.loginForm).then(res => {
                    let data = res.data;
                    if (data.code !== 200) {
                        this.$message({message: data.msg, type: 'error'});
                        return;
                    }
                    this.$message({message: data.msg, type: 'success'});
                    setTimeout(() => {
                        window.location.href = "/";
                    }, 800);
                })
            },
            register() {
                this.$refs["registerForm"].validate((valid) => {
                    if (!valid) {
                        return;
                    }
                    this.handlerRegister();
                });
            },
            handlerRegister() {
                if (this.registerForm.password !== this.registerForm.confirmPassword) {
                    this.$message.warning("两次输入的密码不一致");
                    return;
                }
                Auth.register(this.registerForm).then(res => {
                    let data = res.data;
                    if (data.code !== 200) {
                        this.$message({message: data.msg, type: 'error'});
                        return;
                    }
                  this.$message({message: "注册成功", type: 'success'});

                    this.loginForm = {model: 0,};
                    this.registerForm = {};
                    this.activeName = "login";
                })
            }
        }
    }
</script>

<style>
 #login-page >>> .el-select-dropdown__item {
   padding-left: 10px;
 }

 .avatar-uploader {
   margin-left: 30px;
 }
 .avatar-uploader .el-upload {
   border: 1px dashed #d9d9d9;
   cursor: pointer;
   position: relative;
   overflow: hidden;
   border-radius: 50%;
 }
 .avatar-uploader .el-upload:hover {
   border-color: #409EFF;
 }
 .avatar-uploader .avatar-uploader-icon {
   font-size: 28px;
   color: #8c939d;
   width: 100px;
   height: 100px;
   line-height: 100px;
   text-align: center;
 }
 .avatar-uploader .avatar {
   width: 100px;
   height: 100px;
   display: block;
   border-radius: 50%;
 }

 .avatar-room-uploader .el-upload {
   border: 1px dashed #d9d9d9;
   cursor: pointer;
   position: relative;
   overflow: hidden;
   border-radius: 6px;
 }
 .avatar-room-uploader .el-upload:hover {
   border-color: #409EFF;
 }
 .avatar-room-uploader .avatar-uploader-icon {
   font-size: 28px;
   color: #8c939d;
   width: 178px;
   height: 100px;
   line-height: 100px;
   text-align: center;
 }
 .avatar-room-uploader .avatar {
   width: 178px;
   height: 100px;
   display: block;
   border-radius: 6px;
 }
</style>
