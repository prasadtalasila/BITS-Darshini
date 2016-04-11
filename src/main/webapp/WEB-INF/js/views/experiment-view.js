window.ExperimentView = Backbone.View.extend({
		el : $('body'),

		events: {
			 'click #newExperiment' : 'newExperiment',
			 'click #loadExperiment' : 'loadExperiment',
             'click #attachBtn' : 'readSingleFile',
             'click #help' :'userHelpPage',
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
            $.ajax({
            url:'/protocolanalyzer/signin',
             type:'POST',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: JSON.stringify(formValues),
             success:function (data) {
                 var jsonData = JSON.parse(data);
                 var status = jsonData.status;
                 if(status === "success") {
                    app.navigate("#/config");
                 }
                 else if(status ==="failure"){
                    alert("Error logging in, please check your details");
                 }
             },
             error:function(){
                console.log("Error logging in");
             }
             });

    	},
    	loadExperiment : function() {
        	
    	},
		initialize: function () {       
		},
        readSingleFile : function(evt) {
            var f = document.getElementById("fileInput").files[0]; 
            if (f) {
                var r = new FileReader();
                r.onload = function(e) { 
                var userParseGraph = e.target.result;
                var temp = userParseGraph;
            }
            r.readAsText(f);
            } else { 
                alert("Failed to load file");
            }
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
        dispose: function() {
            this.remove();
            this.undelegateEvents() ;
            // unbind events that are
            // set on this view
            this.off();
        },
		render: function () { 
			$(this.el).html(this.template());
            return this;
		}
	});
