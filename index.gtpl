<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IA - News Fetch Application</title>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.css" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style type="text/css">
      .App {
        text-align: center;
      }

      .App-header {
        background-color: #222;
        padding: 20px;
        color: white;
        margin-bottom: 20px
      }

      .fa-times {
        margin-left: -57px;
        padding-right: 45px;
      }

      .recharts-wrapper {
        margin-left: 20%;
      }
    </style>
  </head>
  <body>
    
    <div class="App">
      <div class="App-header">
        <h2>Welcome to News Data Fetcher App</h2>
      </div>
      <div class="text-center">Approx Unique Host Count is {{.}}</span></div>
      <div id="root"></div>
    </div>
    <script src="/dist/bundle.js"></script>
  </body>
</html>
