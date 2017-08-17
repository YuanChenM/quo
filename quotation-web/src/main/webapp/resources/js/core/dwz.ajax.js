/**
 * @screen core
 * @author jiang_ting
 */

/**
 * 
 * @param {Object} form
 * @param {Object} callback
 */
function validateCallback(form, callback) {
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	return false;
}
function validateform(form) {
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
	return true;
}
/**
 * 
 * @param {Object} form
 * @param {Object} callback
 */
function iframeCallback(form, callback){
	var $form = $(form), $iframe = $("#callbackframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
	}else{
		$iframe.unbind("load");
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	form.target = "callbackframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone, true);
}
function _iframeResponse(iframe, callback, markflag){
	var $iframe = $(iframe), $document = $(document);
	
	if(markflag == true){
	    $document.trigger("ajaxStart");
	}

	$iframe.bind("load", function(event){
		$iframe.unbind("load");
		if(markflag == true){
		    $document.trigger("ajaxStop");
		}
		
		if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || // For Safari
			iframe.src == "javascript:'<html></html>';") { // For FF, IE
			return;
		}

		var doc = iframe.contentDocument || iframe.document;

		// fixing Opera 9.26,10.00
		if (doc.readyState && doc.readyState != 'complete') return; 
		// fixing Opera 9.64
		if (doc.body && doc.body.innerHTML == "false") return;
	   
		var response;
		
		if (doc.XMLDocument) {
			// response is a xml document Internet Explorer property
			response = doc.XMLDocument;
		} else if (doc.body){
			try{
				response = $iframe.contents().find("body").html();
				// response = doc.body.textContent;
				response = jQuery.parseJSON(response);
			} catch (e){ // response is html document or plain text
				response = doc.body.innerHTML;
			}
		} else {
			// response is a xml document
			response = doc;
		}
		
		callback(response);
	});
}

/**
 * download submit
 * @param {Object} form
 * @param {Object} callback
 */
function downloadCallback(form, callback){
	var $form = $(form), $iframe = $("#downloadframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='downloadframe' name='downloadframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
	}else{
		$iframe.unbind("load");
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	form.target = "downloadframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone, false);
}

/**
 * upload submit
 * @param {Object} form
 * @param {Object} callback
 */
function uploadCallback(form, callback){
	var $form = $(form), $iframe = $("#uploadframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='uploadframe' name='uploadframe' src='about:blank' style='display:none'></iframe>").appendTo("body");
	}else{
		$iframe.unbind("load");
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	form.target = "uploadframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone, true);
}

/**
 * 
 * <form action="/user.do?method=save" onsubmit="return validateCallback(this, navTabAjaxDone)">
 * 
 * 
 * {"statusCode":"200", "message":"", "navTabId":"navNewsLi", "forwardUrl":"", "callbackType":"closeCurrent"}
 * {"statusCode":"300", "message":""}
 * {"statusCode":"301", "message":""}
 * 
 */
function navTabAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId){ //
			navTab.reloadFlag(json.navTabId);
		} else { //
			navTabPageBreak({}, json.rel);
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		} else if ("forwardConfirm" == json.callbackType) {
			alertMsg.confirm(json.confirmMsg || DWZ.msg("forwardConfirmMsg"), {
				okCall: function(){
					navTab.reload(json.forwardUrl);
				}
			});
		} else {
			navTab.getCurrentPanel().find(":input[defaultValue]").each(function(){
				var defaultVal = $(this).attr("defaultValue");
				$(this).val(defaultVal);
			});
		}
	}
}

/**
 * 
 */
function dialogAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId){
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		} else if (json.rel) {
			navTabPageBreak({}, json.rel);
		}
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
}

/**
 * 
 * @param {Object} form
 */
function navTabSearch(form, navTabId){
	var $form = $(form);
	$form.find(":input[thoudigit]").each(function(){
		var element = $(this);
			if(formatThouDigit){
				var t = $(element).attr("thoudigit");
				$(element).val(formatThouDigit($(element).val(),t,0));
			}
	});
	if (!$form.valid()) {
		$form.find(":input[thoudigit]").each(function(){
			var element = $(this);
				if(formatThouDigit){
					var t = $(element).attr("thoudigit");
					$(element).val(formatThouDigit($(element).val(),t,1));
				}
		});
		return false;
	}
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	navTab.reload($form.attr('action'), {data: $form.serializeArray(), navTabId:navTabId});
	return false;
}
/**
 * 
 * @param {Object} form
 */
function dialogSearch(form){
	var $form = $(form);
	$form.find(":input[thoudigit]").each(function(){
		var element = $(this);
			if(formatThouDigit){
				var t = $(element).attr("thoudigit");
				$(element).val(formatThouDigit($(element).val(),t,0));
			}
	});
	if (!$form.valid()) {
		$form.find(":input[thoudigit]").each(function(){
			var element = $(this);
				if(formatThouDigit){
					var t = $(element).attr("thoudigit");
					$(element).val(formatThouDigit($(element).val(),t,1));
				}
		});
		return false;
	}
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	$.pdialog.reload($form.attr('action'), {data: $form.serializeArray()});
	return false;
}
function dwzSearch(form, targetType){
	if (targetType == "dialog") dialogSearch(form);
	else navTabSearch(form);
	return false;
}
/**
 * dialog page search,but data not check 
 * @param form the html form tag
 * @returns {Boolean} the false
 */
//Modify for bug#0102199 at 2013/05/24 by jiang_nan Start. 
function whenNotCheckdialogSearch(form){
	
	var $form = $(form);
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	$.pdialog.reload($form.attr('action'), {data: $form.serializeArray()});
	return false;
}
//Modify for bug#0102199 at 2013/05/24 by jiang_nan End. 
/**
 * 
 * @param {Object} form
 */
