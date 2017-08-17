/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.fn.extend({
		
		checkboxCtrl: function(parent){
			return this.each(function(){
				var $trigger = $(this);
				$trigger.wrap('<div class="checkboxCtrl unchecked"></div>').hide();
				var $triggerdiv = $trigger.parent();
				var $parent = $(parent || document);
				var group = $trigger.attr("group");
				var $checkboxLi = $parent.find(":checkbox[name='"+group+"']");
				$checkboxLi.click(function(ev){
					// Modify for bug#0093643 at 2013/04/09 by jiang_nan start 
					$checkboxLi = $parent.find(":checkbox[name='"+group+"']");
					// Modify for bug#0093643 at 2013/04/09 by jiang_nan end 
					var $this = $(this);
					var $triggerdiv = $('div.checkboxCtrl',$this.parents("table:first"));
					var ck = $checkboxLi.filter(':checked').length;
					$triggerdiv.removeClass('unchecked').removeClass('checked').removeClass('indeterminate');
					//isTrigger
					if(ev.isTrigger){
						if($this.is(":checked")){
							if(ck==1){
								$triggerdiv.addClass('unchecked');
							}else{
								$triggerdiv.addClass('indeterminate');
							}
						}else{
							if(ck==($checkboxLi.length-1)){
								$triggerdiv.addClass('checked');
							}else{
								$triggerdiv.addClass('indeterminate');
							}
						}
					}else{
						if($this.is(":checked")){
							if(ck==($checkboxLi.length)){
								$triggerdiv.addClass('checked');
							}else{
								$triggerdiv.addClass('indeterminate');
							}
						}else{
							if(ck==0){
								$triggerdiv.addClass('unchecked');
							}else{
								$triggerdiv.addClass('indeterminate');
							}
						}
					}
					if($triggerdiv.hasClass('indeterminate')||$triggerdiv.hasClass('unchecked')){
						$(':checkbox',$triggerdiv).attr('checked', false);
					}
					if($triggerdiv.hasClass('checked')){
						$(':checkbox',$triggerdiv).attr('checked', true);
					}
				});
				
				if($trigger.is(":checked")){
						$triggerdiv.addClass('checked');
				}else{
						$triggerdiv.addClass('unchecked');
				}
				
				$triggerdiv.click(function(){
					
					if ($trigger.is(":checkbox")) {
						$trigger.attr('checked', !$trigger.is(":checked"));
						if($trigger.is(":checked")){
							$triggerdiv.removeClass('unchecked').removeClass('checked').removeClass('indeterminate');
							$triggerdiv.addClass('checked');
						}else{
							$triggerdiv.removeClass('unchecked').removeClass('checked').removeClass('indeterminate');
							$triggerdiv.addClass('unchecked');
						}
						var type = $trigger.is(":checked") ? "all" : "none";
						if (group) $.checkbox.select(group, type, parent);
					} else {
						if (group) $.checkbox.select(group, $trigger.attr("selectType") || "all", parent);
					}
					
				});
			});
		}
	});
	
	$.checkbox = {
		selectAll: function(_name, _parent){
			this.select(_name, "all", _parent);
		},
		unSelectAll: function(_name, _parent){
			this.select(_name, "none", _parent);
		},
		selectInvert: function(_name, _parent){
			this.select(_name, "invert", _parent);
		},
		select: function(_name, _type, _parent){
			$parent = $(_parent || document);
			$checkboxLi = $parent.find(":checkbox[name='"+_name+"']");
			switch(_type){
				case "invert":
					$checkboxLi.each(function(){
						$checkbox = $(this);
						$checkbox.attr('checked', !$checkbox.is(":checked"));
					});
					break;
				case "none":
					$checkboxLi.attr('checked', false);
					break;
				default:
					$checkboxLi.attr('checked', true);
					break;
			}
		}
	};
})(jQuery);
