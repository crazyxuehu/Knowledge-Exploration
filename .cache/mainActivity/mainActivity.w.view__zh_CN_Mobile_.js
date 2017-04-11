window.__justep.__ResourceEngine.loadCss([{url: '/v_75480e79591d4642bb7c95f3ffa97a19l_zh_CNs_d_m/system/components/bootstrap.min.css', include: '$model/system/components/bootstrap/lib/css/bootstrap,$model/system/components/bootstrap/lib/css/bootstrap-theme'},{url: '/v_2eeb3e34ca8a4a6583cb056c991f5fc9l_zh_CNs_d_m/system/components/comp.min.css', include: '$model/system/components/justep/lib/css2/dataControl,$model/system/components/justep/input/css/datePickerPC,$model/system/components/justep/messageDialog/css/messageDialog,$model/system/components/justep/lib/css3/round,$model/system/components/justep/input/css/datePicker,$model/system/components/justep/row/css/row,$model/system/components/justep/attachment/css/attachment,$model/system/components/justep/barcode/css/barcodeImage,$model/system/components/bootstrap/dropdown/css/dropdown,$model/system/components/justep/dataTables/css/dataTables,$model/system/components/justep/contents/css/contents,$model/system/components/justep/common/css/forms,$model/system/components/justep/locker/css/locker,$model/system/components/justep/menu/css/menu,$model/system/components/justep/scrollView/css/scrollView,$model/system/components/justep/loadingBar/loadingBar,$model/system/components/justep/dialog/css/dialog,$model/system/components/justep/bar/css/bar,$model/system/components/justep/popMenu/css/popMenu,$model/system/components/justep/lib/css/icons,$model/system/components/justep/lib/css4/e-commerce,$model/system/components/justep/toolBar/css/toolBar,$model/system/components/justep/popOver/css/popOver,$model/system/components/justep/panel/css/panel,$model/system/components/bootstrap/carousel/css/carousel,$model/system/components/justep/wing/css/wing,$model/system/components/bootstrap/scrollSpy/css/scrollSpy,$model/system/components/justep/titleBar/css/titleBar,$model/system/components/justep/lib/css1/linear,$model/system/components/justep/numberSelect/css/numberList,$model/system/components/justep/list/css/list,$model/system/components/justep/dataTables/css/dataTables'}]);window.__justep.__ResourceEngine.loadJs(['/v_f944b18a410a45a7a43965baeb73a48el_zh_CNs_d_m/system/components/comp2.min.js','/v_dec56db40b464ef2b4331cd1fd5aa252l_zh_CNs_d_m/system/core.min.js','/v_69c7c9f079d84590bce76e70612262c1l_zh_CNs_d_m/system/common.min.js','/v_e3158d2b34e04cb2be206866a0c96ec9l_zh_CNs_d_m/system/components/comp.min.js']);define(function(require){
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
