/**
 * @screen core
 * @author jiang_ting
 */

/*
data log dialog
@param table
@param key
 */
function showdatalog(table,key){
	if ($.pdialog) {
		var url="datalog?table="+table+"&key="+key;
		$.pdialog.open(url, "datalog", "Data Log", {mask:true,width:1000,height:360});
	}
}
/*

@param show 
@param hiddenId 
@param len 
 */
function formatChange(show, hiddenId, len) {
	var id = document.getElementById(hiddenId);
	show.value = formatThouDigit(show.value, len, 1);
	id.value = formatNum(show.value);
} 

/*

@param num 
@return '1234567.45'
@type String
 */
function formatNum(num) {
	return num.toString().replace(/\$|\,/g, '');
}



/**
 * mselectoptionmove
 * @param fbox from select
 * @param tbox to select
 */
function mselectoptionmove(fbox, tbox) {
	if($(fbox).find("option:selected").length>=1){
		//$(tbox).append($(fbox).find("option:selected").attr("selected",false));
		$(tbox).find("option:selected").attr("selected",false);
		$(tbox).append($(fbox).find("option:selected"));
		$(fbox).find("option:selected").remove();
		var sortop = $('option',$(tbox)).sort(function(a,b){ 
		    var aText = $(a).text().toUpperCase(); 
		    var bText = $(b).text().toUpperCase(); 
		    if(aText>bText) return 1; 
		    if(aText<bText) return -1; 
		    return 0; 
		});
		sortop.appendTo($(tbox));
	}
}


/*

@param num 
@param cent 
@param isThousand 
@return '1,234,567.45'
@type String
*/
function formatThouDigit(num, digit, isThousand) 
{
	var old = num;
	if(num==""){
		return old;
	}
	num = num.toString().replace(/\$|\,/g, '');
	if(!num.startsWith("0.")){
		num = num.toString().replace(/(0*)/, '');
		if(num==""){
			num="0";
		}
	}
	
	if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(num)) {
		return old;
	}
	var a = RegExp.$1,
	b = RegExp.$2,
	c = RegExp.$3;
	if(digit==0){
		if(parseFloat(c)>=0.5){
			b = (parseFloat(b)+1)+"";
		}
	}
	var re = new RegExp("(\\d)(\\d{3})(,|$)");
	
	if (isNaN(isThousand))// 
		isThousand = 0;
	isThousand = parseInt(isThousand);
	if (isThousand < 0)
		isThousand = 0;
	if (isThousand >= 1) // 
		isThousand = 1;
	if (isThousand == 1){ // 
		while (re.test(b)){
			b = b.replace(re, "$1,$2$3");
		}
	}
	if (isNaN(digit))// 
		digit = 0;
	digit = parseInt(digit);
	digit = Math.abs(digit);// 
	if (c && digit && new RegExp("^.(\\d{" + digit + "})(\\d)").test(c)) {
		if (RegExp.$2 > 4){
			c = (parseFloat(RegExp.$1) + 1) / Math.pow(10, digit);
			if((c+"").indexOf("e")>=0){
				try {
					c=c.toFixed(digit);
				} catch (e) {
					c = "." + RegExp.$1;
				}
			}
			if((c+"").startsWith("0.")){
				c = (c+"").replace("0.",".");
			}
		}else{
			c = "." + RegExp.$1;
		}
	}
	if(digit==0){
		c="";
	}else{
		if((c+"")==""){
			c=".0";
		}
		if(c+""=="1"){
			c=".0";
			b=(parseFloat(b.toString().replace(/\$|\,/g, ''))+1)+"";
			//Modify for Bug#0118020 at 2013/08/23 by jiang_nan Start 
			//var re = new RegExp().compile("(\\d)(\\d{3})(,|$)");
			re = new RegExp("(\\d)(\\d{3})(,|$)");
			//Modify for Bug#0118020 at 2013/08/23 by jiang_nan Start 
			if (isThousand == 1){ // 
				while (re.test(b)){
					b = b.replace(re, "$1,$2$3");
				}
			}
		}
		while ((c+"").length < digit+1) {// 
			c = c+ "0";
		}
	}
	

	return a + "" + b + "" + (c + "").substr((c + "").indexOf("."));
}


