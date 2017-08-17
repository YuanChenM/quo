/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.fn.extend({
		theme: function(options){
			 //Add for bug#0126164 at 2013/10/02 by zhangtao1 start.  
			var logo=$("#logoPicture").text();
			 //Add for bug#0126164 at 2013/10/02 by zhangtao1 end. 
			var op = $.extend({themeBase:"themes"}, options);
			var _themeHref = op.themeBase + "/#theme#/style.css";
			var _themeHref2 = op.themeBase + "/#theme#/style-quotation.css";
			 //Modify for bug#0126164 at 2013/10/02 by zhangtao1 start. 
			var _themeimg = op.themeBase + "/#theme#/images/"+logo;
			 //Modify for bug#0126164 at 2013/10/02 by zhangtao1 end. 
			var annmimg = op.themeBase + "/#theme#/images/message.png";
			var alertimg = op.themeBase + "/#theme#/images/alert.png";
			var favimg = op.themeBase + "/#theme#/images/favorite.png";
			var helpimg = op.themeBase + "/#theme#/images/help.png";
			return this.each(function(){
				var jThemeLi = $(this).find(">li[theme]");
				var setTheme = function(themeName){
					$("head").find("link[href$='style.css']").attr("href", _themeHref.replace("#theme#", themeName));
					$("head").find("link[href$='style-quotation.css']").attr("href", _themeHref2.replace("#theme#", themeName));
					$(".syslogo>img").attr('src',_themeimg.replace("#theme#", themeName));
					$("#annmimg").attr('src',annmimg.replace("#theme#", themeName));
					$("#alertimg").attr('src',alertimg.replace("#theme#", themeName));
					$("#favimg").attr('src',favimg.replace("#theme#", themeName));
					$("#helpimg").attr('src',helpimg.replace("#theme#", themeName));
					$("img[src*=icon_],img[src*=order_]").attr('src',function(i,a){
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
					jThemeLi.find(">div").removeClass("selected");
					jThemeLi.filter("[theme="+themeName+"]").find(">div").addClass("selected");
					
					if ($.isFunction($.cookie)) $.cookie("unilogi_"+$('#profileBox .pulldown>span').text()+"_theme", themeName,{expires:365*10});
				};
				
				jThemeLi.each(function(index){
					var $this = $(this);
					var themeName = $this.attr("theme");
					$this.addClass(themeName).click(function(){
						setTheme(themeName);
					});
				});
					
				if ($.isFunction($.cookie)){
					var themeName = $.cookie("unilogi_"+$('#profileBox .pulldown>span').text()+"_theme");
					if (themeName) {
						setTheme(themeName);
					}
				}
				
			});
		}
	});
})(jQuery);
