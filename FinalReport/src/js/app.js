var path = require('path');
var fs = require('fs');
var Server = require(__dirname + '/server.js');
var WSever = require(__dirname + '/websocket.js');

// global absolute root path
global.root_path = path.resolve(__dirname + '/../');

// core app class
var App = function() {
	// {Function} callback Function はスタートした後にコール
	this.start = function(callback) {
		// Web サーバーの立ち上げ
		var server = new Server(function(app) {
			console.log('Server started ...');

			if (typeof callback == 'function') {
				callback();
			}
		});

		// WebSocket サーバーの立ち上げ
		var wserver = new WSever();
	};
};

module.exports = new App();