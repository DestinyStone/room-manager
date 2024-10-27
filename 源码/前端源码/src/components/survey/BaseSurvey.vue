<template>
  <div style="height: 100%;">
    <basic-container style="height: 100%;" id="paper" v-if="!show">
      <avue-crud :option="option"
                 ref="crud"
                 :table-loading="loading"
                 :data="data"
                 :page="page"
                 @row-del="rowDel"
                 v-model="form"
                 :permission="permissionList"
                 @row-update="rowUpdate"
                 @row-save="rowSave"
                 :before-open="beforeOpen"
                 @search-change="searchChange"
                 @search-reset="searchReset"
                 @selection-change="selectionChange"
                 @current-change="currentChange"
                 @size-change="sizeChange"
                 @on-load="onLoad">
        <template slot="menuLeft">
          <el-button v-if="type === -1" size="small" type="warning" @click="clickLoadTemplate">加载模板</el-button>
        </template>
        <template slot="menu" slot-scope="{row, index}">
          <el-button v-if="row.status === -1" type="text" icon="el-icon-edit" size="mini" @click="$refs['crud'].rowEdit(row, index)">编辑</el-button>
          <el-button v-if="row.status === -1" type="text" icon="el-icon-delete" size="mini" @click="rowDel(row)">删除</el-button>
          <el-button v-if="row.status === -1" type="text" icon="el-icon-s-order" size="mini" @click="handlerTopicManager(row)">题目管理</el-button>
          <el-button type="text" icon="el-icon-view" size="mini" @click="handlerPreview(row)">预览</el-button>
          <el-button v-if="row.status === -1" type="text" icon="el-icon-view" size="mini" @click="handlerPublic(row)">发布</el-button>
<!--          <el-button v-if="type === -1 && row.status === 0" type="text" icon="el-icon-view" size="mini" @click="handlerSeeUrl(row)">查看宾馆房客管理链接</el-button>-->
        </template>
        <template slot="appointClass" slot-scope="{row}">
          <div>
            <el-tag style="margin-right: 10px;" v-for="item in row.depts" :key="'depts' + item.id">{{item.deptName}}</el-tag>
          </div>
        </template>
      </avue-crud>
    </basic-container>
    <paper-topic-manager
        :id="selectRow.id"
        @success="handlerSave"
        :style="{'height': height + 'px'}"
        v-if="show" style="height: 100% !important;" @close="show = false"/>
    <paper-select @submit="handlerSubmit"  v-if="showPaperSelect" :show.sync="showPaperSelect"/>
    <el-dialog
            title="宾馆房客管理链接"
            :visible.sync="showUrl"
            append-to-body
            width="800px"
            :before-close="() => {showUrl = false}">
      <el-tag type="success" style="font-size: 20px; height: 50px; line-height: 50px">{{currentUrl}}</el-tag>
    </el-dialog>
  </div>
</template>

<script>
  import Paper from "@/api/Paper";
  import PaperTopicManager from "./component/paper-topic-manager";
  import PaperTopic from "@/api/PaperTopic";
  import PaperSelect from "./component/paper-select";

  export default {
    components: {PaperSelect, PaperTopicManager},
    props: {
      type: {
        type: Number,
      }
    },
    data() {
      return {
        showUrl: false,
        currentUrl: "",
        height: 0,
        show: false,
        form: {
          isAppointClass: "否",
        },
        query: {},
        loading: true,
        page: {
          pageSize: 10,
          currentPage: 1,
          total: 0
        },
        selectionList: [],
        showPaperSelect: false,
        option: {
          editBtn: false,
          delBtn: false,
          menuWidth: 350,
          labelWidth: "120",
          indexLabel: "序号",
          height: 'auto',
          searchShow: true,
          searchMenuSpan: 6,
          tip: false,
          border: true,
          index: true,
          viewBtn: true,
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
              label: "发布状态",
              prop: "status",
              type: "radio",
              search: true,
              addDisplay: false,
              editDisplay:false,
              dicData: [
                {label: "未发布", value: -1},
                {label: "已发布", value: 0},
              ],
              span: 24,
            },
            {
              label: "创建时间",
              prop: "createTime",
              width: 180,
              span: 24,
              addDisplay: false,
              editDisplay:false,
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
        selectRow: {},
      };
    },
    mounted() {
      this.height = document.getElementById("paper").offsetHeight;
    },
    watch: {
      'form.isAppointClass'() {
        let addDisplay = false;
        let editDisplay = false;

        if (this.form.isAppointClass === "是") {
          addDisplay = true;
          editDisplay = true;
        }
        if (this.form.isAppointClass === "否") {
          addDisplay = false;
          editDisplay = false;
        }

        for(let item of this.option.column) {
          if (item.prop !== 'appointClass') {
            continue;
          }
          item.addDisplay = addDisplay;
          item.editDisplay = editDisplay;
        }
      }
    },
    computed: {
      permissionList() {
        return {
          addBtn: true,
          viewBtn: false,
          delBtn: true,
          editBtn: true,
        };
      },
      ids() {
        let ids = [];
        this.selectionList.forEach(ele => {
          ids.push(ele.id);
        });
        return ids.join(",");
      }
    },
    methods: {
      handlerSeeUrl(row) {
        this.currentUrl = "http://" + window.location.host + "/#/report?paperId=" + row.id;
        this.showUrl = true;
      },
      handlerSubmit(data) {
        Paper.load(data.id).then(() => {
          this.$message.success("加载模版成功");
          this.showPaperSelect = false;
          this.onLoad(this.page);
          return;
        });
      },
      clickLoadTemplate() {
        this.showPaperSelect = true;
      },
      handlerPreview(row) {
        window.open(`/#/preview?paperId=${row.id}`);
      },
      handlerSave(data) {
        let copy = JSON.parse(JSON.stringify(data));
        for(let item of copy) {
          item.id = null;
          item.tags = JSON.stringify(item.tags);
        }
        console.log(copy);
        PaperTopic.save(this.selectRow.id, copy).then(() => {
          this.$message.success("保存成功");
          this.show = false;
        })
      },
      handlerTopicManager(row) {
        this.selectRow = JSON.parse(JSON.stringify(row));
        this.show = true;
      },
      handlerChangeClass(data) {
        this.form.appointClass = data.map(item => item.id).join(",");
      },
      rowSave(row, done, loading) {
        row.type = this.type;
        Paper.save(row).then(() => {
          done();
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      rowUpdate(row, index, done, loading) {
        Paper.update(row.id, row).then(() => {
          done();
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!"
          });
        }, error => {
          window.console.log(error);
          loading();
        });
      },
      handlerPublic(row) {
        this.$confirm("一经发布不可取消， 确定发布吗?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
        .then(() => {
          return Paper.publicStudent(row.id);
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "发布成功!"
          });
        }).catch(() => {})
      },
      rowDel(row) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            return Paper.delete(row.id);
          })
          .then(() => {
            this.onLoad(this.page);
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          }).catch(() => {})
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
            return Paper.delete(this.ids);
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
        this.query.type = this.type;
        Paper.page(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
          this.loading = false;
        });
      }
    }
  };
</script>

<style scoped>

</style>
