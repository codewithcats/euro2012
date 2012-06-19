window.LeagueRankingItem = Backbone.Model.extend({});
window.LeagueRankingCollection = Backbone.Collection.extend({
	model: LeagueRankingItem,
	url: 'league/table.json'
});
window.LeagueRankingItemView = Backbone.View.extend({
	tagName: 'tr',
	tpl: _.template($('#tpl-league-tr').html()),
	render: function() {
		var html = this.tpl(this.model.toJSON());
		this.$el.html(html);
		return this;
	}
});
window.LeagueTableView = Backbone.View.extend({
	el: '#league-table',
	initialize: function() {
		this.collection.on('reset', this.render, this);
		this.$tbody = this.$el.find('tbody');
	},
	render: function() {
		this.collection.each(function(item) {
			var itemView = new LeagueRankingItemView({
				model: item
			});
			var html = itemView.render().el;
			this.$tbody.append(html);
		}, this);
		return this;
	}
});