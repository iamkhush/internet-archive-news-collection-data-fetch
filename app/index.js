import React from 'react';
import ReactDOM from 'react-dom';
import {BarChart, Bar, XAxis, YAxis, Tooltip, Legend} from 'recharts';
import Jquery from 'jquery';

var App = React.createClass({    
  getInitialState: function() {
    return {
      hosts: [],
      this_host: '',
      fetching: false,
      data: []
    }
  },
  render: function() {
    return (
      <div>
        <div className="form-group">
          <div className="container">
            <form action="#" method="GET" onSubmit={this.formSubmit}>
                <div className="form-group">
                  <input type="text" name="key" className="form-control" placeholder="Enter the host name." onChange={this.handleChange} value={this.state.this_host}/>
                </div>
                <button type="submit" className={this.state.fetching?"btn btn-primary disabled":"btn btn-primary"} onClick={this.formSubmit}>Submit <i className={this.state.fetching?"fa fa-spinner fa-spin":""} /></button> 
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
                  var min = host.Mindate.substr(0,4) + '-' + host.Mindate.substr(4,2) + '-' +
                  host.Mindate.substr(6,2);
                  var max = host.Maxdate.substr(0,4) + '-' + host.Maxdate.substr(4,2) + '-' +
                  host.Maxdate.substr(6,2);
                  return (
                    <tr>
                      <td><i className="fa fa-times" onClick={this.deleteRow}></i>{host.Host}</td>
                      <td>{host.Url}</td>
                      <td>{min}</td>
                      <td>{max}</td>
                    </tr>
                  )
                }.bind(this))
              }
            </tbody>
          </table>
          <BarChart width={600} height={300} data={this.state.data}>
            <XAxis dataKey="key"/>
            <YAxis />
            <Tooltip/>
            <Legend />
            <Bar dataKey="count" fill="#0275d8" />
          </BarChart>
        </div>
      </div>
    );
  },
  deleteRow: function(e){
    this.setState({hosts: [], data:[], this_host:''});
  },
  handleChange: function(e) {
    this.setState({
      this_host: e.target.value
    })
  },
  formSubmit: function(event){
    this.setState({fetching: true, hosts: [], data:[]});
    event.preventDefault();
    Jquery.ajax({
      url: '/fetch?key='+ this.state.this_host,
      type: 'GET',
      success: function(data){
        this.setState(function(prevState){
          var modUrlCount = data.UrlCount.map(function(eachDict){
            eachDict['count'] = parseInt(eachDict['count']);
            return eachDict;
          })
          return {
            hosts: prevState.hosts.concat([data]),
            fetching: false, 
            data: modUrlCount
          }
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