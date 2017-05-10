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
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/babel">
      var App = React.createClass({    
        getInitialState: function() {
          return {
            hosts: [],
            this_host: ''
          }
        },
        render: function() {
          return (
          <div className="App">
            <div className="App-header">
              <h2>Welcome to News Data Fetcher App</h2>
            </div>
            <div className="form-group">
              <div className="container">
                <form action="#" method="GET" onSubmit={this.formSubmit}>
                  <input type="text" name="key" className="form-control" placeholder="Enter the host name." onChange={this.handleChange} />
                  <button type="submit" className="btn btn-primary" onClick={this.formSubmit}>Submit</button> 
                </form>
              </div>
            </div>
            <div className="container">
              <table className="table">
                <thead><tr>
                  <th>Host</th>
                  <th>Unique Count</th>
                  <th>MinDate</th>
                  <th>MaxDate</th>
                </tr></thead>
                <tbody>
                  {
                    this.state.hosts.map(function(host){
                      return (
                        <tr>
                          <td>{host.host}</td>
                          <td>{host.url_count}</td>
                          <td>{host.mindate}</td>
                          <td>{host.maxdate}</td>
                        </tr>
                      )
                  })
                  }
                </tbody>
              </table>
            </div>
          </div>
          );
        },
        handleChange: function(e) {
          this.setState({
            this_host: e.target.value
          })
        },
        formSubmit: function(event){
          event.stopPropagation();
          $.ajax({
            url: '/fetch?key='+ this.state.this_host,
            type: 'GET',
            success: function(data){
              data.host = this.state.this_host;
              this.setState(function(prevState){
                hosts: prevState.hosts.concat([data])
              })
            }.bind(this)
          })
        }
      });
    ReactDOM.render(<App />, document.getElementById('root'));
    </script>
  </body>
</html>
