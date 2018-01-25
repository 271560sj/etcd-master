"use strict"
define([
    "jquery",
    "vue",
],function($,Vue){
     var vue = new Vue({
         el: "#index",
         data: {
           path: {
               registryMasterInfosPath: "/etcd/registerService",//注册服务信息的URL
               deleteMasterServicePath: "/etcd/deleteService",//删除服务信息的URL
               watcherWorkSerivePath: "/etcd/watcherService",//监控work service的变化

           },
           objData: {
              nodes: [],//记录Node信息的数组
              nodeInfo: {
                  action: "",//操作类型
                  node: {//node的信息
                      key: "",//key的信息
                      value: "",//key值
                      ttl: 0,//生存时间
                      dir: false,//是否是目录
                  },
              }
           }
         },
         mounted:function(){
             var _self = this;
             setInterval(_self.registryMasterInfos,1000 * 8);
             setInterval(_self.deleteMasterService,1000 * 9);
             setInterval(_self.watcherWorkService,1000 * 10);
         },
         methods: {
             //注册服务信息
             registryMasterInfos: function(){
                 var _self = this;
                 $.ajax({//ajax请求
                    type: "post",
                    url: _self.path.registryMasterInfosPath,
                    dataType: "json",
                    success: function(data){
                        if(data != undefined){
                           var nodes = _self.objData.nodeInfo;
                           nodes.action = data.action;
                           if (data.node != undefined){
                               nodes.node.key = data.node.key;
                               nodes.node.value = data.node.value;
                               nodes.node.ttl = data.node.ttl;
                               nodes.node.dir = data.node.dir;
                           }
                           _self.objData.nodes.push(nodes);
                        }
                    },
                    error: function(data){
                    }

                 })
             },
             deleteMasterService: function(){
                 var _self = this;
                 $.ajax({
                     url: _self.path.deleteMasterServicePath,
                     type: "POST",
                     dataType: "json",
                     success: function (data) {
                         if(data != undefined){
                             var nodes = _self.objData.nodeInfo;
                             nodes.action = data.action;
                             if (data.node != undefined){
                                 nodes.node.key = data.node.key;
                                 if (data.node.value != undefined){
                                     nodes.node.value = data.node.value;
                                 }
                                 nodes.node.ttl = data.node.ttl;
                                 nodes.node.dir = data.node.dir;
                             }
                             _self.objData.nodes.push(nodes);
                         }
                     },
                     error: function (data) {
                         
                     }
                 })
             },
             watcherWorkService: function () {
                 var _self = this;
                 $.ajax({
                     url: _self.path.watcherWorkSerivePath,
                     type: "POST",
                     dataType: "json",
                     success: function (data) {
                         if(data != undefined){
                             var nodes = _self.objData.nodeInfo;
                             nodes.action = data.action;
                             if (data.node != undefined){
                                 nodes.node.key = data.node.key;
                                 if (data.node.value != undefined){
                                     nodes.node.value = data.node.value;
                                 }
                                 nodes.node.ttl = data.node.ttl;
                                 nodes.node.dir = data.node.dir;
                             }
                             _self.objData.nodes.push(nodes);
                         }
                     },
                     error: function (data) {

                     }
                 })
             }

         }

     });
})