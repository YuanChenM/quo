
var rightCoordinate = [1050,1029,1008,985,963,941,919,899,878,856,834,812,790,768,746,723,700,677,654,631,618,595,572,549,526,503,480,457,434,411,388,365,342,319,296];
// Modify for bug#0227587 at 2017/03/15 by li_detian Start
var PREFIX=String.fromCharCode(5);
// Modify for bug#0227587 at 2017/03/15 by li_detian end
/**
 * Construction of LocationObject   add by DK
 */
var LocationObject = function() {};
LocationObject.prototype = {
    labelType:"LO",
    locationNo:"",
    backNo:"",
    length:0,
    width:0,
    height:0,
    packageType:"",
    warehouseCode:"",
    offset:0,
    getQrNumber:function() {
        return  fillWithSpace(this.labelType, 2)
                + fillWithSpace(this.locationNo, 10)
                + fillWithSpace(this.backNo, 10)
                + fillWithSpace(this.length, 4)
                + fillWithSpace(this.width, 4)
                + fillWithSpace(this.height, 4)
                + fillWithSpace(this.packageType, 10)
                + fillWithSpace(this.warehouseCode, 10);
    }
};

/**
 * Construction of UserObject   add by DK
 */
var UserObject = function() {};
UserObject.prototype = {
    labelType:"US",
    CompanyName:"",
    familyName:"",
    givenName:"",
    issueDate:"",
    issueDateQR:"",
    loginID:"",
    ownerCode:"",
    title:"",
    offset:0,
    getQrNumber:function() {
        return  fillWithSpace(this.labelType, 2)
                + fillWithSpace(this.loginID, 20)
                + fillWithSpace(this.ownerCode, 10)
                + fillWithSpace(this.issueDateQR, 8);
    }
};

/**
 * Construction of PackageObject    add by DK
 */
var PackageObject = function() {};
PackageObject.prototype = {
    labelType:"OP",
    packageNo:"",
    backNo:"",
    length:0,
    width:0,
    height:0,
    qrLength:0,
    qrWidth:0,
    qrHeight:0,
    packageType:"",
    packageTypeCode:"",
    offset:0,
    getQrNumber:function() {
        return  fillWithSpace(this.labelType, 2)
                + fillWithSpace(this.packageNo, 10)
                + fillWithSpace(this.backNo, 10)
                + fillWithSpace(this.qrLength, 4)
                + fillWithSpace(this.qrWidth, 4)
                + fillWithSpace(this.qrHeight, 4)
                + fillWithSpace(this.packageTypeCode, 10);
    }
};

/**
 * Construction of InnerPackageObject   add by DK
 */
var InnerPackageObject = function() {};
InnerPackageObject.prototype = {
    LabelType:"IP",
    Title:"",
    TPN:"",
    BackNO:"",
    EndUser:"",
    
    CustomerCode:"",
    CPN:"",
    MPN:"",
    PartsName:"",
    COO:"",
    Maker:"",
    ReceiveDate:"",
    MfgDate:"",
    ExpiryDate:"",
    LotNo:"",
    DateCode:"",
    cpnBarcode:"",
    qtyBarcode:"",
    Quantity:"",
    UOM:"",
    IpNo:"",
    BarcodeQtyValue:"",
    expiryDateForQRCode:"",
    mfgDateForQRCode:"",
    offset:0,
    autoLabelFlag:0,
    // Modify for bug#0226531 at 2017/01/17 by lu_bin start
    printCOOFlag:0,
    // Modify for bug#0226531 at 2017/01/17 by lu_bin end
    // Modify for bug#0227587 at 2017/03/15 by li_detian Start
    ipLabelQrVersion:"",
    locationNo:"",
    receiveDateForT2:"",

    getQRString:function() {
        var qrString = "";
        if (this.ipLabelQrVersion == "T2") {
            qrString = "<B1>" +
            fillWithSpace(this.IpNo, 15) + PREFIX +
            fillWithSpace(this.BackNO, 6) + PREFIX +
            fillWithSpace(this.CPN, 20) + PREFIX +
            fillWithSpace(this.MPN, 30) + PREFIX +
            fillWithSpace(this.Maker, 30) + PREFIX +
            fillWithSpace(this.receiveDateForT2, 8) + PREFIX +
            fillWithSpace(this.BarcodeQtyValue, 10) + PREFIX +
            fillWithSpace(this.LotNo, 20) + PREFIX +
            fillWithSpace(this.DateCode, 20) + PREFIX +
            fillWithSpace(this.locationNo, 10) + PREFIX;
        } else {
            qrString = "IP"
                + fillWithSpace(this.IpNo, 20)
                + fillWithSpace(this.TPN, 10)
                + fillWithSpace(this.BackNO, 6)
                + fillWithSpace(this.CPN, 30)
                + fillWithSpace(this.MPN, 30)
                + fillWithSpace(this.BarcodeQtyValue, 10)
                + fillWithSpace(this.COO, 3)
                + fillWithSpace(this.DateCode, 20)
                + fillWithSpace(this.LotNo, 20)
                + fillWithSpace(this.mfgDateForQRCode, 8)
                + fillWithSpace(this.expiryDateForQRCode, 8);
    }
        return qrString;
    }
    // Modify for bug#0227587 at 2017/03/15 by li_detian end
};
/**
 * change the Date format  add by DK
 * @param Datestr  The Date String to change.
 */