function divSearch(form, rel){
	var $form = $(form);
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	if (rel) {
		var $box = $("#" + rel);
		$box.ajaxUrl({
			type:"POST", url:$form.attr("action"), data: $form.serializeArray(), callback:function(){
				$box.find("[layoutH]").layoutH();
			}
		});
	}
	return false;
}
/**
 * 
 * @param {Object} args {pageNum:"",numPerPage:"",orderField:"",orderDirection:""}
 * @param String formId  "pagerForm"
 */
function _getPagerForm($parent, args) {
	var form = $("#pagerForm", $parent).get(0);

	if (form) {
		if (args["pageNum"]) form[DWZ.pageInfo.pageNum].value = args["pageNum"];
		if (args["numPerPage"]) form[DWZ.pageInfo.numPerPage].value = args["numPerPage"];
		if (args["orderField"]) form[DWZ.pageInfo.orderField].value = args["orderField"];
		if (args["orderDirection"] && form[DWZ.pageInfo.orderDirection]) form[DWZ.pageInfo.orderDirection].value = args["orderDirection"];
	}
	
	return form;
}


/**
 * targetType: navTab  dialog
 * rel: 
 * data: pagerForm{pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * callback: 
 */
function dwzPageBreak(options){
	var op = $.extend({ targetType:"navTab", rel:"", data:{pageNum:"", numPerPage:"", orderField:"", orderDirection:""}, callback:null}, options);
	var $parent = op.targetType == "dialog" ? $.pdialog.getCurrent() : navTab.getCurrentPanel();

	if (op.rel) {
		var $box = $parent.find("#" + op.rel);
		var form = _getPagerForm($box, op.data);
		if (form) {
			$box.ajaxUrl({
				type:"POST", url:$(form).attr("action"), data: $(form).serializeArray(), callback:function(){
					$box.find("[layoutH]").layoutH();
				}
			});
		}
	} else {
		var form = _getPagerForm($parent, op.data);
		var params = $(form).serializeArray();
		
		if (op.targetType == "dialog") {
			if (form) $.pdialog.reload($(form).attr("action"), {data: params, callback: op.callback});
		} else {
			if (form) navTab.reload($(form).attr("action"), {data: params, callback: op.callback});
		}
	}
}
/**
 * 
 * @param args {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * @param rel
 */
function navTabPageBreak(args, rel){
	dwzPageBreak({targetType:"navTab", rel:rel, data:args});
}
/**
 * navTabPageBreak 
 */
function dialogPageBreak(args, rel){
	dwzPageBreak({targetType:"dialog", rel:rel, data:args});
}


function ajaxTodo(url, callback){
	var $callback = callback || navTabAjaxDone;
	if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
	$.ajax({
		type:'POST',
		url:url,
		dataType:"json",
		cache: false,
		success: $callback,
		error: DWZ.ajaxError
	});
}

/**
 * A function that triggers when all file uploads have completed. There is no default event handler.
 * @param {Object} event: The event object.
 * @param {Object} data: An object containing details about the upload process:
 * 		- filesUploaded: The total number of files uploaded
 * 		- errors: The total number of errors while uploading
 * 		- allBytesLoaded: The total number of bytes uploaded
 * 		- speed: The average speed of all uploaded files	
 */
function uploadifyAllComplete(event, data){
	if (data.errors) {
		var msg = "The total number of files uploaded: "+data.filesUploaded+"\n"
			+ "The total number of errors while uploading: "+data.errors+"\n"
			+ "The total number of bytes uploaded: "+data.allBytesLoaded+"\n"
			+ "The average speed of all uploaded files: "+data.speed;
		alert("event:" + event + "\n" + msg);
	}
}
/**
 * http://www.uploadify.com/documentation/
 * @param {Object} event
 * @param {Object} queueID
 * @param {Object} fileObj
 * @param {Object} response
 * @param {Object} data
 */
function uploadifyComplete(event, queueId, fileObj, response, data){
	DWZ.ajaxDone(DWZ.jsonEval(response));
}

/**
 * http://www.uploadify.com/documentation/
 * @param {Object} event
 * @param {Object} queueID
 * @param {Object} fileObj
 * @param {Object} errorObj
 */
function uploadifyError(event, queueId, fileObj, errorObj){
	alert("event:" + event + "\nqueueId:" + queueId + "\nfileObj.name:" 
		+ fileObj.name + "\nerrorObj.type:" + errorObj.type + "\nerrorObj.info:" + errorObj.info);
}


$.fn.extend({
	ajaxTodo:function(){
		return this.each(function(){
			var $this = $(this);
			$this.click(function(event){
				var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
				DWZ.debug(url);
				if (!url.isFinishedTm()) {
					alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
					return false;
				}
				var title = $this.attr("title");
				if (title) {
					alertMsg.confirm(title, {
						okCall: function(){
							ajaxTodo(url, $this.attr("callback"));
						}
					});
				} else {
					ajaxTodo(url, $this.attr("callback"));
				}
				event.preventDefault();
			});
		});
	},
	dwzExport: function(){
		function _doExport($this) {
			var $p = $this.attr("targetType") == "dialog" ? $.pdialog.getCurrent() : navTab.getCurrentPanel();
			var $form = $("#pagerForm", $p);
			var url = $this.attr("href");
			window.location = url+(url.indexOf('?') == -1 ? "?" : "&")+$form.serialize();
		}
		
		return this.each(function(){
			var $this = $(this);
			$this.click(function(event){
				var title = $this.attr("title");
				if (title) {
					alertMsg.confirm(title, {
						okCall: function(){_doExport($this);}
					});
				} else {_doExport($this);}
			
				event.preventDefault();
			});
		});
	}
});
