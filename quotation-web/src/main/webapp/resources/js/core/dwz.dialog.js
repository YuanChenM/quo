/**
 * @screen core
 * @author jiang_ting
 */
(function($) {
	$.pdialog = {
		_op : {
			height : 300,
			width : 580,
			minH : 40,
			minW : 50,
			total : 20,
			max : false,
			mask : false,
			resizable : true,
			drawable : true,
			maxable : true,
			minable : true,
			fresh : true
		},
		_current : null,
		_zIndex : 42,
		getCurrent : function() {
			return this._current;
		},
		reload : function(url, options) {
			var op = $.extend({
				data : {},
				dialogId : "",
				callback : null
			}, options);
			var dialog = (op.dialogId && $("body").data(op.dialogId))
					|| this._current;
			if (dialog) {
				$.pdialog.reopensize(dialog,options);
				var jDContent = dialog.find(".dialogContent");
				jDContent
						.ajaxUrl({
							type : "POST",
							url : url,
							data : op.data,
							callback : function(response) {
								jDContent.find("[layoutH]").layoutH(jDContent);
								$(".pageContent", dialog).width(
										$(dialog).width() - 14);
								$(":button.close", dialog).click(function() {
									$.pdialog.close(dialog);
									return false;
								});
								if ($.isFunction(op.callback)){
									op.callback(response);
								}
								if(dialog.is(":visible")){
									$.pdialog.switchDialog(dialog);
									$.pdialog.settabindex(dialog);
								}
							}
						});
			}
		},
		//
		openform : function(url, dlgid, title, options, optionsdata) {
			var op = $.extend({}, $.pdialog._op, options);
			var opda = $.extend({
				data : {},
				dialogId : "",
				callback : null
			}, optionsdata);
			var dialog = $("body").data(dlgid);
			// 
			if (dialog) {
				if (dialog.is(":hidden")) {
					dialog.show();
				}
				if (op.fresh || url != $(dialog).data("url")) {
					dialog.data("url", url);
					//dialog.find(".dialogHeader").find("h1").html(title);
					dialog.find(".dialogHeader").find("h1").html("");
					$.pdialog.reopensize(dialog,options);
					this.switchDialog(dialog);
					var jDContent = dialog.find(".dialogContent");
					// jDContent.loadUrl(url, {}, function(){
					// jDContent.find("[layoutH]").layoutH(jDContent);
					// $(".pageContent", dialog).width($(dialog).width()-14);
					// $("button.close").click(function(){
					// $.pdialog.close(dialog);
					// return false;
					// });
					// });
					jDContent.ajaxUrl({
						type : "POST",
						url : url,
						data : opda.data,
						callback : function(response) {
							jDContent.find("[layoutH]").layoutH(jDContent);
							$(".pageContent", dialog).width(
									$(dialog).width() - 14);
							$(":button.close", dialog).click(function() {
								$.pdialog.close(dialog);
								return false;
							});
							if(dialog.is(":visible")){
								$.pdialog.switchDialog(dialog);
								$.pdialog.settabindex(dialog);
							}	
							if ($.isFunction(opda.callback))
								opda.callback(response);
						},
						error:function(){$.pdialog.close(dlgid);}
					});
				}

			} else { // 

				$("body").append(DWZ.frag["dialogFrag"]);
				dialog = $(">.dialog:last-child", "body");
				dialog.data("id", dlgid);
				dialog.data("url", url);
				if (options.close)
					dialog.data("close", options.close);
				if (options.param)
					dialog.data("param", options.param);
				($.fn.bgiframe && dialog.bgiframe());

				//dialog.find(".dialogHeader").find("h1").html(title);
				dialog.find(".dialogHeader").find("h1").html("");
				$(dialog).css("zIndex", ($.pdialog._zIndex += 2));
				$("div.shadow").css("zIndex", $.pdialog._zIndex - 3).show();
				$.pdialog._init(dialog, options);
				$(dialog).click(function() {
					$.pdialog.switchDialog(dialog);
				});

				if (op.resizable)
					dialog.jresize();
				if (op.drawable)
					dialog.dialogDrag();
				$("a.close", dialog).click(function(event) {
					$.pdialog.close(dialog);
					return false;
				});
				if (op.maxable) {
					$("a.maximize", dialog).show().click(function(event) {
						$.pdialog.switchDialog(dialog);
						$.pdialog.maxsize(dialog);
						dialog.jresize("destroy").dialogDrag("destroy");
						return false;
					});
				} else {
					$("a.maximize", dialog).hide();
				}
				$("a.restore", dialog).click(function(event) {
					$.pdialog.restore(dialog);
					dialog.jresize().dialogDrag();
					return false;
				});
				if (op.minable) {
					$("a.minimize", dialog).show().click(function(event) {
						$.pdialog.minimize(dialog);
						return false;
					});
				} else {
					$("a.minimize", dialog).hide();
				}
				$("div.dialogHeader a", dialog).mousedown(function() {
					return false;
				});
				$("div.dialogHeader", dialog).dblclick(function() {
					if ($("a.restore", dialog).is(":hidden"))
						$("a.maximize", dialog).trigger("click");
					else
						$("a.restore", dialog).trigger("click");
				});
				if (op.max) {
					// $.pdialog.switchDialog(dialog);
					$.pdialog.maxsize(dialog);
					dialog.jresize("destroy").dialogDrag("destroy");
				}
				$("body").data(dlgid, dialog);
				$.pdialog._current = dialog;
				$.pdialog.attachShadow(dialog);
				// load data
				var jDContent = $(".dialogContent", dialog);
				// jDContent.loadUrl(url, {}, function(){
				// jDContent.find("[layoutH]").layoutH(jDContent);
				// $(".pageContent", dialog).width($(dialog).width()-14);
				// $("button.close").click(function(){
				// $.pdialog.close(dialog);
				// return false;
				// });
				// });
				jDContent
						.ajaxUrl({
							type : "POST",
							url : url,
							data : opda.data,
							callback : function(response) {
								jDContent.find("[layoutH]").layoutH(jDContent);
								$(".pageContent", dialog).width(
										$(dialog).width() - 14);
								$(":button.close", dialog).click(function() {
									$.pdialog.close(dialog);
									return false;
								});
								if ($.isFunction(opda.callback)){
									opda.callback(response);
								}
								if(dialog.is(":visible")){
									$.pdialog.switchDialog(dialog);
									$.pdialog.settabindex(dialog);
								}
							},
							error:function(){$.pdialog.close(dlgid);}
						});
			}
			if (op.mask) {
				$(dialog).css("zIndex", 1000);
				$("a.minimize", dialog).hide();
				$(dialog).data("mask", true);
				$("#dialogBackground").show();
				$.each($("body").data(), function() {
					if($.pdialog._current.data('id')!=$(this).data('id')){
						if($(this).data("mask")){
							$(this).css("zIndex", 800);
						}
					}
				});
			} else {
				// add a task to task bar
				if (op.minable)
					$.taskBar.addDialog(dlgid, title);
			}
		},
		// Modify for bug#0205791 at 2015/09/18 by li_detian start
		// submit form data to a new dialog, param optionsdata  declares the form data
		openwithformdata : function(url, dlgid, title, options, optionsdata) {
			var op = $.extend({}, $.pdialog._op, options);
			var opda = $.extend({
				data : optionsdata,
				dialogId : "",
				callback : null
			}, optionsdata);
			var dialog = $("body").data(dlgid);
		// 
			if (dialog) {
				if (dialog.is(":hidden")) {
					dialog.show();
				}
				if (op.fresh || url != $(dialog).data("url")) {
					dialog.data("url", url);
					//dialog.find(".dialogHeader").find("h1").html(title);
					dialog.find(".dialogHeader").find("h1").html("");
					$.pdialog.reopensize(dialog,options);
					this.switchDialog(dialog);
					var jDContent = dialog.find(".dialogContent");
					// jDContent.loadUrl(url, {}, function(){
					// jDContent.find("[layoutH]").layoutH(jDContent);
					// $(".pageContent", dialog).width($(dialog).width()-14);
					// $("button.close").click(function(){
					// $.pdialog.close(dialog);
					// return false;
					// });
					// });
					jDContent.ajaxUrl({
						type : "POST",
						url : url,
						data : opda.data,
						callback : function(response) {
							jDContent.find("[layoutH]").layoutH(jDContent);
							$(".pageContent", dialog).width(
								$(dialog).width() - 14);
							$(":button.close", dialog).click(function() {
								$.pdialog.close(dialog);
								return false;
							});
							if(dialog.is(":visible")){
								$.pdialog.switchDialog(dialog);
								$.pdialog.settabindex(dialog);
							}
							if ($.isFunction(opda.callback))
								opda.callback(response);
						},
						error:function(){$.pdialog.close(dlgid);}
					});
				}

			} else { //

				$("body").append(DWZ.frag["dialogFrag"]);
				dialog = $(">.dialog:last-child", "body");
				dialog.data("id", dlgid);
				dialog.data("url", url);
				if (options.close)
					dialog.data("close", options.close);
				if (options.param)
					dialog.data("param", options.param);
				($.fn.bgiframe && dialog.bgiframe());

				//dialog.find(".dialogHeader").find("h1").html(title);
				dialog.find(".dialogHeader").find("h1").html("");
				$(dialog).css("zIndex", ($.pdialog._zIndex += 2));
				$("div.shadow").css("zIndex", $.pdialog._zIndex - 3).show();
				$.pdialog._init(dialog, options);
				$(dialog).click(function() {
					$.pdialog.switchDialog(dialog);
				});

				if (op.resizable)
					dialog.jresize();
				if (op.drawable)
					dialog.dialogDrag();
				$("a.close", dialog).click(function(event) {
					$.pdialog.close(dialog);
					return false;
				});
				if (op.maxable) {
					$("a.maximize", dialog).show().click(function(event) {
						$.pdialog.switchDialog(dialog);
						$.pdialog.maxsize(dialog);
						dialog.jresize("destroy").dialogDrag("destroy");
						return false;
					});
				} else {
					$("a.maximize", dialog).hide();
				}
				$("a.restore", dialog).click(function(event) {
					$.pdialog.restore(dialog);
					dialog.jresize().dialogDrag();
					return false;
				});
				if (op.minable) {
					$("a.minimize", dialog).show().click(function(event) {
						$.pdialog.minimize(dialog);
						return false;
					});
				} else {
					$("a.minimize", dialog).hide();
				}
				$("div.dialogHeader a", dialog).mousedown(function() {
					return false;
				});
				$("div.dialogHeader", dialog).dblclick(function() {
					if ($("a.restore", dialog).is(":hidden"))
						$("a.maximize", dialog).trigger("click");
					else
						$("a.restore", dialog).trigger("click");
				});
				if (op.max) {
					// $.pdialog.switchDialog(dialog);
					$.pdialog.maxsize(dialog);
					dialog.jresize("destroy").dialogDrag("destroy");
				}
				$("body").data(dlgid, dialog);
				$.pdialog._current = dialog;
				$.pdialog.attachShadow(dialog);
				// load data
				var jDContent = $(".dialogContent", dialog);
				// jDContent.loadUrl(url, {}, function(){
				// jDContent.find("[layoutH]").layoutH(jDContent);
				// $(".pageContent", dialog).width($(dialog).width()-14);
				// $("button.close").click(function(){
				// $.pdialog.close(dialog);
				// return false;
				// });
				// });
				jDContent
					.ajaxUrl({
						type : "POST",
						url : url,
						data : opda.data,
						callback : function(response) {
							jDContent.find("[layoutH]").layoutH(jDContent);
							$(".pageContent", dialog).width(
								$(dialog).width() - 14);
							$(":button.close", dialog).click(function() {
								$.pdialog.close(dialog);
								return false;
							});
							if ($.isFunction(opda.callback)){
								opda.callback(response);
							}
							if(dialog.is(":visible")){
								$.pdialog.switchDialog(dialog);
								$.pdialog.settabindex(dialog);
							}
						},
						error:function(){$.pdialog.close(dlgid);}
					});
			}
			if (op.mask) {
				$(dialog).css("zIndex", 1000);
				$("a.minimize", dialog).hide();
				$(dialog).data("mask", true);
				$("#dialogBackground").show();
				$.each($("body").data(), function() {
					if($.pdialog._current.data('id')!=$(this).data('id')){
						if($(this).data("mask")){
							$(this).css("zIndex", 800);
						}
					}
				});
			} else {
				// add a task to task bar
				if (op.minable)
					$.taskBar.addDialog(dlgid, title);
			}
		},
		// Modify for bug#0205791 at 2015/09/18 by li_detian end
		open : function(url, dlgid, title, options) {
			var op = $.extend({}, $.pdialog._op, options);
			var dialog = $("body").data(dlgid);
			// 
			if (dialog) {
				if (dialog.is(":hidden")) {
					dialog.show();
				}
				if (op.fresh || url != $(dialog).data("url")) {
					dialog.data("url", url);
					dialog.find(".dialogHeader").find("h1").html(title);
                    //dialog.find(".dialogHeader").find("h1").html("");
					$.pdialog.reopensize(dialog,options);
					this.switchDialog(dialog);
					var jDContent = dialog.find(".dialogContent");
					jDContent.ajaxUrl({
						url : url,
						data : {},
						callback : function() {
							jDContent.find("[layoutH]").layoutH(jDContent);
							$(".pageContent", dialog).width(
									$(dialog).width() - 14);
							if(dialog.is(":visible")){
								$.pdialog.switchDialog(dialog);
								$.pdialog.settabindex(dialog);
							}
							$(":button.close").click(function() {
								$.pdialog.close(dialog);
								return false;
							});
						},
						error:function(){$.pdialog.close(dlgid);}
					});
				}

			} else { // 

				$("body").append(DWZ.frag["dialogFrag"]);
				dialog = $(">.dialog:last-child", "body");
				dialog.data("id", dlgid);
				dialog.data("url", url);
				if (options.close)
					dialog.data("close", options.close);
				if (options.param)
					dialog.data("param", options.param);
				($.fn.bgiframe && dialog.bgiframe());

				dialog.find(".dialogHeader").find("h1").html(title);
                //dialog.find(".dialogHeader").find("h1").html("");
				$(dialog).css("zIndex", ($.pdialog._zIndex += 2));
				$("div.shadow").css("zIndex", $.pdialog._zIndex - 3).show();
				$.pdialog._init(dialog, options);
				$(dialog).click(function() {
					$.pdialog.switchDialog(dialog);
				});

				if (op.resizable)
					dialog.jresize();
				if (op.drawable)
					dialog.dialogDrag();
				$("a.close", dialog).click(function(event) {
					$.pdialog.close(dialog);
					return false;
				});
				if (op.maxable) {
					$("a.maximize", dialog).show().click(function(event) {
						$.pdialog.switchDialog(dialog);
						$.pdialog.maxsize(dialog);
						dialog.jresize("destroy").dialogDrag("destroy");
						return false;
					});
				} else {
					$("a.maximize", dialog).hide();
				}
				$("a.restore", dialog).click(function(event) {
					$.pdialog.restore(dialog);
					dialog.jresize().dialogDrag();
					return false;
				});
				if (op.minable) {
					$("a.minimize", dialog).show().click(function(event) {
						$.pdialog.minimize(dialog);
						return false;
					});
				} else {
					$("a.minimize", dialog).hide();
				}
				$("div.dialogHeader a", dialog).mousedown(function() {
					return false;
				});
				$("div.dialogHeader", dialog).dblclick(function() {
					if ($("a.restore", dialog).is(":hidden"))
						$("a.maximize", dialog).trigger("click");
					else
						$("a.restore", dialog).trigger("click");
				});
				if (op.max) {
					// $.pdialog.switchDialog(dialog);
					$.pdialog.maxsize(dialog);
					dialog.jresize("destroy").dialogDrag("destroy");
				}
				$("body").data(dlgid, dialog);
				$.pdialog._current = dialog;
				$.pdialog.attachShadow(dialog);
				// load data
				var jDContent = $(".dialogContent", dialog);
				jDContent.ajaxUrl({url : url,
					data : {},
					callback : function() {
						jDContent.find("[layoutH]").layoutH(jDContent);
						$(".pageContent", dialog).width($(dialog).width() - 14);
						$(":button.close").click(function() {
							$.pdialog.close(dialog);
							return false;
						});
						if(dialog.is(":visible")){
							$.pdialog.switchDialog(dialog);
							$.pdialog.settabindex(dialog);
						}},
						error:function(){$.pdialog.close(dlgid);}
						});
			}
			if (op.mask) {
				$(dialog).css("zIndex", 1000);
				$("a.minimize", dialog).hide();
				$(dialog).data("mask", true);
				$("#dialogBackground").show();
				$.each($("body").data(), function() {
					if($.pdialog._current.data('id')!=$(this).data('id')){
						if($(this).data("mask")){
							$(this).css("zIndex", 800);
						}
					}
				});
			} else {
				// add a task to task bar
				if (op.minable)
					$.taskBar.addDialog(dlgid, title);
			}
		},
		/**
		 *
		 * 
		 * @param {Object}
		 *            dialog
		 */
		switchDialog : function(dialog) {
			var index = $(dialog).css("zIndex");
			$.pdialog.attachShadow(dialog);
			if ($.pdialog._current) {
				var cindex = $($.pdialog._current).css("zIndex");
				$($.pdialog._current).css("zIndex", index);
				$(dialog).css("zIndex", cindex);
				$("div.shadow").css("zIndex", cindex - 1);
				$.pdialog._current = dialog;
			}
			$.taskBar.switchTask(dialog.data("id"));
		},
		/**
		 * 
		 * 
		 * @param {Object}
		 *            dialog
		 */
		attachShadow : function(dialog) {
			if(dialog.is(":visible")){
				var shadow = $("div.shadow");
				if (shadow.is(":hidden"))
					shadow.show();
				shadow.css({
					top : parseInt($(dialog)[0].style.top) - 2,
					left : parseInt($(dialog)[0].style.left) - 4,
					height : parseInt($(dialog).height()) + 8,
					width : parseInt($(dialog).width()) + 8,
					zIndex : parseInt($(dialog).css("zIndex")) - 1
				});
				$(".shadow_c", shadow).children().andSelf().each(function() {
					$(this).css("height", $(dialog).outerHeight() - 4);
				});
			}
		},
		_init : function(dialog, options) {
			var op = $.extend({}, this._op, options);
			var height = op.height > op.minH ? op.height : op.minH;
			var width = op.width > op.minW ? op.width : op.minW;
			if (isNaN(dialog.height()) || dialog.height() < height) {
				$(dialog).height(height + "px");
				$(".dialogContent", dialog).height(
						height - $(".dialogHeader", dialog).outerHeight()
								- $(".dialogFooter", dialog).outerHeight() - 6);
			}
			if (isNaN(dialog.css("width")) || dialog.width() < width) {
				$(dialog).width(width + "px");
			}

			var iTop = ($(window).height() - dialog.height()) / 2;
			dialog.css({
				left : ($(window).width() - dialog.width()) / 2,
				top : iTop > 0 ? iTop : 0
			});
		},
		reopensize : function(dialog, options) {
			if(options.height || options.width){
				var height = dialog.height();
				var width = dialog.width();
				if(options.height){
					height = options.height;
				}
				if(options.width){
					width = options.width;
				}
				$(dialog).height(height + "px");
				$(".dialogContent", dialog).height(
						height - $(".dialogHeader", dialog).outerHeight()
								- $(".dialogFooter", dialog).outerHeight() - 6);
				$(dialog).width(width + "px");

				var iTop = ($(window).height() - dialog.height()) / 2;
				dialog.css({
					left : ($(window).width() - dialog.width()) / 2,
					top : iTop > 0 ? iTop : 0
				});
			}
		},
		/**
		 * 
		 * 
		 * @param {Object}
		 *            resizable
		 * @param {Object}
		 *            dialog
		 * @param {Object}
		 *            target
		 */
		initResize : function(resizable, dialog, target) {
			$("body").css("cursor", target + "-resize");
			resizable.css({
				top : $(dialog).css("top"),
				left : $(dialog).css("left"),
				height : $(dialog).css("height"),
				width : $(dialog).css("width")
			});
			resizable.show();
		},
		/**
		 * 
		 * 
		 * @param {Object}
		 *            target
		 * @param {Object}
		 *            options
		 */
		repaint : function(target, options) {
			var shadow = $("div.shadow");
			if (target != "w" && target != "e") {
				shadow.css("height", shadow.outerHeight() + options.tmove);
				$(".shadow_c", shadow).children().andSelf().each(
						function() {
							$(this).css("height",
									$(this).outerHeight() + options.tmove);
						});
			}
			if (target == "n" || target == "nw" || target == "ne") {
				shadow.css("top", options.otop - 2);
			}
			if (options.owidth && (target != "n" || target != "s")) {
				shadow.css("width", options.owidth + 8);
			}
			if (target.indexOf("w") >= 0) {
				shadow.css("left", options.oleft - 4);
			}
		},
		/**
		 * 
		 * 
		 * @param {Object}
		 *            target
		 * @param {Object}
		 *            tmove
		 * @param {Object}
		 *            dialog
		 */
		resizeTool : function(target, tmove, dialog) {
			$("div[class^='resizable']", dialog).filter(
					function() {
						return $(this).attr("tar") == 'w'
								|| $(this).attr("tar") == 'e';
					}).each(function() {
				$(this).css("height", $(this).outerHeight() + tmove);
			});
		},
		/**
		 * 
		 * 
		 * @param {Object}
		 *            obj
		 * @param {Object}
		 *            dialog
		 * @param {Object}
		 *            target
		 */
		resizeDialog : function(obj, dialog, target) {
			var oleft = parseInt(obj.style.left);
			var otop = parseInt(obj.style.top);
			var height = parseInt(obj.style.height);
			var width = parseInt(obj.style.width);
			if (target == "n" || target == "nw") {
				tmove = parseInt($(dialog).css("top")) - otop;
			} else {
				tmove = height - parseInt($(dialog).css("height"));
			}
			$(dialog).css({
				left : oleft,
				width : width,
				top : otop,
				height : height
			});
			$(".dialogContent", dialog).css("width", (width - 12) + "px");
			$(".pageContent", dialog).css("width", (width - 14) + "px");
			if (target != "w" && target != "e") {
				var content = $(".dialogContent", dialog);
				content.css({
					height : height - $(".dialogHeader", dialog).outerHeight()
							- $(".dialogFooter", dialog).outerHeight() - 6
				});
				content.find("[layoutH]").layoutH(content);
				$.pdialog.resizeTool(target, tmove, dialog);
			}
			$.pdialog.repaint(target, {
				oleft : oleft,
				otop : otop,
				tmove : tmove,
				owidth : width
			});
			
			var twidth=0;
			$('table', dialog).each(function(){
				if($(this).width()>twidth){
					twidth=$(this).width();
				}
			});
			if(twidth> dialog.width()){
				$("div.panelBar", dialog).width(twidth);
				$("div.totalBar", dialog).width(twidth);
				$("div.detailpanelBar", dialog).width(twidth);
				if($("h1.contentTitle", dialog).parent().is('div')){
				$("h1.contentTitle", dialog).width(twidth-24);
				}
			}else{
				$("div.panelBar", dialog).css("width","");
				$("div.totalBar", dialog).css("width","");
				$("div.detailpanelBar", dialog).css("width","");
				if($("h1.contentTitle", dialog).parent().is('div')){
				$("h1.contentTitle", dialog).css("width","");
				}
			}
			
			$(window).trigger("resizeGrid");
		},
		close : function(dialog) {
			if (typeof dialog == 'string')
				dialog = $("body").data(dialog);
			var close = dialog.data("close");
			var go = true;
			if (close && $.isFunction(close)) {
				var param = dialog.data("param");
				if (param && param != "") {
					param = DWZ.jsonEval(param);
					go = close(param);
				} else {
					go = close();
				}
				if (!go)
					return;
			}
			if ($.fn.xheditor) {
				$("textarea.editor", dialog).xheditor(false);
			}
			$(dialog).unbind("click").hide();
			$("div.dialogContent", dialog).html("");
			$("div.shadow").hide();
			if ($(dialog).data("mask")) {
				var oth=true;
				$.each($("body").data(), function() {
					if($(dialog).data('id')!=$(this).data('id')){
						if($(this).data("mask")&& $(this).css("zIndex")=='800'){
							if(oth){
								$(this).css("zIndex", 1000);
								$.pdialog.switchDialog($(this));
							}
							oth=false;
						}
					}
				});
				if(oth){
				$("#dialogBackground").hide();
				}
			} else {
				if ($(dialog).data("id"))
					$.taskBar.closeDialog($(dialog).data("id"));
			}
			$("body").removeData($(dialog).data("id"));
			$(document).trigger("click");
			$(dialog).remove();
		},
		closeCurrent : function() {
			this.close($.pdialog._current);
		},
		checkTimeout : function() {
			var $conetnt = $(".dialogContent", $.pdialog._current);
			var json = DWZ.jsonEval($conetnt.html());
			if (json && json.statusCode == DWZ.statusCode.timeout)
				this.closeCurrent();
		},
		maxsize : function(dialog) {
			$(dialog).data("original", {
				top : $(dialog).css("top"),
				left : $(dialog).css("left"),
				width : $(dialog).css("width"),
				height : $(dialog).css("height")
			});
			$("a.maximize", dialog).hide();
			$("a.restore", dialog).show();
			var iContentW = $(window).width();
			var iContentH = $(window).height() - 34;
			$(dialog).css({
				top : "0px",
				left : "0px",
				width : iContentW + "px",
				height : iContentH + "px"
			});
			$.pdialog._resizeContent(dialog, iContentW, iContentH);
			$.pdialog.attachShadow(dialog);
		},
		restore : function(dialog) {
			var original = $(dialog).data("original");
			var dwidth = parseInt(original.width);
			var dheight = parseInt(original.height);
			$(dialog).css({
				top : original.top,
				left : original.left,
				width : dwidth,
				height : dheight
			});
			$.pdialog._resizeContent(dialog, dwidth, dheight);
			$("a.maximize", dialog).show();
			$("a.restore", dialog).hide();
			$.pdialog.attachShadow(dialog);
		},
		minimize : function(dialog) {
			$(dialog).hide();
			$("div.shadow").hide();
			var task = $.taskBar.getTask($(dialog).data("id"));
			$(".resizable").css({
				top : $(dialog).css("top"),
				left : $(dialog).css("left"),
				height : $(dialog).css("height"),
				width : $(dialog).css("width")
			}).show().animate({
				top : $(window).height() - 60,
				left : task.position().left,
				width : task.outerWidth(),
				height : task.outerHeight()
			}, 250, function() {
				$(this).hide();
				$.taskBar.inactive($(dialog).data("id"));
			});
		},
		_resizeContent : function(dialog, width, height) {
			var content = $(".dialogContent", dialog);
			content.css({
				width : (width - 12) + "px",
				height : height - $(".dialogHeader", dialog).outerHeight()
						- $(".dialogFooter", dialog).outerHeight() - 6
			});
			content.find("[layoutH]").layoutH(content);
			$(".pageContent", dialog).css("width", (width - 14) + "px");
			
			var twidth=0;
			$('table', dialog).each(function(){
				if($(this).width()>twidth){
					twidth=$(this).width();
				}
			});
			if(twidth> dialog.width()){
				$("div.panelBar", dialog).width(twidth);
				$("div.totalBar", dialog).width(twidth);
				$("div.detailpanelBar", dialog).width(twidth);
				if($("h1.contentTitle", dialog).parent().is('div')){
				$("h1.contentTitle", dialog).width(twidth-24);
				}
			}else{
				$("div.panelBar", dialog).css("width","");
				$("div.totalBar", dialog).css("width","");
				$("div.detailpanelBar", dialog).css("width","");
				if($("h1.contentTitle", dialog).parent().is('div')){
				$("h1.contentTitle", dialog).css("width","");
				}
			}
			
			$(window).trigger("resizeGrid");
			$(document).trigger("click");
		},
		settabindex:function(dialog){
			try {
				if($('#skipautofocus',dialog).length==0){
					var iff = $('input[type!=hidden]:visible',dialog);
					iff = iff.filter('[type=text]:not([disabled]):not([readonly]):not("#pageinput"):not([tabindex]):first');
					if(iff.length==1){
						iff.trigger('focus');
					}else{
						iff = $('input[type=password]:visible:first',dialog);
						if(iff.length==1){
							iff.trigger('focus');
						}else{
						
						var aff = $('a:visible:not([disabled]):not("#pageinput"):not([tabindex]):first',$(".dialogContent", dialog));
						if(aff.length==1){
							try {
								aff.trigger('focus');
								event.preventDefault();
							} catch (e) {
							}
						}else{
							try {
								$('#diaclose',dialog).trigger('focus');
								event.preventDefault();
							} catch (e) {
							}
						}
						}
					}
				}else{
					try {
						$('#diaclose',dialog).trigger('focus');
						event.preventDefault();
					} catch (e) {
					}
				}
			} catch (e) {
			}
			$('input[type!=hidden][type=button]:last',dialog).keydown(function(event){
				if (event.keyCode==DWZ.keyCode.TAB && !event.shiftKey){
					try {
						$('#diaclose',dialog).trigger('focus');
						event.preventDefault();
					} catch (e) {
					}
				}
			});
			$('#diaclose',dialog).keydown(function(event){
				if (event.keyCode==DWZ.keyCode.TAB && event.shiftKey){
					try {
						$('input[type!=hidden][type=button]:last',dialog).trigger('focus');
						event.preventDefault();
					} catch (e) {
					}
				}
			});
		}
	};
})(jQuery);