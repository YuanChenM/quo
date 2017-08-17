/**
 * @screen core
 * @author jiang_ting
 */

(function($){
	var currVal=0;
	var allSelectBox = [];
	var killAllBox = function(bid){
		currVal = 0;
		$.each(allSelectBox, function(i){
			
			if (allSelectBox[i] != bid) {
				
				if (!$("#" + allSelectBox[i])[0]) {
					$("#op_" + allSelectBox[i]).remove();
					//allSelectBox.splice(i,1);
				} else {
					$("#op_" + allSelectBox[i]).css({ height: "", width: "" }).hide();
				}
				$(document).unbind("click", killAllBox);
			}else{
				//Modify for bug#0129766 at 2013/10/23 by jiang_nan start
				$("#"+bid).find("a").focus();
				//Modify for bug#0129766 at 2013/10/23 by jiang_nan start
				$("ul.comboxop:visible",document).each(function(){
					if($(this).attr("id") != "op_"+bid){
						$(this).css({ height: "", width: "" }).hide();
					}
				});
			}
		});
	};
	
	$.extend($.fn, {
		comboxSelect: function(options){
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
						var ob = $(">a",$("#op_" + box.attr('id')).find(".selected").prev());
						if(ob.length==0){
							ob= $("a:last",$("#op_" + box.attr('id')));
						}
						ob.parent().parent().find(".selected").removeClass("selected");
						ob.parent().addClass("selected");
						$("#op_" + box.attr('id')).scrollTop(ob.parent()[0].offsetTop);
						//selector.text(ob.text());
						//$("select", box).val(ob.attr("value")).trigger("refChange").trigger("change");
					}
					if( event.keyCode ==40){
						var ob = $(">a",$("#op_" + box.attr('id')).find(".selected").next());
						if(ob.length==0){
							ob= $("a:first",$("#op_" + box.attr('id')));
						}
						ob.parent().parent().find(".selected").removeClass("selected");
						ob.parent().addClass("selected");
						$("#op_" + box.attr('id')).scrollTop(ob.parent()[0].offsetTop);
						//selector.text(ob.text());
						//$("select", box).val(ob.attr("value")).trigger("refChange").trigger("change");
					}
					if(event.keyCode ==27){
						killAllBox();
						return false;
					}
					if(event.keyCode ==8){
						killAllBox();
						return false;
					}
					
					if(event.keyCode ==13){
						var ob = $("li.selected>a","#op_" + box.attr('id'));
						selector.text(ob.text());
						var $input = $("select", box);
						if ($input.val() != ob.attr("value")) {
							$("select", box).val(ob.attr("value")).trigger("refChange").trigger("change");
						}
						killAllBox();
						return false;
					}
					
					if ((event.keyCode < 91 && event.keyCode > 59)
							|| (event.keyCode < 58 && event.keyCode > 47)) {
						var currValstr = String.fromCharCode(event.keyCode);
						var not1=0;
						//alert(1);
						$("#op_" + box.attr('id')).children().each(function(i){
							//if($(">a",$("#op_" + box.attr('id')).children()[i]).text().toLowerCase().startsWith(currValstr)){
								//not1++;
							//}
							var matcher = new RegExp( "^" + currValstr, "i" );
							if ( $(">a",$(this)).text().match( matcher ) ) {
								not1++;
							}
						});
						//alert(2);
						if(not1>0){
							for(currVal;currVal<$("#op_" + box.attr('id')).children().length;currVal++){
								var ob = $(">a",$("#op_" + box.attr('id')).children()[currVal]);
								var sstr = ob.text();
								var matcher = new RegExp( "^" + currValstr, "i" );
								if(sstr.match( matcher )&&ob.parent().hasClass('selected')){
									if(not1==1){
										break;
									}else{
										if($("#op_" + box.attr('id')).children().length!=currVal+1){
											continue;
										}
									}
								}
								if(sstr.match( matcher )&&!ob.parent().hasClass('selected')){
									//ob.trigger('click');
									ob.parent().parent().find(".selected").removeClass("selected");
									ob.parent().addClass("selected");
									$("#op_" + box.attr('id')).scrollTop(ob.parent()[0].offsetTop);
									//selector.text(ob.text());
									//var $input = $("select", box);
									//if ($input.val() != ob.attr("value")) {
										//$("select", box).val(ob.attr("value")).trigger("refChange").trigger("change");
									//}
									break;
								}
								if($("#op_" + box.attr('id')).children().length==currVal+1){
									currVal=-1;
								}
							}
						}
					}
					}
				});


				allSelectBox.push(box.attr("id"));
				//var disabled = $('>a',box).hasClass('disabled');
//				box.parent().parent().bind('resize',function(){
//					var ww = box.parent().parent().find('select').css('width');
//					box.parent().css('width',ww);
//					box.css('width',ww);
//					$('>a',box).css('width',ww);					
//				});
				//if(disabled){
					//$('>a',box).css("background-color","#f0f0ea");
					//$('>a',box).addClass("disabled");
				//}
				$(op.selector, box).click(function(){
					$(document).trigger('click');
					if(!$('>a',box).hasClass('disabled')){
					var options = $("#op_"+box.attr("id"));
					if (options.is(":hidden")) {
						if(options.height() > 300) {
							options.css({height:"300px",overflow:"scroll"});
						}
						//alert($("#"+box.attr("id")).width());
						//alert(options.width());
						if($("#"+box.attr("id")).width()>options.width()){
							options.width($("#"+box.attr("id")).innerWidth()-5);
						}
						var top = box.offset().top+box[0].offsetHeight;
						if(top + options.height() > $(window).height() - 20) {
							top =  $(window).height() - 20 - options.height();
						}
						if(options.children().length>=1 || box.parent().find('select').children().length>=1){
							options.css({top:top,left:box.offset().left}).show();
						}
						
//						for(var i=0;i<$("#op_" + box.attr('id')).children().length;i++) {
//							var ob = $(">a",$("#op_" + box.attr('id')).children()[i]);
//							if($("select", box).val()==ob.attr("value")){
//								ob.parent().parent().find(".selected").removeClass("selected");
//								ob.parent().addClass("selected");
//								break;
//							}
//						}
						
						var ob = $("#op_" + box.attr('id')).find('a[value="'+$("select", box).val()+'"]'); 
						if(ob.length==1){
							ob.parent().parent().find(".selected").removeClass("selected");
							ob.parent().addClass("selected");
						}
						
						
						if($("li.selected>a","#op_" + box.attr('id')).length==1){
							$("#op_" + box.attr('id')).scrollTop($("li.selected>a","#op_" + box.attr('id'))[0].offsetTop);
						}
						
						killAllBox(box.attr("id"));
						$(document).click(killAllBox);
					} else {
						$(document).unbind("click", killAllBox);
						killAllBox();
					}
					}
					return false;
				});
				
				$("#op_"+box.attr("id")).find(">li").comboxOption(selector, box);
				$("#op_"+box.attr("id")).find(">li").hoverClass("lihover");
				//alert(box.parent().parent().find('select').css('width'));
				//alert(box.parent().parent().find('select').width());

			});
		},
		comboxOption: function(selector, box){
			return this.each(function(){
				$(this).click(function(){
					var $this = $(">a",this);
					$this.parent().parent().find(".selected").removeClass("selected");
					$this.parent().addClass("selected");
					selector.text($this.text()).removeClass("error");
					
					var $input = $("select", box);
					if ($input.val() != $this.attr("value")) {
						$("select", box).val($this.attr("value")).trigger("refChange").trigger("change");
					}
				});
			});
		},
		cbDisabled: function(dis){
			var box=$(this).parent();
			if(dis){
				$('>a',box).removeClass("required").addClass("disabled");
				//$('>a',box).attr("disabled","disabled");
			}else{
				$('>a',box).removeClass("disabled");
				//$('>a',box).removeAttr("disabled");
			}
		},
		cbSetValue: function(val){
			var box=$(this).parent();
			var selector = $('>a', box);
			for(var i=0;i<$("#op_" + box.attr('id')).children().length;i++){
				var ob = $(">a",$("#op_" + box.attr('id')).children()[i]);
				if(ob.attr('value')==val){
					ob.parent().parent().find(".selected").removeClass("selected");
					ob.parent().addClass("selected");
					selector.text(ob.text());
					var $input = $("select", box);
					if ($input.val() != ob.attr("value")) {
						$("select", box).val(ob.attr("value")).trigger("refChange").trigger("change");
					}
					break;
				}
			}
			
			
		},
		combox:function(){
			/*  */
			var _selectBox = [];
			$.each(allSelectBox, function(i){ 
				if ($("#" + allSelectBox[i])[0]) {
					_selectBox.push(allSelectBox[i]);
				} else {
					$("#op_" + allSelectBox[i]).remove();
				}
			});
			allSelectBox = _selectBox;
			
			return this.each(function(i){
				var $this = $(this).removeClass("combox");
				var name = $this.attr("name");
				var value= $this.attr("value");
				var label = "";
//				if($("option[value=" + value.replaceAll('%','\\%') + "]",$this).length>=1){
//					label = $("option[value=" + value.replaceAll('%','\\%') + "]",$this).html();
//				}
				$("option", $this).each(function(i){
					var option = $(this);
					if(value==option[0].value){
						label = $(option[0]).html();
					}
				});
				
				
				var ref = $this.attr("ref");
				var refUrl = $this.attr("refUrl") || "";

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
				var claz = $this.attr("class");
				var select = '<div style="'+$this.attr("style")+'" class="combox"><div style="width:100%" id="combox_'+ cid +'" class="select"' + (ref?' ref="' + ref + '"' : '') + '>';
				select += '<a style="width:100%;text-overflow:ellipsis;white-space: nowrap;" '+'class="'+claz+'" href="javascript:;" name="' + name +'" value="' + value + '">' + label +'</a></div></div>';
				var options = '<ul class="comboxop" id="op_combox_'+ cid +'" style="cursor:hand">';
				$("option", $this).each(function(){
					var option = $(this);
					options +="<li class=\""+ (value==option[0].value?"selected":"") +"\"><a  href=\"#\" value=\"" + option[0].value + "\">" + option.html() + "</a></li>";
				});
				options +="</ul>";
				$this.removeAttr("disabled");
				$("body").append(options);
				$this.after(select);
				$("div.select", $this.next()).comboxSelect().append($this);
				if (ref && refUrl) {
					
					$this.unbind("refChange").bind("refChange", function(event){
						var $ref = $("#"+ref);
						if ($ref.size() == 0) return false;
						$.ajax({
							type:'GET', dataType:"json", url:refUrl.replace("{value}", $this.attr("value")), cache: false,
							data:{},
							success: function(json){
								if (!json) return;
								var html = '';
	
								$.each(json, function(i){
									if (json[i] && json[i].length > 1){
										html += '<option value="'+json[i][0]+'">' + json[i][1] + '</option>';
									}
								});
								
								var $refCombox = $ref.parents("div.combox:first");
								$ref.html(html).insertAfter($refCombox);
								$refCombox.remove();
								$ref.trigger("refChange").trigger("change").combox();
							},
							error: DWZ.ajaxError
						});
					});
				}
				
			});
		}
	});
})(jQuery);
