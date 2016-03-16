 
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
        'config': 'configPlaygroundViewDisplay',
        'analysis': 'analysisViewDisplay'
    },

    initialize: function () {
    },
    loginViewDisplay: function () {
        this.loginView = new LoginView();
        this.loginView.render();
    },
    experimentViewDisplay: function () {    
        $.ajax({
            url:'/protocolanalyzer/auth',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')},
             success:function (data) {
                 if(data==="success"){
                    this.experimentView = new ExperimentView();
                    this.experimentView.render();
                 }
                 else{
                    alert("You have been logged out. Please login to continue");
                 }
             },
             error:function(){
                alert("You have been logged out. Please login to continue");
                app.navigate("#");
             }
             });
    },
    configPlaygroundViewDisplay: function () {
        $.ajax({
            url:'/protocolanalyzer/auth',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')},
             success:function (data) {
                 if(data==="success"){
                    this.configView = new ConfigPlaygroundView();
                    this.configView.render();
                 }
                 else{
                    alert("You have been logged out. Please login to continue");
                    app.navigate("#");
                 }
             },
             error:function(){
                alert("You have been logged out. Please login to continue");
                app.navigate("#");
             }
             });
    },
    analysisViewDisplay: function(){
        this.analysisView = new AnalysisView();
        this.analysisView.render();
    }
});

templateLoader.load(["LoginView","ExperimentView","ConfigPlaygroundView","AnalysisView"],
    function () {
        app = new Router();
        Backbone.history.start();
    });