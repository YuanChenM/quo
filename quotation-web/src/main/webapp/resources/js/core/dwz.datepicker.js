/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	$.setRegional("datepicker", {
		dayNames:['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
		monthNames:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
		todaybut:['today'],okbut:['ok'],WK:['W'],clearbut:['clear']
	});

	$.fn.datepicker = function(opts){
		var setting = {
			box$:"#calendar",
			year$:"#calendar [name=year]", month$:"#calendar [name=month]",
			tmInputs$:"#calendar .time :text", hour$:"#calendar .time .hh", minute$:"#calendar .time .mm", second$:"#calendar .time .ss",
			tmBox$:"#calendar .tm", tmUp$:"#calendar .time .up", tmDown$:"#calendar .time .down",
			close$:"#calendar .close", calIcon$:"a.inputDateButton",
			main$:"#calendar .main", days$:"#calendar .days", dayNames$:"#calendar .dayNames",
			clearBut$:"#calendar .clearBut", okBut$:"#calendar .okBut", todayBut$:"#calendar .todayBut",
			maBut$:"#calendar .maBut", mbBut$:"#calendar .mbBut", yaBut$:"#calendar .yaBut", ybBut$:"#calendar .ybBut",
			wk$:"#calendar .wk",body$:"#calendar .body"
		};

		function changeTmMenu(sltClass){
			var $tm = $(setting.tmBox$);
			$tm.removeClass("hh").removeClass("mm").removeClass("ss");
			if (sltClass) {
				$tm.addClass(sltClass);
				$(setting.tmInputs$).removeClass("slt").filter("." + sltClass).addClass("slt");
			}
		}
		function clickTmMenu($input, type){
			$(setting.tmBox$).find("."+type+" li").each(function(){
				var $li = $(this);
				$li.click(function(){
					$input.val($li.text());
				});
			});
		}
		function keydownInt(e){
			if (!((e.keyCode >= 48 && e.keyCode <= 57) || (e.keyCode == DWZ.keyCode.DELETE || e.keyCode == DWZ.keyCode.BACKSPACE))) { return false; }
		}
		function changeTm($input, type){
			var ivalue = parseInt($input.val()), istart = parseInt($input.attr("start")), iend = parseInt($input.attr("end"));
			if (type == 1) {
				if (ivalue < iend){$input.val(ivalue + 1);}
			} else if (type == -1){
				if (ivalue > istart){$input.val(ivalue - 1);}
			} else if (ivalue > iend) {
				$input.val(iend);
			} else if (ivalue < istart) {
				$input.val(istart);
			}
		}
				
		return this.each(function(){
			var $this = $(this);
			var dp = new Datepicker($this.val(), opts);
			
			function generateCalendar(dp){
				var dw = dp.getDateWrap();

				var monthStart = new Date(dw.year,dw.month-1,1);
				var startDay = monthStart.getDay();
				var dayStr="";
				if (startDay > 0){
					monthStart.setMonth(monthStart.getMonth() - 1);
					var prevDateWrap = dp.getDateWrap(monthStart);
					for(var t=prevDateWrap.days-startDay+1;t<=prevDateWrap.days;t++) {
						dayStr+='<dd class="other" chMonth="-1" day="' + t + '">'+t+'</dd>';
					}
				}
				for(var t=1;t<=dw.days;t++){
					if(t==dw.day){
						dayStr+='<dd class="slt" day="' + t + '">'+t+'</dd>';
					}else{
						dayStr+='<dd day="' + t + '">'+t+'</dd>';
					}
				}
				for(var t=1;t<=42-startDay-dw.days;t++){
					dayStr+='<dd class="other" chMonth="1" day="' + t + '">'+t+'</dd>';
				}
				
				var $days = $(setting.days$).html(dayStr).find("dd");
				
				$days.filter(function(i){
					return (i+7)%7==0 || (i+7)%7==6;
				}).addClass('bgg');
				$(':nth-child(1),:nth-child(7)',$(setting.dayNames$)).addClass('bgg');
				
				
				$days.click(function(){
					var $day = $(this);
					$this.val(dp.formatDate(dp.changeDay($day.attr("day"), $day.attr("chMonth"))));
					$this.trigger("change").trigger("focusin");
					if($this.attr('ondatachange')&& $this.data("changed")){
						eval($this.attr('ondatachange'));
						$this.data("changed",false);
					}

					if (!dp.hasTime()) { closeCalendar(); }
					else {
						$days.removeClass("slt");
						$day.addClass("slt");
					}
				});
				var wkstr = "<div>"+$.regional.datepicker.WK+"</div>";
				var wkk=$('dd:nth-child(2)',setting.days$);
				var tm =dp.sDate;
				var wknum=dp.iso8601Week(dp.changeDay(wkk.attr("day"), wkk.attr("chMonth")));
				dp.sDate = tm;
				for(var w=0;w<6;w++){
					if(wknum==53){
						wknum=1;
					}
					wkstr += "<div>"+wknum+"</div>";
					wknum++;
				}
				$(setting.wk$).html(wkstr);
				
				if (!dp.hasDate()) $(setting.main$).addClass('nodate'); // 
				
				if (!dp.onlyYearMonth()) $(setting.body$).hide();
				
				if (dp.hasTime()) {
					$("#calendar .time").show();
					
					var $hour = $(setting.hour$).val(dw.hour).focus(function(){
						changeTmMenu("hh");
					});
					var $minute = $(setting.minute$).val(dw.minute).focus(function(){
						changeTmMenu("mm");
					});
					var $second = $(setting.second$).val(dw.second).focus(function(){
						changeTmMenu("ss");
					});
					$hour.add($minute).add($second).click(function(){return false;});
					
					clickTmMenu($hour,"hh");
					clickTmMenu($minute,"mm");
					clickTmMenu($second,"ss");
					$(setting.box$).click(function(){
						changeTmMenu();
					});
					
					var $inputs = $(setting.tmInputs$);
					$inputs.keydown(keydownInt).each(function(){
						var $input = $(this);
						$input.keyup(function(){
							changeTm($input, 0);
						});
					});
					$(setting.tmUp$).click(function(){
						$inputs.filter(".slt").each(function(){
							changeTm($(this), 1);
						});
					});
					$(setting.tmDown$).click(function(){
						$inputs.filter(".slt").each(function(){
							changeTm($(this), -1);
						});
					});
					
					if (!dp.hasHour()) $hour.attr("disabled",true);
					if (!dp.hasMinute()) $minute.attr("disabled",true);
					if (!dp.hasSecond()) $second.attr("disabled",true);
				}
				
			}
			
			function closeCalendar() {
				$(setting.box$).remove();
				$(document).unbind("click", closeCalendar);
			}

			$this.click(function(event){
				$(document).trigger('click');
				if($this.attr("editmodel") && $this.attr("editmodel")!="true"){
					return false;
				}
				closeCalendar();
				var dp = new Datepicker($this.val(), opts);
				var offset = $this.offset();
				var dpleft = offset.left;
				if((document.documentElement.clientWidth-offset.left)<240){
					dpleft = document.documentElement.clientWidth-240;
				}
				var iTop = offset.top+this.offsetHeight;
				$(DWZ.frag['calendarFrag']).appendTo("body").css({
					left:dpleft+'px',
					top:iTop+'px'
				}).show().click(function(event){
					event.stopPropagation();
				});
				
				($.fn.bgiframe && $(setting.box$).bgiframe());
				
				var dayNames = "";
				$.each($.regional.datepicker.dayNames, function(i,v){
					dayNames += "<dt>" + v + "</dt>";
				});
				$(setting.dayNames$).html(dayNames);
				
				var dw = dp.getDateWrap();
				var $year = $(setting.year$);
				var yearstart = dw.year+parseInt(dp.get("yearstart"));
				var yearend = dw.year+parseInt(dp.get("yearend"));
				for(y=yearstart; y<=yearend; y++){
					$year.append('<option value="'+ y +'"'+ (dw.year==y ? 'selected="selected"' : '') +'>'+ y +'</option>');
				}
				var $month = $(setting.month$);
				$.each($.regional.datepicker.monthNames, function(i,v){
					var m = i+1;
					$month.append('<option value="'+ m +'"'+ (dw.month==m ? 'selected="selected"' : '') +'>'+ v +'</option>');
				});
				
				// generate calendar
				generateCalendar(dp);
				$year.add($month).change(function(){
					dp.changeDate($year.val(), $month.val());
					generateCalendar(dp);
				});
				
				// fix top
				var iBoxH = $(setting.box$).outerHeight(true);
				if (iTop > iBoxH && iTop > $(window).height()-iBoxH) {
					$(setting.box$).css("top", offset.top - iBoxH);
				}
				
				$(setting.close$).click(function(){
					closeCalendar();
				});
				$(setting.clearBut$).click(function(){
					$this.val("");
					$this.trigger("change").trigger("focusin");
					if($this.attr('ondatachange')&& $this.data("changed")){
						eval($this.attr('ondatachange'));
						$this.data("changed",false);
					}

					closeCalendar();
				}).text($.regional.datepicker.clearbut);;
				$(setting.ybBut$).click(function(){
					$year.val(parseInt($year.val())-1);
					dp.changeDate($year.val(), $month.val());
					generateCalendar(dp);
				});
				$(setting.mbBut$).click(function(){
					if(parseInt($month.val())==1){
						$month.val(12);
						$year.val(parseInt($year.val())-1);
					}else{
						$month.val(parseInt($month.val())-1);
					}
					dp.changeDate($year.val(), $month.val());
					generateCalendar(dp);
				});
				$(setting.todayBut$).click(function(){
					var $days = $(setting.days$).find("dd");
					dw = dp.getDateWrap(new Date());
					$this.val(dp.formatDate(dp.changeDate(dw.year, dw.month, dw.day)));
					$this.trigger("change").trigger("focusin");
					if($this.attr('ondatachange')&& $this.data("changed")){
						eval($this.attr('ondatachange'));
						$this.data("changed",false);
					}

					if (!dp.hasTime()) { closeCalendar(); }
					else {
						$days.removeClass("slt");
						//$day.addClass("slt");
					}
				}).text($.regional.datepicker.todaybut);
				$(setting.maBut$).click(function(){
					if(parseInt($month.val())==12){
						$month.val(1);
						$year.val(parseInt($year.val())+1);
					}else{
						$month.val(parseInt($month.val())+1);
					}
					dp.changeDate($year.val(), $month.val());
					generateCalendar(dp);
				});
				$(setting.yaBut$).click(function(){
					$year.val(parseInt($year.val())+1);
					dp.changeDate($year.val(), $month.val());
					generateCalendar(dp);
				});
				$(setting.okBut$).click(function(){
					var $dd = $(setting.days$).find("dd.slt");
					var date = dp.changeDay($dd.attr("day"), $dd.attr("chMonth"));
					
					if (dp.hasTime()) {
					 	date.setHours(parseInt($(setting.hour$).val()));
						date.setMinutes(parseInt($(setting.minute$).val()));
						date.setSeconds(parseInt($(setting.second$).val()));
					}
					
					$this.val(dp.formatDate(date));
					$this.trigger("change").trigger("focusin");
					if($this.attr('ondatachange')&& $this.data("changed")){
						eval($this.attr('ondatachange'));
						$this.data("changed",false);
					}

					closeCalendar();
				}).text($.regional.datepicker.okbut);
				$(document).bind("click", closeCalendar);
				return false;
			});
			if($this.attr("editmodel") && $this.attr("editmodel")!="true"){
				$this.parent().parent().find(setting.calIcon$).css('cursor','default');
			}else{
				$this.parent().parent().find(setting.calIcon$).hover(function(){
					$(this).addClass('inputDateButtonhover');
				},function(){
					$(this).removeClass('inputDateButtonhover');
				});
			}
			$this.parent().parent().find(setting.calIcon$).click(function(){
				$this.trigger("click");
				return false;
			});
			$this.parent().parent().find(setting.calIcon$).keydown(function(event){
				if(event.keyCode ==9 ){
					closeCalendar();
				}
			});
			
			
			
		});
		
	};

	var Datepicker = function(sDate, opts) {
		this.opts = $.extend({
			pattern:'dd-NNN-yyyy',
			//pattern:'yyyy-MM-dd',
			yearstart:-32,
			yearend:17
		}, opts);
		
		this.sDate = sDate.trim();
	};
	
	$.extend(Datepicker.prototype, {
		get: function(name) {
			return this.opts[name];
		},
		_getDays: function (y,m){//
			return m==2?(y%4||!(y%100)&&y%400?28:29):(/4|6|9|11/.test(m)?30:31);
		},

		getDateWrap: function(date){ //
			if (!date) date = this.parseDate(this.sDate) || new Date();
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var days = this._getDays(y,m);
			return {
				year:y, month:m, day:date.getDate(),
				hour:date.getHours(),minute:date.getMinutes(),second:date.getSeconds(),
				days: days, date:date
			};
		},
		/**
		 * @param {year:2010, month:05, day:24}
		 */
		changeDate: function(y, m, d){
			var date = new Date(y, m - 1, d || 1);
			this.sDate = this.formatDate(date);
			return date;
		},
		changeDay: function(day, chMonth){
			if (!chMonth) chMonth = 0;
			var dw = this.getDateWrap();
			return this.changeDate(dw.year, dw.month+parseInt(chMonth), day);
		},
		parseDate: function(sDate){
			return sDate.parseDate(this.opts.pattern);
		},
		formatDate: function(date){
			return date.formatDate(this.opts.pattern);
		},
		hasHour: function() {
			return this.opts.pattern.indexOf("H") != -1;
		},
		hasMinute: function() {
			return this.opts.pattern.indexOf("m") != -1;
		},
		hasSecond: function() {
			return this.opts.pattern.indexOf("s") != -1;
		},
		hasTime: function() {
			return this.hasHour() || this.hasMinute() || this.hasSecond();
		},
		hasDate: function() {
			var _dateKeys = ['y','M','d','E'];
			for (var i=0; i<_dateKeys.length; i++){
				if (this.opts.pattern.indexOf(_dateKeys[i]) != -1) return true;
			}

			return false;
		},
		onlyYearMonth: function() {
			var _dateKeys = ['d','E','H','m','s'];
			for (var i=0; i<_dateKeys.length; i++){
				if (this.opts.pattern.indexOf(_dateKeys[i]) != -1) return true;
			}

			return false;
		},
		iso8601Week: function(date) {
			var checkDate = new Date(date.getTime());
			// Find Thursday of this week starting on Monday
			checkDate.setDate(checkDate.getDate() + 4 - (checkDate.getDay() || 7));
			var time = checkDate.getTime();
			checkDate.setMonth(0); // Compare with Jan 1
			checkDate.setDate(1);
			return Math.floor(Math.round((time - checkDate) / 86400000) / 7) + 1;
		}
	});
})(jQuery);
 