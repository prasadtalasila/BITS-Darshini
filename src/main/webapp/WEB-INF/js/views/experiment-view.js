window.ExperimentView = Backbone.View.extend({
		el : $('.container'),

		events: {
			 'click #newExperiment' : 'newExperiment',
			 'click #loadExperiment' : 'loadExperiment'
		},

		newExperiment : function() {
        	var formValues = {
        		experimentName : $('#experimentName').val(),
        		description : $('#description').val(),
        		experimenter : $('#experimenter').val(),
        		testid : $('#testid').val(),
        		pcapfiles : $('#pcapfiles').val()
        	};
            //do backend check here
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
