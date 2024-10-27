<template>
  <el-dialog
    title="安排维修人员"
    :visible.sync="show"
    width="1000px"
    :before-close="handleClose">
    <div>
      <avue-crud :option="option"
                 :table-loading="loading"
                 :data="data"
                 :page="page"
                 :before-open="beforeOpen"
                 v-model="form"
                 ref="crud"
                 @row-update="rowUpdate"
                 @row-save="rowSave"
                 @row-del="rowDel"
                 @search-change="searchChange"
                 @search-reset="searchReset"
                 @selection-change="selectionChange"
                 @current-change="currentChange"
                 @size-change="sizeChange"
                 @refresh-change="refreshChange"
                 @on-load="onLoad">
        <template slot="menu" slot-scope="{row}">
          <el-button type="text" @click="handlerSelect(row)">选择</el-button>
        </template>
      </avue-crud>
    </div>
  </el-dialog>
</template>

<script>
  import Approval from "@/api/Approval";
  import Group from "@/api/Group";
  import Person from "@/api/Person";

  export default {
    name: "repair-person-select",
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
        form: {
          imgs: [],
        },
        query: {},
        loading: true,
        page: {
          pageSize: 5,
          currentPage: 1,
          total: 0
        },
        selectionList: [],
        option: {
          viewBtn: false,
          addBtn: false,
          editBtn: false,
          delBtn: false,
          labelWidth: 100,
          indexLabel: "序号",
          height: '400',
          tip: false,
          searchShow: true,
          searchMenuSpan: 6,
          border: true,
          index: true,
          selection: false,
          column: [
            {
              label: "登录账号",
              prop: "username",
              span: 24,
              search: true,
            },
            {
              label: "姓名",
              prop: "nick",
              span: 24,
            },
            {
              label: "入驻时间",
              prop: "createTime",
              width: 180,
              span: 24,
            },
          ]
        },
        data: [],
        showReject: false,
        selectRow: {}
      }
    },
    methods: {
      handlerSelect(row) {
        this.$emit("submit", row);
      },
      handlerRejectSubmit(form) {
        Approval.cleanReject(this.selectRow.id, form.cause).then(() => {
          this.onLoad(this.page);
          this.showReject = false;
          this.$message({type: "success", message: "审批驳回成功!"});
        });
      },
      handlerPass(row) {
        this.$confirm("确定审批通过吗?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          return Approval.cleanPass(row.id);
        }).then(() => {
          this.onLoad(this.page);
          this.$message({type: "success", message: "审批通过成功!"});
        });
      },
      handlerReject(row) {
        this.showReject = true;
        this.selectRow = row;
      },
      rowSave(row, done, loading) {
        Group.save(row).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          done();
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        Group.update(row.id, row).then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
          done();
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowDel(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return Group.deletes(row.id);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          });
      },
      handleDelete() {
        if (this.selectionList.length === 0) {
          this.$message.warning("请选择至少一条数据");
          return;
        }
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return Group.deletes(this.ids);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.$refs.crud.toggleSelection();
          });
      },
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {

        }
        done();
      },
      searchReset() {
        this.query = {};
        this.onLoad(this.page);
      },
      searchChange(params, done) {
        this.query = params;
        this.page.currentPage = 1;
        this.onLoad(this.page, params);
        done();
      },
      selectionChange(list) {
        this.selectionList = list;
      },
      selectionClear() {
        this.selectionList = [];
        this.$refs.crud.toggleSelection();
      },
      currentChange(currentPage) {
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize) {
        this.page.pageSize = pageSize;
      },
      refreshChange() {
        this.onLoad(this.page, this.query);
      },
      onLoad(page, params = {}) {
        this.loading = true;
        Person.repairPage(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
          this.loading = false;
          this.selectionClear();
        });
      },
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
