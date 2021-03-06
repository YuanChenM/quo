/**
 * @screen core
 * @author jiang_ting
 */

	function barresize($p){
		var twidth=0;
		if(!$("h1.contentTitle", $p).parent().hasClass("floatDiv")){
			$('table', $p).each(function(){
				if($(this).width()>twidth){
					twidth=$(this).width();
				}
			});
			if(twidth> $p.width()){
				$("div.panelBar", $p).width(twidth);
				$("div.totalBar", $p).width(twidth);
				$("div.detailpanelBar", $p).width(twidth);
				if($("h1.contentTitle", $p).parent().is('div')){
					$("h1.contentTitle", $p).width(twidth-20);
				}else{
					if($("h1.contentTitle", $p).parent().parent().parent().parent().is('table')){
						$("h1.contentTitle", $p).parent().parent().parent().parent().width(twidth);
					}
				}
			}else{
				$("div.panelBar", $p).css("width","");
				$("div.totalBar", $p).css("width","");
				$("div.detailpanelBar", $p).css("width","");
				if($("h1.contentTitle", $p).parent().is('div')){
					$("h1.contentTitle", $p).css("width","");
				}else{
					if($("h1.contentTitle", $p).parent().parent().parent().parent().is('table')){
						$("h1.contentTitle", $p).parent().parent().parent().parent().css("width","100%");
					}
				}
			}
		}
		$(document).trigger("click");
	}

(function($){
	$.fn.cssv = function(pre){
		var cssPre = $(this).css(pre);
		return cssPre.substring(0, cssPre.indexOf("px")) * 1;
	};
	
	$.fn.jBar = function(options){
		var op = $.extend({container:"#container", collapse:".collapse", toggleBut:".toggleCollapse div", sideBar:"#sidebar", sideBar2:"#sidebar_s", splitBar:"#splitBar", splitBar2:"#splitBarProxy"}, options);
		return this.each(function(){
			var jbar = this;
			var sbar = $(op.sideBar2, jbar);
			var bar = $(op.sideBar, jbar);
			$(op.toggleBut, bar).parent().click(function(){
				DWZ.ui.sbar = false;
				$(op.splitBar).hide();
				var sbarwidth = sbar.cssv("left") + sbar.outerWidth();
				var barleft = sbarwidth - bar.outerWidth();
				var cleft = $(op.container).cssv("left") - (bar.outerWidth() - sbar.outerWidth());
				var cwidth = bar.outerWidth() - sbar.outerWidth() + $(op.container).outerWidth();
				$(op.container).animate({left: cleft-0,width: cwidth},50,function(){
					bar.animate({left: barleft}, 500, function(){
						bar.hide();
						sbar.show().css("left", -50).animate({left: 3}, 200);
						$(window).trigger("resizeGrid");
						var $p = navTab.getCurrentPanel();
						barresize($p);
					});
				});
				
				$(op.collapse,sbar).click(function(){
					DWZ.ui.sbar = true;
					sbar.animate({left: -25}, 200, function(){				
						bar.show();
					});
					bar.animate({left: 3}, 800, function(){
						$(op.splitBar).show();
						$(op.toggleBut, bar).show();					
						var cleft = 5 + bar.outerWidth() + $(op.splitBar).outerWidth();
						var cwidth = $(op.container).outerWidth() - (cleft - $(op.container).cssv("left"));
						$(op.container).css({left: cleft,width: cwidth});
						$(op.collapse, sbar).unbind('click');
						$(window).trigger("resizeGrid");
						var $p = $(op.container);
						barresize($p);
					});
					return false;
				});
				return false;
			});
//			$(op.toggleBut, sbar).click(function(){
//				DWZ.ui.sbar = true;
//				sbar.animate({left: -25}, 200, function(){				
//					bar.show();
//				});
//				bar.animate({left: 3}, 800, function(){
//					$(op.splitBar).show();
//					$(op.toggleBut, bar).show();					
//					var cleft = 5 + bar.outerWidth() + $(op.splitBar).outerWidth();
//					var cwidth = $(op.container).outerWidth() - (cleft - $(op.container).cssv("left"));
//					$(op.container).css({left: cleft,width: cwidth});
//					$(op.collapse, sbar).unbind('click');
//					$(window).trigger("resizeGrid");
//					var $p = $(op.container);
//					barresize($p);
//				});
//				return false;
//			});
			$(op.splitBar).mousedown(function(event){
				$(op.splitBar2).each(function(){
					var spbar2 = $(this);
					setTimeout(function(){spbar2.show();}, 100);
					spbar2.css({visibility: "visible",left: $(op.splitBar).css("left")});					
					spbar2.jDrag($.extend(options, {obj:$("#sidebar"), move:"horizontal", event:event,stop: function(){
						$(this).css("visibility", "hidden");
						var move = $(this).cssv("left") - $(op.splitBar).cssv("left");
						var sbarwidth = bar.outerWidth() + move;
						var cleft = $(op.container).cssv("left") + move;
						var cwidth = $(op.container).outerWidth() - move;
						bar.css("width", sbarwidth);
						$(op.splitBar).css("left", $(this).css("left"));
						$(op.container).css({left: cleft,width: cwidth});
						$(window).trigger("resizeGrid");
						var $p = $(op.container);
						barresize($p);
					}}));
					return false;					
				});
			});
		});
	};
})(jQuery);
