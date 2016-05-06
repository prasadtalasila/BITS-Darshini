window.AnalysisView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage',
			 'click #logout': 'userLogout',
			 'click #populateTable': 'footerDisplay',
       'click #packetInfo tbody tr': 'rowClick',
       'slidechange #slider': 'setPrefetchValue'
		},
		initialize: function () {
			this.delegateEvents();
      this.globalData = [];
		},
    setPrefetchValue : function(){
      sessionStorage.setItem('sliderValue',$('#slider').slider("option", "value"));  
    },
    rowClick : function(event){
      //get index from table
      var currentRow = event.currentTarget.children[0].innerHTML;
      //count number of layers, subtract 2 to discount start and endof p4 graph
      var layersCount = sessionStorage.getItem('layers').split(',').length -2;
      var packetCount = sessionStorage.getItem('packetCount');
      //loop through layers and write to footer
      for(var countForLayers=0;countForLayers<layersCount;countForLayers++){
        botInfo = this.globalData["docs"][(currentRow-1)+(countForLayers*packetCount)]["_source"];
        var packetDetails="";
        for(var key in botInfo){
          packetDetails += '<span class ="footerPacketDetails">\t'+key + " : " + JSON.stringify(botInfo[key]).replace(/\"/g, "") +' </span>';
        }
        var countForLayers2 = countForLayers+1;
        document.getElementById("dataContainer"+countForLayers2).innerHTML = packetDetails;
      }

      //multi get data based on which row user is on
      var tablePacketInfo = document.getElementById('packetInfo');
      var lastRow = tablePacketInfo.rows.length-1;
      var rowDiff = lastRow - currentRow;
      var sliderValue = sessionStorage.getItem('sliderValue'); 
      if(rowDiff< sliderValue){
        this.multiGet(lastRow+1,lastRow +(sliderValue - rowDiff),layersCount);
      }
    },
		footerDisplay :function(){
      _this = this;
      $('#lowerHalf').html('');
      var sessionName = sessionStorage.getItem('sessionName');
      var layers = sessionStorage.getItem('layers').split(',');

      var layerCount =0; //for knowing number of layers
      var layers = sessionStorage.getItem('layers').split(',');    
      //loop for appending required number of summary tags in the footer for full display of each layer
      var lowerHalfAppend = document.getElementById('lowerHalf');
      for(var i =1;i<layers.length -1;i++){
        layerCount++;
        var newDivId = 'dataContainer'+i;
        var newDiv = document.createElement('section');
        newDiv.setAttribute('id',newDivId);
        lowerHalfAppend.appendChild(newDiv);
        newDiv.outerHTML = '<section>'+
          '<article>'+
            '<details>'+
              '<summary >'+layers[i].charAt(0).toUpperCase() + layers[i].slice(1)+'</summary>'+
                '<summary id = dataContainer'+ i+' ></summary>'+
            '</details>'+
          '</article>'+
          '</section>';
      }

      //function creates multi-get request and receiving and displaying data initially
      this.multiGet(1,sessionStorage.getItem('sliderValue'),layerCount);
		},
		userHelpPage : function(){
			window.open("https://github.com/prasadtalasila/packetanalyzer",'_blank');
		},
		userLogout  : function(){
      sessionStorage.clear();
			Cookies.remove('userName');
			Cookies.remove('userAuth');		
			app.navigate("#",{trigger: true});
			alert("You have been logged out. Please login to continue");
        	return false;
		},
    multiGet : function(startId,endId,layerCount){
      if(endId > sessionStorage.getItem('packetCount')){
        endId = sessionStorage.getItem('packetCount');
      }
      if(startId<endId){
        var sessionName = sessionStorage.getItem('sessionName');
        var layers = sessionStorage.getItem('layers').split(',');
        var multiGetRequest ='{ "docs" : [' ;
        for(var countForLayers = 1;countForLayers<=layerCount;countForLayers++){
          //creating the multi get request from ids and layers
          for(var id = startId;id<=endId;id++){
            multiGetRequest = multiGetRequest.concat('{ "_type" : "'+layers[countForLayers]+'", "_id" : "'+id+'" },');
          }
        }
        multiGetRequest = multiGetRequest.substring(0,multiGetRequest.length-1);
        multiGetRequest = multiGetRequest.concat('] }');
          //AJAX call for getting data
          $.ajax({
            url : 'http://localhost:9200/protocol_'+sessionName+'/_mget',
            type : 'POST',
            data : multiGetRequest,
            contentType : 'application/json; charset=utf-8',
            success : function(data) {
              _this.globalData = data;
              _this.populateTable(startId,endId);
            },
            error : function() {
              alert("Error connecting to elasticsearch!!")
            }
          });
        }
    },
    populateTable:function(startId,endId){
      for(var id=startId-1; id<endId;id++){
          var row = this.globalData["docs"][id];
          var td, tr;
          var tdata = $("#packetInfo tbody")
          var rowSource = row["_source"];
          tr = $("<tr>");
          //packetId
          td = $("<td>").text(rowSource["packetId"]);
          tr.append(td);
          //Source MAC
          td = $("<td>").text(rowSource["src_addr"]);
          tr.append(td);
          //Dest MAC
          td = $("<td>").text(rowSource["dst_addr"]);
          tr.append(td);
          tdata.append(tr);
      }
    },
		render: function () {
        	$(this.el).html(this.template());

          //slider initialization  
          $(function() {
            $("#slider").slider({
              range: "max",
              min: 20,
              max: 1000,
              step:10,
              value: 50,
              slide: function( event, ui ) {
                $("#prefetch-amount").val(ui.value);
              }
            });
            $("#prefetch-amount").val($("#slider").slider("value"));
          });

          //collapsible sidebar intialization
          (function() {
            $(function() {
              var collapseMyMenu, expandMyMenu, hideMenuTexts, showMenuTexts,expandMainMenu,collapseMainMenu;
              expandMyMenu = function() {
                return $("nav.sidebar").removeClass("sidebar-menu-collapsed").addClass("sidebar-menu-expanded");
              };
              expandMainMenu = function() {
                return $(".main").removeClass("normal-width").addClass("max-width");
              };
              collapseMyMenu = function() {
                return $("nav.sidebar").removeClass("sidebar-menu-expanded").addClass("sidebar-menu-collapsed");
              };
              collapseMainMenu = function() {
                return $(".main").removeClass("max-width").addClass("normal-width");
              };
              showMenuTexts = function() {
                return $("nav.sidebar ul a span.expanded-element").show();
              };
              hideMenuTexts = function() {
                return $("nav.sidebar ul a span.expanded-element").hide();
              };
              return $("#justify-icon").click(function(e) {
              if ($(this).parent("nav.sidebar").hasClass("sidebar-menu-collapsed")) {
                expandMyMenu();
                showMenuTexts();
                collapseMainMenu();
                $(this).css({
                color: "#00ACE1"
              });
              } else if ($(this).parent("nav.sidebar").hasClass("sidebar-menu-expanded")) {
                collapseMyMenu();
                expandMainMenu();
                hideMenuTexts();
                $(this).css({
                color: "#FFF"
              });
            }
            return false;
          });
        });
      }).call(this);
    	return this;
		}
});
