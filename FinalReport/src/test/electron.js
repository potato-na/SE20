// https://qiita.com/yoshizaki_kkgk/items/da9711c26e71522ad289
// 挙動テスト

// Electron側の初期設定
const electron = require('electron');
const app = electron.app;
const BrowserWindow = electron.BrowserWindow;
let mainWindow;

// アプリを閉じた時にquit
app.on('window-all-closed', function() {
  app.quit();
});

// アプリ起動後の処理
app.on('ready', function() {
  // 別プロセスを呼び出し
  // spawn: CLI を呼び出して実行
  // この処理でポートを開けている
  var subpy = require('child_process').spawn('python3',['test/hello.py']);
  var rq = require('request-promise');
  var mainAddr = 'http://localhost:5000';

  var openWindow = function() {
    mainWindow = new BrowserWindow({width: 400, height: 300 });
    mainWindow.loadURL(mainAddr);

    // 終了処理
    mainWindow.on('closed', function() {
      mainWindow = null;
      subpy.kill('SIGINT');
    });
  };

  var startUp = function() {
    rq(mainAddr)
      .then(function(htmlString) {
        console.log('server started');
        openWindow();
      })
      .catch(function(err) {
      	console.log(err)
      	return
        startUp();
      });
  };

  startUp();
});