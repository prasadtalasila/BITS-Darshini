window.ConfigPlaygroundView = Backbone.View.extend({
	el : $('body'),

	events: {
		'click #help' :'userHelpPage',
		'click #logout' : 'userLogout',
		'click #analyzeBtn' : 'analysis'
	},
	initialize: function () {
    		$(function() {
        		$( "#draggable" ).draggable();
    		});
		interact('.draggable')
		.draggable({
    		// enable inertial throwing
    		inertia: true,
    		// keep the element within the area of it's parent
    		restrict: {
    			restriction: "parent",
    			endOnly: true,
    			elementRect: { top: 0, left: 0, bottom: 1, right: 1 }
    		},
    		autoScroll: true,
    		onmove: dragMoveListener,
    		// call this function on every dragend event
    		onend: function (event) {
    		var textEl = event.target.querySelector('p');

    		textEl && (textEl.textContent =
    			'moved a distance of '
    			+ (Math.sqrt(event.dx * event.dx +
    			event.dy * event.dy)|0) + 'px');
    		}
		});

		function dragMoveListener (event) {
			var target = event.target,
        	// keep the dragged position in the data-x/data-y attributes
        	x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
        	y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

    		// translate the element
   			target.style.webkitTransform =
    		target.style.transform =
    		'translate(' + x + 'px, ' + y + 'px)';

    		// update the posiion attributes
    		target.setAttribute('data-x', x);
    		target.setAttribute('data-y', y);
		}
  		window.dragMoveListener = dragMoveListener;
  		interact('.dropzone').dropzone({
  			// only accept elements matching this CSS selector
  			accept: '#yes-drop',
  			overlap: 1,
  			// listen for drop related events:

  		ondropactivate: function (event) {
    		// add active dropzone feedback
    		event.target.classList.add('drop-active');
		},
		ondragenter: function (event) {
			var draggableElement = event.relatedTarget,
			dropzoneElement = event.target;
    		// feedback the possibility of a drop
    		dropzoneElement.classList.add('drop-target');
    		draggableElement.classList.add('can-drop');
    		draggableElement.textContent = 'Dragged in';
		},
		ondragleave: function (event) {
    		// remove the drop feedback style
    		event.target.classList.remove('drop-target');
    		event.relatedTarget.classList.remove('can-drop');
    		event.relatedTarget.textContent = 'Dragged out';
		},
		ondrop: function (event) {
			event.relatedTarget.textContent = 'Dropped';
		},
		ondropdeactivate: function (event) {
    		// remove active dropzone feedback
    		event.target.classList.remove('drop-active');
    		event.target.classList.remove('drop-target');
		}
		});
	},	

	userHelpPage : function(){
	window.location.href = "https://github.com/prasadtalasila/packetanalyzer";
	},
	userLogout  : function(){
		Cookies.remove('userName');
		Cookies.remove('userAuth');		
		app.navigate("#",{trigger: true});
		alert("You have been logged out. Please login to continue");
	},
	analysis : function(event){
		event.preventDefault();
		app.navigate("#/analysis",{trigger: true});
	},
	render: function () {
		$(this.el).html(this.template());
		return this;
	}
});
