<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 用户列表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-select v-model="undefined" placeholder="权限" class="handle-select mr10">
          <el-option v-for="perm in perms" :label="perm.label" :value="perm.value"></el-option>
        </el-select>
        <el-input v-model="page.pageLike" placeholder="用户名" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
        <el-button style="float: right" type="primary" icon="el-icon-lx-add" @click="handleAdd">添加</el-button>
      </div>
      <el-table :data="users" border class="table" header-cell-class-name="table-header">
        <el-table-column prop="uid" label="UID" width="55" align="center"></el-table-column>
        <el-table-column prop="name" width="80" label="名称"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column label="用户等级">
          <template #default="scope">{{ scope.row.perms }}</template>
        </el-table-column>
        <el-table-column label="头像(查看大图)" align="center">
          <template #default="scope">
            <el-image class="table-td-thumb"
                      :src="`/api/res/get/image/${scope.row.face}`"
                      :preview-src-list="[ `/api/res/get/image/${scope.row.face}` ]"/>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="online" label="在线状态">
          <template #default="scope">
            <el-tooltip
                class="box-item"
                effect="dark"
                content="踢下线!"
                placement="top">
              <el-button @click="forcedOffline(scope.row.username)" :disabled="!scope.row.online">{{ scope.row.online }}</el-button>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="registerDate" label="注册时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="text" icon="el-icon-edit"
                       @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" class="red"
                       @click="handleDelete(scope.$index, scope.row)" :disabled="!scope.row.perms.indexOf('root')">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination background
                       layout="total, prev, pager, next"
                       :current-page="page.pageIndex"
                       :page-size="page.pageCount"
                       :total="page.pageTotal"
                       @current-change="handlePageChange">
        </el-pagination>
      </div>
    </div>

    <!-- 添加弹出框 -->
    <el-dialog title="添加" v-model="addVisible" width="40%" @close="closeAdd">
      <el-form ref="addFormInfo" :rules="rules" :model="addForm" label-width="120px">
        <el-form-item prop="name" label="名称：">
          <el-input v-model="addForm.name" />
        </el-form-item>
        <el-form-item prop="perms" label="用户权限：">
          <el-tag
              v-for="tag in addForm.perms.split(',')"
              :key="tag"
              class="mx-1"
              closable
              :disable-transitions="false"
              @close="handleClose(tag, addForm)"
          >
            {{ tag }}
          </el-tag>
          <el-cascader :options="perms"
                       v-if="inputVisible"
                       ref="InputRef"
                       v-model="inputValue"
                       class="ml-1 w-20"
                       size="small"
                       filterable
                       @change="handleInputConfirm(addForm)"
          />
          <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput">
            + 添加权限
          </el-button>
        </el-form-item>
        <el-form-item prop="username" label="用户名：">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item prop="password_new" label="密码：">
          <el-input type="password" v-model="addForm.password_new" show-password></el-input>
        </el-form-item>
        <el-form-item prop="password" label="确认密码：">
          <el-input type="password" v-model="addForm.password" show-password></el-input>
        </el-form-item>
        <el-form-item prop="address" label="地址：">
          <el-input v-model="addForm.address"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="closeAdd">取 消</el-button>
                    <el-button type="primary" @click="addUser">添 加</el-button>
                </span>
      </template>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="40%" @close="closeEdit">
      <el-form ref="editFormInfo" :model="editForm" label-width="120px">
        <el-form-item prop="name" label="名称:">
          <label>{{ editForm.name }}</label>
        </el-form-item>
        <el-form-item prop="perms" label="用户权限:">
          <el-tag
              v-for="tag in editForm.perms.split(',')"
              :key="tag"
              class="mx-1"
              closable
              :disable-transitions="false"
              @close="handleClose(tag, editForm)"
          >
            {{ tag }}
          </el-tag>
          <el-cascader :options="perms"
                       v-if="inputVisible"
                       ref="InputRef"
                       v-model="inputValue"
                       class="ml-1 w-20"
                       size="small"
                       filterable
                       @change="handleInputConfirm(editForm)"
          />
          <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput">
            + 添加权限
          </el-button>
        </el-form-item>
        <el-form-item prop="password" label="用户密码:">
          <el-col :span="10">
            <el-input type="password" v-model="editForm.password" show-password></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="closeEdit">取 消</el-button>
                    <el-button type="primary" @click="saveEdit">保 存</el-button>
                </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import {ref, reactive, nextTick} from "vue";
import {ElCascader, ElMessage} from "element-plus";
import {addWebsocket, http, logout} from "../api";
import {confirm} from "../api";
import {User} from "../entity/User";
import {Perms} from "../entity/Perms";
import {Page} from "../entity/Page";
import {useRoute} from "vue-router";

const route = useRoute();

const users: ref<User> = ref();
const perms = ref([]);

// 是否登陆
addWebsocket("PING", "users-ping", () => {
  if (route.path === '/users') {
    getUsers();
  }
});
addWebsocket("CLOSE", "users-close", () => {
  if (route.path === '/users') {
    getUsers();
  }
});

// 强制下线
const forcedOffline = (username) => {
  if (username === 'root') {
    ElMessage.warning("你不能踢自己下线哦!");
    return;
  }
  http({
    url: `/api/logout/${username}`,
    method: 'get'
  }).then((resp: number) => {
    if (resp === 1) {
      ElMessage.success("强制下线成功");
    }
  }).catch((error) => {
    getUsers();
  });
}

// 分页
const page = reactive<Page>(new Page(1, 4, 0, ''));

