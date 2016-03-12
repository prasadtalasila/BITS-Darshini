window.LoginView = Backbone.View.extend({
		el : $('.container'),
      	initialize: function () {
      	},
      	events: {
			'click #signinButton' : 'login',
			'click #signupButton' : 'signup'
		},

		login : function(event) {
			event.preventDefault();
        	var formValues = {
            	email: $('#inputEmail').val(),
            	password: $('#inputPassword').val()
        	};
        	 $.ajax({
            url:'/protocolanalyzer/signin',
             type:'POST',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: JSON.stringify(formValues),
             success:function (data) {
                 if(data === "success") { 
                    app.navigate("#/home");
                 }
                 else if(data ==="failure"){ 
                     alert("Error logging in, please check your details");
                 }
             },
             error:function(){
                console.log("Error logging in");
             }
        	 });			
    	},
    	signup : function(event){
    		var formValues = {
            	email: $('#inputEmail').val(),
            	password: $('#inputPassword').val()
        	};
        	$.ajax({
            url:'/protocolanalyzer/signup',
             type:'POST',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: JSON.stringify(formValues),
             success:function (data) {
                 if(data === "success") {
                 	alert("User successfully registered.");
                    app.navigate("#");
                 }
                 else if(data === "failure"){ 
                     alert("An account with this email ID already exists");
                 }
             },
             error:function(){
                alert("There was an issue connecting at this time, please try again later");
             }
        	 });
    	},
		render: function () {
			$(this.el).html(this.template());
        	return this;
		}
	});

