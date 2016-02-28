/*global Backbone */
var app = app || {};

(function () {
	'use strict';

	app.Login = Backbone.Model.extend({
		// Default attributes for the todo
		// and ensure that each todo created has `title` and `completed` keys.
		defaults: {
			username: ''
		}

	});
})();
