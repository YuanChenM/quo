/**
 * preview pdf file
 * @param url url
 * @param jsonData param json data
 */
	function previewPdf(url,jsonData){
		//var i=0;
		
		/**$.each(jsonData,function(key,value){
			if(i==0){
				url +="?"+key+"="+value;
			}else{
				url +="&"+key+"="+value;
			}
			i++;
		});*/
		createPdfFile(url,jsonData);
		
		
		//window.open(url,"previewPdfWindow",'height=1024,width=768, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no') ;
		//$.pdialog.open(url, dlgId, '',{mask:true,width:1024,height:768});
	}
    //Modified by Liu_bing at 2014/9/22 for #0168866 start
	function createPdfFile(originalUrl,originalJsonData){
			$.ajax({
				type : "POST",
				url : originalUrl,
				data:originalJsonData,
				cache : false,
				success : function(data) {
					var jsonData = eval("(" +data+")");
					var errorMessage = jsonData.errorMessage;
					if(errorMessage){
						alertMsg.warn(errorMessage);
						return;
					}
					// Add for bug#0113187 at 2013/07/31 by Jiang Nan Start.
					errorMessage =  jsonData.message;
					if(errorMessage){
						alertMsg.error(errorMessage);
						return;
					}
					// Add for bug#0113187 at 2013/07/31 by Jiang Nan End.
					var fileName = jsonData.fileName;
					var indexOf = fileName.indexOf(".");
					fileName = fileName.substring(0,indexOf);
					var basePath = jsonData.basePath;
					var url = basePath+"/report/previewPdf/"+fileName;
                    //url+="&originalUrl="+esc
//                    alert(originalUrl);
//                    alert(JSON.stringify(originalJsonData));
                    window.originalJsonData=originalJsonData;
                    window.originalUrl=originalUrl;
					var previewWindow=window.open(url,"previewPdfWindow","location=no,menubar=no,resizable=yes,height=1024px,width=768px,alwaysRaised=yes,modal=yes,status=no");
                    //previewWindow.document.getElementById("originalUrlValue").value=originalUrl;
                    //previewWindow.originalUrlValue=originalUrl;
                    //previewWindow.originalJsonDataValue=originalJsonData;
				},// Add for bug#0113187 at 2013/07/31 by Jiang Nan Start.
				error:function(data, textStatus, errorThrown){
					// Modify for bug#0114763 at 2013/08/06 by jiang_nan Start.
					if(data.status==500){
						alertMsg.error(data.responseText);
					}else{
						var jsonData = eval("(" +data+")");
						alertMsg.error(jsonData.message);
					}
				//	var jsonData = eval("(" +data+")");
				//	alertMsg.error(data);
				// Modify for bug#0114763 at 2013/08/06 by jiang_nan End.
				}
				// Add for bug#0113187 at 2013/07/31 by Jiang Nan End.
			});
		
	}
    //Modified by Liu_bing at 2014/9/22 for #0168866 end