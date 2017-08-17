/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.fn.extend({
		cssTable: function(options){

			return this.each(function(){
				var $this = $(this);
				var $trs = $this.find('tbody:first>tr');
				var $grid = $this.parent(); // table
				var nowrap = $this.hasClass("nowrap");
				
				var hp=	parseInt($this.attr("hovernum")) || 1;
				$trs.each(function(index){
					var $tr = $(this);
					if(hp==1 && $tr.attr("hoid")){
						if(index==0){
							$tr.addClass("trbg");
						}else{
							if($tr.prev().attr("hoid") == $tr.attr("hoid")){
								$tr.prev().hasClass("trbg")?$tr.addClass("trbg"):'';
							}else{
								$tr.prev().hasClass("trbg")?'':$tr.addClass("trbg");
							}
						}
					}else{
						if(hp>1){
							if (!nowrap && ((index % (hp*2)) >= hp)) $tr.addClass("trbg");
							$tr.attr("hoid",(index - (index % hp)));
						}else{
							if (!nowrap && (index % 2) == 1) $tr.addClass("trbg");
							$tr.attr("hoid",index);
						}
					}
					$tr.click(function(ev){
						$trs.filter(".selected").removeClass("selected");
						var atr = $trs.filter('[hoid="'+$tr.attr('hoid')+'"]');
						atr.addClass("selected");
						if($this.attr("cbrctl")!="true"){
							if(!ev.target.type){
								var $trck = $('td:first>:checkbox',atr);
								if($trck.length==1){
									$trck.trigger("click");
								}
								var $trrd = $('td:first>:radio',atr);
								if($trrd.length==1){
									$trrd.trigger("click");
								}
							}
						}
						var sTarget = $tr.attr("target");
						if (sTarget) {
							if ($("#"+sTarget, $grid).size() == 0) {
								$grid.prepend('<input id="'+sTarget+'" type="hidden" />');
							}
							$("#"+sTarget, $grid).val($tr.attr("rel"));
						}
					});
					var _className = "hover";
					$(this).hover(function(){
						//alert($(this).attr('hoid'));
						$trs.filter('[hoid="'+$(this).attr('hoid')+'"]').addClass(_className);
					},function(){
						$trs.filter('[hoid="'+$(this).attr('hoid')+'"]').removeClass(_className);
					});
				});

				$this.find("thead [orderField]").orderBy({
					targetType: $this.attr("targetType"),
					rel:$this.attr("rel"),
					asc: $this.attr("asc") || "asc",
					desc:  $this.attr("desc") || "desc"
				});
//				$this.find("thead [orderField]").removeClass('asc');
				//$this.find("thead [orderField] > div").width($this.find("thead [orderField] > div").width()+15);
				$this.find("thead [orderField] > div").addClass("orderdiv");
				var form;
				if($this.attr('targetType')=='dialog'){
					form= $("#pagerForm",$.pdialog.getCurrent()).get(0);
				}else{
					form= $("#pagerForm",navTab.getCurrentPanel()).get(0);
				}

				if (form) {
					$this.find("thead [orderField]").each(function(){
						//alert($(this).attr("orderField"));
						if (form[DWZ.pageInfo.orderField].value == $(this).attr("orderField")){
							if (form[DWZ.pageInfo.orderDirection].value == "asc"){
								//$(this).addClass('asc');
								$(this).find(".orderdiv").append("<img border=0 src='resources/themes/default/images/order_up.png'/>");
							}else if(form[DWZ.pageInfo.orderDirection].value == "desc"){
								$(this).find(".orderdiv").append("<img border=0 src='resources/themes/default/images/order_down.png'/>");
								//$(this).addClass('desc');
							}
						}
					});
				}
//				$this.find("thead [orderField]").removeClass('desc');
				
			});
		}
	});
})(jQuery);
