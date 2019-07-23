<template>
  <el-container style="height: 100%;">
    <el-header>
      <el-menu :default-active="activeIndex" mode="horizontal"><el-menu-item index="1" style="font-size: 20px;">改制企业档案查询系统</el-menu-item></el-menu>
    </el-header>
    <el-main style="background:#F2F2F2;height:100%">
      <el-card style="height:99.5%">
        <div slot="header">
          <span>单位名称 <el-input v-model="params.corpName" placeholder="请输入单位名称" style="width:175px; padding-right: 15px" @keypress.enter.native="getData"></el-input></span>
          <span>日期范围
              <el-input v-model="params.startDate" v-on:keypress.enter.native="getData" placeholder="请输入开始日期yyMMdd" style="width:185px;"></el-input>
              -
              <el-input v-model="params.endDate" @keypress.enter.native="getData" placeholder="请输入结束日期yyMMdd" style="width:185px; padding-right: 15px"></el-input>
              <el-button plain @click="resetData" icon="el-icon-refresh-right"></el-button>
          </span>

          <span style="float:right">
            <el-button plain type="primary" icon="el-icon-search" @click="getData">查询</el-button>
            <el-upload  :disabled="loading" action="/upload" name="file" style="display: inline" :before-upload="beforeUpload" :on-success="uploadSuccess" :on-error="uploadError">
              <el-button plain icon="el-icon-download" :loading="loading">导入文件</el-button>
            </el-upload>

          </span>
        </div>
        <el-table v-loading="loading"
                :height="tableHeight"
                :data="tableData"
                border
                stripe
                fit
                style="width: 100%">
          <el-table-column
                  prop="voucherSn"
                  label="序号"
                  width="60">
          </el-table-column>
          <el-table-column
                  prop="archiveCode"
                  label="档案编号"
                  width="120">
          </el-table-column>
          <el-table-column
                  prop="corpName"
                  label="单位名称">
          </el-table-column>
          <el-table-column
                  prop="voucherType"
                  label="类别"
                  width="120">
          </el-table-column>
          <el-table-column
                  prop="voucherTitle"
                  label="题名"
                  width="180">
          </el-table-column>
          <el-table-column
                  prop="voucherDate"
                  label="起止时间"
                  width="200">
          </el-table-column>
          <el-table-column
                  prop="storeLimit"
                  label="保管期限"
                  width="80">
          </el-table-column>
          <el-table-column
                  prop="remark"
                  label="备注"
                  width="220">
          </el-table-column>
        </el-table>
        <el-pagination :page-size=params.limit
                       :pager-count="5"
                       layout="prev, pager, next"
                       :page-count=pageCount
                       style="text-align: center"
                       :hide-on-single-page=hosp
                       @current-change="changePage">

        </el-pagination>

      </el-card>
    </el-main>
  </el-container>
</template>

<script>
  import {MessageBox} from 'element-ui'

export default {
  name: 'App',
  data() {
    return {
      activeIndex: '1',
      tableData: [],
      params: {page: 1, limit: 10, corpName: '', startDate: '', endDate: ''},
      pageCount: 0,
      loading : false,
      errorData:[
       {"archiveCode":"000000-000","archiveId":0,"collateDate":"","collator":"","corpName":"数据获取错误","detailId":0,"endDate":"","remark":"","startDate":"","storeLimit":0,"voucherDate":"0-0","voucherSn":0,"voucherTitle":"-","voucherType":"-"}
      ],
      fileName: '',
      hosp: true
    }
  },
  methods: {
    getData: function () {
      this.loading=true;
      this.$http.get('selectAll', {params: this.params}).then(res=>{
        this.loading=false;
        this.tableData = res.data.dataList;
        this.pageCount = res.data.pageCount;
          console.log(JSON.stringify(res))
      }).catch(()=>{this.loading=false;this.tableData = this.errorData;});
    },

    resetData: function() {
      this.params.corpName='';
      this.params.startDate=''
      this.params.endDate='';
      this.tableData= [];
      this.pageCount = 0;
    },

    changePage: function(currentPage) {
      console.log(currentPage);
      this.params.page=currentPage;
      this.getData();
    },
    beforeUpload: function(file) {
      this.loading = true;
    },
    uploadSuccess: function(response, file, fileList) {
      this.loading = false;
      console.log(response);
      MessageBox.alert(response.msg,{confirmButtonText: '确定'});
    },
    uploadError: function(err, file, fileList) {
      this.loading = false;
      console.log(err)
      MessageBox.alert('文件上传错误，请检查上传的文件是否正确', {confirmButtonText: '确定'});
    },

    test: function() {
      this.$http.get('test');
    },

  },
  created() {
    //MessageBox.alert('ok')
    //MessageBox.alert('文件导入成功',{confirmButtonText: '确定'});
  },

  computed: {
    tableHeight: function() {
      let headerHeight = 100;
      let cardHeaderHeitht = 90;
      let pagnationHeiht=36;
      let padding = 20*2;
      return (document.documentElement.clientHeight || document.body.clientHeight) - headerHeight - cardHeaderHeitht - pagnationHeiht - padding
    }
  }
}
</script>

<style scoped>

</style>
