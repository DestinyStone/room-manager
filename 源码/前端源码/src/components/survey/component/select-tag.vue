<template>
    <div class="select-tag">
      <el-button @click="clickAdd" type="primary">添加选项</el-button>
      <div>
        <el-table
          :data="data"
          border
          style="width: 100%; margin-top: 10px;">
          <el-table-column
            prop="content"
            align="center"
            label="内容">
          </el-table-column>
          <el-table-column
            prop="sortIndex"
            align="center"
            width="100"
            label="排序">
          </el-table-column>
          <el-table-column
            prop="operator"
            width="180"
            align="center"
            label="操作">
            <template slot-scope="scope">
              <div style="display: flex; justify-content: space-around">
                <el-link :underline="false" type="primary" @click="editTags(scope.row)">編輯</el-link>
                <el-link :underline="false" @click="removeTags(scope.row)" type="danger">刪除</el-link>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <el-dialog
        :title="form.id ? '编辑选项' : '新增选项'"
        :visible.sync="show"
        append-to-body
        width="800px"
        :before-close="() => {show = false}">
        <div class="select-tag-form">
          <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="demo-ruleForm">
            <el-form-item label="内容" prop="content">
              <el-input v-model="form.content" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="排序" prop="sortIndex">
              <el-input oninput="value=value.replace(/[^\d]/g,'')" v-model="form.sortIndex"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="show = false">取 消</el-button>
          <el-button type="primary" @click="clickSave">确 定</el-button>
        </div>
      </el-dialog>
      <image-select @change="handlerChangeImage" :show.sync="showImageSelect" v-if="showImageSelect"></image-select>
    </div>
</template>

<script>
  import func from "@/util/func.js";

  export default {
    name: "selectTag",
    props: {
      tagsData: {
        type: Array,
        default() {
          return []
        }
      }
    },
    data() {
      return {
        show: false,
        rules: {
          content: [{ required: true, message: '请输入内容', trigger: 'change' }],
          flag: [{ required: true, message: '请选择情绪标识', trigger: 'change' }],
          sortIndex: [{ required: true, message: '请输入排序号', trigger: 'change' }],
        },
        form: {},
        showImageSelect: false,
        data: [],
      }
    },
    watch: {
      show() {
        if (!this.show) {
          this.form = {};
        }
      },
      tagsData: {
        handler() {
          this.data = this.tagsData ? JSON.parse(JSON.stringify(this.tagsData)) : [];
        },
        deep: true,
      }
    },
    methods: {
      editTags(tag) {
        this.show = true;
        this.form = JSON.parse(JSON.stringify(tag));
      },
      removeTags(tag) {
        this.$confirm("确定将选择数据删除?", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let index = this.data.indexOf(tag);
          this.data.splice(index, 1);
          this.$emit("submit", JSON.parse(JSON.stringify(this.data)));
        })
      },

      clickSave() {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            return;
          }

          if (this.form.id) {
            let index = this.data.map(item => item.id).indexOf(this.form.id);
            this.data.splice(index, 1, this.form);
          }else {
            this.form.id = func.guid();
            this.data.push(JSON.parse(JSON.stringify(this.form)));
          }

          this.form = {};
          this.data.sort((x, y) => parseInt(x.sortIndex) > parseInt(y.sortIndex) ? 1 : -1);
          this.$emit("submit", JSON.parse(JSON.stringify(this.data)));
          this.show = false;
        });
      },
      handlerChangeImage(image) {
        this.$set(this.form, 'imgUrl', image.url);
      },
      clickAdd() {
        this.show = true;
        this.form = {};
      },
    },
    created() {
      this.data = this.tagsData ? JSON.parse(JSON.stringify(this.tagsData)) : [];
    }
  }
</script>

<style>
  .select-tag-form .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .select-tag-form .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .select-tag-form .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .select-tag-form .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
