/**
 * @screen core
 * @author jiang_ting
 */

(function($){
	var currVal=0;
	var allSelectBoxex = [];
	var killAllBoxex = function(bid){
		currVal=0;
		$.each(allSelectBoxex, function(i){
			if (allSelectBoxex[i] != bid) {
				if (!$("#" + allSelectBoxex[i])[0]) {
					$("#op_" + allSelectBoxex[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					if(bid && bid.target && bid.target.type && bid.target.type=='checkbox'&&
							($(bid.target).parents('ul:first').attr('id') == "op_"+allSelectBoxex[i])){
					}else{
						$("#op_" + allSelectBoxex[i]).css({ height: "", width: "" }).hide();
					}
				}
				if(!(bid && bid.target && bid.target.type && bid.target.type=='checkbox'&&
						($(bid.target).parents('ul:first').attr('id') == "op_"+allSelectBoxex[i]))){
				$(document).unbind("click", killAllBoxex);
				}
			}else{
				//Modify for bug#0129766 at 2013/10/23 by jiang_nan start
				$("#"+bid).find("a").focus();
				//Modify for bug#0129766 at 2013/10/23 by jiang_nan end
				$("ul.comboxop:visible",document).each(function(){
					if($(this).attr("id") != "op_"+bid){
						$(this).css({ height: "", width: "" }).hide();
					}
				});
			}
		});
	};
	
	$.extend($.fn, {
		comboxSelectex: function(options){
			var op = $.extend({ selector: ">a" }, options);
			return this.each(function(){
				var box = $(this);
				var selector = $(op.selector, box);
				if ($.browser.chrome){
					box.attr('tabindex',0);
				}
				box.keydown(function(event){
					var options = $("#op_"+box.attr("id"));
					if (!options.is(":hidden")) {
						if(event.ctrlKey){
							return false;
						}
					if(event.keyCode ==38 ){
						var ob = $(">td",$("#op_" + box.attr('id')+'>table').find(".selected").prev());
						if(ob.length==0){
							ob= $("tr:last>td",$("#op_" + box.attr('id')));
						}
						ob.parents('table:first').find(".selected").removeClass("selected");
						ob.parent().addClass("selected");
						$("#op_" + box.attr('id')).scrollTop(ob.parent()[0].offsetTop);
						//selector.text(ob.parent().attr('lab')).removeClass("error");
						//$("input", box).val(ob.parent().attr("val")).trigger("change");
					}
					if( event.keyCode ==40){
						var ob = $(">td",$("#op_" + box.attr('id')+'>table').find(".selected").next());
						if(ob.length==0){
							ob= $("tr:first>td",$("#op_" + box.attr('id')));
						}
						ob.parents('table:first').find(".selected").removeClass("selected");
						ob.parent().addClass("selected");
						$("#op_" + box.attr('id')).scrollTop(ob.parent()[0].offsetTop);
						//selector.text(ob.parent().attr('lab')).removeClass("error");
						//$("input", box).val(ob.parent().attr("val")).trigger("change");
					}
					if(event.keyCode ==27){
						killAllBoxex();
						return false;
					}
					if(event.keyCode ==8){
						killAllBoxex();
						return false;
					}
					if(event.keyCode ==13){
						var ob = $("tr.selected>td","#op_" + box.attr('id'));
						selector.text(ob.parent().attr('lab')).removeClass("error");
						var $input = $("input", box);
						if ($input.val() != ob.parent().attr("val")) {
							$("input", box).val(ob.parent().attr("val")).trigger("change");
						}
						killAllBoxex();
						return false;
					}
					if ((event.keyCode < 91 && event.keyCode > 59)
							|| (event.keyCode < 58 && event.keyCode > 47)) {
						var currValstr = String.fromCharCode(event.keyCode);
						var not1=0;
						$("#op_" + box.attr('id')+' tbody').children().each(function(i){
//							if($(">td",$("#op_" + box.attr('id')+' tbody').children()[i]).text().toLowerCase().startsWith(currValstr)){
//								not1++;
//							}
							var matcher = new RegExp( "^" + currValstr, "i" );
							if ( $(">td",$(this)).text().match( matcher ) ) {
								not1++;
							}
						});
						if(not1>0){
							for(currVal;currVal<$("#op_" + box.attr('id')+' tbody').children().length;currVal++){
								var ob = $(">td",$("#op_" + box.attr('id')+' tbody').children()[currVal]);
								var sstr = ob.text();
								var matcher = new RegExp( "^" + currValstr, "i" );
								if(sstr.match( matcher )&&ob.parent().hasClass('selected')){
									if(not1==1){
										break;
									}else{
										if($("#op_" + box.attr('id')+' tbody').children().length!=currVal+1){
											continue;
										}
									}
								}
								if(sstr.match( matcher )&&!ob.parent().hasClass('selected')){
									//ob.trigger('click');
									ob.parent().parent().find(".selected").removeClass("selected");
									ob.parent().addClass("selected");
									$("#op_" + box.attr('id')).scrollTop(ob.parent()[0].offsetTop);
									//selector.text(ob.parent().attr('lab')).removeClass("error");
									//var $input = $("input", box);
									//if ($input.val() != ob.parent().attr("val")) {
									//	$("input", box).val(ob.parent().attr("val")).trigger("change");
									//}
									break;
								}
								if($("#op_" + box.attr('id')+' tbody').children().length==currVal+1){
									currVal=-1;
								}
							}
						}
					}
					}
				});
				
				allSelectBoxex.push(box.attr("id"));
				//var disabled = $('>a',box).attr('disabled');
				//if(disabled){
					//$('>a',box).css("background-color","#f0f0ea");
					//$('>a',box).addClass("disabled");
				//}
				$(op.selector, box).click(function(){
					$(document).trigger('click');
					if(!$('>a',box).hasClass("disabled")){
					var options = $("#op_"+box.attr("id"));
					if (options.is(":hidden")) {
						if(options.height() > 300) {
							options.css({height:"300px",overflow:"scroll"});
						}
						//alert($("#"+box.attr("id")).width());
						//alert(options.width());
						if($("input",box).attr('opallwidth')){
							options.width($("input",box).attr('opallwidth'));
						}else{
							options.width($("#"+box.attr("id")).innerWidth()-5);
						}
						var top = box.offset().top+box[0].offsetHeight;
						if(top + options.height() > $(window).height() - 20) {
							top =  $(window).height() - 20 - options.height();
						}
						if(options.children().length>=1){
							options.css({top:top,left:box.offset().left}).show();
						}
//						for(var i=0;i<$("#op_" + box.attr('id')+' tbody').children().length;i++){
//							if($($("#op_" + box.attr('id')+' tbody').children()[i]).hasClass('selected')){
//								$("#op_" + box.attr('id')).scrollTop($("#op_" + box.attr('id')+' tbody').children()[i].offsetTop);
//								break;
//							}
//						}
						
						var ob = $("#op_" + box.attr('id')+' tbody').find('tr[val="'+$("input", box).val()+'"]'); 
						if(ob.length==1){
							ob.parent().find(".selected").removeClass("selected");
							ob.addClass("selected");
						}
						
						if($("tr.selected>td","#op_" + box.attr('id')+' tbody').length==1){
							$("#op_" + box.attr('id')).scrollTop($("tr.selected>td","#op_" + box.attr('id')+' tbody')[0].offsetTop);
						}
						
						killAllBoxex(box.attr("id"));
						$(document).click(killAllBoxex);
					} else {
						$(document).unbind("click", killAllBoxex);
						killAllBoxex();
					}
					}
					return false;
				});
				
				$("#op_"+box.attr("id")).find("tr").comboxOptionex(selector, box);
				$("#op_"+box.attr("id")).find("tr").hoverClass("lihover");
				//alert(box.parent().parent().find('select').css('width'));
				//alert(box.parent().parent().find('select').width());

			});
		},
		comboxOptionex: function(selector, box){
			return this.each(function(){
				$(this).click(function(ev){
					var $this = $(this);
					var $input = $("input", box);
					$this.parent().find(".selected").removeClass("selected");

					if(selector.attr('checkbox')=='true'  ){
						//if (texts.length > 0) texts = texts.substr(0, texts.length - 1);
						if(ev.target.type=='checkbox'){
							var text="";
							var lab="";
							$("input:checked",$("#op_"+box.attr("id"))).each(function(){
								$(this).parents('tr:first').addClass("selected");
								text+=$(this).parents('tr:first').attr('val')+",";
								lab+=$(this).parents('tr:first').attr('lab')+',';
							});
							if (text.length > 0) text = text.substr(0, text.length - 1);
							if (lab.length > 0) lab = lab.substr(0, lab.length - 1);
							if(text!=$input.val()){
								selector.text(lab).removeClass("error");
								$input.val(text);
								$input.trigger("change");
							}
						}
					}else{
						$this.addClass("selected");
						selector.text($this.attr('lab')).removeClass("error");
						if ($input.val() != $this.attr("val")) {
							$input.val($this.attr("val"));
							$input.trigger("change");
						}
					}
				});
			});
		},
		cbexDisabled: function(dis){
			var box=$(this).parent();
			if(dis){
				$('>a',box).removeClass("required").addClass("disabled");
				//$('>a',box).attr("disabled","disabled");
			}else{
				$('>a',box).removeClass("disabled");
				//$('>a',box).removeAttr("disabled");
			}
		},
		cbexSetValue: function(val){
			var box=$(this).parent();
			var selector = $('>a', box);
			for(var i=0;i<$("#op_" + box.attr('id')+' tbody').children().length;i++){
				var ob = $(">td",$("#op_" + box.attr('id')+' tbody').children()[i]);
				if(ob.parent().attr("val")==val){
					ob.parent().parent().find(".selected").removeClass("selected");
					ob.parent().addClass("selected");
					selector.text(ob.parent().attr('lab')).removeClass("error");
					var $input = $("input", box);
					if ($input.val() != ob.parent().attr("val")) {
						$("input", box).val(ob.parent().attr("val")).trigger("change");
					}
					break;
				}
			}
		},
		comboxex:function(opr,reload){
			reload = reload || false;
			/*  */
			var _selectBox = [];
			$.each(allSelectBoxex, function(i){ 
				if ($("#" + allSelectBoxex[i])[0]) {
					_selectBox.push(allSelectBoxex[i]);
				} else {
					$("#op_" + allSelectBoxex[i]).remove();
				}
			});
			allSelectBoxex = _selectBox;
			if(reload==true|| reload=='true'){
				var $refCombox = $(this).parents("div.combox:first");
				$(this).insertAfter($refCombox);
				$refCombox.remove();
				$(this).show();
			}
			return this.each(function(i){
				var $this = $(this).removeClass("combox");
				//var name = $this.attr("name");
				var value= $this.attr("value");
				var label = opr.initText;
				var ref = $this.attr("ref");
				//var refUrl = $this.attr("refUrl") || "";

				var cid = Math.round(Math.random()*10000000);
				if(typeof cid != "number"){
					try {
						cid = cid.replaceAll('\\.','_');
					} catch (e) {
					}
				}
				if($this.attr("disabled")){
					$this.addClass('disabled');
				}
				if($this.attr("readonly") && $this.attr("readonly")=="readonly"){
					//$this.attr("disabled","disabled");
					$this.addClass('disabled');
				}
				if($this.attr("editmodel") && $this.attr("editmodel")!="true"){
					//$this.attr("disabled","disabled");
					$this.addClass('disabled');
					if($this.hasClass('required')){
						$this.removeClass('required');
					};
				}
				//var dis = $this.attr("disabled");
				var claz = $this.attr("class");
				var checkbox= (opr.isCheckBox||false);
				
				var select = '<div style="'+$this.attr("style")+'" class="combox"><div style="width:100%" id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + '>';
				//Modify for bug#0082955 at 2013/02/04 by jiang_ting Start.
				select += '<a style="width:100%;text-overflow:ellipsis;" '+(checkbox?' checkbox="true"':' ')+'class="'+claz+'" href="javascript:;">' + label +'</a></div></div>';
				//Modify for bug#0082955 at 2013/02/04 by jiang_ting End.
				var options = '<ul class="comboxop" id="op_combox_'+ cid +'">';
				options += opr.cont;
//				options +=
//						"<tr val='aaa'><td width='10%'><input type='checkbox'></td><td width='30%'>ddd</td><td width='30%'>ddd</td></tr>" +
//						"<tr val='bbb'><td>ddd</td><td>2ddd</td><td>2ddd</td></tr>" +
//						"<tr><td>ddd</td><td>3ddd</td><td>2ddd</td></tr>" +
				options +="</ul>";
				$this.removeAttr("disabled");
				$("body").append(options);
				$this.after(select);
				$("div.select", $this.next()).comboxSelectex((opr.isCheckBox||false)).append($this);
				$this.hide();
				if(value==''&& $('tr:first',$('#op_combox_'+ cid)).attr('val')!=''){
					$('tr:first',$('#op_combox_'+ cid)).trigger("click");
				}
				
//				if (ref && refUrl) {
//					
//					$this.unbind("refChange").bind("refChange", function(event){
//						var $ref = $("#"+ref);
//						if ($ref.size() == 0) return false;
//						$.ajax({
//							type:'GET', dataType:"json", url:refUrl.replace("{value}", $this.attr("value")), cache: false,
//							data:{},
//							success: function(json){
//								if (!json) return;
//								var html = '';
//	
//								$.each(json, function(i){
//									if (json[i] && json[i].length > 1){
//										html += '<option value="'+json[i][0]+'">' + json[i][1] + '</option>';
//									}
//								});
//								
//								var $refCombox = $ref.parents("div.combox:first");
//								$ref.html(html).insertAfter($refCombox);
//								$refCombox.remove();
//								$ref.trigger("refChange").trigger("change").comboxex();
//							},
//							error: DWZ.ajaxError
//						});
//					});
//				}
				
			});
		}
	});
})(jQuery);
