window.PlayerMatch = Backbone.Model.extend({

});
window.PlayerMatchCollection = Backbone.Collection.extend({
	model: PlayerMatch,
	url: function() {
		return 'league/players/' + this.user.get('id') + '/matches.json';
	}
});
window.PlayerMatchHistoryCollection = Backbone.Collection.extend({
	model: PlayerMatch,
	url: 'league/players/history/matches.json'
});

window.PlayerMatchItemView = Backbone.View.extend({
	tagName: 'div',
	className: 'match',
	tpl: _.template($('#tpl-match-row').html()),
	initialize: function() {
		this.model.on('change', this.render, this);
	},
	events: {
		'click button.save-trigger': 'pick'
	},
	pick: function(e) {
		e.preventDefault();
		this.$el.find('.alert').remove();
		var homeScoreHt = this.$el.find('input[name=homeScoreHt]').val();
		var iHomeScoreHt = parseInt(homeScoreHt);
		var awayScoreHt = this.$el.find('input[name=awayScoreHt]').val();
		var iAwayScoreHt = parseInt(awayScoreHt);
		var homeScoreFt = this.$el.find('input[name=homeScoreFt]').val();
		var iHomeScoreFt = parseInt(homeScoreFt);
		var awayScoreFt = this.$el.find('input[name=awayScoreFt]').val();
		var iAwayScoreFt = parseInt(awayScoreFt);
		var htResult = 'WIN';
		if(iHomeScoreHt == iAwayScoreHt) htResult = 'DRAW';
		else if(iHomeScoreHt < iAwayScoreHt) htResult = 'LOSE';
		var ftResult = 'WIN';
		if(iHomeScoreFt == iAwayScoreFt) ftResult = 'DRAW';
		else if(iHomeScoreFt < iAwayScoreFt) ftResult = 'LOSE';
		
		var data = {
			htScore: homeScoreHt + '-' + awayScoreHt,
			ftScore: homeScoreFt + '-' + awayScoreFt,
			yellowCardMoreThanFive: this.$el.find('input[name=fiveYCards]').is(':checked'),
			redCardHappen: this.$el.find('input[name=redCard]').is(':checked'),
			ownGoalHappen: this.$el.find('input[name=og]').is(':checked'),
			hattrickHappen: this.$el.find('input[name=hattrick]').is(':checked'),
			htResult: htResult,
			ftResult: ftResult,
			matchId: this.model.get('id')
		};
		$.ajax('pmatches/pick/save', {
			context: this,
			data: data,
			type: 'POST',
			dataType: 'json',
			success: function(json) {
				var tpl = _.template($('#tpl-alert-match').html());
				var html = tpl({
					'clazz': 'alert-success',
					'title': 'Well Done!',
					'msg': 'Your match have been saved.'
				});
				this.$el.find('div.form-actions').append(html);
				var m  = json.match;
				var pick = false;
				var playerName = this.options.user.get('username');
				if(playerName === m.playerOne.username) {
					pick = m.playerOnePick;
				} else {
					pick = m.playerTwoPick;
				}
				var lastUpdateTag = '<i class="icon icon-ok-sign"></i>&nbsp;Saved: ' + pick.lastUpdate;
				this.$el.find('.form-actions .last-update').html(lastUpdateTag);
			},
			error: function() {}
		});
	},
	render: function() {
		var playerName = this.options.user.get('username');
		var json = this.model.toJSON();
		var pick = false;
		if(playerName === json.playerOne.username) {
			json.opponent = json.playerTwo;
			pick = json.playerOnePick;
		} else {
			json.opponent = json.playerOne;
			pick = json.playerTwoPick;
		}
		json.index = this.options.index;
		if(pick && pick.lastUpdate) {
			json.lastUpdateTag = '<i class="icon icon-ok-sign"></i>&nbsp;Saved: ' + pick.lastUpdate;
		} else {
			json.lastUpdateTag = 'This match is not saved.';
		}
		this.$el.html(this.tpl(json));
		
		if(pick) {
			this.$el.find('input[name=homeScoreHt]').val(pick.htScore[0]);
			this.$el.find('input[name=awayScoreHt]').val(pick.htScore[2]);
			this.$el.find('input[name=homeScoreFt]').val(pick.ftScore[0]);
			this.$el.find('input[name=awayScoreFt]').val(pick.ftScore[2]);
			if(pick.yellowCardMoreThanFive) this.$el.find('input[name=fiveYCards]').attr('checked', true);
			if(pick.redCardHappen) this.$el.find('input[name=redCard]').attr('checked', true);
			if(pick.ownGoalHappen) this.$el.find('input[name=og]').attr('checked', true);
			if(pick.hattrickHappen) this.$el.find('input[name=hattrick]').attr('checked', true);
		}
		return this;
	}
});
window.PlayerMatchListView = Backbone.View.extend({
	el: '#player-matches',
	initialize: function() {
		this.collection.on('reset', this.render, this);
	},
	render: function() {
		var index = 1;
		this.$el.empty();
		this.collection.each(function(m) {
			var item = new PlayerMatchItemView({
				model: m,
				user: this.collection.user,
				index: index++
			});
			this.$el.append(item.render().el);
		}, this);
		return this;
	}
});
window.HistoryMatchItemView = Backbone.View.extend({
	tagName: 'div',
	className: 'row history-match',
	tpl: _.template($('#tpl-history-match').html()),
	byDefaultTpl: _.template($('#tpl-history-match-by').html()),
	initialize: function() {
		this.model.on('change', this.render, this);
	},
	render: function() {
		var model = this.model.toJSON();
		var playerName = this.options.user.get('username');
		if(playerName == model.playerOne.username) {
			model.opponent = model.playerTwo;
			model.me = model.playerOne;
			model.opponentpick = model.playerTwoPick;
			model.mypick = model.playerOnePick;
			if(!model.mypick) {
				model.resultTag = '<span style="color:red;">Lose</span>';
				this.$el.append(this.byDefaultTpl(model));
				return this;
			} else if(!model.opponentpick) {
				model.resultTag = '<span style="color:#51A351;">Win</span>';
				this.$el.append(this.byDefaultTpl(model));
				return this;
			}
			model.opponentpick.points = model.playerTwoPoints;
			model.mypick.points = model.playerOnePoints;
		} else {
			model.opponent = model.playerOne;
			model.me = model.playerTwo;
			model.opponentpick = model.playerOnePick;
			model.mypick = model.playerTwoPick;
			if(!model.mypick) {
				model.resultTag = '<span style="color:red;">Lose</span>';
				this.$el.append(this.byDefaultTpl(model));
				return this;
			} else if(!model.opponentpick) {
				model.resultTag = '<span style="color:#51A351;">Win</span>';
				this.$el.append(this.byDefaultTpl(model));
				return this;
			}
			model.opponentpick.points = model.playerOnePoints;
			model.mypick.points = model.playerTwoPoints;
		}
		
		if(model.mypick.points > model.opponentpick.points) model.resultTag = '<span style="color:#51A351;">Win</span>';
		else if(model.mypick.points < model.opponentpick.points) model.resultTag = '<span style="color:red;">Lose</span>';
		else model.resultTag = '<span style="color:#999;">Draw</span>';
		
		
		model.match.yellow = model.match.yellowCardMoreThanFive?'Yes':'No' ;
		model.mypick.yellow = model.mypick.yellowCardMoreThanFive?'Yes': 'No';
		model.opponentpick.yellow = model.opponentpick.yellowCardMoreThanFive?'Yes': 'No';
		
		model.match.red = model.match.redCardHappen?'Yes':'No';
		model.mypick.red = model.mypick.redCardHappen?'Yes':'No';
		model.opponentpick.red = model.opponentpick.redCardHappen?'Yes':'No';
		
		model.match.og = model.match.ownGoalHappen?'Yes':'No';
		model.mypick.og = model.mypick.ownGoalHappen?'Yes':'No';
		model.opponentpick.og = model.opponentpick.ownGoalHappen?'Yes':'No';
		
		model.match.hattrick = model.match.hattrickHappen?'Yes':'No';
		model.mypick.hattrick = model.mypick.hattrickHappen?'Yes':'No';
		model.opponentpick.hattrick = model.opponentpick.hattrickHappen?'Yes':'No';
		
		this.$el.append(this.tpl(model));
		return this;
	}
});
window.HistoryMatchCollectionView = Backbone.View.extend({
	el: '#history',
	initialize: function() {
		this.collection.on('reset', this.render, this);
	},
	render: function() {
		this.$el.empty();
		this.collection.each(function(pmatch){
			var item = new HistoryMatchItemView({
				model: pmatch,
				user: this.collection.user
			});
			this.$el.append(item.render().el);
		}, this);
		return this;
	}
});