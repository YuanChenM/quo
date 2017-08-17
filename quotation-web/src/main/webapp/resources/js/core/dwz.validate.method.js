/**
 * @screen core
 * @author jiang_ting
 * @requires jquery.validate.js
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please.w");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please.");
		
		$.validator.addMethod("phone", function(value, element) {
			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
		}, "Please specify a valid phone number.");
		
		$.validator.addMethod("postcode", function(value, element) {
			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
		}, "Please specify a valid postcode.");

		$.validator.addMethod("forcode", function(value, element) {
			return this.optional(element) || /^[0-9A-Za-z-_\/]+$/.test(value);
		}, "Please specify a valid code.");
		
		
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('format') || 'dd-NNN-yyyy';
				
				var datav = false;
				var fmt=[["d-NNN-yyyy"],["yyyyMMdd"],["yyyy-M-d"],["d-M-yyyy"],["yy-M-d"],["M-d-yy"],["d-M-yy"],
				         ["NNN-yyyy"],["M-yyyy"],["yyyy-M"],["yyyyM"]];
				var datad;
				for (var i=0;i<fmt.length;i++) {
					datad = $input.val().replaceAll("/","-").parseDate(fmt[i]);
					if(datad!=0 && (datad.formatDate(pattern) != $input.val())){
						$input.val(datad.formatDate(pattern));
						datav=true;
						break;
					}
				}
				return !$input.val() || $input.val().parseDate(pattern) || datav;
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		$.validator.addMethod("email", function(value, element) {
			// contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
			return this.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d)|(([a-z]|\d)([a-z]|\d|-|\.|_|~)*([a-z]|\d)))\.)+(([a-z])|(([a-z])([a-z]|\d|-|\.|_|~)*([a-z])))$/i.test(value);
		});
		
		$.validator.addMethod("forrefno", function(value, element) {
			if(/[\*%\'\"]/g.test(value)){
				return false;
			}
			return this.optional(element) || !/[^\x00-\xff]/.test(value);
		},"Please specify a valid Ref No.");
		
		$.validator.addMethod("forpartno", function(value, element) {
			// Modify for bug#0144406 at 2014/01/24	by lijie4 Start.
			value = value.trim();
			$(element).val(value);
			// Modify for bug#0144406 at 2014/01/24 by lijie4 End.
			if(/[\*%\'\"]/g.test(value)){
				return false;
			}
			return this.optional(element) || !/[^\x00-\xff]/.test(value);
		},"Please specify a valid Part No.");
		
		$.validator.addMethod("forascii", function(value, element) {
			return this.optional(element) || !/[^\x00-\xff]/.test(value);
		},"Please specify a valid Ascii.");
		
		$.validator.addMethod("gtzero", function(value, element) {
			return this.optional(element) || value > 0;
		},"Must be greater than zero.");
		$.validator.addMethod("gteqzero", function(value, element) {
			return this.optional(element) || value >= 0;
		},"Must be greater than or equal to zero.");
		
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true},
			forcode: {forcode: true},
			forrefno: {forrefno: true},
			forpartno: {forpartno: true},
			forascii: {forascii: true},
			gtzero: {gtzero: true},
			gteqzero: {gteqzero: true}
		});
		
		$.validator.addMethod("minlength", function(value, element, param) {
			return this.optional(element) || this.getLength(value, element) >= param;
		});

		$.validator.addMethod("maxlength", function(value, element, param) {
			var valuetmp = value;
			var lth = valuetmp.replace(/\n/g, "\r\n").length;
			if(this.getLength(valuetmp, element)>lth){
				lth = this.getLength(valuetmp, element);
			}
			return  lth <= param;
		});
		
		$.validator.addMethod("maxnumber", function(value, element, param) {
			param = param.split(",");
			if(!param[1] || param[1]=="0"){
				return this.optional(element) || new RegExp("^-?(\\d{0," + param[0] + "})$").test(value);
			}else{
				if(value!=""&&value.endsWith(".")){
					return false;
				}
				return this.optional(element) || new RegExp("^-?(\\d{0," + param[0] + "})(\\.\\d{0,"+param[1]+"})?$").test(value);
			}
		}, "numeric value out of bounds ({0} digits expected).");
		

		
		
		$.validator.setDefaults({errorElement:"div",
			errorPlacement: function(error, element) {
			},
			onfocusin: function(element, event) {
				this.lastActive = element;

				// hide error label and remove error class on focus if enabled
				if ( this.settings.focusCleanup && !this.blockFocusCleanup ) {
					this.settings.unhighlight && this.settings.unhighlight.call( this, element, this.settings.errorClass, this.settings.validClass );
					this.addWrapper(this.errorsFor(element)).hide();
				}
				//inncheck(element, event);
			},
			onkeyup: function(element, event) {
//				if ( element.name in this.submitted || element == this.lastElement ) {
//					this.element(element);
//				}
			},
			onfocusout: function(element, event) {
				inncheck(element, event);
				//if ( !this.checkable(element) && (element.name in this.submitted || !this.optional(element)) ) {
					//this.element(element);
				//}
			},
			highlight: function(element, errorClass, validClass) {
				if (element.type === 'radio') {
					this.findByName(element.name).addClass(errorClass).removeClass(validClass);
				} else {
					if($(element).parent().hasClass("select")){
						$(">a",$(element).parent()).addClass(errorClass).removeClass(validClass);
					}else{
						$(element).addClass(errorClass).removeClass(validClass);
					}
				}
			}
		});
		$.validator.autoCreateRanges = true;
		
	}

})(jQuery);


function inncheck(element, event){
	//
	if($(element).hasClass("uppercasecss")){
		$(element).val($(element).val().toUpperCase());
	}
	if($(element).attr("thoudigit")!=undefined){
		if(formatThouDigit){
			var t = $(element).attr("thoudigit");
			
			$(element).val(formatThouDigit($(element).val(),t,1));
		}
	}
	if($(element).hasClass("date") && !($(element).attr("readonly")=="readonly")){
		var fmt=[["d-NNN-yyyy"],["yyyyMMdd"],["yyyy-M-d"],["d-M-yyyy"],["yy-M-d"],["M-d-yy"],["d-M-yy"],
		         ["NNN-yyyy"],["M-yyyy"],["yyyy-M"],["yyyyM"]];
		var datad;
		var orgfmt= $(element).attr('format') || 'dd-NNN-yyyy';
		for (var i=0;i<fmt.length;i++) {
			datad = $(element).val().replaceAll("/","-").parseDate(fmt[i]);
			if(datad!=0){
				$(element).val(datad.formatDate(orgfmt));
				if($(element).data("changed")){
					if($(element).attr('ondatachange')){
						eval($(element).attr('ondatachange'));
						$(element).data("changed",false);
					}
				}
				break;
			}
		}
		if($(element).data("changed")){
			if($(element).attr('ondatachange')){
				eval($(element).attr('ondatachange'));
				$(element).data("changed",false);
			}
		}
		//$(element).trigger("datachange");
	}
}