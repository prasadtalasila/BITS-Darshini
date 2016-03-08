window.LoginView = Backbone.View.extend({
		el : '.container',
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
             dataType:"json",
             data: JSON.stringify(formValues),
             async: false,
             success:function (data) {
                 if(data === "success") { 
                    app.navigate("#/home");
                 }
                 else { 
                    console.log("Error logging in");
                 }
             }
        	 });			
    	},
    	signup : function(event){
    		var formValues = {
            	email: $('#inputEmail').val(),
            	password: $('#inputPassword').val()
        	};
        	$.ajax({
            url:'/signup',
             type:'POST',
             dataType:"json",
             data: formValues,
             success:function (data) {
                 if(data === "success") {
                 	alert("User successfully registered.");
                    app.navigate("#");
                 }
                 else { 
                     console.log("Error logging in");
                 }
             }
        	 });
    	},
		render: function () {
			$(this.el).html(this.template());
        	return this;
		}
	});

