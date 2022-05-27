<template>
  <div class="about">
    <v-header/>
    <v-sidebar/>
    <div class="content-box" :class="{ 'content-collapse': collapse }">
      <v-tags></v-tags>
      <div class="content">
        <router-view v-slot="{ Component }">
          <transition name="move" mode="out-in">
            <keep-alive :include="tagsList">
              <component :is="Component"/>
            </keep-alive>
          </transition>
        </router-view>
        <el-backtop target=".content"></el-backtop>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import {computed} from "vue";
import {useStore} from "vuex";
import vHeader from "../components/Header.vue";
import vSidebar from "../components/Sidebar.vue";
import vTags from "../components/Tags.vue";
import {createWebSocket, websocket} from "../plugins/websocket";
import {reload, logout, addWebsocket, websocketCallback} from "../api";
import {User} from "../entity/User";

// 当前登陆用户
const user: User = JSON.parse(sessionStorage.getItem(User.key));

// 连接websocket
if (websocket === undefined || websocket === null) {
  createWebSocket(user.username);
}

// 是否登陆
websocket.onclose = () => {
  reload();
}

// 是否登陆
websocket.onerror = () => {
  reload();
}

// 强制下线
addWebsocket("RELOAD",  "home-reload", (data) => {
  logout("你已经被管理员强制下线");
});

// 接收消息
websocket.onmessage = (data) => {
  for (let invokeMapKey of websocketCallback.keys()) {
    if (data.data === invokeMapKey) {
      let invokeMap: Map<string, Function> = websocketCallback.get(invokeMapKey);
      invokeMap.forEach((callback: Function) => {
        callback(data);
      });
    }
  }
};

const store = useStore();

const tagsList = computed(() =>
    store.state.tagsList.map((item) => item.name)
);

const collapse = computed(() => store.state.collapse);
</script>
