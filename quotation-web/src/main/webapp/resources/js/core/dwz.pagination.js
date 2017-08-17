/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.fn.extend({
		pagination: function(opts){
			var setting = {
				first$:"li.j-first", prev$:"li.j-prev", next$:"li.j-next", last$:"li.j-last", nums$:"li.j-num>a", jumpto$:"li.jumpto",
				//pageNumFrag:'<li class="#liClass#"><a href="javascript:;">#pageNum#</a></li>'
				pageNumFrag:'<li class="jumpto">'+$.regional.pagination.page.page +' <input class="textInput" type="text" size="1" value="#currentPage#" id="pageinput"/> of #tpageNum# </li>'
			};
			return this.each(function(){
				var $this = $(this);
				var pc = new Pagination(opts);
				var interval = pc.getInterval();
	
				//var pageNumFrag = '';
				var pageNumFrag = setting.pageNumFrag;
//				for (var i=interval.start; i<interval.end;i++){
//					pageNumFrag += setting.pageNumFrag.replaceAll("#pageNum#", i).replaceAll("#liClass#", i==pc.getCurrentPage() ? 'selected j-num' : 'j-num');
//				}
				$this.html(DWZ.frag["pagination"].replaceAll("#pageNumFrag#", pageNumFrag).replaceAll("#currentPage#", pc.getCurrentPage()).replaceAll("#tpageNum#", pc.getTotalPages()));
	
				var $first = $this.find(setting.first$);
				var $prev = $this.find(setting.prev$);
				var $next = $this.find(setting.next$);
				var $last = $this.find(setting.last$);
				
				$('img',$first).attr("alt",$.regional.pagination.page.first);
				$('img',$first).attr("title",$.regional.pagination.page.first);
				$('img',$prev).attr("alt",$.regional.pagination.page.prev);
				$('img',$prev).attr("title",$.regional.pagination.page.prev);
				$('img',$next).attr("alt",$.regional.pagination.page.next);
				$('img',$next).attr("title",$.regional.pagination.page.next);
				$('img',$last).attr("alt",$.regional.pagination.page.last);
				$('img',$last).attr("title",$.regional.pagination.page.last);
				
				if (pc.hasPrev()){
					$first.add($prev).find(">span").hide();
					_bindEvent($prev, pc.getCurrentPage()-1, pc.targetType(), pc.rel());
					_bindEvent($first, 1, pc.targetType(), pc.rel());
				} else {
					$first.add($prev).addClass("disabled").find(">a").hide();
				}
				
				if (pc.hasNext()) {
					$next.add($last).find(">span").hide();
					_bindEvent($next, pc.getCurrentPage()+1, pc.targetType(), pc.rel());
					_bindEvent($last, pc.numPages(), pc.targetType(), pc.rel());
				} else {
					$next.add($last).addClass("disabled").find(">a").hide();
				}
	
				$this.find(setting.nums$).each(function(i){
					_bindEvent($(this), i+interval.start, pc.targetType(), pc.rel());
				});
				$this.find(setting.jumpto$).each(function(){
					var $this = $(this);
					var $inputBox = $this.find(":text");
					var $button = $this.find(":button");
					$button.click(function(event){
						var pageNum = $inputBox.val();
						if (pageNum && pageNum.isPositiveInteger()) {
							if(pageNum>0 && pageNum <=pc.getTotalPages()){
								dwzPageBreak({targetType:pc.targetType(), rel:pc.rel(), data: {pageNum:pageNum}});
							}
						}
					});
					$inputBox.keyup(function(event){
						//if (event.keyCode == DWZ.keyCode.ENTER) $button.click();
						if (event.keyCode == DWZ.keyCode.ENTER){
							var pageNum = $inputBox.val();
							if (pageNum && pageNum.isPositiveInteger()) {
								if(pageNum>0 && pageNum <=pc.getTotalPages()){
									dwzPageBreak({targetType:pc.targetType(), rel:pc.rel(), data: {pageNum:pageNum}});
								}
							}
						}
					});
				});
			});
			
			function _bindEvent($target, pageNum, targetType, rel){
				$target.bind("click", {pageNum:pageNum}, function(event){
					dwzPageBreak({targetType:targetType, rel:rel, data:{pageNum:event.data.pageNum}});
					event.preventDefault();
				});
			}
		},
		
		orderBy: function(options){
			var op = $.extend({ targetType:"navTab", rel:"", asc:"asc", desc:"desc"}, options);
			return this.each(function(){
				var $this = $(this).css({cursor:"pointer"}).click(function(){
					var orderField = $this.attr("orderField");
					//var orderDirection = $this.hasClass(op.asc) ? op.desc : op.asc;
					//alert($(":input[name='page.sort.dir']",$("#pagerForm")).val());
					var form;
					if(op.targetType=="navTab"){
						form= $("#pagerForm",navTab.getCurrentPanel()).get(0);						
					}else{
						form= $("#pagerForm",$.pdialog.getCurrent()).get(0);
					}
					var orderDirection=op.asc;
					if (form) {
						if (form[DWZ.pageInfo.orderField].value == orderField){
							if(op.targetType=="navTab"){
								orderDirection = $("#pagerForm :input[name='page.sort.dir']",navTab.getCurrentPanel()).val()=='asc'  ? op.desc : op.asc;
							}else{
								orderDirection = $("#pagerForm :input[name='page.sort.dir']",$.pdialog.getCurrent()).val()=='asc'  ? op.desc : op.asc;
							}
						}
					}
					dwzPageBreak({targetType:op.targetType, rel:op.rel, data:{pageNum:1,orderField: orderField, orderDirection: orderDirection}});
				});
				
			});
		},
		pagerForm: function(options){
			var op = $.extend({pagerForm$:"#pagerForm", parentBox:document}, options);
			var frag = '<input type="hidden" name="#name#" value="#value#" />';
			return this.each(function(){
				var $searchForm = $(this);
				if($('.listfilter',$searchForm).length==1){
					$searchForm = $('.listfilter',$searchForm);
				}
				var $pagerForm = $(op.pagerForm$, op.parentBox);
				var actionUrl = $pagerForm.attr("action").replaceAll("#rel#", $searchForm.attr("action"));
				$pagerForm.attr("action", actionUrl);
				$searchForm.find(":input[name]").each(function(){
					var $input = $(this), name = $input.attr("name");
					if (name && (!$input.is(":checkbox,:radio") || $input.is(":checked"))){
						if ($pagerForm.find(":input[name='"+name+"']").length == 0) {
							var inputFrag = frag.replaceAll("#name#", name).replaceAll("#value#", 
									$input.val().replaceAll('"',"&quot;").replaceAll("<","&lt;").replaceAll(">", "&gt;"));
							$pagerForm.append(inputFrag);
						}
					}
				});
			});
		}
	});
	
	var Pagination = function(opts) {
		this.opts = $.extend({
			targetType:"navTab",	// navTab, dialog
			rel:"", //div id
			totalCount:0,
			numPerPage:10,
			pageNumShown:10,
			currentPage:1,
			totalPages:1,
			callback:function(){return false;}
		}, opts);
	};
	
	$.extend(Pagination.prototype, {
		targetType:function(){return this.opts.targetType;},
		rel:function(){return this.opts.rel;},
		numPages:function() {
			return Math.ceil(this.opts.totalCount/this.opts.numPerPage);
		},
		getInterval:function(){
			var ne_half = Math.ceil(this.opts.pageNumShown/2);
			var np = this.numPages();
			var upper_limit = np - this.opts.pageNumShown;
			var start = this.getCurrentPage() > ne_half ? Math.max( Math.min(this.getCurrentPage() - ne_half, upper_limit), 0 ) : 0;
			var end = this.getCurrentPage() > ne_half ? Math.min(this.getCurrentPage()+ne_half, np) : Math.min(this.opts.pageNumShown, np);
			return {start:start+1, end:end+1};
		},
		getCurrentPage:function(){
			var currentPage = parseInt(this.opts.currentPage);
			if (isNaN(currentPage)) return 1;
			return currentPage;
		},
		getTotalPages:function(){
			var totalPages = parseInt(this.opts.totalPages);
			if (isNaN(totalPages)) return 1;
			return totalPages;
		},
		hasPrev:function(){
			return this.getCurrentPage() > 1;
		},
		hasNext:function(){
			return this.getCurrentPage() < this.numPages();
		}
	});
})(jQuery);
