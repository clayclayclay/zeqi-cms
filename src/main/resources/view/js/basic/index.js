
function openNew(){
	// 获取页面的高度和宽度
	var sWidth=document.body.scrollWidth;
	var sHeight=document.body.scrollHeight;

	// 获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;

	var oMask=document.createElement("div");
	oMask.id="mask";
	oMask.style.height=sHeight+"px";
	oMask.style.width=sWidth+"px";
	document.body.appendChild(oMask);
	var oLogin=document.createElement("div");
	oLogin.id="login";
	oLogin.innerHTML="<div class='loginCon'><p class='log'><i class='icon-cloud'></i>Login</p><div class='ueserInput'><p><i class='icon-user'></i><input type='text' id='ueser'></p></div><div class='ueserInput'><p><i class='icon-lock'></i><input type='password' id='passwords'></p></div><div class='remberPar'><div id='rember'><div id='backWhite'></div><div id='remberButton'></div></div> <span>remember</span></div><button id='enter'>Login</button></div>";
	document.body.appendChild(oLogin);

	// 获取登陆框的宽和高
	var dHeight=oLogin.offsetHeight;
	var dWidth=oLogin.offsetWidth;
	// 设置登陆框的left和top
	oLogin.style.left=sWidth/2-dWidth/2+"px";
	oLogin.style.top=wHeight/2-dHeight/2+"px";

	// 输入默认的用户名与保存的密码的用户的密码
	if ($.cookie('username')) {
		$('#ueser').attr('value',$.cookie('username'));
	}

	if ($.cookie('passwords')) {
		$('#passwords').attr('value',$.cookie('passwords'));
		$('#remberButton').css('left',"+=24px");
		$('#backWhite').css('left',"+=40px");

	}



	// 点击关闭按钮
	// var oClose=document.getElementById("close");
	var scheck=document.getElementById("enter");
	scheck.onclick=function(){
		$.cookie('username',$('#ueser').val());

		var remember = 0;
		if ($('#remberButton').position().left>2) {
			var password = $('#passwords').val();
			$.cookie('passwords',password,{
				expires:1
			});
		}else{
			$.cookie('passwords',null);
		};

		var ueservale = $('#ueser').val();
		var passwordsvale = $('#passwords').val();
		if(ueservale == "" || passwordsvale == ""){
			alert("输入不能为空");
			return;
		};

		$.ajax({
			type:"post",
			url:"/web/login",
			data:{
				"username":ueservale,
				"password":passwordsvale
			},
			dataType:"json",
			beforeSend:function(data){
				$("<i class='icon-spinner icon-spin loader'></i>").appendTo($('#mask'));
				var winH = $(window).height();
				$('.loader').css('top',winH/2);
				$('#login').css('zIndex','1000');

			},
			success:function(data){
				$('.loader').remove();
				$('#login').css('zIndex','1003');
				if (data.status == true) {
					$('#par').empty();
					$('#par').html("<a><img class='user' src=" + data.jsonStr + " id='user'></a>");
					$.cookie('is_first','true',{
							expires:7
						}

					);
					$.cookie("UERSER_VIEW","<a><img class='user' src=" + data.jsonStr + " id='user'></a>",{
						expires:7
					});
					// $('#par a').attr('href',"http://www.zeqiwo.com/web/guy/"
					// + ueservale);
					$('#par a').attr('href',"/web/guy");
					document.body.removeChild(oLogin);
					document.body.removeChild(oMask);
				}else{
					alert(data.errMsg.message);


				}
			},
			error:function(data){
				$('.loader').remove();
				$('#login').css('zIndex',1003);
				alert('服务器繁忙');
			}
		});
	}

	// 点击登陆框以外的区域也可以关闭登陆框
	oMask.onclick=function(){
		document.body.removeChild(oLogin);
		document.body.removeChild(oMask);
	};

	$('#remberButton').click(function(event){
		if ($(this).position().left < 25) {
			$(this).animate({left:"+=24px"},400);
			$('#backWhite').animate({left:"+=40px"},400);
		}else{
			$(this).animate({left:"-=24px"},400);
			$('#backWhite').animate({left:"-=40px"},400);
		}
	});

};


