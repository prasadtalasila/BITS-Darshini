window.ExperimentView = Backbone.View.extend({
		el : '.container',

		events: {
			 'click #newExperiment' : 'newExperimentLoad'
		},

		newExperimentLoad : function(authenticate) {
        	alert("Hello");
    	},

		initialize: function () {

		},
		render: function () {
			$(this.el).html(this.template());
        	return this;
		}
	});
