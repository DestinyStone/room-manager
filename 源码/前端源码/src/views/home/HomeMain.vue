<template>
 <div style="height: 100%">
   <div style="height: 60px; background: rgb(84, 92, 100)">
     <div style="height: 60px; display: flex; justify-content: space-between;">
       <div style="display: flex;">
         <div style="width: 200px; font-size: 20px; font-weight: 700; text-align: center; height: 60px; line-height: 60px; color: #ccc">后台管理系统</div>
         <div style="display: flex; justify-content: center; flex-flow: column;">
           <el-breadcrumb  separator-class="el-icon-arrow-right">
             <el-breadcrumb-item>
               <span class="toLink">首页</span>
             </el-breadcrumb-item>
             <el-breadcrumb-item v-for="(item, index) of routes" :key="index">
               <span class="toLink">{{item.title}}</span>
             </el-breadcrumb-item>
           </el-breadcrumb>
         </div>
       </div>
       <div style="display: flex; justify-content: end; padding-right: 20px; padding-top: 15px; box-sizing: border-box; cursor: pointer;">
         <div>
           <el-dropdown>
             <div class="el-dropdown-link" style="display: flex; color: #409eff;">
               <div style="margin-top: 2px; font-size: 18px;">酒店房务管理系统</div>
               <i style="margin-top: 10px;" class="el-icon-arrow-down el-icon--right"></i>
             </div>
             <el-dropdown-menu slot="dropdown">
               <el-dropdown-item>
                 <div @click="handlerLoginOut">退出</div>
               </el-dropdown-item>
             </el-dropdown-menu>
           </el-dropdown>
         </div>
       </div>
     </div>
   </div>
   <div style="height: 100%; display: flex">
     <div style="min-width: 200px; width: 200px; height: 100%; background: #909399; overflow: hidden">
       <el-menu
           router
           style="width: calc(100% + 1px)"
           :default-openeds="['message', 'user']"
           class="el-menu-vertical-demo"
           background-color="#909399"
           text-color="#fff"
           active-text-color="#fff">

         <el-menu-item index="/group-manager">
            <span slot="title">
              客房分类管理
            </span>
         </el-menu-item>

         <el-menu-item index="/room-manager">
            <span slot="title">
              宾馆客房管理
            </span>
         </el-menu-item>
       </el-menu>
     </div>
     <div style="flex: 1; max-width: calc(100% - 200px)">
       <div style="padding: 10px;">
         <router-view></router-view>
       </div>
     </div>
   </div>
 </div>
</template>

<script>
  import Auth from "../../api/auth";
    export default {
        name: "HomeMain",
      data() {
          return {
            user: {},
            role: "",
            routes: [],
          }
      },
      watch: {
        '$route'() {
          this.routes = this.$route.meta.routes || [];
        }
      },
      methods: {
        handlerLoginOut() {
          Auth.loginOut().then(() => {
            this.$message.success("退出成功");
            setTimeout(() => {
              this.$router.push({path: "/login"});
            }, 300)
          })
        }
      },
      created() {
      }
    }
</script>

<style scoped>
.toLink {
  color: #cccccc;
  font-size: 16px;
}
.toLink-active {
  color: #cccccc;
  cursor: pointer;
}
.toLink-active:hover{
  color: #409eff;
}
</style>