$().ready(function(){
	var Lheight = $(window).height()-30;
	$("#section1").css('height', Lheight);

	var oBtn=document.getElementById("login_out");
	// 点击登录按钮
	oBtn.onclick=function(){
		openNew();
		return false;
	}

	var articleHeight =30;


	window.addEventListener('scroll',scrollHandler);
	var direction='bottom';                   // 实现四个部门块的上下移动
	(function(){
		var css={
			'top':'-=20px'
		};
		if(direction==='bottom'){
			direction='top';
			css.top='+=20px';
		}else{
			direction='bottom';
		}
		$('.tec').animate(css,1200,arguments.callee);
		$('.out').animate(css,1200,arguments.callee);
	})();
	var direction2='bottom';
	(function(){
		var css={
			'top':'+=20px'
		};
		if(direction2==='bottom'){
			direction2='top';
			css.top='-=20px';
		}else{
			direction2='bottom';
		}
		$('.money').animate(css,1200,arguments.callee);
		$('.project').animate(css,1200,arguments.callee);
	})();

	function scrollHandler(e){						// 监听滚动条事件
		var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
		if (scrollTop > 0 && scrollTop <articleHeight) {
		}else if (scrollTop >= articleHeight && scrollTop < articleHeight+200) {
			$('#p2').removeClass('beforetransform').addClass('transform');
			if ($('.p1').css("left") < "200") {
				$('.p1').stop(true).animate({left:"300px"},100);
			};
			if ($('.p3').css("left") > "1000") {
				$('.p3').stop(true).animate({left:"810px"},100);
			};
		}else if (scrollTop >= articleHeight+200 && scrollTop < articleHeight+700) {

		}else if (scrollTop >= articleHeight+1200 && scrollTop <= articleHeight + 1500) {
			$('#section4 div').removeClass('beforetransform2').addClass('transform');
		}else if (scrollTop >= articleHeight+1500 || scrollTop <= articleHeight+ 1200) {
			$('#section4 div').removeClass('transform').addClass('beforetransform2');
		}








	}
	$(function(){

		var $el = $( '#wi-el' );
			windy = $el.windy( {
				// rotation and translation boundaries for the items transitions
				boundaries : {
					rotateX : { min : 40 , max : 90 },
					rotateY : { min : -15 , max : 45 },
					rotateZ : { min : -10 , max : 10 },
					translateX : { min : -400 , max : 400 },
					translateY : { min : -400 , max : 400 },
					translateZ : { min : 350 , max : 550 }
				}
			} ),
			allownavnext = false,
			allownavprev = false;

		$( '#nav-prev' ).on( 'mousedown', function( event ) {

			allownavprev = true;
			navprev();

		} ).on( 'mouseup mouseleave', function( event ) {

			allownavprev = false;

		} );

		$( '#nav-next' ).on( 'mousedown', function( event ) {

			allownavnext = true;
			navnext();

		} ).on( 'mouseup mouseleave', function( event ) {

			allownavnext = false;

		} );

		function navnext() {
			if( allownavnext ) {
				windy.next();
				setTimeout( function() {
					navnext();
				}, 150 );
			}
		}

		function navprev() {
			if( allownavprev ) {
				windy.prev();
				setTimeout( function() {
					navprev();
				}, 150 );
			}
		}

	});

	$(".part").bind("mouseover",function(){           // 部门弹出介绍
		var des = $(this).attr("class").split(/\s+/);

		var outdes = '.' + des[0] + 'Des';

		$(outdes).fadeIn(1200);
		$(this).mouseleave(function(){
			$(outdes).stop();
			$(outdes).fadeOut(1200);
		});
	});


	// $.cookie("UERSER_VIEW",null);

});

