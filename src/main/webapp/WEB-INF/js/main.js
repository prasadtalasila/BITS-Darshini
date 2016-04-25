 
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
        _this = this;
        view = new LoginView();
        _this.render(view);
    },
    experimentViewDisplay: function () {    
        _this = this;
        $.ajax({
            url:'/protocolanalyzer/auth',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')},
             success:function (data) {
                 if(data==="success"){
                    view = new ExperimentView();
                    _this.render(view);
                 }
                 else{
                    Cookies.remove('userName');
                    Cookies.remove('userAuth');
                    app.navigate("#",{trigger:true});
                    alert("You have been logged out. Please login to continue");
                 }
             },
             error:function(){
                Cookies.remove('userName');
                Cookies.remove('userAuth');
                app.navigate("#",{trigger:true});
                alert("You have been logged out. Please login to continue");
             }
             });
    },
    configPlaygroundViewDisplay: function () {
        _this = this;
        $.ajax({
            url:'/protocolanalyzer/auth',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')},
             success:function (data) {
                 if(data==="success"){
                    view = new ConfigPlaygroundView();
                    _this.render(view);
                 }
                 else{
                    Cookies.remove('userName');
                    Cookies.remove('userAuth');
                    app.navigate("#",{trigger:true});
                    alert("You have been logged out. Please login to continue");
                    
                 }
             },
             error:function(){
                Cookies.remove('userName');
                Cookies.remove('userAuth');
                app.navigate("#",{trigger:true});
                alert("You have been logged out. Please login to continue");
             }
             });
    },
    analysisViewDisplay: function(){
        _this = this;
        $.ajax({
            url:'/protocolanalyzer/auth',
             type:'GET',
             contentType: 'application/json; charset=utf-8',
             dataType:'text',
             data: {loginHash : Cookies.get('userAuth'), user : Cookies.get('userName')},
             success:function (data) {
                 if(data==="success"){
                    view = new AnalysisView();
                    _this.render(view);
                 }
                 else{
                    Cookies.remove('userName');
                    Cookies.remove('userAuth');
                    app.navigate("#",{trigger:true});
                    alert("You have been logged out. Please login to continue");
                 }
             },
             error:function(){
                Cookies.remove('userName');
                Cookies.remove('userAuth');
                app.navigate("#",{trigger:true});
                alert("You have been logged out. Please login to continue");
             }
             });    
    },
    render : function(view){
        //Close the current view
        if (this.currentView) {
            this.currentView.remove();
        }
        //render the new view
        view.render();
        //Set the current view
        this.currentView = view;
        return this;
    }
});

templateLoader.load(["LoginView","ExperimentView","ConfigPlaygroundView","AnalysisView"],
    function () {
        app = new Router();
        Backbone.history.start();
    });