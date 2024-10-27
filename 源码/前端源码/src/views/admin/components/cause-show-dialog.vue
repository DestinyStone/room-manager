<template>
  <el-dialog
    title="驳回原因"
    :visible.sync="show"
    width="30%"
    :before-close="handleClose">
    <div>
      <div>{{text}}</div>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="handleClose">关 闭</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    name: "cause-show-dialog",
    props: {
      show: {
        type: Boolean,
        default: false,
      },
      text: String
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
