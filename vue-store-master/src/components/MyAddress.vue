<template>
  <div id="addressadd">
    <el-dialog :visible.sync="isAdd" center title="添加地址" width="400px">
      <el-form
          ref="ruleForm"
          :model="address"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item prop="linkman">
          <el-input
              v-model="address.linkman"
              placeholder="请输入联系人!"
              prefix-icon="el-icon-user-solid"
          ></el-input>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input
              v-model="address.phone"
              placeholder="请输入练习电话"
              prefix-icon="el-icon-view"
              type="text"
          ></el-input>
        </el-form-item>
        <el-form-item prop="address">
          <el-input
              v-model="address.address"
              placeholder="请再输入详细地址!"
              prefix-icon="el-icon-view"
              type="text"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button size="medium" style="width:100%;" type="primary" @click="save()">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "address",
  props: ["address"],
  data() {
    return {
      isAdd: false, // 控制注册组件是否显示
      address: {
        linkname: "",
        phone: "",
        address: ""
      }
    },
        watch
  :
    {
      // 监听父组件传过来的register变量，设置this.isRegister的值
      isAdd: function (val) {
        if (val) {
          this.isAdd = val;
        }
      }
    }
  ,
    {
      save()
      {
        // 通过element自定义表单校验规则，校验用户输入的用户信息

        //如果通过校验开始注册
        this.$axios
            .post("/api/user/address/save", {
              this.address
            })
            .then(res => {
              // “001”代表注册成功，其他的均为失败
              if (res.data.code === "001") {
                // 隐藏注册组件
                this.isAdd = false;
                // 弹出通知框提示注册成功信息
                this.notifySucceed(res.data.msg);
              } else {
                // 弹出通知框提示注册失败信息
                this.notifyError(res.data.msg);
              }
            })
            .catch(err => {
              return Promise.reject(err);
            });


      }
    }
  };
</script>
<style>
</style>