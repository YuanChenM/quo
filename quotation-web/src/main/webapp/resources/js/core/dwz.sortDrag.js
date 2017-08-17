/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	var _op = {
		cursor: 'move', // selector
		sortBoxs: 'div.sortDrag', //
		replace: true, //2
		items: '> *', //
		selector: '', //
		zIndex: 1000
	};
	var isclick = true;
	//Modify for bug#0126181 at 2013/10/15 by jiang_nan start
	var startTime = null;
	var endTime = null;
	//Modify for bug#0126181 at 2013/10/15 by jiang_nan end
	var sortDrag = {
		start:function($sortBox, $item, event, op){
			//Modify for bug#0126181 at 2013/10/15 by jiang_nan start
			startTime = new Date();
			//Modify for bug#0126181 at 2013/10/15 by jiang_nan end
			isclick = true;
			//$item = $item.parent();
			var $placeholder = this._createPlaceholder($item);
			var $helper = $item.clone();
			var position = $item.position();
			$helper.data('$sortBox', $sortBox).data('op', op).data('$item', $item).data('$placeholder', $placeholder);
			$helper.addClass('sortDragHelper').css({position:'absolute',top:position.top,left:position.left,zIndex:op.zIndex,width:$item.width()+'px',height:$item.height()+'px'}).jDrag({
				selector:op.selector,
				drag:this.drag,
				stop:this.stop,
				event:event
			});

			return false;
		},
		drag:function(){
			//Modify for bug#0126181 at 2013/10/15 by jiang_nan start
			endTime = new Date();
			var subTime = endTime.getTime()-startTime.getTime();
			if(subTime<50){
				return;
			}
			//Modify for bug#0126181 at 2013/10/15 by jiang_nan end
			
			isclick = false;
			var $helper = $(arguments[0]), $sortBox = $helper.data('$sortBox'), $placeholder = $helper.data('$placeholder');
			var $item = $helper.data('$item');
			$item.before($placeholder).before($helper).hide();
			var $items = $sortBox.find($helper.data('op')['items']).filter(':visible').filter(':not(.sortDragPlaceholder, .sortDragHelper)');
			var helperPos = $helper.position(), firstPos = $items.eq(0).position();

			var $overBox = sortDrag._getOverSortBox($helper);
			if ($overBox.length > 0 && $overBox[0] != $sortBox[0]){ //
				$placeholder.appendTo($overBox);
				$helper.data('$sortBox', $overBox);
			} else {
				for (var i=0; i<$items.length; i++) {
					var $this = $items.eq(i), position = $this.position();
		
					if (helperPos.top > position.top) {
						$this.after($placeholder);
					} else if (helperPos.top <= position.top-4) {
						$this.before($placeholder);
						break;
					}
				}
			}
		},
		stop:function(){
			var $helper = $(arguments[0]), $item = $helper.data('$item'), $placeholder = $helper.data('$placeholder');
			var position = $placeholder.position();
			$helper.animate({
				top: position.top + "px",
				left: position.left + "px"
			}, {
				complete: function(){
				
					if ($helper.data('op')['replace']){ //2
						$srcBox = $item.parents(_op.sortBoxs+":first");
						$destBox = $placeholder.parents(_op.sortBoxs+":first");
						if ($srcBox[0] != $destBox[0]) { //
							$replaceItem = $placeholder.next();
							if ($replaceItem.size() > 0) {
								$replaceItem.insertAfter($item);
							}
						}
					}
					$item.insertAfter($placeholder).show();
					$placeholder.remove();
					$helper.remove();
					if(!isclick){
						saveFavoriteOrder();
					}
				},
				duration: 300
			});
		},
		_createPlaceholder:function($item){
			return $('<'+$item[0].nodeName+' class="sortDragPlaceholder"/>').css({
				width:$item.width()+'px',
				height:$item.height()+'px',
				marginTop:$item.css('marginTop'),
				marginRight:$item.css('marginRight'),
				marginBottom:$item.css('marginBottom'),
				marginLeft:$item.css('marginLeft')
			});
		},
		_getOverSortBox:function($item){
			var itemPos = $item.position();
			var y = itemPos.top+($item.height()/2), x = itemPos.left+($item.width()/2);
			return $(_op.sortBoxs).filter(':visible').filter(function(){
				var $sortBox = $(this), sortBoxPos = $sortBox.position();
				return DWZ.isOver(y, x, sortBoxPos.top, sortBoxPos.left, $sortBox.height(), $sortBox.width());
			});
		}
	};
	
	$.fn.sortDrag = function(options){
				
		return this.each(function(){
			var op = $.extend({}, _op, options);
			var $sortBox = $(this);
			
			if ($sortBox.attr('selector')) op.selector = $sortBox.attr('selector');
			$sortBox.find(op.items).each(function(i){
				var $item = $(this), $selector = $item;
				if (op.selector) {
					$selector = $item.find(op.selector).css({cursor:op.cursor});
				}

				$selector.mousedown(function(event){
					if(event.isTigger){
						return false;
					}
					sortDrag.start($sortBox, $item, event, op);
					event.preventDefault();
				});
			});
		});
	};
})(jQuery);