function DateFormat(Datestr) {
    if(!Datestr.match("[0-9]{8}"))
        return "error";
    var Stack = new Array();
    var MonthName = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    Stack.push(Datestr.substring(0,4));
    Stack.push(Datestr.substring(4,6));
    Stack.push(Datestr.substring(6));
    if((Stack[1]-1) < 0||(Stack[1]-1) > 11)
        return "error";
    return Stack.pop()+"-"+MonthName[Stack.pop()-1]+"-"+Stack.pop();
}

/**
 * Print a Location Label   add by DK
 * @param LocationObject  The LocationObject to print.
 */
var printLocationLabel = function(LocationObject) {
	printVectorString(LocationObject.locationNo, 160, 725 +  parseInt(LocationObject.offset), 100, 111);
	
	printQRCodeCL(LocationObject.getQrNumber(), 160, 445 + parseInt(LocationObject.offset), 5);
	
	printVectorString(LocationObject.backNo, 400, 455 + parseInt(LocationObject.offset), 75, 75);

    printVectorString("Warehouse:  "+LocationObject.warehouseCode, 500, 445 +  parseInt(LocationObject.offset), 45, 50);

    printVectorStringWithWeight("(LL)", 500, 1080 + parseInt(LocationObject.offset), 50, 60, 4);
};

/**
 * Print a User Label   add by DK
 * @param UserObject  The UserObject to print.
 */
var printUserLabel = function(UserObject) {
    var titleWeight=4;
    printVectorString(UserObject.title, 10, 500 + parseInt(UserObject.offset), 45, 68);
    printQRCodeCL(UserObject.getQrNumber(), 175, 410 + parseInt(UserObject.offset), 7);

    printVectorStringWithWeight("Family Name:", 165, 680 + parseInt(UserObject.offset), 40, 55, titleWeight);
    printVectorString(UserObject.familyName, 220, 710 + parseInt(UserObject.offset), 65, 70);
    printVectorStringWithWeight("Given Name:", 300, 680 + parseInt(UserObject.offset), 40, 55,titleWeight);
    printVectorString(UserObject.givenName, 350, 710 + parseInt(UserObject.offset), 65, 70);

    printVectorStringWithWeight("Issue Date:",500,400 + parseInt(UserObject.offset),40, 60,titleWeight);
    printVectorString(UserObject.issueDate, 500, 600 + parseInt(UserObject.offset), 50, 60);
    printVectorStringWithWeight("("+UserObject.labelType+")",500,1080 + parseInt(UserObject.offset),50, 60, titleWeight);
};


/**
 * Print a Package Label    add by DK
 * @param PackageObject  The PackageObject to print.
 */
