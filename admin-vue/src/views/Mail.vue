<template>
  <div class="">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-copy"></i> 用户邮件
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <el-tabs v-model="message">
        <el-tab-pane :label="`未读消息(${unread.length})`" name="first">
          <el-table :data="unread" :show-header="false" style="width: 100%">
            <el-table-column>
              <template #default="scope">
                <span class="message-title" @click="openMail(scope.row.message)">{{scope.row.message.title}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="message.sendDate" width="180"></el-table-column>
            <el-table-column width="120">
              <template #default="scope">
                <el-button size="small" @click="setMailHaveRead(scope.row.mailId)">标为已读</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="handle-row">
            <el-button type="primary">全部标为已读</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane :label="`已读消息(${haveRead.length})`" name="second">
          <el-table :data="haveRead" :show-header="false" style="width: 100%">
            <el-table-column>
              <template #default="scope">
                <span class="message-title" @click="openMail(scope.row.message)">{{scope.row.message.title}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="message.sendDate" width="180"></el-table-column>
            <el-table-column width="120">
              <template #default="scope">
                <el-button size="small" type="danger" @click="setMailDel(scope.row.mailId)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="handle-row">
            <el-button type="danger">删除全部</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane :label="`回收站(${reclaim.length})`" name="third">
          <el-table :data="reclaim" :show-header="false" style="width: 100%">
            <el-table-column>
              <template #default="scope">
                <span class="message-title" @click="openMail(scope.row.message)">{{scope.row.message.title}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="message.sendDate" width="180"></el-table-column>
            <el-table-column width="180">
              <template #default="scope">
                <el-button size="small" type="danger" @click="confirmDeleteMessage(scope.row.mailId)">删除</el-button>
                <el-button size="small" @click="setMailReclaim(scope.row.mailId)">还原</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="handle-row">
            <el-button type="danger" @click="emptyRecycleBin(reclaim)">清空回收站</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-card v-if="dialogVisible" class="box-card">
      <template #header>
        <div class="card-header">
          <span>{{ currentMessage.title }}</span>
          <el-button class="button" type="text" @click="dialogVisible = false">关闭</el-button>
        </div>
      </template>
      <v-md-preview :text="currentMessage.content"/>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive} from "vue";
import {http, confirm, addWebsocket} from "../api";
import {ElMessage} from "element-plus";
import {MailState} from "../entity/MailState";
import {User} from "../entity/User";
import {Message} from "../entity/Message";

// 当前用户信息
const user: User = JSON.parse(sessionStorage.getItem(User.key));

// 邮件详细内容
const dialogVisible = ref(false);
const currentMessage = ref<Message>();

// 邮件消息
const message = ref("first");
const mailList = ref({});
const unread = reactive([]);
const haveRead = reactive([]);
const reclaim = reactive([]);

// 接收邮件
addWebsocket("NEW_MAIL", "mail-new_mail", (data) => {
  getData();
  ElMessage.success("你有一封新邮件");
});

// 获取表格数据
const getData = () => {
  http({
    url: `/api/mail/get/${user.uid}`,
    method: 'get'
  }).then((resp) => {
    mailList.value = resp;
    parseMail(mailList.value);
  });
};
getData();

// 已读邮件
const setMailHaveRead = (id) => {
  setMailState(id, MailState.HAVE_READ).then((resp) => {
    if (resp !== 1) {
      ElMessage.error("阅读失败");
    } else {
      getData();
      ElMessage.success("已阅读");
    }
  });
};

// 删除邮件
const setMailDel = (id) => {
  confirm("警告", '确认删除码?', () => {
    setMailState(id, MailState.RECOVER).then((resp) => {
      if (resp !== 1) {
        ElMessage.error("删除失败");
      } else {
        getData();
        ElMessage.success("删除成功");
      }
    });
  });
};

// 回收邮件
const setMailReclaim = (id) => {
  setMailState(id, MailState.HAVE_READ).then((resp) => {
    if (resp !== 1) {
      ElMessage.error("回收失败");
    } else {
      getData();
      ElMessage.success("回收成功");
    }
  });
};

// 确认删除
const confirmDeleteMessage = (id) => {
  confirm("警告", '确认删除码?', () => {
    deleteMail('/api/mail/delete', id).then((resp) => {
      if (resp !== 1) {
        ElMessage.error("删除失败");
      } else {
        ElMessage.success("删除成功");
        getData();
      }
    });
  });
}

// 清空回收站
const emptyRecycleBin = (list) => {
  confirm("警告", '确认全部删除码?', () => {
    deleteMailList('/api/mail/delete/list', list).then((resp) => {
      if (resp !== 1) {
        ElMessage.error("删除全部失败");
      } else {
        ElMessage.success("删除全部成功");
        getData();
      }
    });
  });
}

// 删除邮件
const deleteMail = (url, data) => {
  return http({
    url: `${url}/${data}`,
    method: 'get'
  });
}
const deleteMailList = (url, data) => {
  return http({
    url: url,
    method: 'post',
    data: data
  });
}

// 解析邮件
const parseMail = (values) => {
  unread.length = 0;haveRead.length = 0;reclaim.length = 0;
  for (let i = 0; i < values.length; i++) {
    if (values[i].state === 0) {
      unread[unread.length] = values[i];
    }
    if (values[i].state === 1) {
      haveRead[haveRead.length] = values[i];
    }
    if (values[i].state === 2) {
      reclaim[reclaim.length] = values[i];
    }
  }
}

// 设置邮件状态
const setMailState = (id, state) => {
  return http({
    url: '/api/mail/set/state',
    method: 'post',
    data: {
      mailId: id,
      state: state
    }
  });
}

// 打开邮件
const openMail = (message: Message) => {
  dialogVisible.value = true;
  currentMessage.value = message;
}
</script>

<style>
.message-title {
  cursor: pointer;
}

.handle-row {
  margin-top: 30px;
}

.box-card {
  z-index: 2000;
  position: relative;
  border-radius: 5px;
  border: 1px solid #ddd;
  min-height: 600px;
  top: -602px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>

