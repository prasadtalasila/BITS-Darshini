window.AnalysisView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage',
			 'click #logout': 'userLogout'
		},
		initialize: function () {
			
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
			// var test = {
   //      		"id": 1,
   //      		"sourceMac":"00:0f:f8:ef:e9:40",
   //      		"destMac":"c0:38:96:23:73:35",
   //      		"sourceIP":"/54.208.202.178",
   //      		"destIP":"/10.30.11.3",
   //      		"headerLength":5,
   //      		"packetLength":392
   //   		};
	//			$(this.el).html(_.template( this.template, {test:test} ));
        	$(this.el).html(this.template());
        	return this;
		}
	});
