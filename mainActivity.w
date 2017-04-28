<?xml version="1.0" encoding="UTF-8"?>

<div xmlns="http://www.w3.org/1999/xhtml" xid="window" class="window x-full-screen" component="$UI/system/components/justep/window/window" design="device:m;">  
  <div component="$UI/system/components/justep/model/model" xid="model" style="height:auto;top:256px;left:50px;" onLoad="modelLoad"><div component="$UI/system/components/justep/data/data" autoLoad="true" xid="searchdata" idColumn="id"><column label="QID" name="id" type="Integer" xid="xid1"></column>
  <column label="searchID" name="Qname" type="String" xid="xid2"></column>
  <column label="entity" name="Ename" type="String" xid="xid3"></column></div>
  <div component="$UI/system/components/justep/data/data" autoLoad="false" xid="resultdata" autoNew="false" idColumn="ENAME"><column label="实体名称" name="ENAME" type="String" xid="xid5"></column>
  <column label="实体图片" name="FNAME" type="String" xid="xid6"></column>
  <column label="实体类型" name="TYPE" type="String" xid="xid7"></column>
  <column label="行号" name="ID" type="Integer" xid="xid4"></column>
  <column label="描述" name="DESC" type="String" xid="xid12"></column></div>
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="recdata" idColumn="ID"><column label="菜品ID" name="ID" type="String" xid="xid8"></column>
  <column label="菜品图片" name="FNAME" type="String" xid="xid9"></column>
  <column label="菜品名称" name="ENAME" type="String" xid="xid10"></column>
  <column label="菜品类型" name="TYPE" type="String" xid="xid11"></column></div>
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="featuredata" idColumn="QID"><column isCalculate="false" label="queryid" name="QID" type="Integer" xid="xid13"></column>
  <column label="relation" name="RELATION" type="String" xid="xid14"></column>
  <column label="target" name="TARGET" type="String" xid="xid15"></column>
  <column label="src" name="FNAME" type="String" xid="xid16"></column>
  <column label="id" name="ID" type="Integer" xid="xid17"></column></div>
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="simdata" idColumn="ID"><column label="simEntity" name="TARGET" type="String" xid="xid18"></column>
  <column label="src" name="FNAME" type="String" xid="xid19"></column>
  <column label="id" name="ID" type="Integer" xid="xid20"></column></div>
  <div component="$UI/system/components/justep/data/data" autoLoad="true" xid="simfeature" idColumn="ID"><column label="relation" name="RELATION" type="String" xid="xid21"></column>
  <column label="target" name="TARGET" type="String" xid="xid22"></column>
  <column label="url" name="FNAME" type="String" xid="xid23"></column>
  <column label="id" name="ID" type="Integer" xid="xid24"></column></div></div>