// 获取分页数据
const getPageData = () => {
  http({
    url: '/api/user/get/page',
    params: {
      pageCount: page.pageCount,
      pageLike: page.pageLike
    },
    method: 'get'
  }).then((resp: Page) => {
    if (resp !== null) {
      page.pageCount = resp.pageCount;
      page.pageTotal = resp.pageTotal;
      getUsers();
    }
  });
};
getPageData();

// 获取表格数据
const getUsers = () => {
  http({
    url: '/api/user/get/page',
    data: {
      pageIndex: page.pageIndex,
      pageCount: page.pageCount,
      pageLike: page.pageLike
    },
    method: 'post'
  }).then((resp: User[]) => {
    users.value = resp;
  });
};

// 获取权限
const getPerms = () => {
  http({
    url: `/api/user/get/perms/all`,
    method: 'get'
  }).then((resp: Perms[]) => {
    if (resp !== null) {
      for (let i in resp) {
        let item = resp[i];
        perms.value.push({ label: item.perm, value: item.perm, disabled: item.perm.indexOf("root") !== -1 });
      }
    }
  });
};
getPerms();

// 查询操作
const handleSearch = () => {
  page.pageIndex = 1;
  getPageData();
};

// 分页导航
const handlePageChange = (index) => {
  page.pageIndex = index;
  getUsers();
};

// 删除用户
const handleDelete = (index: number, row: User) => {
  if (row.perms.indexOf("root") !== -1) {
    ElMessage.error("你无权删除root用户!");
  } else {
    confirm("警告", "确认删除吗?", () => {
      http({
        url: `/api/user/delete/${row.uid}`,
        method: 'get'
      }).then((resp: number) => {
        if (resp !== 1) {
          ElMessage.error("删除失败");
        } else {
          ElMessage.success("删除成功");
          // 用户没有上线则强制消息的刷新被替代
          row.online ? forcedOffline(row.username) : getUsers();
          page.pageTotal--;
        }
      });
    });
  }
};

// 编辑验证
const rules = {
  name: [
    { required: true, message: "请输入名称", trigger: "blur" }
  ],
  perms: [
    { required: true, message: "请添加新的权限", trigger: "blur" }
  ],
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" }
  ],
  password_new: [
    { required: true, message: "请输入密码", trigger: "blur" }
  ],
  password: [
    { required: true, message: "确认密码不能为空", trigger: "blur" },
    {
      validator: (rule, value, callback) => {
        if (value !== addForm.password_new) {
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
  ]
};

// 提交表单
const addForm = reactive({
  name: '',
  perms: 'user',
  username: '',
  password_new: '',
  password: '',
  address: ''
});

const editForm = reactive({
  uid: "",
  name: "",
  perms: "",
  username: '',
  password: ""
});

// 权限
const inputValue = ref('');
const inputVisible = ref(false);
const InputRef = ref<InstanceType<typeof ElCascader>>();
// 显示添加input
const showInput = () => {
  inputVisible.value = true
  nextTick(() => {
    InputRef.value!.input!.focus()
  })
};

// 删除权限
const handleClose = (tag: string, bind: any) => {
  if (tag.indexOf('root') !== -1) {
    ElMessage.error("不能删除root权限");
    return;
  }
  if (bind.perms.indexOf(tag + ',') !== -1) {
    bind.perms = bind.perms.replace(tag + ',', '');
    return;
  }
  if (bind.perms.indexOf(',' + tag) !== -1) {
    bind.perms = bind.perms.replace(',' + tag, '');
    return;
  }
};

// 添加权限
const handleInputConfirm = (bind) => {
  if (bind.perms.indexOf(inputValue.value) !== -1) {
    ElMessage.error("权限已存在");
    return;
  }
  if (inputValue.value) {
    bind.perms += `,${inputValue.value}`;
  }
  inputVisible.value = false
  inputValue.value = ''
};

// 添加窗口
const addVisible = ref(false);
const addFormInfo = ref(null);

// 关闭添加
const closeAdd = () => {
  inputVisible.value = false;
  addVisible.value = false;
};

// 添加用户
const handleAdd = () => {
  addVisible.value = true;
};

// 添加用户
const addUser = () => {
  // 表单校验
  addFormInfo.value.validate((valid) => {
    if (!valid) {
      ElMessage.error("请按照正确格式填写!");
      return false;
    } else {
      http({
        url: '/api/user/add/user',
        data: {
          name: addForm.name,
          perms: addForm.perms,
          username: addForm.username,
          password: addForm.password,
          address: addForm.address
        },
        method: 'post'
      }).then((resp) => {
        if (resp === 1) {
          closeAdd();
          getUsers();
          page.pageTotal++;
          addFormInfo.value.resetFields();
          ElMessage.success("添加成功");
        }
      });
    }
  });
};

// 编辑窗口
const editVisible = ref(false);
const editFormInfo = ref(null);

// 打开编辑
const handleEdit = (index: number, row: User) => {
  Object.keys(editForm).forEach((item) => {
    editForm[item] = row[item];
  });
  editVisible.value = true;
};

// 关闭编辑
const closeEdit = () => {
  inputVisible.value = false;
  editVisible.value = false;
};

// 保存编辑信息
const saveEdit = () => {
  // 表单校验
  editFormInfo.value.validate((valid) => {
    if (!valid) {
      ElMessage.error("请按照正确格式填写!");
      return false;
    } else {
      http({
        url: '/api/user/set/list/user',
        data: editForm,
        method: 'post'
      }).then((resp) => {
        if (resp === 1) {
          getUsers();
          editFormInfo.value.resetFields();
          editVisible.value = false;
          ElMessage.success("修改成功");
        }
      });
    }
  });
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #ff0000;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
