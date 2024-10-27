<template>
  <basic-container>
    <avue-crud :option="option"
               :table-loading="loading"
               :data="data"
               :page="page"
               :permission="permissionList"
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
      <template slot="menuLeft">
        <el-button type="danger"
                   size="small"
                   icon="el-icon-delete"
                   plain
                   @click="handleDelete">删 除
        </el-button>
      </template>
      <template slot="tagListForm" slot-scope="{row}">
        <div>
          <div>
            <el-input style="width: 200px;" v-model="currentTag" placeholder="请填写设施"/>
            <el-button @click="handlerAddTag" style="width: 100px; margin-left: 10px;" type="primary">添加</el-button>
          </div>
          <div style="width: calc(100% - 10px); height: 100px; border-radius: 15px; border: 1px solid #ccc; margin-top: 10px; display: flex; flex-flow: wrap">
            <el-tag
                v-for="(tag, index) in form.tagList"
                :key="tag"
                closable
                style="margin: 10px 10px 0px 10px"
                @close="handleCloseTag(tag, index)"
                :type="tag">
              {{tag}}
            </el-tag>
          </div>
        </div>
      </template>
      <template slot="convers" slot-scope="{row}">
        <div style="display: flex; flex-wrap: nowrap; overflow: auto;">
          <div  v-for="(item, index) in row.convers" :key="'image' + index" style="position:relative; padding: 10px; ">
            <el-image style="width: 65px; height: 40px; " :preview-src-list="[item]" :src="item" class="avatar"></el-image>
          </div>
        </div>
      </template>
      <template slot="conversForm">
        <div>
          <el-upload
              class="avatar-uploader"
              action="/api/blade-bus/file/upload"
              :on-success="handlerUpload"
              :show-file-list="false">
            <el-button size="mini" type="primary">点击上传</el-button>
          </el-upload>
<!--          <el-upload-->
<!--              class="avatar-uploader"-->
<!--              action=""-->
<!--              :http-request="uploadFile"-->
<!--              :show-file-list="false">-->
<!--            <el-button size="mini" type="primary">点击上传</el-button>-->
<!--          </el-upload>-->
          <div style="display: flex; flex-wrap: nowrap; overflow:auto;" v-if="form.convers">
            <div v-for="item in form.convers" :key="item" style="position:relative; margin-top: 20px; width: 320px; padding: 0 10px;">
              <div style="position:absolute; top: 20px; right: 20px; z-index: 999; cursor: pointer" @click="removeUploadImage(item)">
                <i style="font-size: 20px; color: #000000;" class="el-icon-remove-outline"></i>
              </div>
              <div style="border: 1px solid #cccc99; border-radius: 20px; padding: 10px 10px 0 10px; ">
                <el-image style="width: 300px; height: 178px; border-radius: 20px;" :preview-src-list="[item]" :src="item" class="avatar"></el-image>
              </div>
            </div>
          </div>
        </div>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import FileUtil from "@/api/FileUtil";
import Room from "@/api/Room";

export default {
  name: "RoomManager",
  data() {
    return {
      form: {
        imgs: [],
      },
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0
      },
      currentTag: "",
      selectionList: [],
      option: {
        labelWidth: 100,
        indexLabel: "序号",
        height: 'auto',
        calcHeight: 210,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: false,
        selection: true,
        column: [
          {
            label: "分类",
            prop: "groupId",
            align: "center",
            span: 24,
            search: true,
            searchSpan: 4,
            type: "select",
            dicUrl: "/blade-bus/group/list",
            props: {
              value: "id",
              label: "name",
            },
            rules: [{required: true, message: "请选择分类", trigger: "blur"}]
          },
          {
            label: "客房编号",
            prop: "code",
            align: "center",
            searchSpan: 4,
            span: 24,
            search: true,
            rules: [{required: true, message: "请输入客房编号", trigger: "blur"}]
          },
          {
            label: "客房名称",
            prop: "name",
            span: 24,
            searchSpan: 4,
            align: "center",
            search: true,
            rules: [{required: true, message: "请输入客房名称", trigger: "blur"}]
          },
          {
            label: "图片",
            prop: "convers",
            align: "center",
            span: 24,
            slot: true,
            formslot: true,
            rules: [{required: true, message: "请上传图片", trigger: "blur"}]
          },
          {
            label: "客房数量",
            prop: "number",
            align: "center",
            span: 24,
            type: "number",
            rules: [{required: true, message: "请输入客房数量", trigger: "blur"}]
          },
          {
            label: "地址",
            prop: "address",
            align: "center",
            span: 24,
            rules: [{required: true, message: "请填写地址", trigger: "blur"}]
          },
          {
            label: "定金（元）",
            prop: "startMoney",
            align: "center",
            type: "number",
            span: 24,
            rules: [{required: true, message: "请输入定金", trigger: "blur"}]
          },
          {
            label: "租金(元/天)",
            prop: "moneyDay",
            span: 24,
            align: "center",
            type: "number",
            width: 100,
            rules: [{required: true, message: "请输入租金", trigger: "blur"}]
          },
        ]
      },
      data: []
    };
  },
  computed: {
    ids() {
      let ids = [];
      this.selectionList.forEach(ele => {
        ids.push(ele.id);
      });
      return ids.join(",");
    }
  },
  methods: {
    handleCloseTag(tag, index) {
      this.form.tagList.splice(index, 1);
    },
    handlerAddTag() {
      if (!this.form.tagList) {
        this.$set(this.form, 'tagList', []);
      }
      if (!this.currentTag) {
        this.$message.warning("请输入设施");
        return;
      }
      this.form.tagList.push(this.currentTag);
      this.currentTag = "";
    },
    handlerUpload(uuid) {
      if (!this.form.convers) {
        this.$set(this.form, 'convers', []);
      }
      let url = `./api/blade-bus/file/download?uuid=${uuid}`;
      this.form.convers.push(url);
    },
    removeUploadImage(item) {
      let index = this.form.convers.indexOf(item);
      this.form.convers.splice(index, 1);
    },
    uploadFile(request) {
      let file = request.file;
      if (file.type !== "image/png" && file.type !== "image/jpeg" && file.type !== "image/webp") {
        this.$message({message: "只支持图片格式上传", type: 'warning'});
        return;
      }

      let formData = new FormData();
      console.log(file);
      formData.append("file", file);

      if (!this.form.convers) {
        this.$set(this.form, 'convers', []);
      }

      FileUtil.upload(formData).then(res => {
        let url = `./api/blade-bus/file/download?uuid=${res.data}`;
        this.form.convers.push(url);
      })
    },
    rowSave(row, done, loading) {
      row.tags = (row.tagList || []).join(",")
      Room.save(row).then(() => {
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
      row.tags = (row.tagList || []).join(",")
      Room.update(row.id, row).then(() => {
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
            return Room.deletes(row.id);
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
            return Room.deletes(this.ids);
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
      Room.list(page.currentPage, page.pageSize, Object.assign(params, this.query)).then(res => {
        const data = res.data.data;
        this.data = data.map(item => ({
          ...item,
          tagList: item.tags ? item.tags.split(",") : [],
        }));
        this.loading = false;
        this.selectionClear();
      });
    }
  }
};
</script>

<style>

</style>
