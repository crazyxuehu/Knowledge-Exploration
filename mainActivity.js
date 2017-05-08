define(function(require){
	require("jquery");
///	require("./js/bootstrap");
	//require("css!./css/bootstrap").load();
	require("css!./css/dragula").load();
	require("css!./css/swiper.min").load();
	require("css!./css/jquery-ui.css").load();
	require("./js/jquery-ui");
	require("./js/swiper.min");
	require("./js/dragula");
	var array = require("$UI/system/lib/base/array");
	var Button = require("$UI/system/components/justep/button/button");
	
	var justep = require("$UI/system/lib/justep");
	var Model = function(){
		 this.callParent();
		 this.count=0;
		 this.swiper;
		 this.imgcount=0;
		 this.featurecount=0;
		 this.imgmap={};
		 this.featuremap={};
		 this.querymap={};
		 this.simcount=0;
		 this.simfact=0;
		 this.simfeaturecount=0;
		 this.featurefact=0;
		 this.simfeaturemap={};
		 this.query=null;
		 this.data=null;
		 this.link=null;
		 this.simEntity=null;
		 var dx=require("jquery");
		 this.relationPath={};
		 this.categoryPath={};
		 this.simEntityMap={};
		 this.currentQuery;
		 this.relationmapleft={};
		 this.relationmapright={};
		 this.flag=false;
		 //this.visdom;
		// var urlMap={};
		// console.log(123);
		var me=this;
		$('#inputdata').on('click',function(){
		//debugger
			$('#inputdata').val('');
		});
		$('#inputdata').on('focus',function(){
		//debugger
			$('#inputdata').val('');
		});
		 $('#inputdata').bind('input propertychange',function(){//给input控件添加监听函数
			// debugger
			 var query=$(this).val();
			 if(query.length>0&&query!=''){
			 //console.log(query);
			 var dom=$(this);
			 dx.ajax({
				url:"http://localhost:8080/seed/test",
				data:{query:query},
				type:"POST",
				dataType:'json',
				success:function(result,status){
					if(result.length>0){
						me.autoComplete(result);
					}
				},
				error:function(error,status){
					console.log(status);
					//console.log(error.responseText);
					var result=new Array("1","123","12345");
					me.autoComplete(result);
				}
			 });
			 }
		 });
		// debugger
		
		
	};
	var me=this;
	Model.prototype.autoComplete=function(event){//自动补全功能
				var me=this;
				$("ul.list").empty();
				for(var i=0;i<event.length;i++){
				var liElement=$('<li class="autoli"><span class="ex">'+event[i]+'</span></li>")');
				liElement.appendTo("ul.list");
				}
				//首先让list隐藏起来
					$("ul.list").show();
					//如果当前有已经高亮的下拉选项卡，那么将其移除
					if($("ul.list li:visible").hasClass("lilight")){
						$("ul.list li").removeClass("lilight");
					}
					//如果还存在下拉选项卡，那么将其高亮
					if($("ul.list li:visible")){
						$("ul.list li:visible:eq(0)").addClass("lilight");
					}
					$('#bom').hide();
				$("ul.list li").focus(function(){
					if($("ul.list li:visible").hasClass("lilight")){
						$("ul.list li").removeClass("lilight");
					}
					$(this).addClass("lilight");
				});
				//当鼠标点击某个下拉项时，选中该项，下拉列表隐藏
				$("ul.list li").click(function(){
					//$("#inputdata").val($(this).text());
					//var value=$(this)
					var parNode=me.getElementByXid("btncontainer");
					if(parNode){
						var flag={
						xid:"Query1",
						label:$(this).text(),
						parentNode:parNode,
						'class':'btn btn-success btn-drag'
						};
						//debugger
						new Button(flag);
					}
					$("ul.list").hide();
					$('#bom').show();
					$("#inputdata").get(0).value='';
					console.log($('#inputdata').val())
				});
				//当鼠标划过某个下拉项时，选中该项，下拉列表隐藏
				$("ul.list li").hover(function(){
					$("ul.list li").removeClass("lilight");
					$(this).addClass("lilight");
					$('#bom').show();
				});
				//当鼠标点击其他位置，下拉列表隐藏
				$(document).click(function(){
					$('#inputdata').get(0).value='';
					$("ul.list").hide();
					$('#bom').show();
				}); 
			};
	Model.prototype.modelLoad=function(event){
		 var me=this;
		 this.count=0;
         this.imgcount=0;
		 this.featurecount=0;
		 this.imgmap={};
		 this.featuremap={};
		 this.querymap={};
		 this.simcount=0;
		 this.simfact=0;
		 this.simfeaturecount=0;
		 this.featurefact=0;
		 this.simfeaturemap={};
		 this.query=[];
		 this.data=null;
		 this.link=null;
		 this.simEntity=null;
		 this.relationPath={};
		 this.categoryPath={};
		this.createIndex();
		this.swiper = new Swiper('.swiper-container', {
		pagination: '.swiper-pagination',
        effect: 'cube',
        grabCursor:true,
        loop:true,
        observer:true,
        paginationType : 'custom',
        paginationCustomRender: function (swiper, current, total) {
        	if(me.imgmap.hasOwnProperty(current)){
	        	var data=me.comp("resultdata").find(['ID'],[me.imgmap[current]]);
				var desc=$('#desc');
				if(data.length>0){
					var query=data[0].val("ENAME");
					me.currentQuery=query;
					desc.empty();
					$('#entityName').empty();
					$('#entityName').append(query);
					$('#link').attr('href',"http://en.wikipedia.org/w/index.php?search="+data[0].val("ENAME"));
					if(query.length<5){
						if(data[0].val('DESC').length<150){
							desc.append(data[0].val('DESC'));
						}else{
							desc.append(data[0].val('DESC').substr(0,150)+"...");
						}
					}else{
						desc.append(data[0].val('DESC').substr(0,100)+"...");
					}
					if(total>1&&this.featurecount==this.count){
						console.log("pagination serch");
							me.createFeature(me.imgmap[current]);
					}
				}
			}
			return current+"/"+total;
		},
    });
	/*	var swipArray=[];
		var content='<div class="swiper-slide"><img src="./img/1.jpg';
		var doc='" class="img-rounded" style="height:100%;width:100%;"></img></div>';
		swipArray.push(content+doc);
		this.swiper.appendSlide(swipArray);
		this.swiper.updateSlidesSize();
		this.swiper.updatePagination();
		this.swiper.updateClasses();*/
		var container=this.getElementByXid("btncontainer");//实现对应容器的控件拖拽事件
		//var container=$(dom);
		var dragfactor0=this.getElementByXid("drag2");
		var dragfactor1=this.getElementByXid("drag1");
		var drag=require("./js/dragula");
		drag([container],{removeOnSpill:true});//搜索区域拖拽删除
		drag([dragfactor0,container],{copy:true});//费搜索区域复制拖拽
		drag([dragfactor1,container],{copy:true});
		$(document).on('touchstart','.btn-category',function(){
			$(this).addClass("on");
			$(this).tooltip({
				items:'.btn-category.on',
				position:{my:"left+15 center",at:"right center"},
				content:function(callback){
					var query=$(this).get(0).innerText;
					//console.log(query);
					$.ajax({
						url:"http://localhost:8080/seed/getcategory",
						data:query,
						type:"POST",
						dataType:'json',
						contentType:'application/json',
						success:function(result,status){
							var doc='<ul class="ui-menu">'
							doc+='<li class="ui-menu-item" style="color:#73A839;">'+query+'</li>';
							console.log(result.length);
							for(var i=0;i<result.length;i++){
								doc+='<li class="ui-menu-item" style="color:#033c73;">'+result[i]['name']+'</li>';
							}
							console.log(doc);
							doc+='</ul>';
							callback(doc);
						},
						error:function(er,status){
							alert(status);
						}
					});
				}
			});
			$(this).tooltip("open");
		});
		$(document).on('touchend','.btn-category.on',function(){
			$(this).tooltip('close');
			$(this).removeClass('on');
		});
		$(document).on('click','.btn-drag',function(){
			if($(this)!=null){
				$(this).remove();
			}
		});
		$(document).on('click','.menu-list',function(){
			me.createQuery($(this).text());
			$(this).addClass("list-select");
		});
		this.MenuSlide();
		//this.createIndex();
	};
	Model.prototype.createIndex=function(event){
		var me=this;
		$.ajax({
				url:"http://localhost:8080/seed/getIndex",
				data:{},
				type:"POST",
				dataType:'json',
				contentType:'application/json',
				success:function(result,status){
				//search result
					var entityList=result['queryEntity'];
					var featureList=result['queryFeatureList'];
					var data=me.comp("resultdata");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					var list=new Array();
					list.push(entityList[0]);
					var query=new Array();
					/*for(var i=1;i<entityList.length;i++){
						me.createQuery(entityList[i]['name']);
					}*/
					for(var i=0;i<list.length;i++){
						me.getImageURL(list[i]["name"],i+1,1);
						query.push(list[i]["name"]);
					}
					 me.getSearchResult(list,featureList);
					 me.query=query;
					//simentity
					data=me.comp("simdata");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					var simlist=result['simEntityList'];
					me.simcount=simlist.length;
					var list=new Array();
					for(var i=0;i<simlist.length;i++){
						me.getImageURL(simlist[i]['name'],i+1,2);
						list.push(simlist[i]['name']);
					}
					//me.Visualization(query,result);
					me.simEntity=list;
					
					//simfeature
					data=me.comp("simfeature");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					var simfeature=result['recomendFeatureList'];
					me.simfeaturecount=simfeature.length;
					for(var i=0;i<simfeature.length;i++){
						me.saveFeature('',simfeature[i]['relation']['name'],simfeature[i]['target']['name'],i+1,2);
					}
					
					//metaPath
				    me.relationPath={};
				    me.categoryPath={};
				    var metapath=result['metaPathList'];
				    for(var key in metapath['category']){
				    	me.categoryPath[key]=metapath['category'][key];
				   	}
				   	for(var key in metapath['relation']){
				   		me.relationPath[key]=metapath['relation'][key];
				   	}
				    me.createMetaPathSelect();
				   	console.log("visulization");
				    //search path
				    var vis=result['vis'];
				    var dom1=me.getElementByXid("viscontainer");
				    var repnum=1000;
				    var size=80;
				    var desc1="Relation Explaination";
				    var vdata=vis['nodeList'];
				    var vlink=vis['linkList'];
				    me.data=vdata;
				    me.link=vlink;
				   	console.log("Relation visulization");
				   // debugger
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});
	}
	Model.prototype.DataVisual = function(dom,desc,data,links,repulnum,size){
	var echarts=require('./js/echarts');
      var myCharts=echarts.init(dom);
    //  debugger
    //  var dialog=this.comp('dialog');
    var edgelength=100;
    var gravity=0.1;
    var flag=true;
      var option = {
      title:{
    	  show:true,
    	  text:desc,
    	  textStyle:{
    		  color:'gray',
		      fontStyle:'italic',
		      fontWeight:'bolder',
		      fontSize:20
    	  },
      },
    tooltip: {
        show: true,
        confine:true,
        textStyle:{
    		  fontSize:20
    	  },
    	 
    },
    toolbox:{
    	show:true,
    	feature:{
    		restore:{show:true}
    	},
    	right:340
    },
    legend: {
        x: "right",
        data: ["Category", "Entity","Query","Similar"],
        formatter:function(name){
        	return echarts.format.truncateText(name, 120, '25px bolder Times New Roman', '…');
        }
    },
    animation: false,
    series: [{
        categories: [{
            name: 'Category',
            itemStyle: {
                normal: {
                    color: "#033c73",
                }
            }
        }, {
            name: 'Entity',
            itemStyle: {
                normal: {
                    color: "#73a839",
                }
            }
           },
           {
           name:'Query',
           itemStyle:{
        	   normal:{
        		  
        	   }
           }
           }, 
           {
           name:'Similar',
           itemStyle:{
        	   normal:{
        		  color: "#EC971F", 
        	   }
           }
        }],
        type: 'graph',
        layout: 'force',
        symbol: "circle",
        symbolSize: size,
        hoverAnimation:true,
        roam: true, //禁止用鼠标滚轮缩小放大效果
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [0, 10],
        // 连接线上的文字
        focusNodeAdjacency: true, //划过只显示对应关系
        edgeLabel: {
            normal: {
                show: flag,
                textStyle: {
                    fontSize: 20
                },
                formatter: "{c}"
            }
        },
        lineStyle: {
            normal: {
                opacity: 1,
                width:1,
                curveness: 0
            }
        },
        // 圆圈内的文字
        label: {
            normal: {
                show: true
            },
            textStyle: {
               fontSize: 25
            }
        },
        force: {
            repulsion:repulnum,
            gravity:gravity,
            edgeLength:edgelength,
            layoutAnimation:true
        },
        data: data,
        links:links
    }]
};
      myCharts.setOption(option);  
};
	Model.prototype.PathVisual = function(dom,desc,data,links){
      var echarts=require('./js/echarts');
      var myCharts=echarts.init(dom);
      var option = {
      title:{
    	  show:true,
    	  text:desc,
    	  textStyle:{
    		  color:'gray',
		      fontStyle:'italic',
		      fontWeight:'bolder',
		      fontSize:20
    	  },
      },
    tooltip: {
        show: true,
        textStyle:{
    		  fontSize:20
    	  }
    },
    toolbox:{
    	show:false,
    	feature:{
    		dataView:{show:true,readOnly:true},
    		restore:{show:true},
    		magicType:{
    			show:true,
    			type:['Les Miserables']
    		}
    	}
    },
    legend: {
        x: "right",
        data: ["Category", "Entity","Query","Similar"],
        formatter:function(name){
        	return echarts.format.truncateText(name,120,'25px Times New Roman', '…');
        }
    },
    animation: false,
    series: [{
        categories: [{
            name: 'Category',
            itemStyle: {
                normal: {
                    color: "#033c73",
                }
            }
        }, {
            name: 'Entity',
            itemStyle: {
                normal: {
                    color: "#73a839",
                }
            }
           },
           {
           name:'Query',
           itemStyle:{
        	   normal:{
        		  
        	   }
           }
           }, 
           {
           name:'Similar',
           itemStyle:{
        	   normal:{
        		  color: "#EC971F", 
        	   }
           }
        }],
        type: 'graph',
        layout: 'none',
        symbol: "roundRect",
        symbolSize: [100,50],
        hoverAnimation:true,
        roam: true, //禁止用鼠标滚轮缩小放大效果
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [0, 10],
        // 连接线上的文字
        focusNodeAdjacency: true, //划过只显示对应关系
        edgeLabel: {
            normal: {
                show: true,
                textStyle: {
                    fontSize: 20
                },
                formatter: "{c}"
            }
        },
        lineStyle: {
            normal: {
                opacity: 1,
                width:1,
                curveness: 0
            }
        },
        // 圆圈内的文字
        label: {
            normal: {
                show: true
            },
             textStyle: {
                    fontSize: 35,
                    fontWeight:'bold',
                    fontFamily:'Times New Roman'
            },
        },
        data: data,
        links:links
    }]
};
      myCharts.setOption(option);  
};
Model.prototype.MetaPathVisual = function(dom,desc,result,type){
	//debugger
      var echarts=require('./js/echarts');
      var myCharts=echarts.init(dom);
      var data=null;
      var link=null;
      if(!type){
       data=result['nodeList'];
       link=result['linkList'];
      }else{
    	  data=result;
    	  link=type;
      }
      var option = {
      title:{
    	  show:true,
    	  text:desc,
    	  textStyle:{
    		  color:'gray',
		      fontStyle:'italic',
		      fontWeight:'bolder',
		      fontSize:20
    	  },
    	  
      },
    tooltip: {
        show: true,
        textStyle:{
    		  fontSize:20
    	  }
    },
    legend: {
        x: "right",
        data: ["Category", "Relation","Query"],
        formatter:function(name){
        	return echarts.format.truncateText(name,120,'25px Times New Roman', '…');
        }
    },
    animation: false,
    label:{
    	normal:{
    		show:true,
    		position:'insideLeft',
    		textStyle:{
    			fontWeight:'bold',
    			fontSize:25
    		}
    	}
    },
    series: [{
        categories: [{
            name: 'Category',
            itemStyle: {
                normal: {
                    color: "#033c73",
                }
            }
        }, 
           {
           name:'Relation',
           itemStyle:{
        	   normal:{
        		  color: "#EC971F", 
        	   }
           }
        },
        	{
        	name:'Query',
        	itemStyle:{
        		normal:{
        		}
        	}
        }
        ],
        type: 'graph',
        layout: 'force',
        symbol: "circle",
        symbolSize: 100,
        hoverAnimation:true,
        scale: true, //禁止用鼠标滚轮缩小放大效果
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [0, 10],
        // 连接线上的文字
        focusNodeAdjacency: false, //划过只显示对应关系
        edgeLabel: {
            normal: {
                show:true,
                textStyle: {
                    fontSize: 20
                },
                formatter: "{c}"
            }
        },
        lineStyle: {
            normal: {
                opacity: 1,
                width:1,
                curveness: 0
            }
        },
        // 圆圈内的文字
        label: {
            normal: {
                show: true
            },
            textStyle: {
               fontSize: 20
            }
        },
        force: {
            repulsion:500,
            gravity:0.1,
            edgeLength:220,
            layoutAnimation:true
        },
        data: data,
        links:link
    }]
    /*left:0,
    right:0*/
};
      myCharts.setOption(option);  
};
	//Model.prototype.openPage=function(){};
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
	Model.prototype.checkChange = function(event){
	   var Reldom=$('#checkRelation');
	   var Typdom=$('#checkType');
	   if(Reldom.is(':checked')&&!Typdom.is(':checked')){
		  this.changeType(1);
	   }else if(Reldom.is(':checked')&&Typdom.is(':checked')){
		   this.changeType(2);
	   }else if(!Reldom.is(':checked')&&Typdom.is(':checked')){
		   this.changeType(0);
	   }else{
		   this.changeType(3);
	   }
	};
	Model.prototype.changeType=function(num){
		var parNode=$('#rec');
		parNode.empty();
		if(num==2){
			this.createRecommendSim();
			this.createSimFeature();
		}else if(num==1){
			this.createSimFeature();
		}else if(num==0){
			this.createRecommendSim();
		}
	};
	Model.prototype.searchbtnClick = function(event){
		 this.featuremap={};
		 this.querymap={};
		 this.imgmap={};
		 this.imgcount=0;
		 this.featurecount=0;
		 this.visflag=false;
		 this.flag=false;
	     var Reldom=$('#checkRelation');
	     var Typdom=$('#checkType');
	     Reldom.attr('checked',true);
	     Typdom.attr('checked',true);
	    var dom=this.comp("btncontainer");
		var n=dom.domNode.childElementCount;
		if(n===0){
			return;
		}
	    var shbtn=this.comp("Qbtn");
	    var label=shbtn.get("label");
	    var num=label.substr(1);
	    num++;
	    shbtn.set({label:"Q"+num});
		var sdata=this.comp("searchdata");
		var deArray=[];
		var query=new Array();
		for(var i=0;i<n;i++){
		   var zz=dom.domNode.children[i].outerText;
		   query.push(zz);
		   array.merge(deArray,[{"id":justep.UUID,
			"Qname":label,
			"Ename":zz
			}]);
		}
		//query.push(indom.val());
		//array.merge(deArray,[{"id":justep.UUID,"Qname":label,"Ename":indom.val()}]);
		$('.menu-list').removeClass("list-select");
		this.query=query;
		this.searchdataNew(deArray);
		this.resultLoad(query);
		this.recommendLoad(query);
		this.createMetaPath(query);
		this.Visualization(query);
	};
	Model.prototype.searchdataNew = function(event){
	//debugger
		var label;
		var enArray=new Array();
		if(event.length>0){
			for(var i=0;i<event.length;i++){
				label=event[i]["Qname"];
				enArray.push(event[i]["Ename"]);
			}
			var parNode=this.getElementByXid("menulist");
			var btn=" <a component='$UI/system/components/justep/button/button' class='btn btn-color btn-menu-size'";
			btn=btn+" label='"+label+"' data-toggle='collapse' data-target='#"+label+"'>";
			btn=btn+"<i></i>";
	        btn=btn+"<span>"+label+"</span></a>";
	        $(btn).appendTo(parNode);
			var content="<div id='"+label+"' class='collapse'><ul>";
			for(i=0;i<enArray.length;i++){
				content=content+"<li class='menu-list'>"+enArray[i]+"</li>";
			}
			content=content+"</ul></div>";
			$(content).appendTo(parNode);
		}
	};
		Model.prototype.getTitle=function(query){
			var url = "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&limit=1&callback=?&search=" + query;
			return $.ajax({
		    	url: url,
				type: 'GET',
				contentType: "application/json; charset=utf-8",
				async: false,
				cache:false,
				dataType: "json"
			});
		};
		Model.prototype.getImage=function(query){
		var url = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&piprop=original&callback=?&titles=" + query;
			return $.ajax({
		    	url: url,
				type: 'GET',
				contentType: "application/json; charset=utf-8",
				async: false,
				cache:false,
				dataType: "json"
			});
		};
		Model.prototype.getImageURL=function(query,num,type){
		//debugger 
		var me=this;
		this.getTitle(query).success(function(data){
			var desc='';
			if(data[2][0]){
				desc=data[2][0];
			}
			me.getImage(data[1][0]).success(function(data){
		    	var pages = data.query.pages;
				for (var page in pages) {
					if (pages.hasOwnProperty(page)) {
					//debugger
						var pdata = pages[page];
						if(pdata.thumbnail&&pdata.thumbnail.original){
							//console.log("img: "+pdata);
							if(type==1){
								me.createResult(query,pdata.thumbnail.original,desc,num);
							}else if(type==2){
								me.createSimEntity(query,pdata.thumbnail.original,num);
							}
						}
						else{
								//console.log(pdata);
								var url="http://www.ehypermart.in/0/images/frontend/image-not-found.png";
								if(type==1){
									me.createResult(query,url,desc,num,me);
								}else if(type==2){
									me.createSimEntity(query,url,num);
								}
							}
					}
				}
			});
	    });
	};
	
		Model.prototype.createResult=function(name,url,desc,num){
				this.imgmap[++this.imgcount]=num;
				//console.log("name:"+name+" num:"+num+" img:"+this.imgcount);
				var swipArray=new Array();
					var resultData=this.comp("resultdata");
					resultData.newData({
						"defaultValues":[{"ENAME":name,"FNAME":url,"TYPE":1,"ID":num,"DESC":desc}],
						"onSuccess":function(){
							//console.log(name);
						}
					});
					var content='<div class="swiper-slide"><img src="';
					var doc='" class="img-rounded" style="height:100%;width:100%;"></img></div>';
					swipArray.push(content+url+doc);
					this.swiper.appendSlide(swipArray);
					this.swiper.updateSlidesSize();
					this.swiper.updatePagination();
					this.swiper.updateClasses();
		};
		Model.prototype.createSimEntity=function(name,url,num){
			this.simfact++;
			var simdata=this.comp("simdata");
		//	var me=this;
			simdata.newData({
				"defaultValues":[{"TARGET":name,"FNAME":url,"ID":num}],
				"onError":function(event){
					console.log(event.errorNode);
				},
				"onSuccess":function(event){
				}
			});
			//debugger
			if(this.simcount==this.simfact){
				this.createRecommendSim();
			}
		}
		Model.prototype.createRecommendSim=function(){
			var simdata=this.comp("simdata");
			var count=0;
			var parNode=$('#rec');
			parNode.empty();
			var content='';
			var me=this;
			simdata.each(function(params){
				if(count==0){
						var target=params.row.val('TARGET');
						var url=params.row.val('FNAME');
						var type=me.simEntityMap[target];
						content+=me.createSimRow('Type',target,url,type);
						count++;
				}else{
					var target=params.row.val('TARGET');
					var url=params.row.val('FNAME');
					var type=me.simEntityMap[target];
					content+=me.createSimCol(target,url);
					count++;
					if(count==3){
						parNode.append(content);
						content='';
						count=0;
					}
				}
			});
			console.log("recommend");
			if(this.flag==true){
				console.log("create 2");
				this.createSimFeature();
				this.flag=false;
			}
		};
		Model.prototype.createSimRow=function(type,name,url,tag){
			var content='';
			var row='<div component="$UI/system/components/justep/row/row" class="x-row row-size" xid="row19">';
			var col='<div class="x-col col-size" xid="col43">';
			if(type=='Type'){
				content='<div xid="123" class="btn  btn-size"></div>';
			}else if(type=='Relation'){
				content='<div component="$UI/system/components/justep/button/button" class="btn btn-size btn-relation" ';
				content=content+'label="'+tag+'" xid="button30"><i></i><span>'+tag+'</span></div>';
			}else{
				content+=' <div class="dropdown btn-group" component="$UI/system/components/bootstrap/dropdown/dropdown" xid="dropdown1" style="width:109%;margin-left:3%;">';
				content+=' <a component="$UI/system/components/justep/button/button" class="btn btn-size dropdown-relation btn-icon-right dropdown-toggle"';
				content+='label="'+tag+'" icon="icon-arrow-down-b" xid="button1" data-toggle="dropdown">';
				content+='<i class="icon-arrow-down-b" xid="i2"></i><span xid="span2">'+tag+'</span></a>';
				content+='<ul component="$UI/system/components/justep/menu/menu" class="dropdown-list dropdown-menu" id="dropmenuright"></ul></div>';
			}
			var img='<img src="'+url;
			img+='" xid="image15" class="img-rounded img-size img-right-size"></img>';
			var btn='<div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size "';
			btn=btn+'label="'+name+'" xid="button27"><i></i><span class="btn-test btn-category">'+name;
			btn=btn+'</span></div> </div>';
			return row+col+content+img+btn;
		};
		Model.prototype.createSimCol=function(name,url,type){
			if(type){
				var col='<div class="x-col col-size" xid="col43">';
				var content='<div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" ';
				content=content+'label="'+type+'" xid="button30"><i></i><span>'+type+'</span></div>';
				var img='<img src="'+url;
				img+='" xid="image15" class="img-rounded img-size img-right-size"></img>';
				var btn='<div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size "';
				btn=btn+'label="'+name+'" xid="button27"><i></i><span class="btn-test btn-category">'+name;
				btn=btn+'</span></div> </div>';
				return col+content+img+btn;
			}else{
				var col='<div class="x-col col-size" xid="col43">';
				var content='<div xid="rtdiv" class="btn-size"></div>';
				var img='<img src="'+url;
				img+='" xid="image15" class="img-rounded img-size img-right-size"></img>';
				var btn='<div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size "';
				btn=btn+'label="'+name+'" xid="button27"><i></i><span class="btn-test btn-category">'+name;
				btn=btn+'</span></div> </div>';
				return col+content+img+btn;
			}
		};
		Model.prototype.createSimFeatureResult=function(relation,target,url,num){
			var data=this.comp("simfeature");
			this.simfeaturemap[++this.featurefact]=num;
			data.newData({
				"defaultValues":[{"RELATION":relation,"TARGET":target,"FNAME":url,"ID":num}],
				"onSuccess":function(event){
				},
				"onError":function(event){
					console.log(event.errorNode);
				}
			});
			if(this.featurefact==this.simfeaturecount){
				this.flag=true;
				console.log("create 1");
				this.createSimFeature();
			}
		};
		Model.prototype.createFeatureResult=function(id,relation,target,url,num){
		//debugger
			//console.log("id:"+id+"target:"+target);
			this.featuremap[++this.featurecount]=num;
			var data=this.comp("featuredata");
			data.newData({
				"defaultValues":[{"QID":id,"RELATION":relation,"TARGET":target,"FNAME":url,"ID":this.featurecount}],
				"onSuccess":function(event){
				},
				"onError":function(event){
					console.log(event.errorNode);
				}
			});
			if(this.featurecount==this.count){
				console.log("begin search");
				this.createFeature(this.imgmap[1]);
			}
		};
		Model.prototype.saveFeature=function(id,relation,target,num,type){
			var me=this;
			this.getTitle(target).success(function(data){
			//var desc=data[2][0];
			me.getImage(data[1][0]).success(function(data){
		    	var pages = data.query.pages;
				for (var page in pages) {
					if (pages.hasOwnProperty(page)) {
						var pdata = pages[page];
						if(pdata.thumbnail&&pdata.thumbnail.original){
							//console.log("img: "+pdata);
							if(type==1){
								me.createFeatureResult(id,relation,target,pdata.thumbnail.original,num);
							}else{
								me.createSimFeatureResult(relation,target,pdata.thumbnail.original,num);
							}
							//me.featuremap[num]=pdata.thumbnail.original;
						}
						else{
								var url="http://www.ehypermart.in/0/images/frontend/image-not-found.png";
								//console.log(pdata);
								if(type==1){
									me.createFeatureResult(id,relation,target,url,num);
								}else{
									me.createSimFeatureResult(relation,target,url,num);
								}
								//me.featuremap[num]=url;
							}
					}
				}
			});
			me.getImage(data[1][0]).error(function(data){
				console.log("error:"+target);
			});
		  });
		  this.getTitle(target).error(function(event){
			  console.log("error："+target);
		  });
		};
		Model.prototype.getSearchResult=function(entityList,featureList){
			var data=this.comp("featuredata");
			data.directDeleteMode=true;
			data.confirmDelete=false;
			data.deleteAllData();
			console.log("clear data");
			var d=1;
			for(var i=0;i<entityList.length;i++){
				if(!this.querymap.hasOwnProperty(entityList[i]['name'])){
					this.querymap[entityList[i]['name']]=d++;
				}
			}
			//debugger
			this.count=featureList.length;
			for(var j=0;j<featureList.length;j++){
				var id=this.querymap[featureList[j]['query']['name']];
				console.log("id: "+id);
				this.saveFeature(id,featureList[j]['relation']['name'],featureList[j]['target']['name'],j+1,1);
				//feature.push({"QID":id,"RELATION":featureList[j]['relation']['name'],"TARGET":featureList[j]['query']['target']['name'],"ID":j+1});
			}
		};
		Model.prototype.createFeatureRow=function(entity,relation,fname,type){
			var row='<div  class="x-row row-sizeleft" xid="222">';
			var col='<div class="x-col col-size" xid="333">';
			var content='';
			if(type==1){
			    content+=' <div class="dropdown btn-group" component="$UI/system/components/bootstrap/dropdown/dropdown" xid="dropdown1" style="width:100%">';
				content+=' <a component="$UI/system/components/justep/button/button" class="btn dropdown-relation btn-left btn-icon-right dropdown-toggle"';
				content+='label="'+relation+'" icon="icon-arrow-down-b" xid="button1" data-toggle="dropdown">';
				content+='<i class="icon-arrow-down-b" xid="i2"></i><span xid="span2">'+relation+'</span></a>';
				content+='<ul component="$UI/system/components/justep/menu/menu" class="dropdown-list dropdown-menu" id="dropmenu"></ul></div>';
			}else{
				content='<a component="$UI/system/components/justep/button/button" class="btn btn-left btn-relation" ';
				content=content+'label="'+relation+'" xid="button30"><i></i><span>'+relation+'</span></a>';
			}
				var img='<img src="';
				img=img+fname+'" xid="image15" class="img-rounded img-left-size"></img>';
				var btn='<a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left "';
				btn=btn+'label="'+entity+'" xid="button27"><i></i><span class="btn-test btn-category">'+entity;
				btn=btn+'</span></a> </div>';
			   return row+col+content+img+btn;
		};
		Model.prototype.createFeatureCollumn=function(entity,fname){
				//console.log("no re:"+entity);
				var col='<div class="x-col col-size" xid="333"><div xid="fid" class="btn-left"></div>';
				var img='<img src="';
				img=img+fname+'" xid="image15" class="img-rounded img-left-size"></img>';
				var btn='<a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left "';
				btn=btn+'label="'+entity+'" xid="button27"><i></i><span class="btn-test btn-category">'+entity;
				btn=btn+'</span></a></div>';
				return col+img+btn;
		};
		Model.prototype.createFeatureCollumnRE=function(entity,relation,fname){
				//console.log("re:"+entity);
				var col='<div class="x-col col-size" xid="333">';
				var content='<a component="$UI/system/components/justep/button/button" class="btn btn-left btn-relation" ';
				content=content+'label="'+relation+'" xid="button30"><i></i><span>'+relation+'</span></a>';
				var img='<img src="';
				img=img+fname+'" xid="image15" class="img-rounded img-left-size"></img>';
				var btn='<a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left "';
				btn=btn+'label="'+entity+'" xid="button27"><i></i><span class="btn-test btn-category">'+entity;
				btn=btn+'</span></a></div>';
				return col+content+img+btn;
		};
		Model.prototype.createFeature=function(num,category){
		//debugger;
			var flag=0;
			var data=null;
			if(category&&category!="all"){
				flag=1;
				data=this.comp("featuredata").find(['QID','RELATION'],[num,category]);
			}else{
				data=this.comp("featuredata").find(['QID'],[num]);
			}
			var dom=$("#leftcontainer");
			dom.empty();
			//dom.clear();
			//console.log("clear now!");
			//debugger
			var count=0;
			var content='';
			//debugger
			var map={};
			//console.log("relation");
			if(flag==0) this.relationmapleft={};
			for(var i=0;i<data.length;i++){
				//console.log(data[i].val('RELATION'));
				map[this.featuremap[data[i].val('ID')]]=data[i];
				if(flag==0){
					this.relationmapleft[data[i].val('RELATION')]=1;
				}
			}
			var pre=null;
			var count=0;
			var content='';
			var tag=0;
			for(var key in map){
				if(count===0){
					if(tag==0){
						content+=this.createFeatureRow(map[key].val('TARGET'),map[key].val('RELATION'),map[key].val('FNAME'),1);
						tag++;
						count++;
					}else{
						content+=this.createFeatureRow(map[key].val('TARGET'),map[key].val('RELATION'),map[key].val('FNAME'),0);
					   //console.log(data[i].val('RELATION')+":"+data[i].val('TARGET'));
						count++;
					}
				}else{
					if(map[key].val('RELATION')==map[pre].val('RELATION')){
						content+=this.createFeatureCollumn(map[key].val('TARGET'), map[key].val('FNAME'));
						count++;
						if(count==3){
							content+='</div>';
							dom.append(content);
							count=0;
							content='';
						}
					}else{
						content+=this.createFeatureCollumnRE(map[key].val('TARGET'),map[key].val('RELATION'),map[key].val('FNAME'));
						count++;
						if(count==3){
						 //tag=false;
							content+='</div>';
							dom.append(content);
							count=0;
							content='';
						}
					}
				}
				pre=key;
			}
			while(count>0&&count!=3){
				content+='<div class="x-col col-size" xid="333"></div>';
				count++;
			}
			content+='</div>';
			dom.append($(content));
			console.log("asump:"+this.count+" fact:"+this.featurecount);
			var list=$('#dropmenu');
				//delete(list);
			list.empty();
			//debugger
			var doc="<li class='x-menu-item menu-item item-left' xid='item1'>all</i>";
			for(key in this.relationmapleft){
				 doc+="<li class='x-menu-item menu-item item-left' xid='item1'>";
				doc+=key+"</i>";
			}
			list.append($(doc));
			var me=this;
			//$('.item-left').off('click');
			$('.item-left').on('click',function(){
				var category=$(this)[0].innerText;
				me.RelationChange(category);
			});
			console.log("search");
		};
		Model.prototype.createSimFeature=function(category){
			var simdata=this.comp("simfeature");
			var count=0;
			var parNode=$('#rec');
			var content='';
			var me=this;
			var map={};
			var order=1;
			var tag=0;
			var flag=0;
			if(category&&category!='all'){
				flag=1;
				var data=simdata.find(["RELATION"],[category]);
				for(var i=0;i<data.length;i++){
					map[me.simfeaturemap[i+1]]=data[i];
				}
			}else{
				simdata.each(function(params){
					map[me.simfeaturemap[order++]]=params.row;
				});
			}
			if(flag==0){
				this.relationmapright={};
			}
			for(var key in map){
				if(count==0){
						if(tag==0){
							var target=map[key].val('TARGET');
							var url=map[key].val('FNAME');
							var relation=map[key].val('RELATION');
							if(flag==0){
								this.relationmapright[relation]=1;
							}
							content+=me.createSimRow('',target,url,relation);
							count++;
							tag++;
						}else{
							var target=map[key].val('TARGET');
							var url=map[key].val('FNAME');
							var relation=map[key].val('RELATION');
							if(flag==0){
								this.relationmapright[relation]=1;
							}
							content+=me.createSimRow('Relation',target,url,relation);
							count++;
						}
					}else{
						var target=map[key].val('TARGET');
						var url=map[key].val('FNAME');
						content+=me.createSimCol(target,url);
						count++;
						if(count==3){
							parNode.append(content);
							content='';
							count=0;
						}
					}
			}
			//debugger
				var list=$("#dropmenuright");
				list.empty();
				var doc="<li class='x-menu-item menu-item item-right' xid='item1'>all</i>";
				for(key in this.relationmapright){
					 doc+="<li class='x-menu-item menu-item item-right' xid='item1'>";
					doc+=key+"</i>";
				}
				list.append($(doc));
			
			$('.item-right').on('click',function(){
				//debugger
				var query=$(this)[0].innerText;
				me.RelationChangeRight(query);
			});
			console.log("recommend feature");
		};
		Model.prototype.resultLoad=function(query){
			 var me=this;
			 this.swiper.removeAllSlides();
			 $.ajax({
				url:"http://localhost:8080/seed/getSearchResult",
				data:JSON.stringify(query),
				type:"POST",
				dataType:'json',
				contentType:"application/json",
				success:function(result,status){
					var data=me.comp("resultdata");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					var entityList=result['queryEntity'];
					var featureList=result['queryFeatureList'];
					if(entityList.length==0) justep.Util.hint("we don't have the information of the Entity!");
					for(var i=0;i<entityList.length;i++){
						me.getImageURL(entityList[i]["name"],i+1,1);
					}
					 me.getSearchResult(entityList,featureList);
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});	
	};
	Model.prototype.recommendLoad=function(query){
		this.simcount=0;
		this.simfact=0;
		this.simfeaturecount=0;
		this.featurefact=0;
		this.simfeaturemap={};
		var me=this;
		this.simEntity=null;
		this.simEntityMap={};
		$.ajax({
				url:"http://localhost:8080/seed/getSimEntity",
				data:JSON.stringify(query),
				type:"POST",
				dataType:'json',
				contentType:"application/json",
				success:function(result,status){
					//debugger;
					var data=me.comp("simdata");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					//var categoryList=result['simCategoryList'];
					me.simcount=result.length;
					var list=new Array();
					for(var i=0;i<result.length;i++){
						me.getImageURL(result[i]['name'],i+1,2);
						list.push(result[i]['name']);
						//me.simEntityMap[simList[i]['name']]=categoryList[i]['name'];
					}
					//me.Visualization(query,simList);
					me.simEntity=list;
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});
		$.ajax({
				url:"http://localhost:8080/seed/getSimFeature",
				data:JSON.stringify(query),
				type:"POST",
				dataType:'json',
				contentType:"application/json",
				success:function(result,status){
					//debugger;
					var data=me.comp("simfeature");
					data.directDeleteMode=true;
					data.confirmDelete=false;
					data.deleteAllData();
					//debugger
					me.simfeaturecount=result.length;
					for(var i=0;i<result.length;i++){
						me.saveFeature('',result[i]['relation']['name'],result[i]['target']['name'],i+1,2);
					}
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});
	/*	 var dx=require("jquery");
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
					for(var i=0;i<result.length;i++){*/
					/*	var content="";
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
						}		    					*/
					/*	array.merge(recArray,[result[i]]);
					}
					//debugger
*/			/*		while(i%3!==0){
						doc=doc+col+'</div>';
						i++;
					}
					doc=doc+'</div>';
					$(doc).appendTo(parNode);*/
					/*var data=me.comp("recdata");
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
					me.changeType("2");
				}
			});	*/
	};
	Model.prototype.RelationChange=function(relation){
		 var query=this.currentQuery;
		if(this.querymap.hasOwnProperty(query)){
			this.createFeature(this.querymap[query],relation);
		}
	};
	Model.prototype.RelationChangeRight=function(relation){
		var parNode=$('#rec');
		parNode.empty();
		var dom=$('#checkType');
		this.flag=false;
		if(dom.is(':checked')){
			this.createRecommendSim();
		}
		this.createSimFeature(relation);
	}
	Model.prototype.Visualization=function(query,target){
	//debugger 
			var me=this;
			var list=new Array();
			var dom1=this.getElementByXid("viscontainer");
			var repnum=1000;
			var size=80;
			if(target){
				for(var i=0;i<target.length;i++){
					list.push(target[i]['name']);
				}
			}
			$.ajax({
				url:"http://localhost:8080/seed/getExAllPath",
				data:JSON.stringify({head:query[0],tail:list}),
				type:"POST",
				dataType:'json',
				contentType:'application/json',
				success:function(result,status){
					var desc1="Relation Explaination";
				    var data=result['nodeList'];
				    var links=result['linkList'];
				  /*  for(var i=0;i<data.length;i++){
				    	//console.log(data[i]);
				    }
				     for(var i=0;i<links.length;i++){
				    	//console.log(links[i]);
				    }*/
				    me.data=data;
				    me.link=links;
				   	me.DataVisual(dom1, desc1,data,links,repnum,size);//调用数据可视化函数，主要是通过Echarts控件实现
				   	console.log("visulization");
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});
	};
	Model.prototype.PathVisualization=function(head,tail){
	//debugger 
			var me=this;
			$.ajax({
				url:"http://localhost:8080/seed/getExPath",
				data:JSON.stringify({head:head,tail:tail}),
				type:"POST",
				dataType:'json',
				contentType:'application/json',
				success:function(result,status){
					var dom1=me.getElementByXid("viscontainer");//获取容器实现数据可视化
					var desc1="Relation Explaination";
				    var data=result['vertexList'];
				    var links=result['edgeList'];
				 /*   for(var i=0;i<data.length;i++){
				    	//console.log(data[i]);
				    }
				     for(var i=0;i<links.length;i++){
				    	//console.log(links[i]);
				    }*/
				   	me.PathVisual(dom1, desc1,data,links);//调用数据可视化函数，主要是通过Echarts控件实现
				   	console.log("visulization");
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});
	};
	Model.prototype.button6Click = function(event){
	 console.log(123);
	 //debugger
		var dom=this.getElementByXid('viscontainer');
		var head=$('#selecthead');
		var tail=$('#selecttail');
		head.empty();
		tail.empty();
		var doc='';
		//debugger
		
		for(var i=0;i<this.query.length;i++){
			if(i==0){
				doc='<option value="'+this.query[i]+'" selected="true">'+this.query[i]+'</option>';
			}else{
				doc+='<option value="'+this.query[i]+'">'+this.query[i]+'</option>';
		  }
		}
		head.append(doc);
		doc='<option value="All" selected="true">All</option>';
		for(var i=0;i<this.simEntity.length;i++){
			doc+='<option value="'+this.simEntity[i]+'">'+this.simEntity[i]+'</option>';
		}
		tail.append(doc);
		var desc="Rlation Explaination";
		if(this.data!=null&&this.link!=null){
			this.DataVisual(dom, desc, this.data, this.link, 1000,80);
		}
	};
	Model.prototype.selectheadChange = function(event){
		var head=$('#selecthead').val();
		var tail=$('#selecttail').val();
		var list=new Array();
		if(tail=='All'){
			list.push(head);
			this.Visualization(list);
		}else{
			this.PathVisualization(head, tail);
		}
	};
	Model.prototype.selecttailChange = function(event){
		var head=$('#selecthead').val();
		var tail=$('#selecttail').val();
		var list=new Array();
		if(tail=='All'){
			list.push(head);
			this.Visualization(list);
		}else{
			this.PathVisualization(head, tail);
		}
	};
	Model.prototype.createMetaPath=function(query){
		var me=this;
		$.ajax({
				url:"http://localhost:8080/seed/getMetaPath",
				data:JSON.stringify(query),
				type:"POST",
				dataType:'json',
				contentType:'application/json',
				success:function(result,status){
				//debugger
				    me.relationPath={};
				    me.categoryPath={};
				    for(var key in result['category']){
				    	me.categoryPath[key]=result['category'][key]
				   	}
				   	for(var key in result['relation']){
				   		me.relationPath[key]=result['relation'][key];
				   	}
				    me.createMetaPathSelect();
				   	console.log("visulization");
				},
				error:function(er,status){
					console.log(er.responseText);
				}
			});
	};
	Model.prototype.createMetaPathSelect=function(){
	//debugger
		var dom=$('#categoryselect');
		dom.empty();
		var doc='<option value="All" selected="true">All</option>';
		var data=[{"name":"Query","category":2,"draggable":true}];
		var link=[];
		//var data=null;
		//var link=null;
		var i=0;
		for(var key in this.categoryPath){
			doc+='<option value="'+key+'">'+key+'</option>';
			array.merge(data, [{"name":key,"category":0,"draggable":true}]);
			array.merge(link, [{"source":"Query","target":key,"value":""}]);
		}
		dom.append(doc);
		dom=$('#relationselect');
		dom.empty();
		doc='<option value="All" selected="true">All</option>';
		for(var key in this.relationPath){
			doc+='<option value="'+key+'">'+key+"</option>";
		}
		dom.append(doc);
		var visdom=this.getElementByXid("metapathvis");
		this.MetaPathVisual(visdom,"Meta Path Explore",data,link);
	};
	Model.prototype.categoryselectChange = function(event){
			var head=$('#categoryselect').val();
			var dom=this.getElementByXid("metapathvis");
			var data=[{"name":"Query","category":2,"draggable":true}];
			var link=[];
			for(var key in this.categoryPath){
				array.merge(data, [{"name":key,"category":0,"draggable":true}]);
				array.merge(link, [{"source":"Query","target":key,"value":""}]);
			}
			//console.log(this.categoryPath[head]['nodeList']);
			if(head!="All"){
				array.merge(data,this.categoryPath[head]['nodeList']);
				array.merge(link,this.categoryPath[head]['linkList']);
			}
			this.MetaPathVisual(dom,"Meta Path Explore",data,link);
	};
	Model.prototype.relationselectChange = function(event){
		var head=$('#relationselect').val();
		var dom=this.getElementByXid("metapathvis");
		var data=[{"name":"Query","category":2,"draggable":true}];
		var link=[];
		for(var key in this.relationPath){
				array.merge(data, [{"name":key,"category":1,"draggable":true}]);
				array.merge(link, [{"source":"Query","target":key,"value":""}]);
		}
		if(head!="All"){
			array.merge(data,this.relationPath[head]['nodeList']);
			array.merge(link,this.relationPath[head]['linkList']);
		}
		this.MetaPathVisual(dom,"Meta Path Explore",data,link);
	};
	Model.prototype.switchVisClick = function(event){
		var dom=this.getElementByXid('viscontainer');
		var head=$('#selecthead');
		var tail=$('#selecttail');
		head.empty();
		tail.empty();
		var doc='';
		//debugger
		
		for(var i=0;i<this.query.length;i++){
			if(i==0){
				doc='<option value="'+this.query[i]+'" selected="true">'+this.query[i]+'</option>';
			}else{
				doc+='<option value="'+this.query[i]+'">'+this.query[i]+'</option>';
		  }
		}
		head.append(doc);
		doc='<option value="All" selected="true">All</option>';
		for(var i=0;i<this.simEntity.length;i++){
			doc+='<option value="'+this.simEntity[i]+'">'+this.simEntity[i]+'</option>';
		}
		tail.append(doc);
		var desc="Relation Explaination";
		if(this.data!=null&&this.link!=null){
			this.DataVisual(dom, desc, this.data, this.link, 1000,80);
		}else{
			this.Visualization(this.query);
		}
	};
	Model.prototype.createQuery=function(event){
		var parNode=this.getElementByXid("btncontainer");
		if(parNode){
			var flag={
			xid:"Query1",
			label:event,
			parentNode:parNode,
			'class':'btn btn-success btn-drag'
			};
			//debugger
			new Button(flag);
		}
	}
	return Model;
});
