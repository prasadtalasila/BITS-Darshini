window.AnalysisView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage'
		},
		initialize: function () {
		},

		userHelpPage : function(){
			window.location.href = "https://github.com/prasadtalasila/packetanalyzer";
		},
		render: function () {
			$(this.el).html(this.template());
        	return this;
		}
	});
