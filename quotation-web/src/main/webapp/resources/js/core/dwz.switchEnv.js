/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.fn.navMenu = function(){
		return this.each(function(){
			var $box = $(this);
			$box.find("li>a").click(function(){
				var $a = $(this);
//				$.post($a.attr("href"), {}, function(html){
//					$("#sidebar").find(".accordion").remove().end().append(html).initUI();
//					$box.find("li").removeClass("selected");
//					$a.parent().addClass("selected");
//					navTab.closeAllTab();
//				});
				$("#buCode").val=$a.attr("rel");
				$("#mainform").submit();
				return false;
			});
		});
	};
	
	$.fn.switchEnv = function(){
		var op = {cities$:">ul>li", boxTitle$:">a>span"};
		return this.each(function(){
			var $this = $(this);
			$this.click(function(){
				if ($this.hasClass("selected")){
					_hide($this);
				} else {
					_hide($(".switchBox"));
					_show($this);
				}
				return false;
			});
			
			$this.find(op.cities$).click(function(){
				var $li = $(this);

//				$.post($li.find(">a").attr("href"), {}, function(html){
//					_hide($this);
//					$this.find(op.boxTitle$).html($li.find(">a").html());
//					navTab.closeAllTab();
//					$("#sidebar").find(".accordion").remove().end().append(html).initUI();
//				});
				var actionType = $li.find(">a").attr("href");
				if (actionType == "#changeBu") {
					$("#currentBu").attr("value", $li.find(">a").attr("rel"));
					$("#mainform").submit();
				} else if (actionType == "#changeLanguge") {
					$("#language").attr("value", $li.find(">a").attr("rel"));
					$("#mainform").submit();
				} else if (actionType == "#logout") {
					$("#mainform").attr("action", "logout");
					$("#mainform").submit();
				}
				
				return false;
			});
		});
	};
	
	function _show($box){
		$box.addClass("selected");
		$(document).bind("click",{box:$box}, _handler);
	}
	function _hide($box){
		$box.removeClass("selected");
		$(document).unbind("click", _handler);
	}
	
	function _handler(event){
		_hide(event.data.box);
	}
})(jQuery);