var printPackageLabel = function(PackageObject) {
	printQRCodeCL(PackageObject.getQrNumber(), 100, 400 + parseInt(PackageObject.offset), 7);
    printVectorStringWithWeight("PKG No.:", 100, 720 + parseInt(PackageObject.offset), 48, 60, 4);
    printVectorString(PackageObject.packageNo, 100, 900 + parseInt(PackageObject.offset), 48, 60);
    printVectorStringWithWeight("TYPE:", 175, 720 + parseInt(PackageObject.offset), 48, 60, 4);
    printVectorString(PackageObject.packageType, 175, 900 + parseInt(PackageObject.offset), 48, 60);
    printVectorStringWithWeight("Length:", 250, 720 + parseInt(PackageObject.offset), 48, 60, 4);
    printVectorString(PackageObject.length+" CM", 250, 900 + parseInt(PackageObject.offset), 48, 60);
    printVectorStringWithWeight("Width:", 325, 720 + parseInt(PackageObject.offset), 48, 60, 4);
    printVectorString(PackageObject.width+" CM", 325, 900 + parseInt(PackageObject.offset), 48, 60);
    printVectorStringWithWeight("Height:", 400, 720 + parseInt(PackageObject.offset), 48, 60, 4);
    printVectorString(PackageObject.height+" CM", 400, 900 + parseInt(PackageObject.offset), 48, 60);
    printVectorStringWithWeight("("+PackageObject.labelType+")", 500, 400 + parseInt(PackageObject.offset), 50, 60, 4);
 // Modify for Bug#0149868 at 2014/4/4 by wei_zhili End
};

/**
 * Print a Inner Package Label  add by DK
 * @param InnerPackageObject  The InnerPackageObject to print.
 */
