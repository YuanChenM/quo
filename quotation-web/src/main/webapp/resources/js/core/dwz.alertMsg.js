/**
 * @screen core
 * @author jiang_ting
 */

$.setRegional("alertMsg", {
	title:{error:"Error", info:"Information", warn:"Warning", correct:"Successful", confirm:"Confirmation"},
	butMsg:{ok:"OK", yes:"Yes", no:"No", cancel:"Cancel",clear:"Clear"}
});
var alertMsgtext="";
var isconfirm=false;
var alertMsg = {
	_boxId: "#alertMsgBox",
	_bgId: "#alertBackground",
	_closeTimer: null,

	_types: {error:"error", info:"info", warn:"warn", correct:"correct", confirm:"confirm"},

	_getTitle: function(key){
		return $.regional.alertMsg.title[key];
	},
	_clearCall: function(){
		$(alertMsg._boxId).remove();
	},
	/**
	 * 
	 * @param {Object} type
	 * @param {Object} msg
	 * @param {Object} buttons [button1, button2]
	 */
	_open: function(type, msg, buttons){
		if(!isconfirm){
			alertMsgtext = $('#msgalertInner',$(this._boxId)).html() || "";
			//alertMsgtext = alertMsgtext.replaceAll("<H1>","<H2>").replaceAll("</H1>","</H2>");
		}
		//alert(alertMsgtext);
		$(this._boxId).remove();
		var butsHtml = "";
		if (buttons) {
			for (var i = 0; i < buttons.length; i++) {
				var sRel = buttons[i].call ? "callback" : "";
				butsHtml += DWZ.frag["alertButFrag"].replace("#butMsg#", buttons[i].name).replace("#callback#", sRel);
			}
		}
		
		var msgHtml = "";
		if (msg && msg instanceof Array) {
			for (x in msg) {
				if (msgHtml != "") {
					msgHtml = msgHtml + "<br/>";
				}
				msgHtml = msgHtml + msg[x];
			}
		} else {
			msgHtml = msg;
		}
			
		var boxHtml = DWZ.frag["alertBoxFrag"].replaceAll("#type#", type).replace("#title#", this._getTitle(type)).replace("#message#", msgHtml).replace("#butFragment#", butsHtml);
		//$(boxHtml).appendTo("body").css({top:-$(this._boxId).height()+"px"}).animate({top:"0px"}, 500);
		
		$(boxHtml).appendTo("#msgboxContent");
		if(type!=this._types.confirm){
			$(alertMsgtext).appendTo('#msgalertInner');
			isconfirm = false;
		}else{
			isconfirm = true;
		}
		if($("#messagebox").css("display")=="block") {
			$("#messagebox").stop(true,true).hide();
	    }
		$("#messagebox").slideDown("slow");
				
		if (this._closeTimer) {
			clearTimeout(this._closeTimer);
			this._closeTimer = null;
		}
		if (this._types.info == type || this._types.correct == type){
			this._closeTimer = setTimeout(function(){alertMsg.close();}, 3500);
		} else {
			$(this._bgId).show();
		}
		var jCallButs = $(this._boxId).find("[rel=callback]");
		for (var i = 0; i < buttons.length; i++) {
			if (buttons[i].call) jCallButs.eq(i).click(buttons[i].call);
		}
		$('#msgboxHeaderOn').focus();
		$('#msgboxHeaderOn').keydown(function(e){
			if(e.keyCode ==27){
				alertMsg.close();
				return false;
			}
		});
	},
	close: function(){
//		$(this._boxId).animate({top:-$(this._boxId).height()}, 500, function(){
//			$(this).remove();
//		});
//		$(this._bgId).hide();
		$("#messagebox").slideUp("slow", function(){
			//$(this._boxId).remove();
			if(isconfirm){
				$(alertMsg._boxId).remove();
				if(alertMsgtext!=''){
				var op = {okName:$.regional.alertMsg.butMsg.ok, okCall:null,
						clearName:$.regional.alertMsg.butMsg.clear, clearCall:alertMsg._clearCall};
				var buttons = [
				    {name:op.clearName, call: op.clearCall},
					{name:op.okName, call: op.okCall}
				];
				var butsHtml = "";
				if (buttons) {
					for (var i = 0; i < buttons.length; i++) {
						var sRel = buttons[i].call ? "callback" : "";
						butsHtml += DWZ.frag["alertButFrag"].replace("#butMsg#", buttons[i].name).replace("#callback#", sRel);
					}
				}
				var boxHtml = DWZ.frag["alertBoxFrag"].replaceAll("#type#", 'error').replace("#title#", '')
				.replace("#message#", '').replace("#butFragment#", butsHtml);
				$('#msgalertInner',$(boxHtml).appendTo("#msgboxContent")).html('');
				$(alertMsgtext).appendTo('#msgalertInner');
				var jCallButs = $(alertMsg._boxId).find("[rel=callback]");
				for (var i = 0; i < buttons.length; i++) {
					if (buttons[i].call) jCallButs.eq(i).click(buttons[i].call);
				}
				}
				isconfirm=false;
			}
			$(alertMsg._bgId).hide();
		});
		
	},
	error: function(msg, options) {
		this._alert(this._types.error, msg, options);
	},
	info: function(msg, options) {
		this._alert(this._types.info, msg, options);
	},
	warn: function(msg, options) {
		//bug 0088172 
		this._alert(this._types.error, msg, options);
	},
	//modify  for bug #0226783 2017/02/13  chencheng start.
	confirm: function(msg, options) {
		this._alert(this._types.warn, msg, options);
	},
	//modify  for bug #0226783 2017/02/13  chencheng end.
	correct: function(msg, options) {
		this._alert(this._types.correct, msg, options);
	},
	_alert: function(type, msg, options) {
		var op = {okName:$.regional.alertMsg.butMsg.ok, okCall:null,
				clearName:$.regional.alertMsg.butMsg.clear, clearCall:alertMsg._clearCall};
		$.extend(op, options);
		var buttons = [
		    {name:op.clearName, call: op.clearCall},
			{name:op.okName, call: op.okCall}
		];
		this._open(type, msg, buttons);
	},
	/**
	 * 
	 * @param {Object} msg
	 * @param {Object} options {okName, okCal, cancelName, cancelCall}
	 */
	confirm: function(msg, options) {
		var op = {okName:$.regional.alertMsg.butMsg.ok, okCall:null, cancelName:$.regional.alertMsg.butMsg.cancel, cancelCall:null};
		$.extend(op, options);
		var buttons = [
			{name:op.okName, call: op.okCall},
			{name:op.cancelName, call: op.cancelCall}
		];
		this._open(this._types.confirm, msg, buttons);
	},
	
	log: function(msg, errcode){
		switch (errcode){
			case 1: this.error(msg);
			        break;
			//TODO
			default: this.error(msg);
		}
	}
};

