<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>IA - News Fetch Application</title>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" crossorigin="anonymous">
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
    </style>
  </head>
  <body>
    <div id="root"></div>
    <script src="//unpkg.com/react@15/dist/react.min.js"></script>
    <script src="//unpkg.com/react-dom@15/dist/react-dom.min.js"></script>
    <script src="//unpkg.com/babel-standalone@6/babel.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/4.2.1/lodash.min.js"></script>
    <script type="text/babel">
      var App = React.createClass({    
        render: function() {
          return (
          <div className="App">
            <div className="App-header">
              <h2>Welcome to News Data Fetcher App</h2>
            </div>
            <div className="form-group">
              <div className="container">
                <form action="/fetch" method="GET" >
                  <input type="text" name="key" className="form-control" placeholder="Enter the host name." />
                </form>
              </div>
            </div>
          </div>
          );
        }
      });
    ReactDOM.render(<App />, document.getElementById('root'));
    </script>
  </body>
</html>
