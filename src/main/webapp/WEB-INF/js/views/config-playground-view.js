window.ConfigPlaygroundView = Backbone.View.extend({
	el : $('body'),

	events: {
		'click #help' :'userHelpPage',
		'click #logout' : 'userLogout',
    'click #analyzeBtn' : 'analysis',
    'click #validateBtn' : 'graphValidation'
	},
	initialize: function () {
		this.delegateEvents();
	},	

	userHelpPage : function(){
	  window.open("https://github.com/prasadtalasila/packetanalyzer",'_blank');
	},
	userLogout  : function(){
    sessionStorage.clear();
		Cookies.remove('userName');
		Cookies.remove('userAuth');		
    
		app.navigate("#",{trigger: true});
		alert("You have been logged out. Please login to co ntinue");
        return false;
	},
	analysis : function(event){
		event.preventDefault();
    var pcapPath = sessionStorage.getItem('pcapPath');
    $.ajax({
      url:'/protocolanalyzer/session/analysis',
      type:'GET',
      contentType: 'application/json; charset=utf-8',
      dataType:'text',
      data: { pcapPath:pcapPath },
      success:function (data) {
          var jsonData = JSON.parse(data);
          var sessionNumber = jsonData.sessionNumber;
          app.navigate("#/analysis",{trigger: true});
        },
        error:function(){
          alert("Error running experiment. Please try again later.");
        }
      });
	},
  readSingleFile : function(evt) {
    //Retrieve the first (and only!) File from the FileList object
    var f = document.getElementById("fileInput").files[0]; 

    if (f) {
      
      var r = new FileReader();
      r.onload = function(e) { 
          var userParseGraph = e.target.result;
          $.ajax({
            url:'/protocolanalyzer/session/analysis',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {graph : userParseGraph},
             success:function (data) {
              
            	 var jsonData = JSON.parse(data);
            	 var status = jsonData.status;
                 if(status==="success"){
                    app.navigate("#/analysis");
                 }
                 else{
                    alert("Error!!!");  
                 }
             },
             error:function(){
                alert("Something went wrong. Please try again later.");
             }
             });
      }
      r.readAsText(f);
     
    } else { 
      alert("Failed to load file");
    }
  },

  graphValidation : function(){
    var f = document.getElementById("fileInput").files[0]; 
    if (f) {
      var r = new FileReader();
      r.onload = function(e) { 
          var flag =0;
          //test graph is the main graph, user graph checked against this
          var testGraph = 'graph _start_ { ethernet;} graph _ethernet_ { switch(ethertype) {case 0x800: ipv4;}} graph _ipv4_ {switch(protocol) {case 0x06: tcp;}}graph _tcp_ {}graph _end_ {}';
          var testParsing = testGraph.split(/[\{\}]/);
          for (var i = 0; i < testParsing.length; i++) {
            testParsing[i] = testParsing[i].trim();
          }
          //user provided p4 graph
          var userParseGraph = e.target.result;
          var userParsing = userParseGraph.split(/[\{\}]/);
          for (var i = 0; i < userParsing.length; i++) {
            userParsing[i] = userParsing[i].trim();
          }
          if(userParsing.length > testParsing.length ){
            alert("Invalid P4 graph");
            flag++;
          }
          // initial node check
          for(var i=0;i< 2;i++){
            if(userParsing[i]!==testParsing[i]){
              alert("Invalid P4 graph");
              flag++;
            }
          }
          //other nodes
          for(var i=2;i<userParsing.length-4;i++){
            if(userParsing[i]!==testParsing[i]){
              alert("Invalid P4 graph");
              flag++;
            }  
          }
          if(flag===0)
            alert("Valid configuration");
      }
      r.readAsText(f);
     
    } else { 
      alert("Failed to load file");
    }
  },

	render: function () {
		$(this.el).html(this.template());
		return this;
	}
});
