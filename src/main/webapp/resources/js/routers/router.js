/*global Backbone */
var app = app || {};

(function () {
	'use strict';
	var PacketRouter = Backbone.Router.extend({
		routes: {
			'home': 'setFilter'
		},

		setFilter: function () {       
          var experimentView = new app.ExperimentView({ el: $("#experiment-template") });
          window.location.href ="first.html";
          experimentView.render();
		}
	});

	app.PacketRouter = new PacketRouter();
	Backbone.history.start();
})();
