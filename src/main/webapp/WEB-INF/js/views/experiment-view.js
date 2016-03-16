window.ExperimentView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #newExperiment' : 'newExperiment',
			 'click #loadExperiment' : 'loadExperiment'
		},

		newExperiment : function(event) {
            event.preventDefault();
        	var formValues = {
        		experimentName : $('#experimentName').val(),
        		description : $('#description').val(),
        		experimenter : $('#experimenter').val(),
        		testid : $('#testid').val(),
        		pcapfiles : $('#pcapfiles').val()
        	};
        	app.navigate("#/config");

    	},
    	loadExperiment : function() {
        	
    	},
		initialize: function () {       
		},
		render: function () { 
			$(this.el).html(this.template());
        //	$('#description').wysihtml5({
        //    "lists": true,  
        //    "link": true, 
        //    "image": true, 
        //    "color": true 
        //});
            return this;
		}
	});
