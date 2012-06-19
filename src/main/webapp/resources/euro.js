$(function() {
	
	var User = Backbone.Model.extend({
		
	});

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
							clazz: '',
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
						clazz: '',
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
			$('#dashboard-nav-link').show(0);
			return this;
		},
		hide: function() {
			$('#dashboard-nav-link').hide(0);
			this.$el.hide(0);
		},
		show: function() {
			$('#dashboard-nav-link').show(0);
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
					$('#dashboard-nav-link').hide(0);
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
				this.user = null;
				this.navigate('signin', {trigger: true});
			}, this);
			
			this.playerMatchCollection = new PlayerMatchCollection;
			this.playerMatchCollection.user = this.user;
			this.playerMatchListView = new PlayerMatchListView({
				collection: this.playerMatchCollection
			});
			
			this.playerMatchHistoryCollection = new PlayerMatchHistoryCollection;
			this.playerMatchHistoryCollection.user = this.user;
			this.playerMatchHistoryCollectionView = new HistoryMatchCollectionView({
				collection: this.playerMatchHistoryCollection
			});
			
			this.leagueRankingCollection = new LeagueRankingCollection;
			this.leagueTableView = new LeagueTableView({
				collection: this.leagueRankingCollection
			});
			this.leagueRankingCollection.fetch();
		},
		routes: {
			'': 'dashboard',
			'register': 'register',
			'signin': 'signIn',
			'dashboard': 'dashboard',
			'league': 'league'
		},
		league: function() {
			$('div.content').hide(0);
			$('#league').show(0);
		},
		dashboard: function() {
			if(!this.user.get('username')) {
				this.getCurrentUser();
			} else {
				$('div.content').hide(0);
				this.dashboardView.show();
				this.playerMatchCollection.fetch();
				this.playerMatchHistoryCollection.fetch();
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
			$('div.content').hide(0);
			this.registerView.render();
		},
		signIn: function() {
			$('div.content').hide(0);
			this.signInView.render();
		}
	});
	
	new AppRouter;
	Backbone.history.start();
	
});