$(document).ready(
		function() {
			var userIframeId = $(".user").attr('href');
			// 获取iframe
			window.addEventListener('message', function(e) {
				switch (e.data.origin) {
				case ("modify"):
					$(".userName").text(e.data.name + "-" + e.data.nick_name);
					$(".position").text(e.data.position);
					break;
				case ("headimg"):
					$(".imgofhead").attr('src', e.data.imgurl);
					break;
				}

			});
			var isOk = false;
			$("#newPasswords").blur(
					function(event) {
						if ($(this).val() != $("#checkPasswords").val()
								&& $("#checkPasswords").val() != "") {
							$("#curl-error").css('display', 'inline');
							isOk = false;
						} else if ($(this).val() != "") {
							isOk = true;
							$("#curl-error").css('display', 'none');
						}
					});

			$("#checkPasswords").blur(function(event) {
				if ($(this).val() != $("#newPasswords").val()) {
					$("#curl-error").css('display', 'inline');
					isOk = false;
				} else if ($(this).val() != "") {
					isOk = true;
					$("#curl-error").css('display', 'none');
				}
			});
			$("#upNewPasswords").on(
					'click',
					function(event) {
						var oldP = $("#oldPasswords").val();
						var newP = $("#newPasswords").val();
						if (isOk == false) {
							if (oldP == "" || newP == ""
									|| $("#checkPasswords").val() == "") {
								swal("小仔>_<", "有空没填就想跑？", "error");
							} else {
								swal("小仔>_<", "你确定两次密码是一样的吗？", "error");
							}
						} else {
							$.ajax({
								url : "/web/guy/password",
								type : 'PUT',
								dataType : 'json',
								// contentType:'json',
								data : {
									"oldPassword" : $("#oldPasswords").val(),
									"newPassword" : $("#newPasswords").val()
								}
							}).done(function(data) {
								if (data.status == true) {
									$(".close").trigger('click');
									swal("修改成功", "密码更新成功", "success");

								} else {
									swal("修改失败", data.errMsg.message, "error");
								}
							}).fail(function() {
								swal("修改失败", "请检查网络情况", "error");
							}).always(function() {
								console.log("complete");
							});

						}
						;
						return false;

					});

		});