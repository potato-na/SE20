const electron = require('electron');
const core = require('./app.js');
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;
const globalShortcut = electron.globalShortcut;


// window オブジェクトへの参照をキープ
let mainWindow;


function createWindow() {
	var electronOptionsDefaults = {
		width: 800, 
		height: 600, 
		x: 0, 
		y: 0, 
	};

	electronOptionsDefaults.kiosk = true;

	// ブラウザウィンドウの立ち上げ
	mainWindow = new BrowserWindow(electronOptionsDefaults);
	var prefix = 'http://';
	var address = 'localhost';
	var port = '5000';
	mainWindow.loadURL(`${prefix}${address}:${port}`);

	// 'electron . dev' で DevTools の立ち上げ
	if (process.argv.includes('dev')) {
		mainWindow.webContents.openDevTools();
	}
}

// mjpg-streamer のプロセスを kill するシェルスクリプトを実行
function killStreamer() {
	const execSync = require('child_process').execSync;
	try {
		console.log('killing mjpg-streamer process...');
		const stdout = execSync('bash ' + global.root_path + '/_streamer_kill.sh');
		console.log(stdout.toString());
	} 
	catch(error) {
		console.log(error.stderr.toString());
	}

}

// electron 初期化後の処理
app.on('ready', function() {
	console.log('Lauching application');
	createWindow();
});

// 全ウィンドウを閉じた時の処理
app.on('window-all-closed', function() {
	console.log('all window closed');
	app.quit();
	// createWindow();
});

app.on('before-quit', (event) => {
	console.log('Shutting down server...');
	// サーバーシャットダウンの処理
	killStreamer();

	console.log('end before-quit');
});

// サーバーの立ち上げ処理
core.start(function() {
	console.log('server start process ended!');
});