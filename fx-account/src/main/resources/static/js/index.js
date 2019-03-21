var data = [{
		img: 1,
		h1: '安全存储',
		h2: '生活井井有条'
	},
	{
		img: 2,
		h1: '在线预览',
		h2: '文件即开即看'
	},
	{
		img: 3,
		h1: '多端并用',
		h2: '数据随身携带'
	},
	{
		img: 4,
		h1: '好友分享',
		h2: '共度美好时光'
	},
];
var g = function(id) {
	if(id.substr(0, 1) == '.') {
		return document.getElementsByClassName(id.substr(1));
	}
	return document.getElementById(id);
}
//添加幻灯片的操作
function addSliders() {
	//3.1 获取模板
	var tpl_main = g('template_main').innerHTML
		.replace(/^\s*/, '')
		.replace(/\s*$/, '');
	var tpl_ctrl = g('template_ctrl').innerHTML
		.replace(/^\s*/, '')
		.replace(/\s*$/, '');
	//3.2 定义最终输出HTML的变量
	var out_main = [];
	var out_ctrl = [];
	//3.3 遍历所有数据，构建最终输出的HTML
	for(i in data) {
		var _html_main = tpl_main
			.replace(/{{index}}/g, data[i].img)
			.replace(/{{h2}}/g, data[i].h1)
			.replace(/{{h3}}/g, data[i].h2)
			.replace(/{{css}}/g,['','main-i_right'][i%2]);
		var _html_ctrl = tpl_ctrl
			.replace(/{{index}}/g, data[i].img)
		out_main.push(_html_main);
		out_ctrl.push(_html_ctrl);
	}
	
	//3.4 把HTML 回写到对应的DOM 里面
	g('template_main').innerHTML = out_main.join('');
	g('template_ctrl').innerHTML = out_ctrl.join('');
	//7 增加#main_background
	g('template_main').innerHTML += tpl_main			
									.replace(/{{index}}/g,'{{index}}')
									.replace(/{{h2}}/g,data[i].h1)
									.replace(/{{h2}}/g,data[i].h2);
	g('main_{{index}}').id='main_background';
}
//5. 幻灯片切换
function switchSlider(n) {
	//5.1 获得要展现的幻灯片&控制按钮 DOM
	var main = g('main_' + n);
	var ctrl = g('ctrl_' + n);
	//5.2 获得所有的幻灯片&控制按钮
	var clear_main = g('.main-i');
	var clear_ctrl = g('.ctrl-i');
	//5.3 清除他们的active样式
	for(i=0;i<clear_ctrl.length;i++){
		clear_main[i].className=clear_main[i].className
								.replace(' main-i_active','');
		clear_ctrl[i].className=clear_ctrl[i].className
								.replace(' ctrl-i_active','');
	}
	//5.4 附加样式
	main.className += ' main-i_active';
	ctrl.className += ' ctrl-i_active';
	
	//7.2 切换时，复制上一张幻灯片到main_background中
	setTimeout(function(){
	g('main_background').innerHTML=main.innerHTML;
	},1000);
	
}
//6. 动态调整图片的 margin-top 以使其垂直居中
function movePictures(){
	var pictures=g('.picture');
	for(i=0;i<pictures.length;i++){
		pictures[i].style.marginTop=(-1 * pictures[i].clientHeight/2) + 'px';
	}
}


// 4.定义何时处理幻灯片输出
window.onload = function() {
	addSliders();
	switchSlider(1);
	setTimeout(function(){
		movePictures();
	},100);
	//幻灯片自动播放
	var x=1;
	setInterval(function(){
		if(x>4)	x=1;
		switchSlider(x++);
	}
	,3000)
}