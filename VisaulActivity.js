define(function(require){
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	
	var Model = function(){
		this.callParent();
	};
	Model.prototype.modelLoad=function(event){
		this.modelParamsReceive();
	};
	Model.prototype.modelParamsReceive = function(event){
        var context = this.getContext();
         
      /*  var data = context.getRequestParameter('data');
        var link = context.getRequestParameter('link');*/
       /* var buf = '来自url的参数: p1=' + p1 + ', p2=' + p2 + '\n';*/
         
		
      /*  //获取简单参数
        buf += '简单参数：params.a1=' + event.params.a1 + ', params.a2=' + event.params.a2 + '\n';
         
        //获取复杂参数
        buf += '复杂参数：\n'
        if (event.params.data){
            buf += '    params.data.d1=' + event.params.data.d1 + '\n';
            buf += '    params.data.d2=' + event.params.data.d2 + '\n';
        }
        alert(buf);*/
        debugger
        var data=event.params.data;
		var link=event.params.link;
        var dom=this.getElementByXid('viscontainer');
        var desc='Relation Explaination';
        this.Visualization(dom,desc,data,link);
    };
    Model.prototype.Visualization=function(dom,desc,data,link){
    	var echarts=require('./js/echarts');
        var myCharts=echarts.init(dom);
        var option = {
        title:{
      	  show:true,
      	  text:desc,
      	  textStyle:{
      		  color:'gray',
  		      fontStyle:'italic',
  		      fontWeight:800,
  		      fontSize:25
      	  },
      	  link:'VisaulActivity.w?data='+data+'&link='+link,
      	  target:'self'
        },
      tooltip: {
          show: false
      },
      legend: {
          x: "right",
          data: ["Category", "Entity"]
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
             name:'Start',
             itemStyle:{
          	   normal:{
          		  
          	   }
             }
             }, 
             {
             name:'Target',
             itemStyle:{
          	   normal:{
          		  color: "#EC971F", 
          	   }
             }
          }],
          type: 'graph',
          layout: 'force',
          symbol: "circle",
          symbolSize: 50,
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
              }
          },
          force: {
              repulsion:100
          },
          data: data,
          links:link
      }]
  };
        myCharts.setOption(option);  
    };
	Model.prototype.button1Click = function(event){
		this.close();
	};
	return Model;
});