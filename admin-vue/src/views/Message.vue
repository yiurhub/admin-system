<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 表单
        </el-breadcrumb-item>
        <el-breadcrumb-item>消息发送</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="form-box">
        <el-form ref="formInfo" :rules="rules" :model="message" label-width="80px">
          <el-form-item label="消息标题" prop="title">
            <el-input v-model="message.title"></el-input>
          </el-form-item>
          <el-form-item label="消息目标" prop="target">
            <el-input v-model="message.target"></el-input>
          </el-form-item>
          <el-form-item label="提示等级" prop="level">
            <el-select v-model="message.level" placeholder="请选择">
              <el-option key="system" label="系统消息" value="1"></el-option>
              <el-option key="user" label="用户消息" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间选择">
            <el-col :span="10">
              <el-form-item prop="sendDate">
                <el-date-picker type="datetime"
                                :format="dateFormat"
                                :disabled="sendDisabled"
                                @change="sendDateVerify"
                                placeholder="发送时间"
                                v-model="message.sendDate"
                                style="width: 100%;">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col class="line" :span="4">-</el-col>
            <el-col :span="10">
              <el-form-item prop="validDate">
                <el-date-picker type="datetime"
                                :format="dateFormat"
                                :disabled="validDisabled"
                                @change="validDateVerify"
                                placeholder="有效时间"
                                v-model="message.validDate"
                                style="width: 100%;">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="消息内容" prop="content">
            <v-md-editor v-model="message.content" :disabled-menus="[]" @upload-image="handleUploadImage" height="450px"></v-md-editor>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" :disabled="sendMessageButton">发送消息</el-button>
            <el-button @click="onReset">重置表单</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref, computed} from "vue";
import {ElMessage} from "element-plus";
import {http, post} from "../api";
import moment from "moment";
import {Message} from "../entity/Message";
import axios from "axios";

const rules = {
  title: [
    { required: true, message: "请输入消息标题", trigger: "blur" },
  ],
  target: [
    { required: true, message: "请输入消息目标", trigger: "blur" },
  ],
  level: [
    { required: true, message: "请选择消息等级", trigger: "blur" },
  ],
  content: [
    { required: true, message: "请输入消息内容", trigger: "blur" },
  ],
  sendDate: [
    { required: true, message: "请选择消息发送时间", trigger: "blur" },
  ],
  validDate: [
    { required: true, message: "请选择消息有效时间", trigger: "blur" },
  ]
};

const formInfo = ref(null);
const message = reactive<Message>(new Message("", "", "", "", null, null));
const sendMessageButton = ref(false);
// 时间格式化
const dateFormat = ref("YYYY-MM-DD HH:mm:ss");
// 时间设置
const dataTime = { sendDate: 5, validDate: 30 }
const sendDisabled = computed(() => {
  return message.sendDate !== null;
});
const validDisabled = computed(() => {
  return message.sendDate === null;
});
// 设置默认发生时间
const defaultSendDate = () => {
  return new Date(new Date().getTime() + ( 1000 * 60 * 5 ))
}
// 发送时间验证
const sendDateVerify = (value) => {
  if (!(value.getTime() <= new Date().getTime() + (1000 * 60 * dataTime.sendDate))) {
    message.validDate = new Date(value.getTime() + (1000 * 60 * dataTime.validDate));
  } else {
    message.sendDate = new Date(new Date().getTime() + (1000 * 60 * dataTime.sendDate));
    message.validDate = new Date(message.sendDate.getTime() + (1000 * 60 * dataTime.validDate));
  }
}
// 有效时间验证
const validDateVerify = (value) => {
  if (value.getTime() <= message.sendDate.getTime() + (1000 * 60 * dataTime.validDate)) {
    ElMessage.warning(`有效时间不能小于发送时间且最少大于发送时间${dataTime.validDate}分钟`);
    message.validDate = null;
  }
}
//检测发生时间
const detectSendDate = () => {
  if (message.sendDate.getTime() <= new Date().getTime()) {
    onReset();
    ElMessage.error("发生时间已经失效请重新输入!");
    return false;
  }
  return true;
}

// 提交数据
const onSubmit = () => {
  // 表单校验
  formInfo.value.validate((valid) => {
    if (!valid && detectSendDate()) {
      ElMessage.error("请按照正确格式填写!");
      return false;
    } else {
      sendMessageButton.value = true;
      message.sendDate = moment(message.sendDate).format(dateFormat.value);
      message.validDate = moment(message.validDate).format(dateFormat.value);
      http({
        url: '/api/msg/send',
        method: 'put',
        data: message
      }).then((resp) => {
        if (resp !== 1) {
          ElMessage.error("发送失败!");
        } else {
          onReset();
          ElMessage.success("发送成功!");
        }
        sendMessageButton.value = false;
      }).catch(() => ElMessage.error("服务器异常!"));
    }
  });
};

// 上传图片
const handleUploadImage = (event, insertImage, files) => {
  let uploadFormData = new FormData()
  uploadFormData.append("file", files[0]);
  axios({
    url: '/api/upload/image',
    method: 'post',
    data: uploadFormData,
    headers: {
      "Accept": "*/*",
      "Content-Type": "multipart/form-entity"
    }
  }).then((filename: any) => {
    insertImage({
      url: `http://${window.location.host}/api/res/get/image/${filename.data}`,
      desc: `http://${window.location.host}/api/res/get/image/${filename.data}`,
      width: 'auto',
      height: 'auto'
    })
  });
}

// 重置表单
const onReset = () => {
  formInfo.value.resetFields();
}
</script>

<style>
.v-md-editor {
  min-width: 1200px;
}
</style>