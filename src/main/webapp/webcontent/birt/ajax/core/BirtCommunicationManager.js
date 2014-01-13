/******************************************************************************
 *    Copyright (c) 2004 Actuate Corporation and others.
 *    All rights reserved. This program and the accompanying materials
 *    are made available under the terms of the Eclipse Public License v1.0
 *    which accompanies this distribution, and is available at
 *        http://www.eclipse.org/legal/epl-v10.html
 *
 *    Contributors:
 *        Actuate Corporation - Initial implementation.
 *****************************************************************************/

/**
 *    BirtCommunicationManager
 *    ...
 */
BirtCommunicationManager = Class.create();

BirtCommunicationManager.prototype =
{
    __active: false,
    __before_req: null,
    __after_req: null,

    /**
     *    Initialization routine required by "ProtoType" lib.
     *
     *    @return, void
     */
    initialize: function () {
    },

    /**
     *    Make xml http request.
     *
     *    return, void
     */
    connect: function () {
        var xmlDoc = birtSoapRequest.__xml_document;

        if (xmlDoc) {
            debug(birtSoapRequest.prettyPrintXML(xmlDoc), true);
            if (BrowserUtility.isSafari || BrowserUtility.isFirefox3) {
                // WORKAROUND: sending the XML DOM doesn't replace the
                // ampersands properly but the XMLSerializer does.
                xmlDoc = (new XMLSerializer()).serializeToString(xmlDoc);
            }
        }

        if (!birtSoapRequest.getURL()) return;

        //activate delay message manager;
        this.__active = true;
        __start = new Date();
        birtProgressBar.__start();


        __before_req = new Date();

        //workaround for Bugzilla Bug 144598. Add request header "Connection" as "keep-alive"
        var myAjax = new Ajax.Request(birtSoapRequest.getURL(), { method: 'post', postBody: xmlDoc,
            contentType: 'text/xml; charset=UTF-8',
            onSuccess: this.responseHandler, onFailure: this.invalidResponseHandler,
            requestHeaders: ['SOAPAction', '""', 'request-type', 'SOAP', 'Connection', 'keep-alive' ] });
        birtSoapRequest.reset();
    },

    /**
     *    Callback function triggered when reponse is ready, status is 200.
     *
     *    @request, httpXmlRequest instance
     *    @return, void
     */
    responseHandler: function (request) {

        if (isDebugging()) {
            debug(request.responseText, true);
            debug(birtSoapRequest.prettyPrintXML(request.responseXML.documentElement), true);
        }

        if (request.responseXML && request.responseXML.documentElement) {
            birtSoapResponse.process(request.responseXML.documentElement);
        }

        birtCommunicationManager.postProcess();
        __after_req = new Date();
        used_time = (__after_req.getTime() - __before_req.getTime()) / 1000;
        if (document.getElementById("rpt_load_time") && document.getElementById("load_time_part")) {
            document.getElementById("rpt_load_time").innerHTML = used_time;
            document.getElementById("load_time_part").style = "display:block;";
        }
        //todo handle responseText
        birtCommunicationManager.report_style_render();
        birtCommunicationManager.addReportGrid();
        __end = new Date();
        parent.__common.reportRender();
    },
    addReportGrid: function () {
        var docDocument = document.getElementById("Document");
        var divChild = docDocument.getElementsByTagName("div")[0];
        var tableChild = divChild.getElementsByTagName("table")[1];
        var trList = tableChild.getElementsByTagName("tr");
        for (var i = 1; i < trList.length; i++) {
            var rowBackGroundColor = trList[i].style.backgroundColor;
//			trList[i].addEventListener("mouseover", function () {this.style.backgroundColor = "#F0F0F0";}, false);
//			trList[i].addEventListener("mouseout", function ()  {this.style.backgroundColor = rowBackGroundColor;}, false);
        }
    },
    /**
     * 改变报表样式
     */
    report_style_render: function () {
        var docDocument = document.getElementById("Document");
        var divChild = docDocument.getElementsByTagName("div")[0];
        var tableChild = divChild.getElementsByTagName("table")[1];
        var trList = tableChild.getElementsByTagName("tr");
        var rowCount = 0;
        var rowHeight = 0;

        var tableLayout = document.getElementById("layout");
//		tableLayout.style.border = "1px solid #C1DAD7";
        tableChild.style.fontFamily = "微软雅黑";
        for (var i = 0; i < trList.length; i++) {
            if (trList[i].style.display != "none") {
                rowHeight += trList[i].offsetHeight + 1;
                rowCount++;
            }

            if (i > 0) {
                trList[i].style.fontSize = "9pt";
            }
        }

        var tableLayout = document.getElementById("layout");
        var rowHeight = tableLayout.offsetHeight + 20;

        if (document.getElementById("imp_notes")) {
            var imp_notes = document.getElementById("imp_notes");
            rowHeight = rowHeight + imp_notes.offsetHeight;
        }
        parent.__common.changeIframeHeight(rowHeight);

        document.getElementById("__BIRT_ROOT").title = "";
    },

    /**
     *    Callback function triggered when reponse is ready status is not 200.
     *    Process any http (non-200) errors. Note this is not exception from
     *    server side.
     *
     *    @request, httpXmlRequest instance
     *    @return, void
     */
    invalidResponseHandler: function (request) {
        debug("invalid response");

        if (request.responseXML && request.responseXML.documentElement) {
            birtSoapResponse.process(request.responseXML.documentElement);
        }

        birtCommunicationManager.postProcess();
        birtCommunicationManager.changeIframeHeight();
    },

    /**
     *    Post process after finish processing the response.
     *
     *    @return, void
     */
    postProcess: function () {
        //deactivate delay message manager
        birtProgressBar.__stop();
        this.__active = false;
    }
}

var birtCommunicationManager = new BirtCommunicationManager();