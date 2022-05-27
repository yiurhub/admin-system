<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>基础信息</span>
            </div>
          </template>
          <div class="info">
            <div class="info-image" @click="showDialog">
              <img :src="currentFace"/>
              <span class="info-edit">
                <i class="el-icon-lx-camerafill"></i>
              </span>
            </div>
            <div class="info-name">{{ user.name }}</div>
            <div class="info-desc">{{ user.desc }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="clearfix">
              <span>账户编辑</span>
            </div>
          </template>
          <el-form ref="userForm" :rules="rules" :model="form" label-width="100px">
            <el-form-item prop="name" label="名称：">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item prop="password_new" label="密码：">
              <el-input type="password" v-model="form.password_new" show-password></el-input>
            </el-form-item>
            <el-form-item prop="password" label="确认密码：">
              <el-input type="password" v-model="form.password" show-password></el-input>
            </el-form-item>
            <el-form-item prop="address" label="地址：">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item prop="desc" label="个人简介：">
              <el-input v-model="form.desc"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="裁剪图片" v-model="dialogVisible" width="600px">
      <vue-cropper ref="cropper" :src="currentFace" :ready="cropImage" :zoom="cropImage" :cropmove="cropImage"
                   style="width: 100%; height: 400px">
      </vue-cropper>

      <template #footer>
                <span class="dialog-footer">
                    <el-button class="crop-demo-btn" type="primary">选择图片
                        <input class="crop-input" type="file" name="image" accept="image/*" @change="setImage"/>
                    </el-button>
                    <el-button type="primary" @click="saveAvatar">确认编辑</el-button>
                </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from "vue";
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import {http} from "../api";
import {ElMessage} from "element-plus";
import {User} from "../entity/User";
import axios from "axios";

// 当前用户信息
const user: User = JSON.parse(sessionStorage.getItem(User.key));
const currentFace = ref(`/api/res/get/image/${user.face}`);
const currentFaceFile = ref(null);

// 编辑验证
const rules = {
  name: [
    { required: true, message: "请输入名称", trigger: "blur" }
  ],
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" }
  ],
  password_new: [
    { required: true, message: "请输入密码", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (value === user.password) {
          callback(new Error("你输入的密码与旧一致"));
        } else {
          callback();
        }
      },
      trigger: "blur"
    }
  ],
  password: [
    { required: true, message: "确认密码不能为空", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (value !== form.password_new) {
          callback(new Error("你输入的密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur"
    }
  ],
  address: [
    { required: true, message: "请输入地址", trigger: "blur" }
  ],
  desc: [
    { required: true, message: "请输入个人简介", trigger: "blur" }
  ]
};

// 表单
const userForm = ref(null);
const form = reactive({
  name: user.name,
  face: user.face,
  password_new: '',
  password: '',
  address: user.address,
  desc: user.desc,
});

// 提交数据
const onSubmit = () => {
  // 表单校验
  userForm.value.validate((valid) => {
    // 上传图片
    if (!valid) {
      ElMessage.error("请按照正确格式填写!");
      return false;
    } else {
      if (currentFaceFile.value === null) {
        editUser();
      } else {
        let uploadFormData = new FormData()
        uploadFormData.append("file", currentFaceFile.value);
        axios({
          url: '/api/upload/image',
          method: 'post',
          data: uploadFormData,
          headers: {
            "Accept": "*/*",
            "Content-Type": "multipart/form-entity"
          }
        }).then((filename: any) => {
          form.face = filename.data;
          editUser();
        });
      }
    }
  });
};

// 修改用户
const editUser = () => {
  http({
    url: '/api/user/set/user',
    method: 'post',
    data: {
      uid: user.uid,
      face: form.face,
      name: form.name,
      username: user.username,
      password: form.password,
      address: form.address,
      desc: form.desc
    }
  }).then((resp) => {
    if (resp === 1) {
      userForm.value.resetFields();
      ElMessage.success("修改成功");
    }
  });
};

const dialogVisible = ref(false);
const cropper = ref(null);

const showDialog = () => {
  dialogVisible.value = true;
};

const setImage = (e) => {
  const file = e.target.files[0];
  if (!file.type.includes("image/")) {
    return;
  }
  const reader = new FileReader();
  reader.onload = (event: ProgressEvent<any>) => {
    dialogVisible.value = true;
    currentFace.value = event.target.result;
    cropper.value && cropper.value.replace(event.target.result);
  };
  reader.readAsDataURL(file);
};

const cropImage = () => {
  currentFace.value = cropper.value.getCroppedCanvas().toDataURL();
  cropper.value.getCroppedCanvas().toBlob((data) => {
    currentFaceFile.value = data;
  });
};

const saveAvatar = () => {
  dialogVisible.value = false;
};
</script>

<style scoped>
.info {
  text-align: center;
  padding: 35px 0;
}

.info-image {
  position: relative;
  margin: auto;
  width: 100px;
  height: 100px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50px;
  overflow: hidden;
}

.info-image img {
  width: 100%;
  height: 100%;
}

.info-edit {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.info-edit i {
  color: #eee;
  font-size: 25px;
}

.info-image:hover .info-edit {
  opacity: 1;
}

.info-name {
  margin: 15px 0 10px;
  font-size: 24px;
  font-weight: 500;
  color: #262626;
}

.crop-demo-btn {
  position: relative;
}

.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
</style>