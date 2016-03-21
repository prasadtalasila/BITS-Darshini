window.AnalysisView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage',
			 'click #logout': 'userLogout'
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
		render: function () {
			var test = {
    		"row1": {
        		"id": 1,
        		"sourceMac":"00:0f:f8:ef:e9:40",
        		"destMac":"c0:38:96:23:73:35",
        		"sourceIP":"/54.208.202.178",
        		"destIP":"/10.30.11.3",
        		"headerLength":5,
        		"packetLength":392,
        		},
        	"row2" :{
        		"id": 1,
        		"sourceMac":"c0:38:96:23:73:35",
        		"destMac":"00:00:0c:07:ac:7c",
        		"sourceIP":"/10.30.11.3",
        		"destIP":"/54.208.202.178",
        		"headerLength":5,
        		"packetLength":40,
        		}
     		};
			$(this.el).html(this.template());
        	return this;
		}
	});
