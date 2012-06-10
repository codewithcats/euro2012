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
		<div id="reg" class="row">
			<h2 class="span4 offset4">One step away from the battle!</h2>
			<form class="well span4 offset4">
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
		<div id="signin" class="row">
			<h2 class="span4 offset4">Sign in to your field.</h2>
			<form class="well span4 offset4">
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
		<div id="dashboard" class="row">
			<div id="user-info"></div>
			<div id="round">
				<h2></h2>
			</div>
		</div>
    </div> <!-- /container -->
    
    <!-- Template -->
    <script type="text/template" id="tpl-round-header">
		{{name}}
	</script>
    <script type="text/template" id="tpl-alert">
		<div class="span4 offset4 alert">
			<button class="close" data-dismiss="alert">×</button>
			<h4 class="alert-heading">{{title}}</h4>
  			<p>{{msg}}</p>
		</div>
	</script>
	<script type="text/template" id="tpl-user-info">
		<h3>{{username}} <a rel="tooltip" class="signout-trigger" title="click to sign out" href="#"><i class="icon icon-off"></i></a></h3>
		<p><strong class="points">{{leaguePoints}}</strong> points</p>
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
    <script src="resources/backbone/backbone-min.js"></script>
    <script src="resources/euro.js"></script>

  </body>
</html>

