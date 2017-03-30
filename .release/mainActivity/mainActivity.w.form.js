define(function(require){
require('$model/UI2/system/components/justep/model/model');
require('$model/UI2/system/components/justep/loadingBar/loadingBar');
require('$model/UI2/system/components/justep/button/button');
require('$model/UI2/system/components/justep/scrollView/scrollView');
require('$model/UI2/system/components/justep/input/input');
require('$model/UI2/system/components/justep/list/list');
require('$model/UI2/system/components/bootstrap/row/row');
require('$model/UI2/system/components/justep/tabs/tabs');
require('$model/UI2/system/components/justep/panel/child');
require('$model/UI2/system/components/justep/panel/panel');
require('$model/UI2/system/components/justep/button/checkbox');
require('$model/UI2/system/components/justep/row/row');
require('$model/UI2/system/components/justep/contents/contents');
require('$model/UI2/system/components/justep/data/data');
require('$model/UI2/system/components/justep/window/window');
require('$model/UI2/system/components/bootstrap/panel/panel');
require('$model/UI2/system/components/justep/button/buttonGroup');
var __parent1=require('$model/UI2/system/lib/base/modelBase'); 
var __parent0=require('$model/UI2/SEED2/mainActivity'); 
var __result = __parent1._extend(__parent0).extend({
	constructor:function(contextUrl){
	this.__sysParam='true';
	this.__contextUrl=contextUrl;
	this.__id='';
	this.__cid='cVNnQfa';
	this._flag_='6e83a9ae5ef0423ad59fceddd597376d';
	this.callParent(contextUrl);
 var __Data__ = require("$UI/system/components/justep/data/data");new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"Ename":{"define":"Ename","label":"entity","name":"Ename","relation":"Ename","type":"String"},"Qname":{"define":"Qname","label":"searchID","name":"Qname","relation":"Qname","type":"String"},"id":{"define":"id","label":"QID","name":"id","relation":"id","rules":{"integer":true},"type":"Integer"}},"directDelete":false,"events":{"onAfterNew":"searchdataAfterNew"},"idColumn":"id","limit":20,"xid":"searchdata"});
 new __Data__(this,{"autoLoad":false,"autoNew":false,"confirmDelete":true,"confirmRefresh":true,"defCols":{"ENAME":{"define":"ENAME","label":"菜品名称","name":"ENAME","relation":"ENAME","type":"String"},"FNAME":{"define":"FNAME","label":"菜品图片","name":"FNAME","relation":"FNAME","type":"String"},"ID":{"define":"ID","label":"菜品ID","name":"ID","relation":"ID","type":"String"},"TYPE":{"define":"TYPE","label":"菜品类型","name":"TYPE","relation":"TYPE","type":"String"}},"directDelete":false,"events":{},"idColumn":"ID","limit":20,"xid":"resultdata"});
 new __Data__(this,{"autoLoad":true,"confirmDelete":true,"confirmRefresh":true,"defCols":{"ENAME":{"define":"ENAME","label":"菜品名称","name":"ENAME","relation":"ENAME","type":"String"},"FNAME":{"define":"FNAME","label":"菜品图片","name":"FNAME","relation":"FNAME","type":"String"},"ID":{"define":"ID","label":"菜品ID","name":"ID","relation":"ID","type":"String"},"TYPE":{"define":"TYPE","label":"菜品类型","name":"TYPE","relation":"TYPE","type":"String"}},"directDelete":false,"events":{},"idColumn":"ID","limit":20,"xid":"recdata"});
}}); 
return __result;});