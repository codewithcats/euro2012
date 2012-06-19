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
    <link href="../resources/assets/css/bootstrap.css" rel="stylesheet">
    <link href="../resources/euro.css" rel="stylesheet">
    <link href="../resources/euro-admin-matches.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="../resources/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../resources/assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../resources/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../resources/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../resources/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../resources/assets/ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="#">Euro 2012</a>
          <div class="nav-collapse">
            <ul class="nav">
              <li><a href="console">Admin</a></li>
              <li class="active"><a href="matches">Match</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
    	<ul class="nav nav-tabs">
			<li class="active">
				<a href="#open-matches" data-toggle="tab">Open</a>
			</li>
			<li><a href="#closed-matches" data-toggle="tab">Closed</a></li>
		</ul>
		<div id="open-matches" class="row active">
		</div>
		<div id="closed-matches" class="row">
			
		</div>
    </div>
    
    <script type="text/template" id="tpl-closed-match" >
		<div class="span3 match">
			<div>
				<h3 class="span3">{{home.name}} {{ftHomeScore}}({{htHomeScore}})-{{ftAwayScore}}({{htAwayScore}}) {{away.name}}</h3>
			</div>
			<div>
				<ul class="span3">
				</ul>
				<div class="span3">
					<button class="btn btn-primary process-trigger"><i class="icon icon-cog icon-white"></i>&nbsp;Process</button>
				</div>
			</div>
		</div>
	</script>
    
    <script type="text/template" id="tpl-match-form">
		<div class="span3">
			<div>
				<h3 class="span3">{{home.name}} vs {{away.name}}</h3>
			</div>
			<div>
				<div class="span3 player-picks">
					<form class="form-inline">
						<label>HT</label>&nbsp;<input type="text" class="input-score" name="homeScoreHt"/> - <input type="text" class="input-score" name="awayScoreHt"/>
					</form>
					<form class="form-inline">
						<label>FT</label>&nbsp;<input type="text" class="input-score" name="homeScoreFt"/> - <input type="text" class="input-score" name="awayScoreFt"/>
					</form>
					<form>
						<label class="checkbox"><input type="checkbox" name="fiveYCards" />&nbsp;Yellow Card &gt; 5</label>
						<label class="checkbox"><input type="checkbox" name="redCard" />&nbsp;Red Card</label>
						<label class="checkbox"><input type="checkbox" name="og" />&nbsp;Own Goal</label>
						<label class="checkbox"><input type="checkbox" name="hattrick" />&nbsp;Hattrick</label>
					</form>
					<div class="form-actions">
						<button class="btn btn-primary close-trigger"><i class="icon icon-ok icon-white"></i>&nbsp;Close</button>
					</div>
				</div>
			</div>
		</div>
	</script>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../resources/assets/js/jquery.js"></script>
    <script src="../resources/assets/js/bootstrap-transition.js"></script>
    <script src="../resources/assets/js/bootstrap-alert.js"></script>
    <script src="../resources/assets/js/bootstrap-modal.js"></script>
    <script src="../resources/assets/js/bootstrap-dropdown.js"></script>
    <script src="../resources/assets/js/bootstrap-scrollspy.js"></script>
    <script src="../resources/assets/js/bootstrap-tab.js"></script>
    <script src="../resources/assets/js/bootstrap-tooltip.js"></script>
    <script src="../resources/assets/js/bootstrap-popover.js"></script>
    <script src="../resources/assets/js/bootstrap-button.js"></script>
    <script src="../resources/assets/js/bootstrap-collapse.js"></script>
    <script src="../resources/assets/js/bootstrap-carousel.js"></script>
    <script src="../resources/assets/js/bootstrap-typeahead.js"></script>
    <script src="../resources/backbone/underscore-min.js"></script>
    <script>
    	_.templateSettings = {
    	    interpolate: /\{\{(.+?)\}\}/g
    	};
    </script>
    <script src="../resources/backbone/backbone-min.js"></script>
    <script src="../resources/app/Match.js"></script>
    <script src="../resources/app/match-console.js"></script>

  </body>
</html>

