var HomeView = Backbone.View.extend({
      template: '<h1>Home</h1>',
      initialize: function () {
          this.render();
      },
      render: function () {
          this.$el.html(this.template);
      }
  });