/*

@param show 
@param hiddenId
@param len 
 */
function percentChange(percent, hiddenId) {
	var id = document.getElementById(hiddenId);
	id.value = restorePercent(percent.value);
	percent.value = formatPercent(percent.value);
} 

/*
@param num 
@return '0.6745'
@type String
 */
function restorePercent(num) {
	if(num == "") {
		return "0";
	}
	num = num.toString().replace(/\$|\,|\%/g, '');
	if (isNaN(num)) {
		num = "0";
	}
	return num;
}

/*

@param num (NumberString)
@return '67.45%'
@type String
*/
function formatPercent(num) {
	if(num == "") {
		return "0";
	}
	num = num.toString().replace(/\$|\,|\%/g, '');
	if (isNaN(num)) {
		num = "0";
	}
	if(num.indexOf(".") < 0) {
		return num * 100 + '%';
	}
	var numArray = num.split(".");
	var fir = numArray[0];
	var sed = (Number(1 + "." + numArray[1]) + "").slice(2);
	if (sed.length < 2) {
		fir = fir + sed * 10;
		sed = "";
	} else if (sed.length == 2) {
		fir = fir + sed;
		sed = "";
	} else {
		fir = fir + sed.slice(0, 2);
		sed = "." + sed.slice(2);
	}
	return Number(fir) + sed + "%";
}

function initEnv() {
	$("body").append(DWZ.frag["dwzFrag"]);

	if ($.browser.msie && /6.0/.test(navigator.userAgent)) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		} catch (e) {
		}
	}
	//
	if ($.browser.msie) {
		window.setInterval("CollectGarbage();", 10000);
	}

	$(window).resize(function() {
		initLayout();
		$(this).trigger("resizeGrid");
	});

	var ajaxbg = $("#background,#progressBar");
	ajaxbg.hide();
	$(document).ajaxStart(function() {
		ajaxbg.show();
	}).ajaxStop(function() {
		ajaxbg.hide();
	});

	$("#leftside").jBar({
		minW : 150,
		maxW : 700
	});

	if ($.taskBar)
		$.taskBar.init();
	navTab.init();
	// if ($.fn.switchEnv) $("#switchEnvBox").switchEnv();
	// if ($.fn.switchEnv) $("#switchLanguageBox").switchEnv();
	// if ($.fn.switchEnv) $("#profileBox").switchEnv();
	if ($.fn.switchEnv)
		$(".switchBox").switchEnv();
	if ($.fn.navMenu)
		$("#navMenu").navMenu();

	//modify bug 0083702 jiang_ting begin
	//setTimeout(function() {
		initLayout();
		initUI();
		// navTab styles
		var jTabsPH = $("div.tabsPageHeader");
		jTabsPH.find(".tabsLeft").hoverClass("tabsLeftHover");
		jTabsPH.find(".tabsRight").hoverClass("tabsRightHover");
		jTabsPH.find(".tabsMore").hoverClass("tabsMoreHover");

	//}, 10);
	//modify bug 0083702 jiang_ting end

}

var callmainback = false;
function initLayout() {
	var iContentW = $(window).width()
			- (DWZ.ui.sbar ? $("#sidebar").width() + 10 : 16) + 2;
	var iContentH = $(window).height() - $("#header").height() - 0;

	$("#container").width(iContentW);
	$("#container .tabsPageContent").height(
			iContentH - $("#tabsPageFooter").height()
					- $("#splitFooterBar").height() - 9).find("[layoutH]")
			.layoutH();
	$("#sidebar, #splitBar, #splitBarProxy").height(iContentH - 5);
	$("#sidebar_s .collapse").height(iContentH - 7);
	$("#taskbar").css({
		top : iContentH + $("#header").height() + 5,
		width : $(window).width()
	});
	$("#sidebar .menuContent")
			.height(
					$("#sidebar").height()
							- $("#sidebar .toggleCollapse").height() - 3);
	
	var $p = navTab.getCurrentPanel();
	barresize($p);
	if(callmainback){
		maincallback="";
	}
	if (typeof maincallback === "function"){
		maincallback();
		callmainback = true;
	}

	// alert($("#container #layout:first").height());
	// var hh = $("#tabsPageFooter").height() + $("#splitFooterBar").height();
	// $("#container #layout
	// div:first").height(iContentH-hh-42).css("overflow-y","auto").css("overflow-x","visible");
}

