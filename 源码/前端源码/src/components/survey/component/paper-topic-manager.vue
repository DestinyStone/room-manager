<template>
    <div id="paper-topic-manager"  style="position:relative; background: rgb(204, 204, 204); padding: 10px;" :style="{minHeight: height + 'px'}">
      <div style="position:absolute; top: 10px; right: 10px;">
        <i class="el-icon-error close" @click="handlerClose" style="font-size: 40px; cursor: pointer"/>
      </div>
      <el-button @click="clickSave" type="primary">添加题目</el-button>
      <el-button @click="clickSuccess" type="warning">保存题目</el-button>
      <div style="margin-top: 10px; overflow: auto" :style="{minHeight: height + 'px', height: height + 'px'}">
        <basic-container v-for="(item, index) in data" :key="'item' + index" shadow="always"
                         style="min-height: 500px; max-height: 500px; position:relative; width: 80%; margin: 0 auto;">
          <div style="max-height: 500px; min-height: 500px; position:relative;">
            <div style="position: absolute; right: 10px; top: 10px;">
              <el-button type="primary" size="small" @click="handlerEdit(item)">编辑</el-button>
              <el-button type="warning" size="small" @click="handlerDelete(item)">删除</el-button>
            </div>
            <div style="">
              <div style="display: flex">
                <div style="font-size: 40px; background: #1e9fff; color: #F2F6FC; line-height: 100px; height: 100px; width: 100px; border-radius: 50%; text-align: center">
                  {{item.sortIndex}}
                </div>
                <div style="font-size: 40px; line-height: 100px; height: 100px; margin-left: 30px;"> {{item.title}}</div>
              </div>
            </div>
            <div style="position:absolute; left: 0; bottom: 60px; width: 100%;">
              <div v-if="item.type === 2">
                <el-input type="textarea" resize="none" :rows="5" placeholder="请输入"></el-input>
              </div>
              <div style="display: flex; justify-content: space-around;" v-if="item.type === 0 || item.type === 1">
                <div
                  style="display: flex; justify-content: center; flex-flow: column;"
                  v-for="(tagItem, currentIndex) in item.tags" :key="'tags' + tagItem.id">
                  <basic-container
                    class="select-tag"
                    shadow="always" style="cursor: pointer; height: auto;"
                  >
                    <div style="text-align: center;font-size: 20px;">{{getContent(item, currentIndex, tagItem.content)}}</div>
                  </basic-container>
                </div>
              </div>
            </div>
          </div>
        </basic-container>
        <div style="height: 150px;">

        </div>
      </div>
      <el-dialog
        :title="form.id ? '编辑选项' : '新增选项'"
        :visible.sync="show"
        append-to-body
        width="60%"
        :before-close="() => {show = false}">
        <div class="select-tag-form" style="padding-right: 80px; height: 600px; overflow: auto">
          <el-form size="small" :model="form" :rules="rules" ref="form" label-width="100px" class="demo-ruleForm">
            <el-form-item label="题目: " prop="title">
              <el-input v-model="form.title" placeholder="请输入题目"></el-input>
            </el-form-item>
            <el-form-item label="类型: " prop="type">
              <el-select v-model="form.type" placeholder="请选择类型">
                <el-option label="单选题" :value="0"></el-option>
                <el-option label="多选题" :value="1"></el-option>
                <el-option label="填空题" :value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-if="form.type === 0 || form.type === 1" label="选项序号: " prop="serialNumberType">
              <el-select v-model="form.serialNumberType" placeholder="请选择选项序号">
                <el-option
                  v-for="item in serialNumberOption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item  v-if="form.type === 0 || form.type === 1" label="选项: " prop="tags">
              <select-tag @submit="(tags) => {form.tags = tags}" :tags-data="form && form.tags">
              </select-tag>
            </el-form-item>
            <el-form-item label="排序号: " prop="sortIndex">
              <el-input type="number" v-model="form.sortIndex" placeholder="请输入排序号"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="show = false">取 消</el-button>
          <el-button type="primary" @click="handlerSave">确 定</el-button>
        </div>
      </el-dialog>
      <select-topic @load="handlerSelectTemplate" v-if="showTemplate" :show.sync="showTemplate"/>
    </div>
</template>