function printInnerPackageLabel(InnerPackageObject) {
    var titleWeight = 4;
    var col = 800 + parseInt(InnerPackageObject.offset);
    var colstep = 145;
    var row = 0;
    var rowstep = 39;
    var fontW = 20;
    var fontL = 48;
    printVectorString(InnerPackageObject.Title, row + 5, 700 + parseInt(InnerPackageObject.offset), 40, 50);


    reverseImage(row + 5, col - colstep, 525, 50);
    printVectorString(InnerPackageObject.BackNO, row + 15, 400 + 10 + parseInt(InnerPackageObject.offset), 70, 80);
    // Modify for bug#0227587 at 2017/03/15 by li_detian Start
    var qrwidth = 5;
    if (InnerPackageObject.ipLabelQrVersion == "T2") {
        qrwidth = 4;
    }
    // Modify for bug#0227587 at 2017/03/15 by li_detian end
    printQRCodeCL(InnerPackageObject.getQRString(), 105, 400 + parseInt(InnerPackageObject.offset), qrwidth);
    printVectorString(InnerPackageObject.CustomerCode, 350, 400 + parseInt(InnerPackageObject.offset), 38, 40);
    printVectorString(InnerPackageObject.EndUser, 390, 400 + parseInt(InnerPackageObject.offset), 45, 60);

    // Modify for bug#0226531 at 2017/01/17 by lu_bin start
    if(InnerPackageObject.printCOOFlag == 1) {
        printVectorString(InnerPackageObject.COO, 450, 400 + parseInt(InnerPackageObject.offset), 38, 40);
    }

    if(InnerPackageObject.autoLabelFlag == 1) {
        printBox(522, 400 + parseInt(InnerPackageObject.offset) +15, 40, 40, 2, 2);
        //printCircle(535, 400 + parseInt(InnerPackageObject.offset) +35, 3, 30, 0, 0);
        printDotString("A", 529, 400 + parseInt(InnerPackageObject.offset) +27, 1, 1, "WB");
    }
    // Modify for bug#0226531 at 2017/01/17 by lu_bin end

    row = row + rowstep + 20;
    var colstep1 = 60;
    var spRow = 6;
    printVectorStringWithWeight("IP No:", row + 3, col - colstep, fontW + 10, fontL, titleWeight);
    printVectorString(InnerPackageObject.IpNo, row, col - colstep1, fontW + 20, fontL);
    // Modify for bug#0227587 at 2017/03/15 by li_detian Start
    printVectorStringWithWeight("(" + InnerPackageObject.ipLabelQrVersion + ")", row, col - colstep1 + 375, fontW + 20, fontL, titleWeight);
    // Modify for bug#0227587 at 2017/03/15 by li_detian end
    row += rowstep + spRow;
    printVectorStringWithWeight("CPN:", row + 3, col - colstep, fontW + 10, fontL, titleWeight);
    if (InnerPackageObject.CPN.length <= 20) {
        printVectorString(InnerPackageObject.CPN, row, col - colstep1, fontW + 25, fontL + 10);
    }
    else{
        printVectorString(InnerPackageObject.CPN, row, col - colstep1, fontW + 15, fontL + 10);
    }
    row += rowstep+spRow+10;
    printVectorStringWithWeight("MPN:", row + 3, col - colstep, fontW+10, fontL, titleWeight);
    if(InnerPackageObject.MPN.length<=20){
        printVectorString(InnerPackageObject.MPN, row, col-colstep1, fontW + 20, fontL );
    }
    else{
        printVectorString(InnerPackageObject.MPN, row, col-colstep1, fontW + 10, fontL );
    }

    row += rowstep+spRow;
    printVectorStringWithWeight("Maker:", row + 3, col - colstep, fontW+10, fontL, titleWeight);
    printVectorString(InnerPackageObject.Maker, row, col-colstep1, fontW + 20, fontL );

    
    row += rowstep+spRow;
    printVectorStringWithWeight("QTY:", row+3, col - colstep, fontW+10, fontL, titleWeight);
    if (InnerPackageObject.Quantity != "") {
        var tempStr = InnerPackageObject.Quantity.replace(/,/g,"").replace(/\./g,"");
        if (tempStr.length <= 6) {
            printVectorString(InnerPackageObject.Quantity+ " " + InnerPackageObject.UOM, row, col-colstep1, fontW + 30, fontL+10);
        } else {
            printVectorString(InnerPackageObject.Quantity+ " " + InnerPackageObject.UOM, row, col-colstep1-20, fontW + 26, fontL+10);
        }

    } else {
        printVectorString(InnerPackageObject.Quantity+ " " + InnerPackageObject.UOM, row, col-colstep1, fontW + 30, fontL+10);
    }

    var qtyStr = "";
    if (InnerPackageObject.Quantity != "") {
        var tempQuantityStr = InnerPackageObject.Quantity.replace( /,/g,"");
        var qtyIndex = tempQuantityStr.indexOf(".");
        if (qtyIndex < 0) {
            qtyStr = InnerPackageObject.qtyBarcode + tempQuantityStr;
        } else if (qtyIndex > 0) {
            qtyStr = InnerPackageObject.qtyBarcode + tempQuantityStr.substring(0, qtyIndex);
        } else {
            qtyStr = InnerPackageObject.qtyBarcode;
        }
    } else {
        qtyStr = InnerPackageObject.qtyBarcode;
    }
    if (qtyStr != "") {
        printBarcodeOnly(qtyStr,row, col - colstep1 + 260, "02", "45", "G");
    }

    row += rowstep+spRow+5;
    var colstep2 = 35;
    printVectorStringWithWeight("Receive Date:", row + 3, col - colstep, fontW+10, fontL, titleWeight);
    printVectorString(InnerPackageObject.ReceiveDate, row, col+colstep2, fontW + 20, fontL );
    row += rowstep+spRow;
    printVectorStringWithWeight("Date Code:", row+3, col - colstep, fontW+10, fontL, titleWeight);
    printVectorString(InnerPackageObject.DateCode, row, col+colstep2, fontW + 20, fontL );
    row += rowstep+spRow;
    printVectorStringWithWeight("LOT No:", row+3, col - colstep, fontW+10, fontL, titleWeight);
    printVectorString(InnerPackageObject.LotNo, row, col+colstep2, fontW + 20, fontL);


    row += rowstep+spRow;
    if(!InnerPackageObject.ExpiryDate==""){
    	printVectorStringWithWeight("Expiry Date:", row+3, col - colstep, fontW+10, fontL, titleWeight);
    	//printRasterString(InnerPackageObject.BackNO, row+10, col+60, fontW+10, fontL,"A");
       printVectorString(InnerPackageObject.ExpiryDate, row, col+colstep2, fontW + 20, fontL );
    }


    row += 62;
    if (InnerPackageObject.CPN!="") {
        var cpnStrInBarcode=InnerPackageObject.CPN;
        var cpnStr=InnerPackageObject.CPN;
        if (InnerPackageObject.cpnBarcode != "") {
            cpnStrInBarcode = InnerPackageObject.cpnBarcode + InnerPackageObject.CPN;
            cpnStr = '(' + InnerPackageObject.cpnBarcode + ')' + InnerPackageObject.CPN;
        }
        printBarcodeOnly(cpnStrInBarcode, row, createBarcodeRightCoordinate(cpnStrInBarcode) +15 + parseInt(InnerPackageObject.offset, 10), "02", "60", "G");
        printDotString(cpnStr, row+62, createBarcodeRightCoordinate(cpnStrInBarcode)+65+parseInt(InnerPackageObject.offset, 10), 1, 1 ,"WB");
    }

}
//create barCode right coordinate
function createBarcodeRightCoordinate(barCodeData){
	var dataLength = barCodeData.length;
	var retRightCoordinate = rightCoordinate[dataLength-1];
	return retRightCoordinate;
}

