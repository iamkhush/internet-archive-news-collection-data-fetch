var path = require('path');

module.exports = {
  entry: './app/index.js',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist')
  },
  module: {
	  loaders: [{
	    loader: 'babel-loader',
	    test: /\.js$/,
	    exclude: /node_modules/,
	  }]
  }
}