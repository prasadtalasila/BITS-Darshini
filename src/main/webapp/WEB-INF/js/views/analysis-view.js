window.AnalysisView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage',
			 'click #logout': 'userLogout',
			 'click #populateTable': 'populateTable'
		},
		initialize: function () {
			this.delegateEvents();
		},
		populateTable :function(){
      var sessionName = sessionStorage.getItem('sessionName');
      var layers = sessionStorage.getItem('layers').split(',');

      var layerCount =0; //for knowing number of ajax requests
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
      
			$.ajax({
			url : 'http://localhost:9200/protocol_session_-1001725913/ethernet/_search?&size=214',
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'text',
			success : function(data) {
				alert("Success connecting to elasticsearch..");
        var globalData = JSON.parse(data);
        rows = globalData["hits"]["hits"];
        var td, tr;
        $("#packetInfo tbody tr").remove(); // clean table
        var tdata = $("#packetInfo tbody")
  			for(var row in rows){
    			var rowSource = rows[row]["_source"]
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
  			$("#packetInfo tbody tr").click( function(){
    			var ind = $(this).index();
    			botInfo = globalData["hits"]["hits"][ind];
    			stringToWrite =  "";
    			for(var key in botInfo){
      				stringToWrite+= key + ": " + JSON.stringify(botInfo[key]) + "\n";
    			}
    			//$("#dataContainer1").show();
    			$("#dataContainer1").text(stringToWrite);
  			});
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
    for(var counter = 2;counter<=layerCount;counter++){
    (function(counter)
    {
    $.ajax({
      url : 'http://localhost:9200/protocol_session_-1001725913/'+layers[counter]+'/_search?&size=214',
      type : 'GET',
      contentType : 'application/json; charset=utf-8',
      dataType : 'text',
      success : function(data) {
        var globalData = JSON.parse(data);
        //update handlers
        $("#packetInfo tbody tr").click( function(){
          var ind = $(this).index();
          botInfo = globalData["hits"]["hits"][ind];
          stringToWrite =  "";
          for(var key in botInfo){
              stringToWrite+= key + ": " + JSON.stringify(botInfo[key]) + "\n";
          }
          //$("#dataContainer2").show();
          $("#dataContainer"+counter).text(stringToWrite);
        });
        //mouseover should change the cursor back to s-resize
        $("#lowerHalf").mouseover( function() {
          if($("#dataContainer"+counter).is(":visible")) {
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
    })(counter);
    }
		},
		counter : function(){
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
        	return this;
		}
	});
