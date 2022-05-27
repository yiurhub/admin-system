<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-calendar"></i> 表单
        </el-breadcrumb-item>
        <el-breadcrumb-item>图片上传</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-upload class="upload-demo" action="/api/upload/image" accept=".jpg,.jpeg,.png"
                 :before-upload="beforeUpload"
                 :on-success="successUpload"
                 :on-error="errorUpload"
                 drag multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip" style="width: 380px; text-align: center">
            只能上传 jpg/png 文件，且不超过 5MB
          </div>
        </template>
      </el-upload>
    </div>
  </div>
</template>

<script lang="ts" setup>
import "cropperjs/dist/cropper.css";
import {ElMessage} from "element-plus";
import {reload} from "../api";

// 文件上传前约束
const beforeUpload = (file) => {
  if ((/((jpg)|(jpeg)|(png))/.exec(file.type) === null || file.size > 1024 * 5000)) {
    ElMessage.error("只能上传 jpg/png 文件，且不超过 5MB");
    return false;
  }
}

// 文件上传成功回调
const successUpload = (resp, file, fileList) => {
  ElMessage.success(`文件${resp}上传成功`);
}

// 文件上传错误
const errorUpload = (error) => {
  reload();
}
</script>

<style scoped>
.upload-demo {
  position: relative;
  width: 380px;
  left: 435px;
  top: 150px;
}
</style>