/**
 * Print a label.   add by DK
 * @param printObject  The LabelObject to print.
 * @param ocxObject  The OCX Object for communicate with SATO.
 * jsp need to call this function to finish the job!
 */
function printLabel(printObject,ocxObject) {
//    ocxObject.Init_Com_Parameter();
//    ocxObject.ComPort = portNo;
//    ocxObject.DelayTime = delayTime;
//    ocxObject.BaudRate = baudRate;
//    ocxObject.ByteSize = "8";
//    ocxObject.StopBits = "1";
//    ocxObject.reserved = "1";
    startPrint();
    if (printObject instanceof LocationObject)
        printLocationLabel(printObject);
    else if (printObject instanceof PackageObject)
        printPackageLabel(printObject);
    else if(printObject instanceof InnerPackageObject)
        printInnerPackageLabel(printObject);
    else if(printObject instanceof UserObject)
        printUserLabel(printObject);
    else
        return false;
    endPrint();
    ocxObject.Send_Data(printCodeString);
    var _flag=UniLabel.ErorrCode;
    clearPrintCodeString();
    return _flag;
}
/*
 var  getIpObject = function(ipEntity){
 var ip=new InnerPackingObject();
 ip.companyName=ipEntity.logisticsName;
 ip.importCountry=ipEntity.regionCode;
 ip.customerCode=ipEntity.customerCode;
 ip.backNo=ipEntity.backNo;
 ip.customerBackNo=ipEntity.customerBackNo;
 ip.customerPartsNo=ipEntity.customerPartsNo;
 ip.impPartsName=ipEntity.impPartsName;
 ip.colorCode=ipEntity.colorCode;
 ip.qty=ipEntity.qty;
 ip.decimalDigits=ipEntity.decimalDigits;
 ip.impPoNo=ipEntity.impPoNo;
 ip.ipNo=ipEntity.ipId;
 ip.ttcPartsNo=ipEntity.ttcPartsNo;
 ip.urgentFlag=ipEntity.orderHandlingType;
 ip.additionalFlag=ipEntity.orderType;
 ip.qrCode=ipEntity.qrCode;
 ip.reprintMark=ipEntity.reprintMark;
 ip.moduleGroupArea=ipEntity.moduleGroupArea;
 ip.moduleGroupNo=ipEntity.moduleGroupNo;
 ip.containerGroupNo=ipEntity.containerGroupNo;
 ip.packingDate=ipEntity.packingDate;
 ip.etd=ipEntity.strEtd;
 ip.impPlanDate=ipEntity.impPlanDate;
 ip.nonSpq=ipEntity.nonSpq;
 ip.fixLocationNo=ipEntity.fixLocationNo;
 ip.pcRackNo=ipEntity.pcRackNo;
 var arr=[];
 var arr1=[];
 if (ipEntity.materialList.length > 0) {
 for (var i = 0; i < ipEntity.materialList.length; i++) {
 arr.push(ipEntity.materialList[i].materialCode);
 arr1.push(ipEntity.materialList[i].qty);
 }
 }
 ip.materialName=arr;
 ip.materialQty=arr1;
 return ip;
 };*/
 
 
 function printLabelFor105(printObject,ocxObject) {
    startPrint();
    if (printObject instanceof LocationObject)
        printLocationLabel(printObject);
    else if (printObject instanceof PackageObject)
        printPackageLabel(printObject);
    else if(printObject instanceof InnerPackageObject)
        printInnerPackageLabelFor105(printObject);
    else if(printObject instanceof UserObject)
        printUserLabel(printObject);
    else
        return false;
    endPrint();
    ocxObject.Send_Data(printCodeString);
    var _flag=UniLabel.ErorrCode;
    clearPrintCodeString();
    return _flag;
}

