$(function() {
	
	_.templateSettings = {
	    interpolate: /\{\{(.+?)\}\}/g
	};
	
	var Round = Backbone.Model.extend({});
	var RoundCollection = Backbone.Collection.extend({
		model: Round,
		url: '../rounds.json'
	});
	
	var PlayerMatch = Backbone.Model.extend({});
	var PlayerMatchCollection = Backbone.Collection.extend({
		model: PlayerMatch,
		url: function() {
			return '../rounds/' + this.roundId + '/matches.json';
		}
	});
	
	var PlayerMatchTableView = Backbone.View.extend({
		el: '#player-match-table',
		rowTpl: _.template($('#tpl-player-match-row').html()),
		initialize: function() {
			this.collection.on('reset', this.render, this);
		},
		events: {
			'click a.matching-trigger': 'match'
		},
		match: function(e) {
			e.preventDefault();
			$.ajax('../league/matching', {
				type: 'POST',
				data: {
					roundId: this.collection.roundId
				},
				dataType: 'json',
				context: this,
				success: function(data) {
					if(data.success) {
						alert('completed!');
						return;
					}
					alert('error!');
				}
			});
		},
		render: function() {
			this.$el.empty();
			if(this.collection.size() <= 0) {
				this.$el.prepend("<h3>This round doesn't matching yet</h3><a class=\"matching-trigger\" href=\"#\">click to matching</a>");
				return;
			}
			this.$el.append('<table class="table"></table>');
			this.collection.each(function(m) {
				this.$el.find('table').append(this.rowTpl(m.toJSON()));
			}, this);
		}
	});
	
	var RoundListView = Backbone.View.extend({
		el: '#rounds-list',
		itemTpl: _.template($('#tpl-round-item').html()),
		initialize: function() {
			this.collection.on('reset', this.render, this);
		},
		events: {
			'click li a': 'onRoundClick'
		},
		onRoundClick: function(e) {
			e.preventDefault();
			var $target = $(e.target);
			this.trigger('select:round', $target.attr('id'));
			this.$el.find('li').removeClass('active');
			$target.parent().addClass('active');
		},
		render: function() {
			this.$el.find('li').not(':first').remove();
			this.collection.each(function(round) {
				this.$el.append(this.itemTpl(round.toJSON()));
			}, this);
		}
	});
	
	var RoundFormView = Backbone.View.extend({
		el: '#round-form',
		initialize: function() {
			this.$round = this.$el.find('input[name=round]');
		},
		events: {
			'click button[type=submit]': 'save'
		},
		save: function(e) {
			e.preventDefault();
			$.ajax('../rounds/add', {
				context: this,
				type: 'POST',
				dataType: 'json',
				data: {
					round: this.$round.val()
				},
				success: function(data) {
					if(data.success) alert('saved');
					else alert('error');
				}
			});
		}
	});
	
	var TeamFormView = Backbone.View.extend({
		el: '#team-form',
		initialize: function() {
			this.$team = this.$el.find('input[name=team]');
		},
		events: {
			'click button[type=submit]': 'save'
		},
		save: function(e) {
			e.preventDefault();
			$.ajax('../teams/add', {
				context: this,
				type: 'POST',
				dataType: 'json',
				data: {
					team: this.$team.val()
				},
				success: function(data) {
					if(data.success) alert('saved');
					else alert('error');
				}
			});
		}
	});
	
	var MatchFormView = Backbone.View.extend({
		el: '#match-form',
		RndTeamOptTpl: _.template($('#tpl-round-team-option').html()),
		initialize: function() {
			this.$home = this.$el.find('select[name=home]');
			this.$away = this.$el.find('select[name=away]');
			this.$round = this.$el.find('select[name=round]');
		},
		events: {
			'click button[type=submit]': 'save'
		},
		save: function(e) {
			e.preventDefault();
			$.ajax('../matches/add', {
				context: this,
				type: 'POST',
				data: {
					roundId: this.$round.val(),
					homeId: this.$home.val(),
					awayId: this.$away.val()
				},
				dataType: 'json',
				success: function(data) {
					if(data.success) alert('saved!');
					else alert('error!');
				}
			});
		},
		render: function() {
			$.ajax('../rounds.json', {
				context: this,
				type: 'GET',
				dataType: 'json',
				success: function(rounds) {
					var html = '';
					var _this = this;
					$.each(rounds, function(i, r) {
						html += _this.RndTeamOptTpl(r);
					});
					this.$round.html(html);
				}
			});
			$.ajax('../teams.json', {
				context: this,
				type: 'GET',
				dataType: 'json',
				success: function(teams) {
					var html = '';
					var _this = this;
					$.each(teams, function(i, team) {
						html += _this.RndTeamOptTpl(team);
					});
					this.$home.html(html);
					this.$away.html(html);
				}
			});
		}
	});
	
	var LeagueView = Backbone.View.extend({
		el: '#league',
		events: {
			'click button.matching-trigger': 'matching'
		},
		matching: function(e) {
			e.preventDefault();
			$.ajax('../league/matching', {
				context: this,
				type: 'POST',
				data: {
					roundId: '4fd1aba244ae3866e0921be2'
				},
				dataType: 'json',
				success: function(data) {
					alert('success!');
				}
			});
		}
	});
	
	var AdminRouter = Backbone.Router.extend({
		initialize: function() {
			this.roundCollection = new RoundCollection;
			this.roundListView = new RoundListView({
				collection: this.roundCollection
			});
			this.roundListView.on('select:round', function(id) {
				this.playerMatchCollection.roundId = id;
				this.playerMatchCollection.fetch();
			}, this);
			this.roundCollection.fetch();
			
			this.playerMatchCollection = new PlayerMatchCollection;
			this.playerMatchTableView = new PlayerMatchTableView({
				collection: this.playerMatchCollection
			});
			
			this.roundFormView = new RoundFormView;
			this.teamFormView = new TeamFormView;
			this.matchFormView = new MatchFormView;
			this.matchFormView.render();
			this.leagueView = new LeagueView;
		}
	});
	
	new AdminRouter;
	//Backbone.history.start();
});