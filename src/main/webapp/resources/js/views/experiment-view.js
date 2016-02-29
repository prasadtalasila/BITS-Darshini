/*global Backbone, jQuery, _, ENTER_KEY, ESC_KEY */
var app = app || {};

(function ($) {
	'use strict';

	app.ExperimentView = Backbone.View.extend({
		el : '#packetapp',

		events: {
			 'click .inputEmail3' : 'authenticate'
		},
		authenticate : function() {
			debugger
        	alert("Hello");
    	},
		
		initialize: function () {
			this.render();
		},

		// Re-render the titles of the todo item.
		render: function () {
			// Backbone LocalStorage is adding `id` attribute instantly after
			// creating a model.  This causes our TodoView to render twice. Once
			// after creating a model and once on `id` change.  We want to
			// filter out the second redundant render, which is caused by this
			// `id` change.  It's known Backbone LocalStorage bug, therefore
			// we've to create a workaround.
			// https://github.com/tastejs/todomvc/issues/469
			//if (this.model.changed.id !== undefined) {
			//	return;
			//}
			var template = _.template($("#experiment-template").html(), {} );
			this.$el.html(template);
			return this;
			//this.$el.html(this.template(this.model.toJSON()));
    		//return this;
		}
	});
})(jQuery);
