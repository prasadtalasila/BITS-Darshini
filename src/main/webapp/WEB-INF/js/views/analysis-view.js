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
		populate: function(){ 
			//var numberOfColumns = document.getElementById("packetInfo").rows[0].cells.length;
			/*var table = document.getElementById("packetInfo").getElementsByTagName('tbody')[0];
			var row = table.insertRow(table.rows.length);
			//run check here to see if length of data exceeds no. of columns in table
			for(i=0;i<10;i++){
			var cell = row.insertCell(i);
			cell.innerHTML = "Test Data"+i;
			}
			$('.show_hide').showHide({			 
				speed: 1400,  // speed you want the toggle to happen	
				easing: '',  // the animation effect you want. Remove this line if you dont want an effect and if you haven't included jQuery UI
				changeText: 1, // if you dont want the button text to change, set this to 0
				showText: 'View',// the button text to show when a div is closed
				hideText: 'Hide' // the button text to show when a div is open
			});
			populateTable();*/
		},
		populateTable :function(){
			$.ajax({
			url : 'http://localhost:9200/protocol_session_-895988176/ethernet/_search',
			type : 'GET',
			contentType : 'application/json; charset=utf-8',
			dataType : 'text',
			success : function(data) {
				alert("Success connecting to elasticsearch..")
				globalData = JSON.parse(data);
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
  			//update handlers
  			$("#packetInfo tbody tr").click( function(){
    			var ind = $(this).index();
    			botInfo = globalData["hits"]["hits"][ind];
    			stringToWrite =  "";
    			for(var key in botInfo){
      				stringToWrite+= key + ": " + JSON.stringify(botInfo[key]) + "\n";
    			}
    			$("#dataContainer").text(stringToWrite);
  			});
			},
			error : function() {
				alert("Error connecting to elasticsearch!!")
			}
		});
		},
		userHelpPage : function(){
			window.open("https://github.com/prasadtalasila/packetanalyzer",'_blank');
		},
		userLogout  : function(){
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
