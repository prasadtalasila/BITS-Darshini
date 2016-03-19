window.ExperimentView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #newExperiment' : 'newExperiment',
			 'click #loadExperiment' : 'loadExperiment',
             'click #logout' : 'userLogout'
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
        userLogout  : function(){
            Cookies.remove('userName');
            Cookies.remove('userAuth');     
            app.navigate("#",{trigger: true});
            alert("You have been logged out. Please login to continue");
            return false;
        },
		render: function () { 
			$(this.el).html(this.template());
            $('#description').wysiwyg();
            return this;
		}
	});
