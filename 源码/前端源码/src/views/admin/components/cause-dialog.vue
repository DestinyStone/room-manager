<template>
  <el-dialog
    title="驳回原因填写"
    :visible.sync="show"
    width="30%"
    :before-close="handleClose">
    <div>
      <el-form :model="form" :rules="rules" ref="form" label-width="0px" class="demo-ruleForm">
        <el-form-item label="" prop="cause">
          <el-input v-model="form.cause" type="textarea" :rows="4" placeholder="请填写驳回原因"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="submit">提 交</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: "cause-dialog",
    props: {
      show: {
        type: Boolean,
        default: false,
      }
    },
    watch: {
      show() {
        this.$emit("update:show", this.show);
      }
    },
    data() {
      return {
        form: {},
        rules: {
          cause: [{ required: true, message: '请填写驳回原因', trigger: 'blur' },],
        }
      }
    },
    methods: {
      handleClose() {
        this.show = false;
      },
      submit() {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            this.$emit("submit", this.form);
          }
        });
      }
    }
  }
</script>

<style scoped>

</style>
