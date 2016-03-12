 
 // Tell jQuery to watch for any 401 or 403 errors and handle them appropriately
$.ajaxSetup({
    statusCode: {
        401: function(){
            // Redirec the to the login page.
            window.location.replace('#login');
         
        },
        403: function() {
            // 403 -- Access denied
            window.location.replace('#denied');
        }
    }
});

window.Router = Backbone.Router.extend({
    routes: {
        'home': 'experimentViewDisplay',
        'config': 'configPlaygroundViewDisplay'
        //"contact": "contact",
        //"employees/:id": "employeeDetails",
        //"login" : "login"
    },

    initialize: function () {
        this.loginView = new LoginView();
        this.loginView.render();
    },
    experimentViewDisplay: function () {
       this.experimentView = new ExperimentView();
       this.experimentView.render();
    },
    configPlaygroundViewDisplay: function () {
       this.configView = new ConfigPlaygroundView();
       this.configView.render();
    }


});

templateLoader.load(["LoginView","ExperimentView","ConfigPlaygroundView"],
    function () {
        app = new Router();
        Backbone.history.start();
    });