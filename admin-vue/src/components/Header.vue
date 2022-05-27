<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChange">
      <i v-if="!collapse" class="el-icon-s-fold"></i>
      <i v-else class="el-icon-s-unfold"></i>
    </div>
    <div class="logo">{{$t('header.title')}}</div>
    <div class="header-right">
      <div class="header-user-con">
        <!--翻译-->
        <span style="margin-right: 8px" @click="toggleI18nLocal($i18n, 'dashboard')">
          <i style="font-size: 20px; color: white">
            <svg width="1.2em" height="1.2em" preserveAspectRatio="xMidYMid meet" viewBox="0 0 24 24" data-v-1e752d22=""><path d="M18.5 10l4.4 11h-2.155l-1.201-3h-4.09l-1.199 3h-2.154L16.5 10h2zM10 2v2h6v2h-1.968a18.222 18.222 0 0 1-3.62 6.301a14.864 14.864 0 0 0 2.336 1.707l-.751 1.878A17.015 17.015 0 0 1 9 13.725a16.676 16.676 0 0 1-6.201 3.548l-.536-1.929a14.7 14.7 0 0 0 5.327-3.042A18.078 18.078 0 0 1 4.767 8h2.24A16.032 16.032 0 0 0 9 10.877a16.165 16.165 0 0 0 2.91-4.876L2 6V4h6V2h2zm7.5 10.885L16.253 16h2.492L17.5 12.885z" fill="currentColor"></path></svg>
          </i>
        </span>
        <!-- 消息中心 -->
        <div class="btn-bell">
          <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom">
            <router-link to="/mail">
              <i class="el-icon-bell"></i>
            </router-link>
          </el-tooltip>
          <span class="btn-bell-badge" v-if="message"></span>
        </div>
        <!-- 用户头像 -->
        <div class="user-avator">
          <router-link to="/user"><el-image :src="`/api/res/get/image/${ user.face }`" style="width: 40px; height: 40px; border-radius: 50%" /></router-link>
        </div>
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        {{ username }}
                        <i class="el-icon-caret-bottom"></i>
                    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="user">个人中心</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, nextTick, ref} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {toggleI18nLocal, reload, logout, addWebsocket} from "../api";
import {User} from "../entity/User";

// 当前登陆用户
const user = JSON.parse(sessionStorage.getItem(User.key));
const username = user.name;
const message = ref(0);

// 接收邮件
addWebsocket("NEW_MAIL", "header-new_mail", (data) => {
  message.value++;
});

const store = useStore();
const collapse = computed(() => store.state.collapse);
// 侧边栏折叠
const collapseChange = () => {
  store.commit("handleCollapse", !collapse.value);
};

nextTick(() => {
  reload();
});

onMounted(() => {
  if (document.body.clientWidth < 1500) {
    collapseChange();
  }
});

// 用户名下拉菜单选择事件
const router = useRouter();

// command事件
const handleCommand = (command) => {
  if (command === "user") {
    router.push(`/user`);
  }
  if (command === "logout") {
    logout();
  }
};
</script>

<style scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #fff;
}

.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}

.header .logo {
  float: left;
  min-width: 250px;
  line-height: 70px;
}

.header-right {
  float: right;
  padding-right: 50px;
}

.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}

.btn-fullscreen {
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}

.btn-bell,
.btn-fullscreen {
  position: relative;
  width: 30px;
  height: 30px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
}

.btn-bell-badge {
  position: absolute;
  right: 0;
  top: -2px;
  width: 8px;
  height: 8px;
  border-radius: 4px;
  background: #f56c6c;
  color: #fff;
}

.btn-bell .el-icon-bell {
  color: #fff;
}

.user-name {
  margin-left: 10px;
}

.user-avator {
  margin-left: 20px;
  margin-right: 8px  ;
}

.user-avator img {
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.el-dropdown-link {
  color: #fff;
  cursor: pointer;
}

.el-dropdown-menu__item {
  text-align: center;
}
</style>
