 
 // Tell jQuery to watch for any 401 or 403 errors and handle them appropriately
$.ajaxSetup({
    statusCode: {
        401: function(){
            // Redirec the to the login page.
            window.location.replace('#login');
         
        },
        403: function() {
            // 403 -- Access denied
            window.location.replace('#login');
        }
    }
});

window.Router = Backbone.Router.extend({
    routes: {
        '':'loginViewDisplay',
        'home': 'experimentViewDisplay',
        'config': 'configPlaygroundViewDisplay'
    },

    initialize: function () {
    },
    loginViewDisplay: function () {
        this.loginView = new LoginView();
        this.loginView.render();
    },
    experimentViewDisplay: function () {    
        $.get( '/protocolanalyzer/auth', {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')})
        .done(function(status){
            this.experimentView = new ExperimentView();
            this.experimentView.render();
        })
        .fail(function(status){
            alert("You have been logged out. Please login to continue");
            app.navigate("#");
        });
    },
    configPlaygroundViewDisplay: function () {
        $.get( '/protocolanalyzer/auth', {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')})
        .done(function(status){
            this.configView = new ConfigPlaygroundView();
            this.configView.render();
        })
        .fail(function(status){
            alert("You have been logged out. Please login to continue");
            app.navigate("#");
        });
    

    }
});

templateLoader.load(["LoginView","ExperimentView","ConfigPlaygroundView"],
    function () {
        app = new Router();
        Backbone.history.start();
    });