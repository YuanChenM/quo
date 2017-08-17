/*
Common ascii char will used by printer.
 */

/**
Common printer instruction interface.
Usage:
startPrint();
printString(...);
printQRCode(...);
endPrint();
SEND printCodeString to ocx.
clearPrintCodeString();
 */
/**
 * COMMON define, Dont modify!
 */
var printCodeString="";
var ESC=String.fromCharCode(27);
var STX=String.fromCharCode(2);
var ETX=String.fromCharCode(3);
var RETURN=String.fromCharCode(10);
var ENTER=String.fromCharCode(13);
var NULL=String.fromCharCode(0);
var PREFIX=String.fromCharCode(5);
var SUFFIX=String.fromCharCode(17);

var LINE_HORIZONTAL='H';
var LINE_VERTICAL='V';

/**
 * Set darkness of printer to VERY DARK, and set zero slash=1.
 */
var setDarkness=function(){
	printCodeString=printCodeString+ESC+"#E5"+ESC+"LH1";
};

/**
 * Send Start print command.
 */
var startPrint=function(){
	printCodeString=printCodeString+ESC+STX+ESC+"A";

};

/**
 * Set label size.
 * @param width Max width of label.
 * @param height Max height of label.
 */
var setLabelSize=function(width, height){
	printCodeString=printCodeString+ESC+"A1"+fillWithZero(height, 4)+fillWithZero(width, 4);
};

/**
 * Set char pitch, default is 02.
 */
var setCharPitch=function(pitchNumber){
    var pitch;
    if(pitchNumber==undefined){
        pitch="02";
    }
    else{
        pitch=fillWithZero(pitchNumber, 2);
    }
    printCodeString=printCodeString+ESC+"P"+pitch;
};

/**
 * send end print command.
 */
var endPrint=function(){
	printCodeString=printCodeString+ESC+"Q1"+RETURN+ESC+"Z"+ETX+RETURN+NULL;
};

/**
 * Print string with specify size.
 * @param strtoprint  The string you want to print.
 * @param strrow  Row to print
 * @param strcol  Col to print
 * @param strwidth  Font width
 * @param strheight  Font height
 */
var printVectorString=function(strtoprint, strrow, strcol, strwidth, strheight){
	//printCodeString = printCodeString+ ESC+"H"+(strcol)+ESC+"V"+(strrow)+ESC+"WB1"+strtoprint;
	printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC+"$A,"+strwidth+","+strheight+",0"+ESC+"$="+strtoprint;
};
var printFontString=function(strcol,strrow,strtoprint,fontType){
	//alert(fontType)
	//printCodeString = printCodeString+ ESC+"H"+(strcol)+ESC+"V"+(strrow)+ESC+"XU"+strtoprint;
};
/**
 * Print string with specify size, and every char is aequilate.
 * @param strtoprint  The string you want to print.
 * @param strrow  Row to print
 * @param strcol  Col to print
 * @param strwidth  Font width
 * @param strheight  Font height
 */
var printVectorStringWithFixCharWidth=function(strtoprint, strrow, strcol, strwidth, strheight){
	printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC+"$B,"+strwidth+","+strheight+",0"+ESC+"$="+strtoprint;
};

/**
 * Print string with specify size and font weight.
 * @param strtoprint  The string you want to print.
 * @param strrow  Row to print
 * @param strcol  Col to print
 * @param strwidth  Font width
 * @param strheight  Font height
 * @param strweight Font weight, 1-9, if weight is 0, this method is same with  printVectorString.
 */
var printVectorStringWithWeight=function(strtoprint, strrow, strcol, strwidth, strheight, strweight){
	printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol;
    printCodeString=printCodeString+ESC+"$A,"+strwidth+","+strheight+","+strweight;
    printCodeString=printCodeString+ESC+"$="+strtoprint;
};

/**
 * Print string with raster font.
 * @param strtoprint
 * @param strrow
 * @param strcol
 * @param strwidth
 * @param strheight
 * @param strfont , Support font:
 * A: CG Times
 * B: CG Triumvirate
 * C: MKaiS0-Medium-U (Simplified)
 * c: MHeiS-Bold-U (Traditional)
 * K: HYRGoThic-Medium
 * T: AngsanaUPC
 * F: Futura2Book
 * P: CG Palacio
 * S: CG Century Schoolbook
 * G: CG Triumvirate Condensed
 * V: Universe Medium
 * t: CG Times
 */
var printRasterString=function(strtoprint, strrow, strcol, strwidth, strheight, strfont){
    printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol;
    printCodeString=printCodeString+ESC+"RD"+strfont+"00,"+fillWithZero(strwidth, 3)+","+fillWithZero(strheight,3)+","+strtoprint;
};

