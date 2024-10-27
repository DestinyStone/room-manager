<template>
  <div class="basic-container" style="height: 100%;">
    <el-card style="height: 100%;">
      <slot></slot>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "basicContainer"
};
</script>

<style>
.basic-container {
  padding: 10px 6px;
  border-radius: 10px;
  box-sizing: border-box;
  /*.el-card {*/
  /*  width: 100%;*/
  /*}*/
  /*&:first-child {*/
  /*  padding-top: 0;*/
  /*}*/
}
</style>
