<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">{{$t("login.title")}}</div>
      <el-form :model="param" :rules="rules" ref="loginInfo" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="param.username" :placeholder="$t('login.placeholder.username')">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" :placeholder="$t('login.placeholder.password')" v-model="param.password" @keyup.enter="submitForm" show-password>
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">{{$t("login.submit")}}</el-button>
        </div>
        <p class="login-tips">&copy; Yiur admin-system
          <span class="to-it-right" @click="toggleI18nLocal($i18n, 'login')">
            <i style="font-size: 20px; color: white">
              <svg width="1.2em" height="1.2em" preserveAspectRatio="xMidYMid meet" viewBox="0 0 24 24" data-v-1e752d22=""><path d="M18.5 10l4.4 11h-2.155l-1.201-3h-4.09l-1.199 3h-2.154L16.5 10h2zM10 2v2h6v2h-1.968a18.222 18.222 0 0 1-3.62 6.301a14.864 14.864 0 0 0 2.336 1.707l-.751 1.878A17.015 17.015 0 0 1 9 13.725a16.676 16.676 0 0 1-6.201 3.548l-.536-1.929a14.7 14.7 0 0 0 5.327-3.042A18.078 18.078 0 0 1 4.767 8h2.24A16.032 16.032 0 0 0 9 10.877a16.165 16.165 0 0 0 2.91-4.876L2 6V4h6V2h2zm7.5 10.885L16.253 16h2.492L17.5 12.885z" fill="currentColor"></path></svg>
            </i>
          </span>
        </p>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive, computed} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {toggleI18nLocal, login, post} from "../api";
import {useI18n} from "vue-i18n";
import {User} from "../entity/User";
import {createWebSocket} from "../plugins/websocket";

// 国际化
const i18n = useI18n();

const router = useRouter();
const param = reactive({
  username: "",
  password: "",
});

const rules = computed(() => {
  return {
    username: [
      {required: true, message: i18n.t("login.rules.username"), trigger: "blur"},
    ],
    password: [
      {required: true, message: i18n.t("login.rules.password"), trigger: "blur"},
    ]
  };
});

const loginInfo = ref(null);
const submitForm = () => {
  loginInfo.value.validate((valid) => {
    if (!valid) {
      ElMessage.error(i18n.t("login.info.validError"));
      return false;
    } else {
      post('/api/login', param, (resp: User) => {
        if (resp.username === undefined) {
          ElMessage.error(i18n.t("login.info.error"));
        } else {
          createWebSocket(resp.username);
          sessionStorage.setItem(User.key, JSON.stringify(resp));
          ElMessage.success(i18n.t("login.info.success"));
          router.push("/dashboard");
        }
      }, () => ElMessage.error(i18n.t("login.info.error")) );
    }
  });
};

const store = useStore();
store.commit("clearTags");
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/login-bg.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}

.to-it-right {
  cursor: pointer;
  float: right
}
</style>