/**
 * Print string with point font.
 * @param strtoprint
 * @param strrow
 * @param strcol
 * @param strfont , Support Font: S, M, U, OA, OB, XB, XL, XS, XM, XU, WB, WL, XB, XL
 * @param strwidthexpand 1-12
 * @param strheightexpand 1-12
 */
var printDotString=function(strtoprint, strrow, strcol, strwidthexpand, strheightexpand, strfont){
    var smoothingFlag="";
    if(strfont=="WB" || strfont=="WL" || strfont=="XB" || strfont=="XL"){
        smoothingFlag="1";
    }
    printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol;
    printCodeString=printCodeString+ESC+"L"+fillWithZero(strwidthexpand, 2)+fillWithZero(strheightexpand,2);
    printCodeString=printCodeString+ESC+strfont+smoothingFlag+strtoprint;
};

/**
 * Print a
 * @param strtoprint
 * @param strrow
 * @param strcol
 * @param barlinewidth , 1 or 2
 * @param barheight  60, 70
 * @param strCodeType 1 - Code 39, 2 - Code 128
 */
var printBarcode=function(strtoprint, strrow, strcol, barlinewidth, barheight, strCodeType){
	//BG02080
	var tmpStrtoprint=strtoprint;
	if(strCodeType=="1"){
		tmpStrtoprint="*"+tmpStrtoprint+"*";
	}
	printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC+"B"+strCodeType+fillWithZero(barlinewidth, 2)+fillWithZero(barheight, 3)+tmpStrtoprint;
	//Modify for bug#0091721 at 2013/04/11 by jiang_nan start
	printCodeString = printCodeString+ ESC+"H"+(strcol+50)+ESC+"V"+(strrow+parseInt(barheight, 10)+5)+ESC+"XM"+strtoprint;
	//Modify for bug#0091721 at 2013/04/11 by jiang_nan end
};

// Add for Bug#0204216 at 2015/06/22 by Chen Wen1 Start
/**
 * Print Barcode Only
 * @param strtoprint
 * @param strrow
 * @param strcol
 * @param barlinewidth , 1 or 2
 * @param barheight  60, 70
 * @param strCodeType 1 - Code 39, 2 - Code 128
 */
var printBarcodeOnly=function(strtoprint, strrow, strcol, barlinewidth, barheight, strCodeType){
    //BG02080
    var tmpStrtoprint=strtoprint;
    if(strCodeType=="1"){
        tmpStrtoprint="*"+tmpStrtoprint+"*";
    }
    printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC+"B"+strCodeType+fillWithZero(barlinewidth, 2)+fillWithZero(barheight, 3)+tmpStrtoprint;
};
// Add for Bug#0204216 at 2015/06/22 by Chen Wen1 End

/**
 * Print barcode (CODE39).
 * @param strtoprint Barcode content to print
 * @param strrow
 * @param strcol
 * @param barlinewidth Width of barcode line, please use 1-3.
 * @param barheight Height of Barcode., please 50-120
 */
var printCode39=function(strtoprint, strrow, strcol, barlinewidth, barheight){
    printBarcode(strtoprint, strrow, strcol, barlinewidth, barheight, '1');
};

/**
 * Print barcode (CODE93).
 * @param strtoprint Barcode content to print
 * @param strrow
 * @param strcol
 * @param barlinewidth Width of barcode line, please use 1-3.
 * @param barheight Height of Barcode., please 50-120
 */
var printCode93=function(strtoprint, strrow, strcol, barlinewidth, barheight){
    printBarcode(strtoprint, strrow, strcol, barlinewidth, barheight, '2');
    printCodeString += ESC+"H"+strcol+ESC+"V"+strrow+ESC+"XS"+strtoprint;
    
};

/**
 * Print barcode (CODE128).
 * @param strtoprint Barcode content to print
 * @param strrow
 * @param strcol
 * @param barlinewidth Width of barcode line, please use 1-3.
 * @param barheight Height of Barcode., please 50-120
 */
var printCode128=function(strtoprint, strrow, strcol, barlinewidth, barheight){
    printBarcode(strtoprint, strrow, strcol, barlinewidth, barheight, 'G');
};

/**
 * Print QR Code using old CLxxx instruction.
 * @param strtoprint QRCode contents to print.
 * @param strrow
 * @param strcol
 * @param qrwidth QRCode width, 1-10.
 */
