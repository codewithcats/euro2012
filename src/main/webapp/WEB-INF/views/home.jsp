<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Euro 2012 | Battles!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="resources/assets/css/bootstrap.css" rel="stylesheet">
    <link href="resources/euro.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="resources/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="resources/assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="resources/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="resources/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="resources/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="resources/assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div id="main-nav" class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="#">Euro 2012</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li id="dashboard-nav-link" class="active"><a href="#">Dashboard</a></li>
              <li><a href="#league">League</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
		<div id="reg" class="content">
			<div class="row">
				<div class="span2 offset3" style="text-align:right;">
					<img src="resources/images/cup.png"/>
				</div>
				<form class="span4">
					<label>Username</label>
					<input type="text" name="username" />
					<label>Password</label>
					<input type="password" name="password" style="display:block;" />
					<button type="submit" class="btn btn-primary">
						<i class="icon icon-ok icon-white"></i>
						&nbsp;Register
					</button>
				</form>
			</div>
		</div>
		<div id="signin" class="row content">
			<div class="span2 offset3" style="text-align:right;">
				<img src="resources/images/cup.png"/>
			</div>
			<form class="span4">
				<label>Username</label>
				<input type="text" name="username" />
				<label>Password</label>
				<input type="password" name="password" style="display:block;" />
				<button type="submit" class="btn btn-primary">
					<i class="icon icon-lock icon-white"></i>
					&nbsp;Sign In
				</button>
				<button class="btn reg-trigger">
					<i class="icon icon-user"></i>
					&nbsp;Register
				</button>
			</form>
			<div id="signin-fail" class="span4 offset4 alert">
				<button class="close" data-dismiss="alert">×</button>
				<h4 class="alert-heading">Something Wrong!</h4>
  				<p>Check your username, password, then try again.</p>
			</div>
		</div>
		<div id="dashboard" class="content">
			<div id="user-info" class="page-header">
			</div>
			<ul class="nav nav-tabs">
				<li class="active"><a href="#player-matches" data-toggle="tab">Battles</a></li>
				<li><a href="#history" data-toggle="tab">History</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="player-matches">
				</div>
				<div class="tab-pane" id="history">
					
				</div>
			</div>
		</div>
		<div id="league" class="content">
			<div class="page-header">
				<h1>League Information</h1>
			</div>
			<div class="row">
				<table id="league-table" class="table table-striped span6">
					<thead>
						<tr>
							<th>#</th>
							<th><i class="icon icon-user"></i>&nbsp;Player</th>
							<th>Game Points</th>
							<th>League Points</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>
    </div> <!-- /container -->
    
    <!-- Template -->
    <script type="text/template" id="tpl-league-tr">
			<td class="rank">{{rank}}</td>
			<td class="player">{{player.username}}</td>
			<td class="game-points">{{player.gamePoints}}pts</td>
			<td class="league-points">{{player.leaguePoints}}pts</td>
	</script>
    <script type="text/template" id="tpl-history-match">
			<div class="span4">
				<h2>{{resultTag}}&nbsp;{{opponent.username}}&nbsp;{{mypick.points}} - {{opponentpick.points}}</h2>
				<h3 class="home"><img src="resources/images/flags/{{match.home.icon}}"/>&nbsp;{{match.home.name}} vs {{match.away.name}}&nbsp;<img src="resources/images/flags/{{match.away.icon}}"/></h3>
			</div>
			<table class="table span8">
				<thead>
					<tr>
						<th><i class="icon icon-th-list"></i></th>
						<th><i class="icon icon-user"></i>&nbsp;{{me.username}}</th>
						<th><i class="icon icon-globe"></i>&nbsp;Actual</th>
						<th><i class="icon icon-user"></i>&nbsp;{{opponent.username}}</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>HT</td>
						<td>{{mypick.htScore}}</td>
						<td>{{match.htHomeScore}}-{{match.htAwayScore}}</td>
						<td>{{opponentpick.htScore}}</td>
					</tr>
					<tr>
						<td>FT</td>
						<td>{{mypick.ftScore}}</td>
						<td>{{match.ftHomeScore}}-{{match.ftAwayScore}}</td>
						<td>{{opponentpick.ftScore}}</td>
					</tr>
					<tr>
						<td>Yellow cards > 5</td>
						<td>{{mypick.yellow}}</td>
						<td>{{match.yellow}}</td>
						<td>{{opponentpick.yellow}}</td>
					</tr>
					<tr>
						<td>Red card</td>
						<td>{{mypick.red}}</td>
						<td>{{match.red}}</td>
						<td>{{opponentpick.red}}</td>
					</tr>
					<tr>
						<td>Own goal</td>
						<td>{{mypick.og}}</td>
						<td>{{match.og}}</td>
						<td>{{opponentpick.og}}</td>
					</tr>
					<tr>
						<td>Hattrick</td>
						<td>{{mypick.hattrick}}</td>
						<td>{{match.hattrick}}</td>
						<td>{{opponentpick.hattrick}}</td>
					</tr>
					<tr>
						<td><strong>Total Points</strong></td>
						<td><strong>{{mypick.points}}</strong></td>
						<td></td>
						<td><strong>{{opponentpick.points}}</strong></td>
					</tr>
				</tbody>
			</table>
	</script>
	<script type="text/template" id="tpl-history-match-by">
		<div class="span4">
			<h2>{{resultTag}}&nbsp;{{opponent.username}}&nbsp;(by default)</h2>
			<h3 class="home"><img src="resources/images/flags/{{match.home.icon}}"/>&nbsp;{{match.home.name}} vs {{match.away.name}}&nbsp;<img src="resources/images/flags/{{match.away.icon}}"/></h3>
		</div>
	</script>
    <script type="text/template" id="tpl-round-header">
		{{name}}
	</script>
    <script type="text/template" id="tpl-alert">
		<div class="span4 offset4 alert {{clazz}}">
			<button class="close" data-dismiss="alert">×</button>
			<h4 class="alert-heading">{{title}}</h4>
  			<p>{{msg}}</p>
		</div>
	</script>
	<script type="text/template" id="tpl-alert-match">
		<div class="alert {{clazz}}">
			<button class="close" data-dismiss="alert">×</button>
			<strong>{{title}}<strong>{{msg}}
		</div>
	</script>
	<script type="text/template" id="tpl-user-info">
		<h1>{{username}}, {{leaguePoints}}pts
			<small class="points">game point: {{gamePoints}}pts&nbsp;<span>(<a class="signout-trigger" title="click to sign out" href="#">sign out</a>)</span></small>
		</h1>
	</script>
	<script type="text/template" id="tpl-match-row">
			<div class="row">
				<h3 class="span3"><span class="badge">{{index}}</span> {{match.home.name}} vs {{match.away.name}}</h3>
				<h3 class="span9"><span class="label label-important">opponent</span> {{opponent.username}} ({{opponent.leaguePoints}} points)</h3>
				<p class="span12">{{round.name}}</p>
			</div>
			<div class="row">
				<div class="span11 offset1">
					<h3 class="span2 home"><img src="resources/images/flags/{{match.home.icon}}"/>&nbsp;{{match.home.name}}</h3>
					<div class="span3 player-picks">
						<form class="form-inline">
							<label>HT</label>&nbsp;<input type="text" class="input-score" name="homeScoreHt"/> - <input type="text" class="input-score" name="awayScoreHt"/>
							<span class="badge badge-warning">20P</span>&nbsp;<span class="badge badge-success">40P</span>
						</form>
						<form class="form-inline">
							<label>FT</label>&nbsp;<input type="text" class="input-score" name="homeScoreFt"/> - <input type="text" class="input-score" name="awayScoreFt"/>
							<span class="badge badge-warning">20P</span>&nbsp;<span class="badge badge-success">40P</span>
						</form>
						<form>
							<label class="checkbox"><input type="checkbox" name="fiveYCards" />&nbsp;Yellow Card > 5 <span class="label label-inverse">10P</span>&nbsp;<span class="label label-info">10P</span></label>
							<label class="checkbox"><input type="checkbox" name="redCard" />&nbsp;Red Card <span class="label label-inverse">10P</span>&nbsp;<span class="label label-info">30P</span></label>
							<label class="checkbox"><input type="checkbox" name="og" />&nbsp;Own Goal <span class="label label-inverse">10P</span>&nbsp;<span class="label label-info">30P</span></label>
							<label class="checkbox"><input type="checkbox" name="hattrick" />&nbsp;Hattrick <span class="label label-inverse">10P</span>&nbsp;<span class="label label-info">50P</span></label>
						</form>
						<div class="form-actions">
							<span class="last-update">{{lastUpdateTag}}</span>
							<button class="btn btn-primary save-trigger"><i class="icon icon-ok icon-white"></i>&nbsp;Save</button>
						</div>
					</div>
					<h3 class="span2"><img src="resources/images/flags/{{match.away.icon}}"/>&nbsp;{{match.away.name}}</h3>
				</div>
			</div>
	</script>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="resources/assets/js/jquery.js"></script>
    <script src="resources/assets/js/bootstrap-transition.js"></script>
    <script src="resources/assets/js/bootstrap-alert.js"></script>
    <script src="resources/assets/js/bootstrap-modal.js"></script>
    <script src="resources/assets/js/bootstrap-dropdown.js"></script>
    <script src="resources/assets/js/bootstrap-scrollspy.js"></script>
    <script src="resources/assets/js/bootstrap-tab.js"></script>
    <script src="resources/assets/js/bootstrap-tooltip.js"></script>
    <script src="resources/assets/js/bootstrap-popover.js"></script>
    <script src="resources/assets/js/bootstrap-button.js"></script>
    <script src="resources/assets/js/bootstrap-collapse.js"></script>
    <script src="resources/assets/js/bootstrap-carousel.js"></script>
    <script src="resources/assets/js/bootstrap-typeahead.js"></script>
    <script src="resources/backbone/underscore-min.js"></script>
    <script>
    	_.templateSettings = {
    	    interpolate: /\{\{(.+?)\}\}/g
    	};
    </script>
    <script src="resources/backbone/backbone-min.js"></script>
    <script src="resources/app/PlayerMatch.js"></script>
    <script src="resources/app/League.js"></script>
    <script src="resources/euro.js"></script>

  </body>
</html>

