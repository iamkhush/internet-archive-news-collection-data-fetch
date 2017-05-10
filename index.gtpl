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
    </style>
  </head>
  <body>
    <div id="root"></div>
    <script src="//unpkg.com/react@15/dist/react.min.js"></script>
    <script src="//unpkg.com/react-dom@15/dist/react-dom.min.js"></script>
    <script src="//unpkg.com/babel-standalone@6/babel.min.js"></script>    
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
      
    </script>
    <script type="text/babel">
      var App = React.createClass({    
        getInitialState: function() {
          return {
            hosts: [],
            this_host: '',
            fetching: false
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
                  <button type="submit" className={this.state.fetching ? "btn btn-primary disabled": "btn btn-primary"} onClick={this.formSubmit}>Submit <i className={this.state.fetching ? "fa fa-spinner fa-spin": ""} /></button> 
                </form>
              </div>
            </div>
            <div className="container">
              <table className="table">
                <thead><tr>
                  <th className="text-center">Host</th>
                  <th className="text-center">Unique Count</th>
                  <th className="text-center">MinDate</th>
                  <th className="text-center">MaxDate</th>
                </tr></thead>
                <tbody>
                  {
                    this.state.hosts.map(function(host){
                      return (
                        <tr>
                          <td>{host.Host}</td>
                          <td>{host.Url}</td>
                          <td>{host.Mindate}</td>
                          <td>{host.Maxdate}</td>
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
          this.setState({fetching: true});
          event.preventDefault();
          $.ajax({
            url: '/fetch?key='+ this.state.this_host,
            type: 'GET',
            success: function(data){
              this.setState(function(prevState){
                return {hosts: prevState.hosts.concat([data]), fetching: false}
              })
            }.bind(this),
            error: function(data){
              alert('Looks like we havent parsed '+this.state.this_host+' yet.')
              this.setState({fetching: false});
            }.bind(this)
          })
        }
      });
    ReactDOM.render(<App />, document.getElementById('root'));
    </script>
  </body>
</html>
