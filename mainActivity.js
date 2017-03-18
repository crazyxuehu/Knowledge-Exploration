define(function(require){
	require("jquery");
	require("css!./css/dragula").load();
	require("css!./css/swiper.min").load();
	require("./js/swiper.min");
	var array = require("$UI/system/lib/base/array");
	
	var justep = require("$UI/system/lib/justep");
	var Model = function(){
		this.callParent();
		 this.count=0;
		 this.swiper;
	};
	Model.prototype.modelLoad=function(event){
		var Reldom=this.comp("checkRelation");
	    var Typdom=this.comp("checkType");
		var me=this;
		var context="<div xid='div9' class='swiper-slide'>";
        context=context+"<img src='./img/1.jpg' class='img-rounded img-slide' xid='image1'></img></div>"; 
        context=context+"<div xid='div11' class='swiper-slide'>";
        context=context+"<img src='./img/3.jpg' class='img-rounded img-slide' xid='image3'></img></div>";
        $(context).appendTo($('#divswip'));
		this.swiper = new Swiper('.swiper-container', {
		pagination: '.swiper-pagination',
        effect: 'cube',
        grabCursor:true,
        loop:true,
        observer:true,
        paginationType : 'custom',
        paginationCustomRender: function (swiper, current, total) {
        	var data=me.comp("resultdata").find(['ID'],[current]);
			var btn=me.comp("buttonswip");
			if(data.length>0){
				btn.set({
				"label":data[0].val('ENAME')
				});
			}
			return current+"/"+total;
		},
    });
		var dom1=this.getElementByXid("relaEx");//获取容器实现数据可视化
		var dom2=this.getElementByXid("metapath");
		var desc1="Relation Explaination";
		var desc2="Meta Path Explainaion";
		this.DataVisul(dom1, desc1);//调用数据可视化函数，主要是通过Echarts控件实现
		this.DataVisul(dom2, desc2);
		var container=this.getElementByXid("btncontainer");//实现对应容器的控件拖拽事件
		var dragfactor0=this.getElementByXid("drag2");
		var dragfactor1=this.getElementByXid("drag1");
		var dragfactor2=this.getElementByXid("drag3");
		var drag=require("./js/dragula");
		drag([container],{removeOnSpill:true});//搜索区域拖拽删除
		drag([dragfactor0,container],{copy:true});//费搜索区域复制拖拽
		drag([dragfactor1,container],{copy:true});
		drag([dragfactor2,container],{copy:true});
		this.MenuSlide();
	};
	Model.prototype.DataVisul = function(dom,desc){
		var echarts=require("./js/echarts");
		var mycharts=echarts.init(dom);
		var option = {
		/* backgroundColor: new echarts.graphic.RadialGradient(0.5, 0.5, 0.4, [{
         offset: 0,
        color: '#eee'
		 }, {
			 offset: 1,
        color: '#eef'
		 }]),*/
		    title: {
		    	show:true,
		        text: desc,
		        textStyle:{
		        	color:'gray',
		        	fontStyle:'italic'
		        }
		    },
		    tooltip: {},
		    animationDurationUpdate: 1500,
		    animationEasingUpdate: 'quinticInOut',
		    series : [
		        {
		            type: 'graph',
		            layout: 'none',
		            symbolSize: 50,
		            roam: true,
		            label: {
		                normal: {
		                    show: true
		                }
		            },
		            edgeSymbol: ['circle', 'arrow'],
		            edgeSymbolSize: [4, 10],
		            edgeLabel: {
		                normal: {
		                    textStyle: {
		                        fontSize: 20
		                    }
		                }
		            },
		            data: [{
		                name: 'Entity1',
		                x: 100,
		                y: 300
		            }, {
		                name: 'Entity2',
		                x: 800,
		                y: 300
		            }, {
		                name: 'Entity3',
		                x: 550,
		                y: 100
		            }, {
		                name: 'Entity4',
		                x: 350,
		                y: 500
		            }],
		            // links: [],
		            links: [{
		                source: 0,
		                target: 1,
		                symbolSize: [5, 20],
		                label: {
		                    normal: {
		                        show: false
		                    }
		                },
		                lineStyle: {
		                    normal: {
		                        type:'dotted',
		                        width: 1.5,
		                        curveness: 0.2
		                    }
		                }
		            }, {
		                source: 'Entity2',
		                target: 'Entity1',
		                label: {
		                    normal: {
		                        show: true
		                    }
		                },
		                lineStyle: {
		                    normal: { curveness: 0.2 }
		                }
		            }, {
		                source: 'Entity1',
		                target: 'Entity3'
		            }, {
		                source: 'Entity2',
		                target: 'Entity3'
		            }, {
		                source: 'Entity2',
		                target: 'Entity4'
		            }, {
		                source: 'Entity1',
		                target: 'Entity4'
		            }],
		            lineStyle: {
		                normal: {
		                    type:'solid',
		                    opacity: 0.9,
		                    width: 2,
		                    curveness: 0
		                }
		            }
		        }
		    ]
		};
        mycharts.setOption(option);            
	};
	Model.prototype.MenuSlide=function(){
		 var toggleMenu = function(){
			if (swiper.previousIndex == 0)
				swiper.slidePrev()
		}
		, menuButton = document.getElementsByClassName('menu-button')[0]
		, swiper = new Swiper('.swiper-container1', {
			slidesPerView: 'auto'
			, initialSlide: 1
			, resistanceRatio: .00000000000001
			, onSlideChangeStart: function(slider) {
				if (slider.activeIndex == 0) {
					menuButton.classList.add('cross'),
					menuButton.removeEventListener('click', toggleMenu, false)
				} else
					menuButton.classList.remove('cross')
			}
			, onSlideChangeEnd: function(slider) {
				if (slider.activeIndex == 0)
					menuButton.removeEventListener('click', toggleMenu, false)
				else
					menuButton.addEventListener('click', toggleMenu, false)
			}
			, slideToClickedSlide: true
		})
	};
	Model.prototype.checkRelationChange = function(event){
	   var Reldom=event.source;
	   var Typdom=this.comp("checkType");
	   var RelValue=Reldom.get("value");
	   var TypValue=Typdom.get("value");
	   //debugger
	   if(RelValue=="1"&&TypValue=="0"){
		  this.changeType("1");
	   }else if(RelValue=="1"&&TypValue=="1"){
		   this.changeType("2");
	   }else if(RelValue=="0"&&TypValue=="1"){
		   this.changeType("0");
	   }else{
		   this.changeType("3");
	   }
		
	};
	Model.prototype.checkTypeChange = function(event){
	   var Typdom=event.source;
	   var Reldom=this.comp("checkRelation");
	   var RelValue=Reldom.get("value");
	   var TypValue=Typdom.get("value");
	   if(RelValue=="1"&&TypValue=="0"){
		  this.changeType("1");
	   }else if(RelValue=="1"&&TypValue=="1"){
		   this.changeType("2");
	   }else if(RelValue=="0"&&TypValue=="1"){
		   this.changeType("0");
	   }else{
		   this.changeType("3");
	   }
	};
	Model.prototype.changeType=function(num){
		var data=this.comp("recdata");
		var parNode=$('#rec');
		parNode.empty();
		//debugger
		if(num=="3") return;
		var row='<div component="$UI/system/components/justep/row/row" class="x-row row-size" xid="row19">';
		var col='<div class="x-col col-size" xid="col43">';
		var doc='';
		var i=0;
		data.each(function(param){
			var content="";
			var img="";
			var btn="";
			var flag=true;
			//debugger
			if(param.row.val('TYPE')=="0"&&(num=="0"||num=="2")){
				flag=false;
				content='<div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" ';
				content=content+'label="Type" xid="button30"><i></i><span>Type</span></div>';
				img='<img src="./img/';
				img=img+param.row.val('FNAME')+'" xid="image15" class="img-rounded img-size img-right-size"></img>';
				btn='<div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size"';
				btn=btn+'label="'+param.row.val('ENAME')+'" xid="button27"><i></i><span>'+param.row.val('ENAME');
				btn=btn+'</span></div> </div>';
				if(i%3===0){
					if(i)doc=doc+'</div>';
					doc=doc+row+col+content+img+btn;
				}else{
					doc=doc+col+content+img+btn;
				}
				i++;
			}
			if(param.row.val('TYPE')=="1"&&flag&&(num=="1"||num=="2")){
				content='<div component="$UI/system/components/justep/button/button" class="btn btn-size btn-relation" ';
				content=content+'label="Relation" xid="button30"><i></i><span>Relation</span></div>';
				img='<img src="./img/';
				img=img+param.row.val('FNAME')+'" xid="image15" class="img-rounded img-size img-right-size"></img>';
				btn='<div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size"';
				btn=btn+'label="'+param.row.val('ENAME')+'" xid="button27"><i></i><span>'+param.row.val('ENAME');
				btn=btn+'</span></div> </div>';
				if(i%3===0){
					if(i)doc=doc+'</div>';
					doc=doc+row+col+content+img+btn;
				}else{
					doc=doc+col+content+img+btn;
				}
				i++;
			}		    					
		});
		//console.log(i);
			while(i%3!==0){
				doc=doc+col+'</div>';
				i++;
			}
			doc=doc+'</div>';
		$(doc).appendTo(parNode);

	};
	Model.prototype.searchbtnClick = function(event){
	    var Reldom=this.comp("checkRelation");
	    var Typdom=this.comp("checkType");
	    Reldom.val(1);
	    Typdom.val(1);
	    var dom=this.comp("btncontainer");
		var n=dom.domNode.childElementCount;
		var indom=this.comp("inputdata");
		if(indom.value==""&&n==0){
			return
		}
	    var shbtn=this.comp("Qbtn");
	    var label=shbtn.get("label");
	    var num=label.substr(1);
	    num++;
	    shbtn.set({label:"Q"+num});
		var sdata=this.comp("searchdata");
		var deArray=[];
		for(var i=0;i<n;i++){
		   var zz=dom.domNode.children[i].outerText;
		   var rmArray=[{"id":justep.UUID,
			"Qname":label,
			"Ename":zz
			}];
			deArray=array.merge(rmArray, deArray);
		}
		deArray=array.merge(deArray,[{"id":justep.UUID,"Qname":label,"Ename":indom.value}]);
		sdata.newData({
			"defaultValues":deArray,
			"onSuccess":function(event){
			}
		});
		this.resultLoad();
		this.recommendLoad();
	};
	Model.prototype.searchdataAfterNew = function(event){
		var list=event["rows"];
		var label;
		var enArray=[];
		for(var i=0;i<list.length;i++){
			var row=list[i].row;
			label=row["Qname"].value.latestValue;
			array.merge(enArray, [row["Ename"].value.latestValue]);
		}
		var parNode=this.getElementByXid("menulist");
		var btn=" <a component='$UI/system/components/justep/button/button' class='btn btn-color btn-menu-size'";
		btn=btn+" label='"+label+"' data-toggle='collapse' data-target='#"+label+"'>";
		btn=btn+"<i></i>";
        btn=btn+"<span>"+label+"</span></a>";
        $(btn).appendTo(parNode);
		var content="<div id='"+label+"' class='collapse'><ul>";
		for(i=0;i<enArray.length;i++){
			content=content+"<li>"+enArray[i]+"</li>";
		}
		content=content+"</ul></div>";
		$(content).appendTo(parNode);
	};
		Model.prototype.resultLoad=function(){
			 var dx=require("jquery");
			 var me=this;
			 var resArray=[];//json数组
			 this.swiper.removeAllSlides();
			 var swipArray=[];
			 dx.ajax({
				url:"http://localhost:8080/seed/result",
				data:{},
				type:"POST",
				dataType:'json',
				success:function(result,status){
				//debugger
				for(var i=0;i<result.length;i++){
					if(result[i]["TYPE"]=="0"){				    
						var content='<div class="swiper-slide"><img src="./img/';
						content=content+result[i]["FNAME"]+'" class="img-rounded" style="height:100%;width:100%;"></img></div>'; 
						array.merge(swipArray,[content]);						
						array.merge(resArray,[result[i]]);
					}else{
						break;
					}
				}	
					me.swiper.appendSlide(swipArray);
					me.swiper.updateSlidesSize();
					me.swiper.updatePagination();
					me.swiper.updateClasses();
					var data=me.comp("resultdata");
					data.newData({
						"defaultValues":resArray
					});
					var dom=$('#leftcontainer');
					dom.empty();
				//	debugger
					var row=result.length-i;
					row=Math.ceil(row/3);
					var doc='<div  class="x-row row-size" xid="222" >';
					var mycontent='<div  class="x-row row-size" xid="222" >';
					var context='<div class="x-col col-size" xid="333" >';
					var j=1;
					for(;i<result.length;i++,j++){
						var text='<img src="./img/'+result[i]["FNAME"]+'" class="img-rounded img-left-size"></img>';
						var btn='<a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="';
						btn=btn+result[i]["ENAME"]+'" xid="111"><i></i><span>'+result[i]["ENAME"]+'</span></a></div>';
						doc=doc+context+text+btn;
						if(j%3===0){
							if(j/3==row)
								doc=doc+'</div>';
							else
								doc=doc+'</div>'+mycontent;
						}						
					}
					//debugger
					if((j-1)%3!==0){
						doc=doc+context+'</div>';
						while(j%3!==0){
							doc=doc+context+'</div>';
							j++;
						}
						doc=doc+'</div>';
					}
					$(doc).appendTo(dom);		 	
				},
				error:function(er,status){
					alert(er);
				}
			});
	};
	Model.prototype.recommendLoad=function(){
		 var dx=require("jquery");
		 var me=this;
		 var recArray=[];//json数组
		  dx.ajax({
				url:"http://localhost:8080/seed/result",
				data:{},
				type:"POST",
				dataType:'json',
				success:function(result,status){
					var parNode=$('#rec');
					parNode.empty();
					var row='<div component="$UI/system/components/justep/row/row" class="x-row row-size" xid="row19">';
					var col='<div class="x-col col-size" xid="col43">';
					var doc='';
					for(var i=0;i<result.length;i++){
						var content="";
						var img="";
						var btn="";
						if(result[i]["TYPE"]=="0"){
							content='<div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" ';
							content=content+'label="Type" xid="button30"><i></i><span>Type</span></div>';
						}else{
							content='<div component="$UI/system/components/justep/button/button" class="btn btn-size btn-relation" ';
							content=content+'label="Relation" xid="button30"><i></i><span>Relation</span></div>';
						}
						img='<img src="./img/';
						img=img+result[i]["FNAME"]+'" xid="image15" class="img-rounded img-size img-right-size"></img>';
						btn='<div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size"';
						btn=btn+'label="'+result[i]["ENAME"]+'" xid="button27"><i></i><span>'+result[i]["ENAME"];
						btn=btn+'</span></div> </div>';
						if(i%3==0){
							if(i)doc=doc+'</div>';
							doc=doc+row+col+content+img+btn;
						}else{
							doc=doc+col+content+img+btn;
						}		    					
						array.merge(recArray,[result[i]]);
					}
					//debugger
					while(i%3!==0){
						doc=doc+col+'</div>';
						i++;
					}
					doc=doc+'</div>';
					$(doc).appendTo(parNode);
					var data=me.comp("recdata");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					data.newData({
						"defaultValues":recArray,
						"onError":function(event){
							alert(event.errorNode);
						},
						"onSuccess":function(event){
							//alert(123);
						}			
					});
				}
			});	
	};
	return Model;
});