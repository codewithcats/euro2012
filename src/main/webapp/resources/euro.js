$(function() {
	
	var User = Backbone.Model.extend({
		
	});
	
	_.templateSettings = {
	    interpolate: /\{\{(.+?)\}\}/g
	};
	var alertTpl = _.template($('#tpl-alert').html());
	var RegisterView = Backbone.View.extend({
		el: '#reg',
		initialize: function() {
			this.$username = this.$el.find('input[name=username]');
			this.$password = this.$el.find('input[name=password]');
		},
		render: function() {
			this.$el.show(0);
			return this;
		},
		events: {
			'click form button[type=submit]': 'submit'
		},
		submit: function(e) {
			e.preventDefault();
			this.$el.find('.alert').remove();
			$.ajax('users/register', {
				context: this,
				type: 'POST',
				data: {
					username: this.$username.val(),
					password: this.$password.val()
				},
				dataType: 'json',
				success: function(data) {
					if(data.success) {
						this.trigger('success:register', data.user);
						return;
					}
					if(data.usernameDuplicate) {
						this.$el.append(alertTpl({
							title: 'Too Late!',
							msg: 'Someone already pick this username, try another please.'
						}));
					}
				}
			});
		},
		hide: function() {
			this.$el.hide();
		}
	});
	
	var SignInView = Backbone.View.extend({
		el: '#signin',
		initialize: function() {
			this.$username = this.$el.find('input[name=username]');
			this.$password = this.$el.find('input[name=password]');
		},
		render: function() {
			this.$el.show(0);
			return this;
		},
		events: {
			'click form button[type=submit]': 'submit',
			'click form button.reg-trigger': 'register'
		},
		register: function(e) {
			e.preventDefault();
			this.trigger('register');
		},
		submit: function(e) {
			e.preventDefault();
			this.$el.find('.alert').remove();
			$.ajax('users/signin', {
				context: this,
				type: 'POST',
				data: {
					username: this.$username.val(),
					password: this.$password.val()
				},
				dataType: 'json',
				success: function(data) {
					if(data.success) {
						this.trigger('success:signin', data.user);
						return;
					}
					this.$el.append(alertTpl({
						title: 'Something Wrong!',
						msg: 'Check your username, password, then try again.'
					}));
					this.trigger('fail:signin');
				}
			});
		},
		hide: function() {
			this.$el.hide(0);
		}
	});
	
	var DashBoardView = Backbone.View.extend({
		el: '#dashboard',
		userInfoTpl: _.template($('#tpl-user-info').html()),
		initialize: function() {
			this.model.on('change', this.render, this);
		},
		events: {
			'click a.signout-trigger': 'signout'
		},
		render: function() {
			this.$el.find('#user-info').html(this.userInfoTpl(this.model.toJSON()));
			this.renderRound();
			return this;
		},
		renderRound: function() {
			$.ajax('rounds/active.json', {
				context: this,
				type: 'GET',
				dataType: 'json',
				success: function(round) {
					console.log(round);
				}
			});
		},
		hide: function() {
			this.$el.hide(0);
		},
		show: function() {
			this.$el.show(0);
		},
		signout: function(e) {
			e.preventDefault();
			$.ajax('users/signout', {
				context: this,
				type: 'POST',
				dataType: 'json',
				success: function() {
					this.model.clear('silent');
					this.trigger('logout'); 
				}
			});
		}
	});
	
	var AppRouter = Backbone.Router.extend({
		initialize: function() {
			this.user = new User;
			this.registerView = new RegisterView;
			this.registerView.on('success:register', function(user) {
				this.user.set(user);
				this.navigate('dashboard', {trigger: true});
			}, this);
			
			this.signInView = new SignInView;
			this.signInView.on('success:signin', function(user) {
				this.user.set(user);
				this.navigate('dashboard', {trigger: true});
			}, this);
			this.signInView.on('register', function() {
				this.navigate('register', {trigger: true});
			}, this);
			
			this.dashboardView = new DashBoardView({
				model: this.user
			});
			this.dashboardView.on('logout', function() {
				this.navigate('signin', {trigger: true});
			}, this);
		},
		routes: {
			'': 'dashboard',
			'register': 'register',
			'signin': 'signIn',
			'dashboard': 'dashboard'
		},
		dashboard: function() {
			this.signInView.hide();
			this.registerView.hide();
			if(!this.user.get('username')) {
				this.getCurrentUser();
			} else {
				this.dashboardView.show();
			}
		},
		getCurrentUser: function() {
			$.ajax('users/current.json', {
				context: this,
				type: 'GET',
				dataType: 'json',
				success: function(data) {
					if(!data.user) {
						this.navigate('signin', {trigger: true});
						return;
					}
					this.user.set(data.user);
					this.navigate('dashboard', {trigger: true});
				}
			});
		},
		register: function() {
			this.signInView.hide();
			this.dashboardView.hide();
			this.registerView.render();
		},
		signIn: function() {
			this.registerView.hide();
			this.dashboardView.hide();
			this.signInView.render();
		}
	});
	
	new AppRouter;
	Backbone.history.start();
	
});