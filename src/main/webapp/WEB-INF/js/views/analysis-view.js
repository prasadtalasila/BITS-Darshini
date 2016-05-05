window.AnalysisView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage',
			 'click #logout': 'userLogout',
			 'click #populateTable': 'populateTable',
       'click #packetInfo tbody tr': 'rowClick'
		},
		initialize: function () {
			this.delegateEvents();
      this.globalData = [];
		},
    rowClick : function(event){
      //get index from table
      var ind = event.currentTarget.children[0].innerHTML;
      var layers = sessionStorage.getItem('layers').split(',');
      //loop through layers and write to footer
      for(var countForLayers=1;countForLayers< layers.length-1;countForLayers++){
        debugger
        botInfo = this.globalData[ind]["docs"][countForLayers-1]["_source"];
        stringToWrite =  "";
        for(var key in botInfo){
          stringToWrite+= key + ": " + JSON.stringify(botInfo[key]) + "\n";
        }
        //$("#dataContainer1").show();
        $("#dataContainer"+countForLayers).text(stringToWrite);
      }
    },
		populateTable :function(){
      _this = this;
      jQuery('#lowerHalf').html('');
      var sessionName = sessionStorage.getItem('sessionName');
      var layers = sessionStorage.getItem('layers').split(',');

      var layerCount =0; //for knowing number of layers

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
              '<details>'+
                '<summary id = dataContainer'+ i+' ></summary>'+
              '</details>'+
            '</details>'+
          '</article>'+
          '</section>';
      }
      //loop for creating multi-get request and receiving and displaying data
      for(var id = 1;id<=sessionStorage.getItem('packetCount');id++){
        //creating the multi get request from ids and layers
        var multiGetRequest ='{ "docs" : [' ;
        for(var countForLayers = 1;countForLayers<=layerCount;countForLayers++){
          multiGetRequest = multiGetRequest.concat('{ "_type" : "'+layers[countForLayers]+'", "_id" : "'+id+'" },');
			  }
        multiGetRequest = multiGetRequest.substring(0,multiGetRequest.length-1);
        multiGetRequest = multiGetRequest.concat('] }');

        (function(id)
        {
        $.ajax({
			   url : 'http://localhost:9200/protocol_'+sessionName+'/_mget',
			   type : 'POST',
         data : multiGetRequest,
         async: false,
			   contentType : 'application/json; charset=utf-8',
			   success : function(data) {
          _this.globalData[id] = data;
          row = data["docs"][0];
          var td, tr;
          //$("#packetInfo tbody tr").remove(); // clean table
          var tdata = $("#packetInfo tbody")
  			  //for(var row in rows){
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
  			//}
  			var defaultColor = $("#packetInfo tbody tr").css('background-color');
  			//css highlights upon mouse enter
  			$("#packetInfo tbody tr").mouseenter( function(){
  				$(this).css("background-color" ,'rgb(100,149,237)');
  			});
  			//css highlights upon mouse leave
  			$("#packetInfo tbody tr").mouseleave( function(){
  				$(this).css("background-color" ,defaultColor);
  			});
  			//update handlers
  			/*$("#packetInfo tbody tr").click( function(){
    			var ind = $(this).index();
    			botInfo = data["docs"][0]["_source"];
    			stringToWrite =  "";
    			for(var key in botInfo){
      				stringToWrite+= key + ": " + JSON.stringify(botInfo[key]) + "\n";
    			}
    			//$("#dataContainer1").show();
    			$("#dataContainer1").text(stringToWrite);
  			});*/
  			//mouseover should change the cursor back to s-resize
  			$("#lowerHalf").mouseover( function() {
  				if($("#dataContainer1").is(":visible")) {
  					$("#lowerHalf").css('cursor', 's-resize');
  				}
  			});
  			//close data container
  			$("#lowerHalf").click( function() {
  				//$("#dataContainer1").hide();
  				$("#lowerHalf").css('cursor', 'default');
  			});
			},
			error : function() {
				alert("Error connecting to elasticsearch!!")
			}
		});
  /*else{
    $.ajax({
      url : 'http://localhost:9200/protocol_'+sessionName+'/'+layers[id]+'/_search?&size=214',
      type : 'GET',
      contentType : 'application/json; charset=utf-8',
      dataType : 'text',
      success : function(data) {
        //update handlers
        $("#packetInfo tbody tr").click( function(){
          var ind = $(this).index();
          botInfo = data["hits"]["hits"][ind];
          debugger
          stringToWrite =  "";
          for(var key in botInfo){
              stringToWrite+= key + ": " + JSON.stringify(botInfo[key]) + "\n";
          }
          //$("#dataContainer2").show();
          $("#dataContainer"+id).text(stringToWrite);
        });
        //mouseover should change the cursor back to s-resize
        $("#lowerHalf").mouseover( function() {
          if($("#dataContainer"+id).is(":visible")) {
            $("#lowerHalf").css('cursor', 's-resize');
          }
        });
        //close data container
        $("#lowerHalf").click( function() {
          //$("#dataContainer2").hide();
          $("#lowerHalf").css('cursor', 'default');
        });
      },
      error : function() {
        alert("Error connecting to elasticsearch!!")
      }
    });
    }*/
    })(id);
    }
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
		render: function () {
        	$(this.el).html(this.template());

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
