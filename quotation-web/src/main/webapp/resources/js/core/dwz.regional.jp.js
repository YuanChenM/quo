/**
 * @screen core
 * @author jiang_ting
 */
(function($){
	// jQuery validate
	$.extend($.validator.messages, {
		required: "This field is required.",
		remote: "Please fix this field.",
		email: "Please enter a valid email address.",
		url: "Please enter a valid URL.",
		date: "Please enter a valid date.",
		dateISO: "Please enter a valid date (ISO).",
		number: "Please enter a valid number.",
		digits: "Please enter only digits.",
		creditcard: "Please enter a valid credit card number.",
		equalTo: "Please enter the same value again.",
		accept: "Please enter a value with a valid extension.",
		maxlength: $.validator.format("Please enter no more than {0} characters."),
		minlength: $.validator.format("Please enter at least {0} characters."),
		rangelength: $.validator.format("Please enter a value between {0} and {1} characters long."),
		range: $.validator.format("Please enter a value between {0} and {1}."),
		max: $.validator.format("Please enter a value less than or equal to {0}."),
		min: $.validator.format("Please enter a value greater than or equal to {0}."),
		
		alphanumeric: "Letters, numbers or underscores only please.",
		lettersonly: "Letters only please.",
		phone: "Please specify a valid phone number.",
		forcode:"Please specify a valid code.",
		maxnumber:$.validator.format("numeric value out of bounds ({0} digits expected)."),
		forrefno:"Please specify a valid Ref No."
			
	});
	
	// DWZ regional
	$.setRegional("datepicker", {
		dayNames:['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
		monthNames:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
		todaybut:['today'],okbut:['ok'],clearbut:['clear']
	});
	$.setRegional("alertMsg", {
		title:{error:"Error", info:"Information", warn:"Warning", correct:"Successful", confirm:"Confirmation"},
		butMsg:{ok:"OK", yes:"Yes", no:"No", cancel:"Cancel",clear:"Clear"}
	});
})(jQuery);