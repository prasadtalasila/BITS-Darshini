window.ConfigPlaygroundView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #help' :'userHelpPage'
		},
		initialize: function () {
			dragula([document.getElementById('menu'), document.getElementById('config-playground')]);
		},

		userHelpPage : function(){
			window.location.href = "https://github.com/prasadtalasila/packetanalyzer";
		},
		render: function () {
			$(this.el).html(this.template());
        	return this;
		}
	});
