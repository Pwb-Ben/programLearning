var arr = [1, [2, [3, [4, 5]]]];
var res = arr.flat(Infinity);
console.log(arr);

// var http = require('http');
//
// http.createServer(function (request, response) {
//
//     // 发送 HTTP 头部
//     // HTTP 状态值: 200 : OK
//     // 内容类型: text/plain
//     response.writeHead(200, {'Content-Type': 'text/plain'});
//
//     // 发送响应数据 "Hello World"
//     response.end('Hello World\n');
// }).listen(8888);
//
// // 终端打印如下信息
// console.log('Server running at http://127.0.0.1:8888/');

// var fs = require("fs");
//
// // 异步读取
// fs.readFile('test.txt', function (err, data) {
//     if (err) {
//         return console.error(err);
//     }
//     console.log("异步读取: " + data.toString());
// });
//
// // 同步读取
// var data = fs.readFileSync('test.txt');
// console.log("同步读取: " + data.toString());
//
// console.log("程序执行完毕。");