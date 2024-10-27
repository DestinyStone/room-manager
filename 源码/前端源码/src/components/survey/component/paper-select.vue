<template>
  <el-dialog
    title="选择宾馆房客管理"
    :visible.sync="show"
    append-to-body
    width="1200px"
    :before-close="() => {show = false}">
    <avue-crud :option="option"
               :table-loading="loading"
               :data="data"
               :page="page"
               @search-change="searchChange"
               @search-reset="searchReset"
               @selection-change="selectionChange"
               @current-change="currentChange"
               @size-change="sizeChange"
               @on-load="onLoad">
      <template slot="menu" slot-scope="{row}">
        <div>
          <el-link type="primary" @click="clickSave(row)" :underline="false">添加</el-link>
        </div>
      </template>
    </avue-crud>
  </el-dialog>
</template>

<script>
  import Paper from "@/api/Paper";

  export default {
    name: "paper-select",
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
        query: {},
        loading: true,
        page: {
          pageSize: 5,
          currentPage: 1,
          total: 0
        },
        selectionList: [],
        option: {
          header: false,
          addBtn: false,
          editBtn: false,
          delBtn: false,
          viewBtn: false,
          menuWidth: 350,
          labelWidth: "120",
          indexLabel: "序号",
          height: '400',
          searchShow: true,
          searchMenuSpan: 6,
          tip: false,
          border: true,
          index: true,
          selection: false,
          column: [
            {
              label: "编号",
              prop: "code",
              span: 24,
              width: 180,
              search: true,
              rules: [{required: true, message: "请输入编号", trigger: "blur"}],
            },
            {
              label: "宾馆房客管理名称",
              prop: "name",
              span: 24,
              search: true,
              rules: [{required: true, message: "请输入题目", trigger: "blur"}],
            },
            {
              label: "创建时间",
              prop: "createTime",
              width: 180,
              span: 24,
            },
            {
              label: "描述",
              prop: "remark",
              type: "textarea",
              span: 24,
            },
          ]
        },
        data: [],
      }
    },
    methods: {
      clickSave(row) {
        this.$emit("submit", row);
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
      beforeOpen(done, type) {
        if (["edit", "view"].includes(type)) {
        }
        done();
      },
      currentChange(currentPage) {
        this.page.currentPage = currentPage;
      },
      sizeChange(pageSize) {
        this.page.pageSize = pageSize;
      },
      onLoad(page, params = {}) {
        this.loading = false;
        Paper.templatePage(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
          this.loading = false;
        });
      }
    }
  }
</script>

<style scoped>

</style>
