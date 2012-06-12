$(function() {
	
	var OpenMatchView = Backbone.View.extend({
		tagName: 'div',
		tpl: _.template($('#tpl-match-form').html()),
		initialize: function() {
			this.model.on('change', this.render, this);
		},
		events: {
			'click button.close-trigger': 'closeMatch'
		},
		closeMatch: function(e) {
			e.preventDefault();
			$.ajax('../matches/'+this.model.get('id')+'/close', {
				context: this,
				type: 'POST',
				dataType: 'json',
				data: {
					id: this.model.get('id'),
					htHomeScore: this.$el.find('input[name=homeScoreHt]').val(),
					ftHomeScore: this.$el.find('input[name=homeScoreFt]').val(),
					htAwayScore: this.$el.find('input[name=awayScoreHt]').val(),
					ftAwayScore: this.$el.find('input[name=awayScoreFt]').val(),
					yellowCardMoreThanFive: this.$el.find('input[name=fiveYCards]').is(':checked'),
					redCardHappen: this.$el.find('input[name=redCard]').is(':checked'),
					ownGoalHappen: this.$el.find('input[name=og]').is(':checked'),
					hattrickHappen: this.$el.find('input[name=hattrick]').is(':checked')
				},
				success: function(json) {
					if(json.failed) alert('Failed!');
					else {
						this.trigger('success:closeMatch');
						this.$el.remove();
					}
				},
				error: function() {}
			});
		},
		render: function() {
			var json = this.model.toJSON();
			this.$el.html(this.tpl(json));
			return this;
		}
	});
	
	var OpenMatchCollectionView = Backbone.View.extend({
		el: '#open-matches',
		initialize: function() {
			this.collection.on('reset', this.render, this);
		},
		render: function() {
			this.collection.each(function(match) {
				var matchItem = new OpenMatchView({
					model: match
				});
				matchItem.on('success:closeMatch', function() {
					this.trigger('success:closeMatch');
				}, this);
				this.$el.append(matchItem.render().el);
			}, this);
			return this;
		}
	});
	
	var ClosedMatchView = Backbone.View.extend({
		tagName: 'div',
		tpl: _.template($('#tpl-closed-match').html()),
		render: function() {
			var html = this.tpl(this.model.toJSON());
			this.$el.html(html);
			var list = this.$el.find('ul');
			if(this.model.get('yellowCardMoreThanFive')) {
				list.append('<li>Yellow Cards &gt; 5</li>');
			}
			if(this.model.get('redCardHappen')) {
				list.append('<li>Red Card</li>');
			}
			if(this.model.get('ownGoalHappen')) {
				list.append('<li>Own Goal</li>');
			}
			if(this.model.get('hattrickHappen')) {
				list.append('<li>Hattrick</li>');
			}
			return this;
		}
	});
	
	var ClosedMatchCollectionView = Backbone.View.extend({
		el: '#closed-matches',
		initialize: function() {
			this.collection.on('reset', this.render, this);
		},
		render: function() {
			this.$el.empty();
			this.collection.each(function(match) {
				var item = new ClosedMatchView({
					model: match
				});
				var html = item.render().el;
				this.$el.append(html);
			}, this);
			return this;
		}
	});
	
	var AppRouter = Backbone.Router.extend({
		initialize: function() {
			this.openMatchCollection = new OpenMatchCollection;
			this.openMatchCollection.url = '../matches/open.json';
			this.openMatchCollectionView = new OpenMatchCollectionView({
				collection: this.openMatchCollection
			});
			this.openMatchCollectionView.on('success:closeMatch', this.reloadClosedMatches, this);
			this.openMatchCollection.fetch();
			
			this.closedMatchCollection = new ClosedMatchCollection;
			this.closedMatchCollection.url = '../matches/close.json';
			this.closedMatchCollectionView = new ClosedMatchCollectionView({
				collection: this.closedMatchCollection
			});
			this.closedMatchCollection.fetch();
		},
		reloadClosedMatches: function() {
			this.closedMatchCollection.fetch();
		}
	});
	new AppRouter;
});