function printInnerPackageLabelFor105(InnerPackageObject) {

    var leftCol=0+parseInt(InnerPackageObject.offset, 10);
    var rightCol=340+parseInt(InnerPackageObject.offset, 10);
    var rightCol1=rightCol+110;
    var rightCol2=rightCol+230;

    var row = 5;
    var rowstep = 52;
    var spRow = 60;
    var cpatainFloat=3;
    var cpatainFloatForMid=16;
    var captainFloatForBig25=32;
    var captainFloatForBig30=21;

    var captainFont="M";

    printDotString(InnerPackageObject.Title, row + 18, rightCol1 , 1, 1, "WL");
    reverseImage(row + 5, rightCol, 860, 65);

    setCharPitch(10);
    printDotString(InnerPackageObject.BackNO, row + 32, leftCol+20 , 1, 2, "WL");
    setCharPitch();
    // Modify for bug#0227587 at 2017/03/15 by li_detian Start
    var qrwidth = 6;
    if (InnerPackageObject.ipLabelQrVersion == "T2") {
        qrwidth = 5;
    }
    printQRCodeCL(InnerPackageObject.getQRString(), 150, leftCol, qrwidth);
    // Modify for bug#0227587 at 2017/03/15 by li_detian end
    printDotString(InnerPackageObject.CustomerCode, 480, leftCol, 1, 1, "XL");
    // Modify for bug#0226531 at 2017/01/17 by lu_bin start
    if(InnerPackageObject.EndUser!="" && InnerPackageObject.EndUser.length>10){
        printDotString(InnerPackageObject.EndUser.substr(0, 10), 565, leftCol, 1, 1, "WL");
        printDotString(InnerPackageObject.EndUser.substr(10), 625, leftCol, 1, 1, "WL");
    }
    else{
        printDotString(InnerPackageObject.EndUser, 590, leftCol, 1, 1, "WL");
    }

    if(InnerPackageObject.printCOOFlag == 1) {
        printDotString(InnerPackageObject.COO, 700, leftCol, 1, 1, "XL");
    }
    // Modify for bug#0226531 at 2017/01/17 by lu_bin end
    if(InnerPackageObject.autoLabelFlag == 1) {
        printBox(790, leftCol + 20, 60, 60, 2, 2);
        //printCircle(820, leftCol + 50, 5, 40, 0, 0);
        printDotString("A", 795, leftCol+35, 1, 1, "XL");
    }

    row = 100;
    printDotString("IP No:", row+cpatainFloat, rightCol, 1, 1 ,captainFont);
    printDotString(InnerPackageObject.IpNo, row, rightCol1, 1, 1, "WB");
    // Modify for bug#0227587 at 2017/03/15 by li_detian Start
    printDotString("(" + InnerPackageObject.ipLabelQrVersion + ")", row, rightCol1 + 670, 1, 1, captainFont);
    // Modify for bug#0227587 at 2017/03/15 by li_detian end
    row += rowstep;

    if (InnerPackageObject.CPN.length <= 15) {
        printDotString("CPN:", row+captainFloatForBig25, rightCol, 1, 1 ,captainFont);
        setCharPitch(6);
        printDotString(InnerPackageObject.CPN, row, rightCol1, 1, 2, "WL");
    } else
    if (InnerPackageObject.CPN.length <= 25) {
        printDotString("CPN:", row+captainFloatForBig25, rightCol, 1, 1 ,captainFont);
        if (InnerPackageObject.CPN.length <= 20){
            setCharPitch(4);
        } else {
            setCharPitch();
        }
        printDotString(InnerPackageObject.CPN, row, rightCol1, 1, 2, "WL");
    }
    else{
        printDotString("CPN:", row+captainFloatForBig30, rightCol, 1, 1 ,captainFont);
        setCharPitch(4);
        printDotString(InnerPackageObject.CPN, row, rightCol1, 1, 2, "WB");
    }
    row += rowstep+spRow + 10;
    if(InnerPackageObject.MPN.length<=15){
        printDotString("MPN:", row+captainFloatForBig25, rightCol, 1, 1 ,captainFont);
        setCharPitch(6);
        printDotString(InnerPackageObject.MPN, row, rightCol1, 1, 2, "WL");
    } else
    if(InnerPackageObject.MPN.length<=25){
        printDotString("MPN:", row+captainFloatForBig25, rightCol, 1, 1 ,captainFont);
        if (InnerPackageObject.MPN.length <= 20){
            setCharPitch(4);
        } else {
            setCharPitch();
        }
        printDotString(InnerPackageObject.MPN, row, rightCol1, 1, 2, "WL");
    }
    else{
        printDotString("MPN:", row+captainFloatForBig30, rightCol, 1, 1 ,captainFont);
        setCharPitch(4);
        printDotString(InnerPackageObject.MPN, row, rightCol1, 1, 2, "WB");
    }
    row += rowstep+spRow;
    setCharPitch();
    printDotString("Maker:", row+cpatainFloat, rightCol, 1, 1 ,captainFont);
    printDotString(InnerPackageObject.Maker, row, rightCol1, 1, 1, "WB");
    row += rowstep;

    row+=25;
    printDotString("QTY:", row+cpatainFloatForMid, rightCol, 1, 1 ,captainFont);
    printDotString(InnerPackageObject.Quantity+ " " + InnerPackageObject.UOM, row, rightCol1, 1, 1, "WL");

    if (InnerPackageObject.Quantity != "") {
        var qtyStr = "";
        var qtyInbarcode = "";
        var tempQuantityStr = InnerPackageObject.Quantity.replace(/,/g,"");
        var qtyIndex = tempQuantityStr.indexOf(".");
        if (qtyIndex < 0) {
            if (InnerPackageObject.qtyBarcode != "" && InnerPackageObject.qtyBarcode != null) {
                qtyInbarcode = InnerPackageObject.qtyBarcode + tempQuantityStr;
                qtyStr = '(' + InnerPackageObject.qtyBarcode + ')' + tempQuantityStr;
            } else {
                qtyInbarcode = tempQuantityStr;
                qtyStr = tempQuantityStr;
            }
        } else {
            if (InnerPackageObject.qtyBarcode != "" && InnerPackageObject.qtyBarcode != null) {
                qtyInbarcode = InnerPackageObject.qtyBarcode + tempQuantityStr.replace(/\./g, '');
                qtyStr = '(' + InnerPackageObject.qtyBarcode + ')' + tempQuantityStr.replace(/\./g, '');
            } else {
                qtyInbarcode = tempQuantityStr;
                qtyStr = tempQuantityStr;
            }
        }
        var qtyBarcodeCol=createBarcodeRightCoordinate(qtyInbarcode)+10+parseInt(InnerPackageObject.offset, 10);
        printBarcodeOnly(qtyInbarcode,row-20, qtyBarcodeCol, "02", "80", "G");
        printDotString(qtyStr, row+80, createBarcodeRightCoordinate(qtyStr)+65+parseInt(InnerPackageObject.offset, 10), 1, 1 ,"WB");
    }
    //if (qtyStr != "") {
    //    var qtyBarcodeCol=createBarcodeRightCoordinate(qtyStr)+50+parseInt(InnerPackageObject.offset, 10);
    //    printBarcodeOnly(qtyStr,row-20, qtyBarcodeCol, "02", "80", "G");
    //    printDotString(qtyStr, row+88, createBarcodeRightCoordinate(qtyStr)+115+parseInt(InnerPackageObject.offset, 10), 1, 1 ,"WB");
    //}
    row += rowstep+spRow-25;

    printDotString("Receive Date:", row+cpatainFloat, rightCol, 1, 1 ,captainFont);
    printDotString(InnerPackageObject.ReceiveDate, row, rightCol2, 1, 1 ,"WB");
    row += rowstep;

    printDotString("Date Code:", row+cpatainFloat, rightCol, 1, 1, captainFont);
    printDotString(InnerPackageObject.DateCode, row, rightCol2, 1, 1, "WB");
    row += rowstep;

    printDotString("LOT No:", row+cpatainFloat, rightCol, 1, 1, captainFont);
    printDotString(InnerPackageObject.LotNo, row, rightCol2, 1, 1, "WB");
    row += rowstep;

    if(InnerPackageObject.ExpiryDate!=""){
        printDotString("Expiry Date:", row+cpatainFloat, rightCol, 1, 1, captainFont);
        printDotString(InnerPackageObject.ExpiryDate, row, rightCol2, 1, 1 ,"WB");
    }

    row=750;
    if (InnerPackageObject.CPN!="") {
        var cpnStr = InnerPackageObject.CPN;
        var cpnStrInBarcode=InnerPackageObject.CPN;
        if (InnerPackageObject.cpnBarcode != "") {
            cpnStrInBarcode = InnerPackageObject.cpnBarcode + InnerPackageObject.CPN;
            cpnStr='('+InnerPackageObject.cpnBarcode+')'+InnerPackageObject.CPN
        }
        printBarcodeOnly(cpnStrInBarcode, row, createBarcodeRightCoordinate(cpnStrInBarcode)+8+parseInt(InnerPackageObject.offset, 10), "02", "80", "G");
        printDotString(cpnStr, row+88, createBarcodeRightCoordinate(cpnStr)+65+parseInt(InnerPackageObject.offset, 10), 1, 1 ,"WB");
    }
}