/**
 * opennewmain
 * @param {Object} form or {"aa":"bb","cc":"dd"}
 * @param {Object} url
 */
function opennewmain(form, url,target){
	var $form = $(form), $newmain = $("#newmain");
	//if(!$form.valid()) {return false;}
	target = target || "_blank";
	if ($newmain.size() >= 1) {
		$newmain.remove();
	}
	var data=''; 
	if($form.is('form')){
		data = $form.serialize();
	}else{
		data = $.param(form);
	}
	$newmain = $("<form id='newmain' action='main' target='"+target+"' name='newmain' style='display:none' method='post'></form>").appendTo("body");
	$newmain.append('<input type="hidden" name="openformurl" value="'+url+'" />');
	$newmain.append('<input type="hidden" name="openformdata" value="'+data+'" />');
	$newmain.submit();
}


function initUI(_box) {
	var $p = $(_box || document);
	// var iContentH = $(window).height() - $("#header").height() - 5;
	// var hh = $("#tabsPageFooter").height() + $("#splitFooterBar").height();
	// $("#container #layout
	// div:first").height(iContentH-hh-42).css("overflow-y","auto").css("overflow-x","visible");

	// tables
	$("table.table", $p).jTable();

	// css tables
	$('table.list', $p).cssTable();
	
	
	var $pp = navTab.getCurrentPanel();
	barresize($pp);

	$p.scroll(function(){ //
		$(document).trigger("click");
	});
	
	// auto bind tabs
	$("div.tabs", $p).each(function() {
		var $this = $(this);
		var options = {};
		options.currentIndex = $this.attr("currentIndex") || 0;
		options.eventType = $this.attr("eventType") || "click";
		$this.tabs(options);
	});

	$("ul.tree", $p).jTree();
	$('div.accordion', $p).each(function() {
		var $this = $(this);
		$this.accordion({
			fillSpace : $this.attr("fillSpace"),
			alwaysOpen : true,
			active : 0
		});
	});

	$(":button.checkboxCtrl, :checkbox.checkboxCtrl", $p).checkboxCtrl($p);
	$(":button", $p).click(function() {
		$(document).trigger("click");
		var $this = $(this);
		$this.attr("disabled","disabled");
		setTimeout(function() { $this.removeAttr("disabled"); }, 2000);
	});
	
	

	if ($.fn.combox)
		$("select.combox", $p).combox();

	if ($.fn.xheditor) {
		$("textarea.editor", $p).each(
				function() {
					var $this = $(this);
					var op = {
						html5Upload : false,
						skin : 'vista',
						tools : $this.attr("tools") || 'full'
					};
					var upAttrs = [
							[ "upLinkUrl", "upLinkExt", "zip,rar,txt" ],
							[ "upImgUrl", "upImgExt", "jpg,jpeg,gif,png" ],
							[ "upFlashUrl", "upFlashExt", "swf" ],
							[ "upMediaUrl", "upMediaExt", "avi" ] ];

					$(upAttrs).each(function(i) {
						var urlAttr = upAttrs[i][0];
						var extAttr = upAttrs[i][1];

						if ($this.attr(urlAttr)) {
							op[urlAttr] = $this.attr(urlAttr);
							op[extAttr] = $this.attr(extAttr) || upAttrs[i][2];
						}
					});

					$this.xheditor(op);
				});
	}

	if ($.fn.uploadify) {
		$(":file[uploader]", $p).each(
				function() {
					var $this = $(this);
					var options = {
						uploader : $this.attr("uploader"),
						script : $this.attr("script"),
						cancelImg : $this.attr("cancelImg"),
						queueID : $this.attr("fileQueue") || "fileQueue",
						fileDesc : $this.attr("fileDesc")
								|| "*.jpg;*.jpeg;*.gif;*.png;*.pdf",
						fileExt : $this.attr("fileExt")
								|| "*.jpg;*.jpeg;*.gif;*.png;*.pdf",
						folder : $this.attr("folder"),
						auto : true,
						multi : true,
						onError : uploadifyError,
						onComplete : uploadifyComplete,
						onAllComplete : uploadifyAllComplete
					};
					if ($this.attr("onComplete")) {
						options.onComplete = DWZ.jsonEval($this
								.attr("onComplete"));
					}
					if ($this.attr("onAllComplete")) {
						options.onAllComplete = DWZ.jsonEval($this
								.attr("onAllComplete"));
					}
					if ($this.attr("scriptData")) {
						options.scriptData = DWZ.jsonEval($this
								.attr("scriptData"));
					}
					$this.uploadify(options);
				});
	}

	// init styles
	$("input[type!=hidden][type=text], input[type!=hidden][type=password], textarea", $p).addClass(
			"textInput").focusClass("focus");
	$(":input[type!=hidden][editmodel=false]",$p).each(function(){
		var $this = $(this);
		if($this.hasClass('required')){
			$this.removeClass('required');
		};
		if($this.is(":checkbox,:radio")){
			$this.attr("disabled","disabled");
		}else{
			$this.attr("readonly","readonly");
		}
	});

	$(":input[type!=hidden][readonly]:not('.button')",$p).each(function(){
		var $this = $(this);
		if(!$this.is(":checkbox,:radio")){
			$this.addClass("readonly");
		}
	});
	$(":input[type!=hidden][disabled]:not('.button')",$p).each(function(){
		var $this = $(this);
		if(!$this.is(":checkbox,:radio")){
			$this.addClass("disabled");
		}
	});
	
	// Grid ToolBar
	$("div.panelBar li, div.panelBar", $p).hoverClass("hover");
	// Button
	$("div.button", $p).hoverClass("buttonHover");
	$("div.buttonActive", $p).hoverClass("buttonActiveHover");

	// tabsPageHeader
	$(
			"div.tabsHeader li, div.tabsPageHeader li, div.accordionHeader, div.accordion",
			$p).hoverClass("hover");

	$("div.panel", $p).jPanel();

	// validate form
	$("form.required-validate", $p).each(function() {
		$(this).validate({
			focusInvalid : false,
			focusCleanup : true,
			errorElement : "span",
			ignore : ".ignore",
			invalidHandler : function(form, validator) {
				var errorCount = validator.numberOfInvalids();
				if (errorCount && errorCount > 0) {
					var message = "";

//					var errors = validator.invalid;
//					// var message = DWZ.msg("validateFormError",[errors]);
//					for ( var error in errors) {
//						if (message != "") {
//							message += "</br>";
//						}
//						message += error + ":" + errors[error];
//					}
					for ( var i = 0; validator.errorList[i]; i++ ) {
						var error = validator.errorList[i];
						//message += $('>label',$(error.element).parent().prev()).text() + error.message;
						if($(error.element).attr("forerrmsg")){
							message += $(error.element).attr("forerrmsg")+": "+ error.message+"<br>";							
						}else{
							if($(error.element).hasClass('date')){
								message += ($('label',$(error.element).parents('table:first').parent().prev()).length==0?error.element.name+":":$('label',$(error.element).parents('table:first').parent().prev()).text()) +" "+ error.message+"<br>";
							}else if($(error.element).parent().hasClass('select')){
								message += ($('label',$(error.element).parents('div.combox').parent().prev()).length==0?error.element.name+":":$('label',$(error.element).parents('div.combox').parent().prev()).text()) +" "+ error.message+"<br>";
							}else{
								message += ($('label',$(error.element).parent().prev()).length==0?error.element.name+":":$('label',$(error.element).parent().prev()).text()) +" "+ error.message+"<br>";
							}
						}
					}
					alertMsg.error(message);
				}
			}
		});
	});

	if ($.fn.datepicker) {
		$('input.date', $p).each(function() {
			var $this = $(this);
			this.tabIndex=-1;
				$this.parent().wrapInner("<table style='border-spacing:0px;padding:0px;"+$this.attr('style')+"'><tr></tr></table>");
				$this.parent().find('a.inputDateButton').wrap("<td style='padding:0 1px 0 2px;width:20px;height:21px;'></td>");
				$this.css("width","100%").wrap("<td style='padding:0px;width:100%;overflow:visible;'></td>");
			//alert($this.outerWidth());
			//alert($this.parent().width());
			var opts = {};
			if ($this.attr("format"))
				opts.pattern = $this.attr("format");
			if ($this.attr("yearstart"))
				opts.yearstart = $this.attr("yearstart");
			if ($this.attr("yearend"))
				opts.yearend = $this.attr("yearend");
			
			if($this.val()!=""){
				var dl = ($this.attr("format")||"dd-NNN-yyyy").length;
				$this.val($this.val().substring(0,dl));
			}
			$this.datepicker(opts);
			
			if($this.attr('onchange')){
				var func = $this.attr('onchange');
				$this.attr('onchange',"");
				$this.change(function(){
					$this.data("changed",true);
				});
				$this.attr('ondatachange',func);
			}
		});
	}
	
	$("input[type=text]", $p).not("div.tabs input[type=text]", $p).filter("[alt]").inputAlert();
	$("textarea", $p).filter("[alt]").inputAlert();

	// navTab
	$("a[target=navTab]", $p).each(
			function() {
				$(this).click(
						function(event) {
							var $this = $(this);
							var title = $this.attr("title") || $this.text();
							// var tabid = "main";
							var tabid = $this.attr("rel") || "main";
							var fresh = eval($this.attr("fresh") || "true");
							var external = eval($this.attr("external")
									|| "false");
							var url = unescape($this.attr("href"))
									.replaceTmById(
											$(event.target).parents(
													".unitBox:first"));
							DWZ.debug(url);
							if (!url.isFinishedTm()) {
								alertMsg.error($this.attr("warn")
										|| DWZ.msg("alertSelectMsg"));
								return false;
							}
							navTab.openTab(tabid, url, {
								title : title,
								fresh : fresh,
								external : external
							});

							event.preventDefault();
						});
			});

	// dialogs
	$("a[target=dialog]", $p).each(
			function() {
				$(this)
						.click(
								function(event) {
									var $this = $(this);
									var title = $this.attr("title")
											|| $this.text();
									var rel = $this.attr("rel") || "_blank";
									var options = {};
									var w = $this.attr("width");
									var h = $this.attr("height");
									if (w)
										options.width = w;
									if (h)
										options.height = h;
									options.max = eval($this.attr("max")
											|| "false");
									options.mask = eval($this.attr("mask")
											|| "false");
									options.maxable = eval($this
											.attr("maxable")
											|| "true");
									options.minable = eval($this
											.attr("minable")
											|| "true");
									options.fresh = eval($this.attr("fresh")
											|| "true");
									options.resizable = eval($this
											.attr("resizable")
											|| "true");
									options.drawable = eval($this
											.attr("drawable")
											|| "true");
									options.close = eval($this.attr("close")
											|| "");
									options.param = $this.attr("param") || "";

									var url = unescape($this.attr("href"))
											.replaceTmById(
													$(event.target).parents(
															".unitBox:first"));
									DWZ.debug(url);
									if (!url.isFinishedTm()) {
										alertMsg.error($this.attr("warn")
												|| DWZ.msg("alertSelectMsg"));
										return false;
									}
									$.pdialog.open(url, rel, title, options);
									if($this.parent().parent().is('td')){
										$this.parent().parent().trigger('click');
									}
									return false;
								});
			});

	// dialogs
	$("form[target=dialog]", $p).each(function() {
		this.onsubmit = function() {
			var $this = $(this);
			var title = $this.attr("title") || "";
			var rel = $this.attr("rel") || "_blank";
			var options = {};
			var w = $this.attr("width");
			var h = $this.attr("height");
			if (w)
				options.width = w;
			if (h)
				options.height = h;
			options.max = eval($this.attr("max") || "false");
			options.mask = eval($this.attr("mask") || "false");
			options.maxable = eval($this.attr("maxable") || "true");
			options.minable = eval($this.attr("minable") || "true");
			options.fresh = eval($this.attr("fresh") || "true");
			options.resizable = eval($this.attr("resizable") || "true");
			options.drawable = eval($this.attr("drawable") || "true");
			options.close = eval($this.attr("close") || "");
			options.param = $this.attr("param") || "";

			// var url =
			// unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
			// DWZ.debug(url);
			// if (!url.isFinishedTm()) {
			// alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
			// return false;
			// }
			// $.pdialog.open(url, rel, title, options);
			var $form = $(this);
			// if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value
			// = 1;
			$.pdialog.openform($form.attr('action'), rel, title, options, {
				data : $form.serializeArray()
			});

			return false;
		};
	});
	$("a[target=ajax]", $p).each(function() {
		$(this).click(function(event) {
			var $this = $(this);
			var rel = $this.attr("rel");
			if (rel) {
				var $rel = $("#" + rel);
				$rel.loadUrl($this.attr("href"), {}, function() {
					$rel.find("[layoutH]").layoutH();
				});
			}

			event.preventDefault();
		});
	});

	$("div.pagination", $p).each(function() {
		var $this = $(this);
		$this.pagination({
			targetType : $this.attr("targetType"),
			rel : $this.attr("rel"),
			totalCount : $this.attr("totalCount"),
			numPerPage : $this.attr("numPerPage"),
			pageNumShown : $this.attr("pageNumShown"),
			currentPage : $this.attr("currentPage"),
			totalPages : $this.attr("totalPages")
		});
	});

	if ($.fn.sortDrag)
		$("div.sortDrag", $p).sortDrag();

	// dwz.ajax.js
	if ($.fn.ajaxTodo)
		$("a[target=ajaxTodo]", $p).ajaxTodo();
	if ($.fn.dwzExport)
		$("a[target=dwzExport]", $p).dwzExport();

	if ($.fn.lookup)
		$("a[lookupGroup]", $p).lookup();
	if ($.fn.multLookup)
		$("[multLookup]:button", $p).multLookup();
	if ($.fn.suggest)
		$("input[suggestFields]", $p).suggest();
	if ($.fn.itemDetail)
		$("table.itemDetail", $p).itemDetail();
	if ($.fn.selectedTodo)
		$("a[target=selectedTodo]", $p).selectedTodo();
	
	if ($.fn.pagerForm)
		$("form[rel=pagerForm]", $p).pagerForm({
			parentBox : $p
		});

	//
	$p.find(":input[thoudigit]").each(function(){
		var element = $(this);
			if(formatThouDigit){
				var t = $(element).attr("thoudigit");
				$(element).val(formatThouDigit($(element).val(),t,1));
			}
	});
	$p.find("#findnum").click(function(event){
		$('input[type=submit]',$(this).parents('a:first')).trigger('click');
	});
	var themeName = $("#themeList>li>div.selected").parent().attr('theme');
	$("img[src*=icon_],img[src*=order_]",$p).attr('src',function(i,a){
		if(a.indexOf('/themes/default/images/')>0){
			return a.replace("/themes/default/images/", "/themes/"+themeName+"/images/");
		}else if(a.indexOf('/themes/orange/images/')>0){
			return a.replace("/themes/orange/images/", "/themes/"+themeName+"/images/");
		}else if(a.indexOf('/themes/purple/images/')>0){
			return a.replace("/themes/purple/images/", "/themes/"+themeName+"/images/");
		}else if(a.indexOf('/themes/red/images/')>0){
			return a.replace("/themes/red/images/", "/themes/"+themeName+"/images/");
		}else if(a.indexOf('/themes/tawny/images/')>0){
			return a.replace("/themes/tawny/images/", "/themes/"+themeName+"/images/");
		}
	});
	
	$('#favoriteUrl').sortDrag({sortBoxs: 'ul',items: '>li[order]'});

}
