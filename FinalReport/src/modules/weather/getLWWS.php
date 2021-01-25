<?php
// https://torisky.com/javascript%EF%BC%9Aapi%E3%82%B5%E3%83%BC%E3%83%93%E3%82%B9%E3%81%AEjson%E3%83%87%E3%83%BC%E3%82%BF%E3%82%92%E5%8F%96%E5%BE%97%E3%81%97%E3%81%A6%E8%A1%A8%E7%A4%BA%E3%81%99%E3%82%8B/
// http://ginpro.winofsql.jp/article/419510275.htmlより引用
header( "Content-Type: application/json; Charset=utf-8" );
header( "pragma: no-cache" );
header( "Expires: Wed, 9 Feb 2100 14:59:58 GMT" );
header( "Cache-control: no-cache" );
header( "Access-Control-Allow-Origin: *" );

if ( $_GET['city'] == '' ) {
    $_GET['city'] = "270000";
}
 
$work = file_get_contents( "https://weather.tsukumijima.net/api/forecast?city={$_GET['city']}" );
$obj = json_decode( $work );
$work = json_encode( $obj, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT );

print $work;
?>