<script>
    import func from "../../../util/func";
    import SelectTag from "./select-tag";
    import PaperTopic from "../../../api/PaperTopic";
    export default {
      name: "paper-topic-manager",
      components: {SelectTag},
      // components: {SelectTopic, SelectTag},
      props: {
        id: {
          type: String,
          default: ""
        },
        isUse: {
          type: Boolean,
          default: false,
        }
      },
      watch: {
        'form.type'() {
          this.rules = {
            type: [{required: true, message: '请选择类型', trigger: 'change'}],
            title: [{required: true, message: '请输入题目', trigger: 'change'}],
            sortIndex: [{ required: true, message: '请输入排序号', trigger: 'change' }],
          }
          if (this.form.type === 1 || this.form.type === 0) {
            this.rules = {
              type: [{ required: true, message: '请选择类型', trigger: 'change' }],
              title: [{ required: true, message: '请输入题目', trigger: 'change' }],
              serialNumberType: [{ required: true, message: '请选项序号', trigger: 'change' }],
              tags: [{ required: true, message: '请选择选项', trigger: 'change' }],
              sortIndex: [{ required: true, message: '请输入排序号', trigger: 'change' }],
            }
          }
        }
      },
      data() {
        return {
          form: {
            type: 0,
          },
          show: false,
          rules: {
            type: [{ required: true, message: '请选择类型', trigger: 'change' }],
            title: [{ required: true, message: '请输入题目', trigger: 'change' }],
            serialNumberType: [{ required: true, message: '请选项序号', trigger: 'change' }],
            tags: [{ required: true, message: '请选择选项', trigger: 'change' }],
            sortIndex: [{ required: true, message: '请输入排序号', trigger: 'change' }],
          },
          serialNumberOption: [
            {label: "1.... 2.... 3.....", value: 0},
            {label: "(1)..... (2)..... (3).....", value: 1},
            {label: "A...... B...... C......", value: 2},
            {label: "a...... b...... c......", value: 3},
            {label: "●...... ●...... ●......", value: 4},
          ],
          showTemplate: false,
          data: [],
          height: 0,
        }
      },
      mounted() {
        this.height = document.body.offsetHeight - 80;
      },
      methods: {
        getContent(data, index, value) {
          let indexValue = "";
          if (data.serialNumberType === 0) {
            indexValue =  index + 1 + "";
          }
          if (data.serialNumberType === 1) {
            indexValue = `（${index + 1}）`;
          }
          if (data.serialNumberType === 2) {
            indexValue = String.fromCharCode(65 + index);
          }
          if (data.serialNumberType === 3) {
            indexValue = String.fromCharCode(97 + index);
          }
          if (data.serialNumberType === 4) {
            indexValue = "●";
          }
          return indexValue + ". " + value;
        },
        clickSuccess() {
          if (this.data.length === 0) {
            this.$message.warning("请添加至少一条题目");
            return;
          }
          this.$emit("success", this.data);
          this.show = false;
        },
        handlerDelete(row) {
          this.$confirm("确定将选择数据删除?", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            let index = this.data.map(item => item.id).indexOf(row.id);
            this.data.splice(index, 1);
          });
        },
        handlerEdit(row) {
          this.form = JSON.parse(JSON.stringify(row));
          this.show = true;
        },
        handlerSelectTemplate(row) {
          let id = this.form.id;
          let sortIndex = this.form.sortIndex;
          this.form = JSON.parse(JSON.stringify(row));
          this.form.id = id;
          this.$set(this.form, 'sortIndex', sortIndex);
          this.showTemplate = false;
        },
        clickLoadTemplate() {
          this.showTemplate = true;
        },
        handlerClose() {
          this.$emit("close");
        },
        clickSave() {
          this.show = true;
          this.form = {type: 0};
        },
        handlerSave() {
          this.$refs['form'].validate((valid) => {
            if (!valid) {
              return;
            }
            if (this.form.id) {
              let index = this.data.map(item => item.id).indexOf(this.form.id);
              this.data.splice(index, 1, this.form);
            }else {
              let form = JSON.parse(JSON.stringify(this.form));
              form.id = func.guid();
              this.data.push(form);
            }
            this.data.sort((x, y) => parseInt(x.sortIndex) > parseInt(y.sortIndex) ? 1 : -1);
            this.form = {};
            this.show = false;
          });
        }
      },
      created() {
        if (!func.isEmpty(this.id)) {
          PaperTopic.list(this.id).then(res => {
            let data = res.data.data;
            this.data = data || [];
          })
        }
        this.$set(this.form, 'type', 0);

      }
    }
</script>

<style scoped>
#paper-topic-manager .close:hover {
  color: #1e9fff;
}
#paper-topic-manager  .select-tag {
  background: #EBEEF5;
}
#paper-topic-manager  .select-tag:hover {
  background: #1e9fff;
}
</style>
