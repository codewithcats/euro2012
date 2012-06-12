window.Match = Backbone.Model.extend({
	
});
window.OpenMatchCollection = Backbone.Collection.extend({
	model: Match,
	url: 'matches/open.json'
});
window.ClosedMatchCollection = Backbone.Collection.extend({
	model: Match,
	url: 'matches/close.json'
});