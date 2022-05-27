<template>
  <div class="">
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-share"></i> 历史记录
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div v-for="serviceHistory in serviceHistories" style="margin-bottom: 30px">
        <el-card class="box-card">
          <div class="history-box-title">
            <span class="history-target">[{{ serviceHistory.info.target }}]</span>&nbsp;
            日期:<span class="history-date-time">{{ serviceHistory.info.dateTime }}</span>
            执行是否成功:<span>{{ serviceHistory.info.invokeResult ? '成功' : '失败' }}</span>&nbsp;
            执行方法:<span>{{ serviceHistory.info.method }}</span>&nbsp;
          </div>
          <json-viewer :value="serviceHistory.result" :expand-depth="2" copyable boxed sort></json-viewer>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {get} from "../api";
import {ServiceHistory} from "../entity/ServiceHistory";

// service 历史记录
const serviceHistories = ref<ServiceHistory[]>([]);

// 获取 service 历史记录
get('/api/history/service/get/all', {}, data => {
  serviceHistories.value = data;
  for (let serviceHistory of serviceHistories.value) {
    serviceHistory.info = JSON.parse(serviceHistory.info.toString());
    serviceHistory.result = JSON.parse(serviceHistory.result.toString());
  }
})
</script>

<style>
.history-box-title {
  height: 30px;
  line-height: 30px;
  padding: 5px;
  margin-bottom: 10px
}
.history-target {
  font-size: 20px;
  color: #13ce66;
}
.history-date-time {
  color: #2d8cf0;
}
</style>