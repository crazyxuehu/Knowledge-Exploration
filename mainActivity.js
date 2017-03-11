define(function(require){
	require("jquery");
	require("css!./css/dragula").load();
	require("css!./css/swiper.min").load();
	require("./js/swiper.min");
	
	var justep = require("$UI/system/lib/justep");
	var Model = function(){
		this.callParent();
	};
	Model.prototype.modelLoad=function(event){

		var me=this;
		var swiper = new Swiper('.swiper-container', {
		pagination: '.swiper-pagination',
		paginationHide :true,
        effect: 'slide',
        grabCursor:true,
        loop:true,
        paginationType : 'custom',
        paginationCustomRender: function (swiper, current, total) {
			me.comp("buttonswip").set({
			"label":current,
			});
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
		var dragfactor0=this.getElementByXid("col40");
		var dragfactor1=this.getElementByXid("col41");
		var dragfactor2=this.getElementByXid("col42");
		var dragfactor3=this.getElementByXid("col43");
		var dragfactor4=this.getElementByXid("col44");
		var dragfactor5=this.getElementByXid("col45");
		var drag=require("./js/dragula");
		drag([container],{removeOnSpill:true});//搜索区域拖拽删除
		drag([dragfactor0,container],{copy:true});//费搜索区域复制拖拽
		drag([dragfactor1,container],{copy:true});
		drag([dragfactor2,container],{copy:true});
		drag([dragfactor3,container],{copy:true});
		drag([dragfactor4,container],{copy:true});
		drag([dragfactor5,container],{copy:true});
		this.MenuSlide();
	};
	Model.prototype.DataVisul = function(dom,desc){
		var echarts=require("./js/echarts");
		var mycharts=echarts.init(dom);
		var option = {
		    title: {
		        text: desc,
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
		                        show: true
		                    }
		                },
		                lineStyle: {
		                    normal: {
		                        width: 5,
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
					menuButton.classList.add('cross')
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
	return Model;
});