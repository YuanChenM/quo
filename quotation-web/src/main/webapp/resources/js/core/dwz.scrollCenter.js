/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.fn.extend({

		getWindowSize: function(){
			if ($.browser.opera) { return { width: window.innerWidth, height: window.innerHeight }; }
			return { width: $(window).width(), height: $(window).height() };
		},
		/**
		 * @param options
		 */		
		scrollCenter: function(options){
			//
			var op = $.extend({ z: 1000000, mode:"WH"}, options);
			
			// 
			var windowSize = this.getWindowSize();

			if (op.mode == "W") {
				this.appendTo(document.body).css({
					'left': (windowSize.width - this.width()) / 2 + $(window).scrollLeft() + 'px'
				});
			} else if (op.model == "H"){
				this.appendTo(document.body).css({
					'top': (windowSize.height - this.height()) / 2 + $(window).scrollTop() + 'px'
				});	
			} else {
				this.appendTo(document.body).css({
				//	'position': 'absolute',
				//	'z-index': op.z,
					'top': (windowSize.height - this.height()) / 2 + $(window).scrollTop() + 'px',
					'left': (windowSize.width - this.width()) / 2 + $(window).scrollLeft() + 'px'
				});
			}

			
			// 
			var bodyScrollTop = $(document).scrollTop();
			var bodyScrollLeft = $(document).scrollLeft();
			var movedivTop = this.offset().top;
			var movedivLeft = this.offset().left;
			
			var thisjQuery = this;
			
			// 
			$(window).scroll(function(e){
				var windowSize = thisjQuery.getWindowSize();
				var tmpBodyScrollTop = $(document).scrollTop();
				var tmpBodyScrollLeft = $(document).scrollLeft();
				
				movedivTop += tmpBodyScrollTop - bodyScrollTop;
				movedivLeft += tmpBodyScrollLeft - bodyScrollLeft;
				bodyScrollTop = tmpBodyScrollTop;
				bodyScrollLeft = tmpBodyScrollLeft;

				// 
				if (op.mode == "W") {
					thisjQuery.stop().animate({
						'left': movedivLeft + 'px'
					});
				} else if (op.mode == "H") {
					thisjQuery.stop().animate({
						'top': movedivTop + 'px'
					});
				} else {
					thisjQuery.stop().animate({
						'top': movedivTop + 'px',
						'left': movedivLeft + 'px'
					});
				}
				
			});
			
			// 
			$(window).resize(function(){
				var windowSize = thisjQuery.getWindowSize();
				movedivTop = (windowSize.height - thisjQuery.height()) / 2 + $(document).scrollTop();
				movedivLeft = (windowSize.width - thisjQuery.width()) / 2 + $(document).scrollLeft();
				
				if (op.mode == "W") {
					thisjQuery.stop().animate({
						'left': movedivLeft + 'px'
					});
				} else if (op.mode == "H") {
					thisjQuery.stop().animate({
						'top': movedivTop + 'px'
					});
				} else {
					thisjQuery.stop().animate({
						'top': movedivTop + 'px',
						'left': movedivLeft + 'px'
					});
				}
				
			});
			
			return this;
		}
	});
})(jQuery);