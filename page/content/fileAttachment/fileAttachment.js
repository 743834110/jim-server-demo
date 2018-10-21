// socket = new WebSocket("wss:"+ip+":"+port+"?username="+username+"&password="+password);


/*
    * 测试WebSocket上传
    * 本地浏览器上传速度测试单个文件，上传速度IE>FF>Google(Google浏览器慢相当多)
    */
var reader = null;  //读取操作对象
var step = 1024 * 256;  //每次读取文件大小 ,字节数(每次读取256K)
var cuLoaded = 0; //当前已经读取总数
var file = null; //当前读取的文件对象
var enableRead = true;//标识是否可以读取文件
var total = 0;        //记录当前文件总字节数
var startTime = null; //标识开始上传时间
var slice = 1; //分片数量
var index = 1; //以1开始，当前分发分片的索引
var onchange = function (event) {

    //获取文件对象
    file = event.target.files[0];
    total = file.size;
    console.info(file);
    // 获取改文件被分片后的数量
    slice = Math.ceil(file.size * 1.0 / step);
    index = 1;
    console.log("应当被分片的数量为：" + slice);
    if (ws == null) {
        if (window.confirm('建立与服务器链接失败,确定重试链接吗')) {
            createSocket(function () {
                bindReader();
            });
        }
        return;
    }
    bindReader();
}
//绑定reader
function bindReader() {
    cuLoaded = 0;
    startTime = new Date();
    enableRead = true;
    reader = new FileReader();
    //读取一段成功
    reader.onload = function (e) {
        console.log(e)
        console.info('读取总数：' + e.loaded);
        if (enableRead == false)
            return false;
        //根据当前缓冲区来控制客户端读取速度
        var webSocket = ws;
        if (ws.bufferedAmount > step * 10) {
            setTimeout(function () {
                //继续读取,等待三毫秒再读取
                console.log('--------------》进入等待');
                loadSuccess(e.loaded);
            }, 3);
        } else {
            //继续读取
            loadSuccess(e.loaded);
        }
    };
    reader.onloadend = function (ev) {

    };

    //开始读取
    readBlob();
}

/**
 * 指定开始位置，分块读取文件
 */

function readBlob() {
    //指定开始位置和结束位置读取文件
    var blob = file.slice(cuLoaded, cuLoaded + step);
    reader.readAsDataURL(blob);
}

//读取文件成功处理
function loadSuccess(loaded) {
    //将分段数据上传到服务器
    var blob = reader.result;
    //使用WebSocket 服务器发送数据
    var message = JSON.stringify({
        "from": "55555",
        "to": "888",
        "cmd": 11,
        "createTime": function () {
            return new Date().getTime();
        },
        "msgType": 6,
        "chatType": 2,
        "content": blob,
        "extras": {
            "file": {

                "name": file.name,
                "size": file.size,
                "index": index,
                "slice": slice,
                "currentLoading": loaded
            }
        }
    });
    ws.send(message);
    index ++;
    //如果没有读完，继续
    cuLoaded += loaded;
    if (cuLoaded < total) {
        readBlob();
    } else {
        console.log('总共上传：' + cuLoaded + ',总共用时：' + (new Date().getTime() - startTime.getTime()) / 1000);
    }
    //显示结果进度
    var percent = (cuLoaded / total) * 100;
    document.getElementById('Status').innerText = percent;
    document.getElementById('progressOne').value = percent;
}

//中止
function stop() {
    //中止读取操作
    console.info('中止，cuLoaded：' + cuLoaded);
    enableRead = false;
    reader.abort();
}
//继续
function containue() {
    console.info('继续，cuLoaded：' + cuLoaded);
    enableRead = true;
    readBlob();
}
var ws = null;
//创建和服务器的WebSocket 链接
function createSocket(onSuccess) {
    var ip = "localhost";
    var port = "8880";
    ws = new WebSocket("wss:"+ip+":"+port+"?username="+"18487545454"+"&password="+"fdfdfdf");
    ws.onopen = function () {
        console.log('connected成功');
        console.log(ws)
    }
    ws.onmessage = function (e) {
        var data = e.data;
        if (isNaN(data) == false) {
            //console.log('当前上传成功：' + data);
        } else {
            console.info(data);
        }
    }
    ws.onclose = function (e) {
        //中止客户端读取
        stop();
        console.log('链接断开');
    }
    ws.onerror = function (e) {
        //中止客户端读取
        stop();
        console.info(e);
        console.log('传输中发生异常');
    }
}
//页面加载完建立链接
window.onload = function (ev) {
    createSocket();
}