var printQRCodeCL=function(strtoprint, strrow, strcol, qrwidth){
	 //Modify for Bug#0099247 at 2013/05/09 by jiang_nan Start
	//printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC +"2D30,M,"+qrwidth+",1,0"+ESC+"DS"+strtoprint;
	  printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC +"2D30,M,0"+qrwidth+",1,0"+ESC+"DN"+getCharLength(strtoprint)+","+strtoprint;
	//printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC +"2D30,M,0"+qrwidth+",0,0,14,15,FF"+ESC+"DN"+getCharLength(strtoprint)+","+strtoprint;
	//printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC +"2D30,M,0"+qrwidth+",0,0,"+ESC+"DS"+1+","+strtoprint;
	//Modify for Bug#0099247 at 2013/05/09 by jiang_nan End
};

/**
 * Print QR Code, using new CL/GLxxxe instruction.
 * @param strtoprint QRCode contents to print.
 * @param strrow
 * @param strcol
 * @param qrwidth QRCode width, 1-10.
 */
var printQRCode=function(strtoprint, strrow, strcol, qrwidth){
	printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC +"BQ20"+fillWithZero(qrwidth, 2)+",3"+getCharLength(strtoprint)+strtoprint;
};

/**
 * Reverse image with specify width and height.
 * Should after printString.
 * @param row
 * @param col
 * @param reverseWidth
 * @param reverseHeight
 */
var reverseImage=function(row, col, reverseWidth, reverseHeight){
    var command="("+fillWithZero(reverseWidth, 4)+","+fillWithZero(reverseHeight, 4);
    printCustom(command, row, col);
};

/**
 * Print a Vertical line.
 * @param row
 * @param col
 * @param lineLength
 */
var printVerticalLine=function(row, col, lineLength){
    printLine(row, col, lineLength, 2, LINE_VERTICAL);
};

/**
 * Print a Horizontal line.
 * @param row
 * @param col
 * @param lineLength
 */
var printHorizontalLine=function(row, col, lineLength){
    printLine(row, col, lineLength, 2, LINE_HORIZONTAL);
};

/**
 * Print line with length, width, direction.
 * @param row
 * @param col
 * @param lineLength
 * @param lineWidth
 * @param direction
 */
var printLine=function(row, col, lineLength, lineWidth, direction){
    var command="FW"+fillWithZero(lineWidth, 2)+direction+fillWithZero(lineLength, 4);
    printCustom(command, row, col);
};

/**
 * Print box with line width, width, height.
 * @param row
 * @param col
 * @param width
 * @param height
 * @param horizontalLineWidth
 * @param verticalLineWidth
 */
var printBox=function (row, col, width, height, horizontalLineWidth, verticalLineWidth){
    var command="FW"+fillWithZero(horizontalLineWidth, 2)+fillWithZero(verticalLineWidth, 2);
    command+="V"+fillWithZero(height, 4)+"H"+fillWithZero(width, 4);
    printCustom(command, row, col);
};

//Modify for bug#0216032 at 2016/02/14 by tang_feng start
/**
 * Print a circle.  please refer SBPL.
 * @param strrow
 * @param strcol
 * @param strwidth
 * @param strradius
 * @param strsection please refer SBPL
 * @param strtype please refer SBPL
 */
var printCircle=function(strrow, strcol, strwidth, strradius, strsection, strtype){
    printCodeString=printCodeString+ESC+"H"+strcol;
    printCodeString=printCodeString+ESC+"V"+strrow;
    printCodeString=printCodeString+ESC+"FC"+","+strradius+","+strwidth+","+strsection+","+strtype;
};
//Modify for bug#0216032 at 2016/02/14 by tang_feng end


/**
 * Print a custom command, please dont call it directly.
 * @param commandtoprint
 * @param strrow
 * @param strcol
 */
var printCustom=function(commandtoprint, strrow, strcol){
	printCodeString=printCodeString+ESC+"V"+strrow+ESC+"H"+strcol+ESC+commandtoprint;
};

/**
 * get code string to send to OCX.
 */
var getCodeString=function(){
    return printCodeString;
};

/**
 * Clear print code string, then you can print next label.
 */
var clearPrintCodeString=function(){
	printCodeString="";
};

/**
 * Get length of string, and fill pre-zero to 4 bits.
 * Used in Print QR Code function. don't call it directly.
 * @param strtoprint
 */
var getCharLength=function(strtoprint){
    var len=strtoprint.length;
    return fillWithZero(len, 4);
};

/**
 * Fill string to specify length with prefix-zero.
 * @param str
 * @param len
 */
var fillWithZero=function(str, len){
    var lenStr=""+str;
    while(lenStr.length<len){
        lenStr="0"+lenStr;
    }
    return lenStr;
};
/**
 * Fill string to specify length with Space.
 * @param str
 * @param len
 */
var fillWithSpace=function(str, len){
    var lenStr=""+str;
    lenStr = (lenStr.length>len)?lenStr.substring(0,len):lenStr;
    while(lenStr.length<len){
        lenStr=lenStr+" ";
    }
    return lenStr;
};
