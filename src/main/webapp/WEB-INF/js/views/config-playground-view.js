window.ConfigPlaygroundView = Backbone.View.extend({
	el : $('body'),

	events: {
		'click #help' :'userHelpPage',
		'click #logout' : 'userLogout',
		'click #analyzeBtn' : 'analysis',
        'click #analyzeBtn' : 'readSingleFile'
	},
	initialize: function () {
    		
	},	

	userHelpPage : function(){
	window.location.href = "https://github.com/prasadtalasila/packetanalyzer";
	},
	userLogout  : function(){
		Cookies.remove('userName');
		Cookies.remove('userAuth');		
		app.navigate("#",{trigger: true});
		alert("You have been logged out. Please login to continue");
        return false;
	},
	analysis : function(event){
		event.preventDefault();
		app.navigate("#/analysis",{trigger: true});
	},
    readSingleFile : function(evt) {
    //Retrieve the first (and only!) File from the FileList object
    var f = document.getElementById("fileInput").files[0]; 

    if (f) {
      var r = new FileReader();
      r.onload = function(e) { 
          var userParseGraph = e.target.result;
          var temp = userParseGraph;
          $.ajax({
            url:'/protocolanalyzer/session/analysis',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {graph : userParseGraph},
             success:function (data) {
                 if(data==="success"){
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
	render: function () {
		$(this.el).html(this.template());
		return this;
	}
});
