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
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">Euro 2012</a>
          <!-- 
          <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
            </ul>
          </div> --><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">
    	<div id="league" class="row">
    		<ul id="rounds-list" class="span4 nav nav-list well">
    			<li class="nav-header">Rounds</li>
    		</ul>
    		<div id="player-match-table" class="span7">
    			<table class="table"></table>
    		</div>
    	</div>
    	<div class="row">
    		<form id="round-form" class="span6">
	    		<h2>Add Round</h2>
    			<label>Round</label>
    			<input type="text" name="round" style="display:block;" />
    			<button type="submit" class="btn">Save</button>
    		</form>
    		<form id="team-form" class="span6">
    			<h2>Add Team</h2>
    			<label>Team</label>
    			<input type="text" name="team" style="display:block;" />
    			<button type="submit" class="btn">Save</button>
    		</form>
    	</div>
    	<div class="row">
    		<form id="match-form" class="well">
	    		<h2>Add Match</h2>
    			<label>Round</label>
    			<select name="round"></select>
    			<label>Team 1</label>
    			<select name="home"></select>
    			<label>Team 2</label>
    			<select name="away" style="display:block;"></select>
    			<button type="submit" class="btn">Save</button>
    		</form>
    	</div>
    	
    </div> <!-- /container -->
    <script type="text/template" id="tpl-player-match-row">
		<tr>
    		<td>{{match.home.name}} - {{match.away.name}}</td>
    		<td>{{playerOne.username}} vs {{playerTwo.username}}</td>
    	</tr>
	</script>
    <script type="text/template" id="tpl-round-item">
		<li><a id="{{id}}" href="#">{{name}}</a></li>
	</script>
    <script type="text/template" id="tpl-round-team-option">
		<option value="{{id}}">{{name}}</option>
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
    <script src="../resources/backbone/backbone-min.js"></script>
    <script src="../resources/euro-admin.js"></script>

  </body>
</html>

