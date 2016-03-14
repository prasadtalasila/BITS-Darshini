window.LoginView = Backbone.View.extend({
        el : $('body'),
        initialize: function () {
        },
        events: {
            'click #signinButton' : 'login',
            'click #signupRedirectButton' : 'signupRedirect',
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
        signupRedirect : function(){
            $('#popup1').w2popup();
        },
        signup : function(){
            var registerValues = {
              email:$('#w2ui-popup #registerEmail').val(),
              password: $('#w2ui-popup #registerPassword').val()
          };
        $.ajax({
            url:'/protocolanalyzer/signup',
            type:'POST',
            contentType: 'application/json; charset=utf-8',
            dataType:'text',
            data: JSON.stringify(registerValues),
              success:function (data) {
                  if(data === "success") {
                    alert("User successfully registered.");
                    w2popup.close();
                     app.navigate("#");
                  }
                  else if(data === "failure"){ 
                    w2popup.close();
                    alert("An account with this email ID already exists");
                  }
              },
              error:function(){
                w2popup.close();
                alert("There was an issue connecting at this time, please try again later");
              }
           });
        },
        render: function () {
            $(this.el).html(this.template());
            return this;
        }
    });