<!--   <div xid="div13" style="height:100%;width:100%;">
  <div style="height:100%;width:100%;">
  <div xid="div50" style="height:100%;width:100%;"> -->
  
  
  
   
  <div component="$UI/system/components/justep/contents/contents" class="x-contents x-full" active="0" xid="contents1">
   <div class="x-contents-content" xid="content1"><div xid="div13" style="height:100%;width:100%;" class="swiper-container1">
   <div xid="div8" style="height:100%;width:100%;" class="swiper-wrapper">
    <div xid="div7" style="width:20%;height:100%;pull-left" class="swiper-slide menu menu-slide">
     <div class="panel panel-default x-card" component="$UI/system/components/bootstrap/panel/panel" xid="panel2" style="height:100%;">
      <div class="panel-heading" xid="heading1" style="height:10%;">
       <h4 class="pull-left panel-title" xid="h41" style="width:100%;">Search</h4></div> 
      <div class="panel-body  x-scroll-view" xid="body1" style="height:80%;">
       <div class="x-scroll" component="$UI/system/components/justep/scrollView/scrollView" xid="scrollView2">
        <div class="x-content-center x-pull-down container" xid="div4">
         <i class="x-pull-down-img glyphicon x-icon-pull-down" xid="i4"></i>
         <span class="x-pull-down-label" xid="span4">下拉刷新...</span></div> 
        <div class="x-scroll-content" xid="menucontent" style="height:100%;">
         <div component="$UI/system/components/justep/list/list" class="x-list x-cards" xid="list2" style="height:100%;">
          <ul class="x-list-template x-min-height list-group" xid="listTemplateUl1" componentname="$UI/system/components/justep/list/list#listTemplateUl" id="undefined_listTemplateUl1">
           <li xid="menulist" class="x-min-height"></li></ul> </div> </div> 
        <div class="x-content-center x-pull-up" xid="div9">
         <span class="x-pull-up-label" xid="span5"></span></div> </div> </div> 
      <div class="panel-footer" xid="footer1" style="height:10%;"></div></div> </div> 
    <div xid="div6" style="position:relative;height:100%;width:100%;" class="swiper-slide content">
     <div component="$UI/system/components/bootstrap/row/row" class="row" id="top" xid="row1" style="height:10%;padding-left:10px;padding-right:10px;">
      <div class="col" xid="col1" style="width:100%;height:100%;padding-top:10px;padding-left:10px;">
       <div component="$UI/system/components/justep/button/button" class="btn pull-left btn-color menu-button" style="margin-right:5px;" label="Q1" xid="Qbtn">
        <i xid="i1"></i>
        <span xid="span1">Q1</span></div> 
       <a component="$UI/system/components/justep/button/button" class="btn btn-primary btn-sm btn-only-icon pull-right" label="button" xid="searchbtn" icon="linear linear-book" onClick="searchbtnClick">
        <i xid="i9" class="linear linear-book"></i>
        <span xid="span9"></span></a> 
       <input component="$UI/system/components/justep/input/input" id="inputdata" class="form-control pull-right" list="keyword" xid="input" style="font-size:25px;width:40%;margin-right:10px;"></input>
       <div component="$UI/system/components/justep/button/buttonGroup" class="btn-group" tabbed="true" xid="btncontainer" style="height:40px;width:50%;"></div>
       <ul class="list pull-right" xid="ul1"></ul></div> </div> 
     <div component="$UI/system/components/bootstrap/row/row" class="row" xid="row2" style="height:60%;padding-left:10px;">
      <div class="col pull-left" xid="col4" style="width:50%;height:100%;">
       <div component="$UI/system/components/justep/row/row" class="x-row" xid="row20" style="height:100%;">
        <div class="x-col" xid="col25" style="height:100%;">
         <div component="$UI/system/components/justep/row/row" class="x-row" xid="row16" style="height:10%;width:80%;padding-left:20px;">
          <div class="x-col font-auto" xid="col20" style="width:100%;height:100%;padding-left:10px;">Query Results</div></div> 
         <div component="$UI/system/components/justep/row/row" class="x-row " xid="row10" style="height:42%;width:80%;padding-left:10px;">
          <div class="x-col" xid="col28" style="height:100%;width:100%;padding-top:0%;padding-right:10%;">
           <div xid="divcontainer" id="divcontainer" style="width:100%;height:100%;" class="swiper-container">
            <div xid="divswip" id="divswip" style="height:100%;width:100%;" class="swiper-wrapper"></div>
            <div xid="div15" class="swiper-pagination"></div></div> </div> </div> 
         <div component="$UI/system/components/justep/row/row" class="x-row" xid="row11" style="height:14%;width:80%;">
          <div class="x-col" xid="drag3" style="height:100%;width:100%;">
           <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-result" label="Entity" xid="buttonswip">
            <i xid="i16"></i>
            <span xid="span16"></span></a> </div> </div> 
         <div component="$UI/system/components/justep/row/row" class="x-row" xid="row8" style="height:34%;width:100%;">
          <div class="x-col" xid="col9" style="height:100%;width:100%;">
           <div xid="div11" id="desc" class="abstract">hello word! how are you! I'm fine thank you!hello word! how are you! I'm fine thank you!</div>
           <a href="#" class="pull-right" style="margin-right:20%;" xid="a1">view more</a></div> </div> </div> 
        <div class="x-col" xid="col26" style="height:100%;width:75%;">
         <div component="$UI/system/components/justep/row/row" class="x-row pull-right" xid="row12" style="height:100%;width:120%;">
          <div class="x-col" xid="col34" style="height:100%;width:100%;">
           <div component="$UI/system/components/justep/row/row" class="x-row" xid="row15" style="width:100%;height:12%;">
            <div class="x-col" xid="col16" style="height:100%;width:100%;">
             <div class="dropdown btn-group pull-left" component="$UI/system/components/bootstrap/dropdown/dropdown" xid="dropdown1">
              <a component="$UI/system/components/justep/button/button" class="btn dropdown-relation btn-icon-right dropdown-toggle" label="Relation" icon="icon-arrow-down-b" xid="button1" style="height:90%;width:100%;margin-right:42px;">
               <i class="icon-arrow-down-b" xid="i2"></i>
               <span xid="span2">Relation</span></a> 
              <ul component="$UI/system/components/justep/menu/menu" class="dropdown-list dropdown-menu" xid="dropmenu">
               <li class="menu-item x-menu-item" xid="item1">nationality</li>
               <li class="menu-item x-menu-item" xid="item2">Director</li></ul> </div> </div> </div> 
           
  <div component="$UI/system/components/justep/row/row" class="x-row" xid="row21" style="width:100%;height:92%;">
   <div class="x-col  x-scroll-view" xid="drag1" style="width:100%;height:100%;">
   <div component="$UI/system/components/justep/scrollView/scrollView" xid="scrollView1" style="height:100%;width:100%;" class="x-scroll">
    <div class="x-content-center x-pull-down container" xid="div1">
     <i class="x-pull-down-img glyphicon x-icon-pull-down" xid="i31"></i>
     <span class="x-pull-down-label" xid="span31">下拉刷新...</span></div> 
    <div class="x-scroll-content" xid="leftcontainer" id="leftcontainer" style="height:90%;">
     <div class="x-row row-size pull-right" xid="row4">
      <div class="x-col" xid="col10" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-info btn-left" label="Type" xid="button4">
        <i xid="i6"></i>
        <span xid="span7">Type</span></a> 
       <img src="./img/2.jpg" alt="" xid="image19" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button10">
        <i xid="i10"></i>
        <span xid="span10"></span></a> </div> 
      <div class="x-col" xid="col11" style="height:100%;width:100%;">
       <div class="btn-left" xid="div12"></div>
       <img src="./img/3.jpg" alt="" xid="image17" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button11">
        <i xid="i11"></i>
        <span xid="span11"></span></a> </div> 
      <div class="x-col" xid="col12" style="height:100%;width:100%;">
       <div class="btn-left" xid="div14"></div>
       <img src="./img/3.jpg" alt="" xid="image18" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button12">
        <i xid="i12"></i>
        <span xid="span12"></span></a> </div> </div> 
     <div class="x-row row-size pull-right" xid="row5">
      <div class="x-col" xid="col24" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button5">
        <i xid="i7"></i>
        <span xid="span8">Relation</span></a> 
       <img src="./img/4.jpg" alt="" xid="image7" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button13">
        <i xid="i14"></i>
        <span xid="span13"></span></a> </div> 
      <div class="x-col" xid="col23" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button8">
        <i xid="i36"></i>
        <span xid="span37">Relation</span></a> 
       <img src="./img/5.jpg" alt="" xid="image5" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button15">
        <i xid="i13"></i>
        <span xid="span15"></span></a> </div> 
      <div class="x-col" xid="col22" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button9">
        <i xid="i37"></i>
        <span xid="span38">Relation</span></a> 
       <img src="./img/6.jpg" alt="" xid="image6" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button14">
        <i xid="i15"></i>
        <span xid="span14"></span></a> </div> </div> 
     <div class="x-row row-size pull-right" xid="row6">
      <div class="x-col" xid="col2" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button16">
        <i xid="i18"></i>
        <span xid="span17"></span></a> 
       <img src="./img/4.jpg" alt="" xid="image16" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button34">
        <i xid="i40"></i>
        <span xid="span41"></span></a> </div> 
      <div class="x-col" xid="col3" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button35">
        <i xid="i41"></i>
        <span xid="span42">Relation</span></a> 
       <img src="./img/5.jpg" alt="" xid="image8" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button17">
        <i xid="i3"></i>
        <span xid="span18"></span></a> </div> 
      <div class="x-col" xid="col5" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button36">
        <i xid="i42"></i>
        <span xid="span43">Relation</span></a> 
       <img src="./img/6.jpg" alt="" xid="image9" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button3">
        <i xid="i17"></i>
        <span xid="span3"></span></a> </div> </div> 
     <div class="x-row row-size" xid="row7">
      <div class="x-col col-size" xid="col8">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button37">
        <i xid="i43"></i>
        <span xid="span44">Relation</span></a> 
       <img src="./img/2.jpg" alt="" xid="image21" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button18">
        <i xid="i33"></i>
        <span xid="span33"></span></a> </div> 
      <div class="x-col" xid="col7" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button38">
        <i xid="i44"></i>
        <span xid="span45">Relation</span></a> 
       <img src="./img/3.jpg" alt="" xid="image20" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button32">
        <i xid="i32"></i>
        <span xid="span35"></span></a> </div> 
      <div class="x-col" xid="col6" style="height:100%;width:100%;">
       <a component="$UI/system/components/justep/button/button" class="btn btn-relation btn-left" label="Relation" xid="button39">
        <i xid="i45"></i>
        <span xid="span46">Relation</span></a> 
       <img src="./img/3.jpg" alt="" xid="image22" class="img-rounded img-left-size"></img>
       <a component="$UI/system/components/justep/button/button" class="btn btn-success btn-left" label="Entity" xid="button31">
        <i xid="i34"></i>
        <span xid="span34"></span></a> </div> </div> </div> 
    <div class="x-content-center x-pull-up" xid="div3"></div></div> </div></div></div> </div> 
  </div> </div> </div> 
      <div class="col pull-right" id="bom" xid="factor" style="width:50%;height:100%;padding-right:10px;">
       <div component="$UI/system/components/justep/row/row" class="x-row" xid="row9" style="width:100%;height:13%;">
        <div class="x-col" xid="col15" style="width:100%;height:100%;">
         <div xid="div2" class="pull-left font-auto" style="height:100%;width:40%;">Recommend Results</div>
         <label class="pull-left" style="margin-right:10px;font-size:20px;color:#ec971f;" xid="label3">Relation
          <input id="checkRelation" class="mui-switch mui-switch-animbg" type="checkbox" checked="true" style="margin-right:15px;" data-bind="click:checkChange" xid="checkbox1"></input></label> 
         <label class="pull-left" style="font-size:20px;color:#5bc0de" xid="label4">Type
          <input id="checkType" class="mui-switch mui-switch-animbg" type="checkbox" checked="true" style="margin-right:15px;" data-bind="click:checkChange" xid="checkbox2"></input></label> </div> </div> 
       <div component="$UI/system/components/justep/row/row" class="x-row" xid="row13" style="width:100%;height:90%;">
        <div class="x-col x-scroll-view" xid="col18" style="height: 100%; width: 100%;">
         <div class="x-scroll" component="$UI/system/components/justep/scrollView/scrollView" xid="scrollView3" style="height:100%;width:100%;">
          <div class="x-content-center x-pull-down container" xid="div18">
           <i class="x-pull-down-img glyphicon x-icon-pull-down" xid="i39"></i>
           <span class="x-pull-down-label" xid="span40">下拉刷新...</span></div> 
          <div class="x-scroll-content" xid="drag2" style="height:90%;">
           <div xid="div10" id="rec" style="height:100%;width:100%;">
            <div component="$UI/system/components/justep/row/row" class="x-row row-size" xid="row19">
             <div class="x-col col-size" xid="col43">
              <div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" label="Type" xid="button30">
               <i xid="i25"></i>
               <span xid="span30">Type</span></div> 
              <img src="./img/3.jpg" alt="" xid="image15" class="img-rounded img-size img-right-size"></img>
              <div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size" label="Entity" xid="button27">
               <i xid="i26"></i>
               <span xid="span27">Entity</span></div> </div> 
             <div class="x-col col-size" xid="col44">
              <div component="$UI/system/components/justep/button/button" class="btn btn-relation btn-size" label="Relation" xid="button26">
               <i xid="i28"></i>
               <span xid="span26">Type</span></div> 
              <img src="./img/4.jpg" alt="" xid="image14" class="img-rounded img-size img-right-size"></img>
              <div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size" label="Entity" xid="button29">
               <i xid="i27"></i>
               <span xid="span29">Entity</span></div> </div> 
             <div class="x-col col-size" xid="col45">
              <div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" label="Type" xid="button28">
               <i xid="i29"></i>
               <span xid="span28">Type</span></div> 
              <img src="./img/5.jpg" alt="" xid="image13" class="img-rounded img-size img-right-size"></img>
              <div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size" label="Entity" xid="button25">
               <i xid="i30"></i>
               <span xid="span25">Entity</span></div> </div> </div> 
            <div component="$UI/system/components/justep/row/row" class="x-row row-size" xid="row14">
             <div class="x-col col-size" xid="col40">
              <div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" label="Type" xid="button19">
               <i xid="i19"></i>
               <span xid="span19">Type</span></div> 
              <img src="./img/7.jpg" alt="" xid="image10" class="img-rounded img-size img-right-size"></img>
              <div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size" label="Entity" xid="button20" draggable="true" ondragstart="drag(event)">
               <i xid="i20"></i>
               <span xid="span20">Entity</span></div> </div> 
             <div class="x-col col-size" xid="col41">
              <div component="$UI/system/components/justep/button/button" class="btn btn-size btn-relation" label="Relation" xid="button21">
               <i xid="i21"></i>
               <span xid="span21">Relation</span></div> 
              <img src="./img/8.jpg" alt="" xid="image11" class="img-rounded img-size img-right-size"></img>
              <div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size" label="Entity" xid="button22">
               <i xid="i22"></i>
               <span xid="span22">Entity</span></div> </div> 
             <div class="x-col col-size" xid="col42">
              <div component="$UI/system/components/justep/button/button" class="btn btn-info btn-size" label="Type" xid="button23">
               <i xid="i24"></i>
               <span xid="span23">Type</span></div> 
              <img src="./img/9.jpg" alt="" xid="image12" class="img-rounded img-size img-right-size"></img>
              <div component="$UI/system/components/justep/button/button" class="btn btn-success btn-size" label="Entity" xid="button24">
               <i xid="i23"></i>
               <span xid="span24">Entity</span></div> </div> </div> </div> </div> 
          <div class="x-content-center" xid="div20" style="height:5%;width:100%;"></div></div> </div> </div> </div> </div> 
     <div component="$UI/system/components/justep/row/row" class="x-row" xid="row3" style="height:30%;">
      <div class="x-col x-col-fixed" xid="colgraph" style="width:100%;height:100%;">
       <div xid="relaEx" style="height:100%;width:46%;" class="pull-left"></div>
       <div xid="divvis" class="pull-right" style="height:100%;width:50%;"><div component="$UI/system/components/justep/row/row" class="x-row" xid="row22" style="width:100%;height:30%;">
   <div class="font-auto pull-left" xid="col14" style="height:100%;width:43%;">Meta path Explaination
  </div>
   <div class="pull-right" xid="col27" style="height:100%;width:57%;"><div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelEdit3" style="width:100%;height:50%;">
   <label class="x-label" xid="label5">Category</label>
   <select component="$UI/system/components/justep/select/select" class="form-control x-edit meta-select" id="categoryselect" xid="categoryselect" onChange="categoryselectChange"></select></div><div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelEdit4" style="width:100%;height:50%;">
   <label class="x-label" xid="label6"><![CDATA[Relation]]></label>
   <select component="$UI/system/components/justep/select/select" class="form-control x-edit meta-select" id="relationselect" xid="relationselect" onChange="relationselectChange"></select></div>
  </div></div>
  <div component="$UI/system/components/justep/row/row" class="x-row" xid="row23" style="width:100%;height:70%;">
   <div class="" xid="metapath" style="width:100%;height:100%;"></div>
   </div></div>
       <a component="$UI/system/components/justep/button/button" class="btn btn-link btn-only-icon" xid="button6" icon="linear linear-bus" label="more" target="content2" onClick="button6Click">
        <i xid="i8" class="linear linear-bus"></i>
        <span xid="span32">more</span></a> </div> </div> 
     <div component="$UI/system/components/justep/tabs/tabs" class="x-tabs" xid="tabs2" style="height:0%;width:0%;">
      <div component="$UI/system/components/justep/panel/panel" class="x-panel" xid="panel3">
       <div class="x-panel-content" xid="content3">
        <div component="$UI/system/components/justep/contents/contents" class="x-contents" xid="contents2"></div></div> </div> </div> </div> </div> </div></div>
  <div class="x-contents-content" xid="content2"><div component="$UI/system/components/justep/row/row" class="x-row" xid="row17" style="height:10%;width:100%;">
   <div class="x-col" xid="col17" style="height:100%;width:100%;">
    <div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelEdit1">
     <label class="x-label label-head" xid="label1" style="height:22px;">Head Entity</label>
     <select component="$UI/system/components/justep/select/select" class="form-control x-edit" id="selecthead" xid="selecthead" onChange="selectheadChange"></select></div> </div> 
   <div class="x-col" xid="col13" style="height:100%;width:100%;">
    <div component="$UI/system/components/justep/labelEdit/labelEdit" class="x-label-edit x-label30" xid="labelEdit2">
     <label class="x-label label-tail" xid="label2">Tail Entity</label>
     <select component="$UI/system/components/justep/select/select" class="form-control x-edit" id="selecttail" xid="selecttail" onChange="selecttailChange"></select></div> </div> </div>
  <div component="$UI/system/components/justep/row/row" class="x-row" xid="row18" style="height:90%;width:100%;">
   <div class="x-col" xid="col19" style="width:100%;height:100%;">
    <div xid="viscontainer" style="width:100%;height:100%;"></div></div> </div></div></div></div>