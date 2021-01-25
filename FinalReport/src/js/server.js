var express = require('express'); 
var app = require('express')();
var path = require('path');
var fs = require('fs');

// サーバーの立ち上げ
var Server = function(callback) {
	var port = '5000';
	var server = require('http').Server(app);
	var address = 'localhost';

	server.listen(port, address)

	// root にアクセスがあったときに実行する処理
	app.get('/', function(req, res) {
		console.log(global.root_path)
		var html = fs.readFileSync(global.root_path + "/index.html", {encoding: 'utf8'});
		res.send(html)
	});

	if (typeof callback == 'function') {
		callback(app);
	}

	// /js のアクセスがあったときに実行
	app.use('/js', express.static(__dirname));
	var directories = ['/css', '/modules'];
	var directory;
	for (var i in directories) {
		directory = directories[i];
		app.use(directory, express.static(path.resolve(global.root_path + directory)));
	}
};

module.exports = Server;