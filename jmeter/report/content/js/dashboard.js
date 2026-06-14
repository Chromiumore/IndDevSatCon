/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 91.83540169823645, "KoPercent": 8.164598301763553};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.8011103853690399, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.7583333333333333, 500, 1500, "DeleteConst-60"], "isController": false}, {"data": [0.845360824742268, 500, 1500, "UpdateSat-38"], "isController": false}, {"data": [0.8378378378378378, 500, 1500, "CreateConst-39"], "isController": false}, {"data": [0.7583333333333333, 500, 1500, "DeleteConst"], "isController": true}, {"data": [0.813953488372093, 500, 1500, "RemoveSatFromConst-57"], "isController": false}, {"data": [0.777511961722488, 500, 1500, "CreateSat-32"], "isController": false}, {"data": [0.7093023255813954, 500, 1500, "GetConst-49"], "isController": false}, {"data": [0.813953488372093, 500, 1500, "RemoveSatFromConst"], "isController": true}, {"data": [0.8693467336683417, 500, 1500, "GetSat-36"], "isController": false}, {"data": [0.7093023255813954, 500, 1500, "GetConst"], "isController": true}, {"data": [0.7534246575342466, 500, 1500, "GetOverview"], "isController": true}, {"data": [0.777511961722488, 500, 1500, "CreateSat"], "isController": true}, {"data": [0.8378378378378378, 500, 1500, "CreateConst"], "isController": true}, {"data": [0.7534246575342466, 500, 1500, "GetOverview-58"], "isController": false}, {"data": [0.8135593220338984, 500, 1500, "AddSatToConst-48"], "isController": false}, {"data": [0.8693467336683417, 500, 1500, "GetSat"], "isController": true}, {"data": [0.8135593220338984, 500, 1500, "AddSatToConst"], "isController": true}, {"data": [0.845360824742268, 500, 1500, "UpdateSat"], "isController": true}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1531, 125, 8.164598301763553, 18241.18680600914, 5, 516112, 49.0, 40933.8, 218097.8, 218496.88, 2.618199479096302, 1.9399867737959444, 1.3612635244068862], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["DeleteConst-60", 120, 18, 15.0, 26448.74166666667, 8, 516112, 62.0, 41304.7, 218167.0, 453598.77999999764, 0.20761497111556715, 0.025698435145408333, 0.11130919838129527], "isController": false}, {"data": ["UpdateSat-38", 194, 15, 7.731958762886598, 12178.577319587632, 9, 218412, 52.5, 7979.5, 85311.75, 218265.7, 0.6803651526788501, 0.34215528457850675, 0.4052956475918931], "isController": false}, {"data": ["CreateConst-39", 185, 0, 0.0, 12724.778378378378, 7, 218546, 36.0, 40812.8, 41072.2, 218343.04, 0.6523709275304058, 0.16436689385043426, 0.3605878368966891], "isController": false}, {"data": ["DeleteConst", 120, 18, 15.0, 26448.74166666667, 8, 516112, 62.0, 41304.7, 218167.0, 453598.77999999764, 0.20751587928801302, 0.025686169628667627, 0.11125607200109291], "isController": true}, {"data": ["RemoveSatFromConst-57", 129, 0, 0.0, 18726.24031007752, 9, 218423, 62.0, 40706.0, 218232.0, 218420.9, 0.45898326312194015, 0.04571903597503701, 0.2801411518078248], "isController": false}, {"data": ["CreateSat-32", 209, 0, 0.0, 12568.741626794259, 9, 218549, 59.0, 7923.0, 129622.0, 218476.1, 0.7271310331870954, 0.37776729458548314, 0.42747351755725727], "isController": false}, {"data": ["GetConst-49", 172, 38, 22.093023255813954, 40635.302325581404, 6, 515729, 32.5, 218149.6, 218400.45, 515323.85, 0.29664941385870175, 0.15357630163553862, 0.1251489714716398], "isController": false}, {"data": ["RemoveSatFromConst", 129, 0, 0.0, 18726.24031007752, 9, 218423, 62.0, 40706.0, 218232.0, 218420.9, 0.4587302772651141, 0.04569383621195472, 0.2799867414948206], "isController": true}, {"data": ["GetSat-36", 199, 11, 5.527638190954774, 7508.316582914572, 5, 218329, 23.0, 7808.0, 41039.0, 218268.0, 0.6969031584771791, 0.35198096473810103, 0.2919643115104588], "isController": false}, {"data": ["GetConst", 172, 38, 22.093023255813954, 40635.302325581404, 6, 515729, 32.5, 218149.6, 218400.45, 515323.85, 0.2967374408465988, 0.15362187347426062, 0.1251861078571589], "isController": true}, {"data": ["GetOverview", 146, 18, 12.32876712328767, 30819.123287671227, 15, 515577, 91.5, 218085.6, 218385.7, 375973.8400000003, 0.2523506637168142, 1.1060596567339602, 0.1079390534257467], "isController": true}, {"data": ["CreateSat", 209, 0, 0.0, 12568.741626794259, 9, 218549, 59.0, 7923.0, 129622.0, 218476.1, 0.726201272415818, 0.37728425480978045, 0.4269269199163305], "isController": true}, {"data": ["CreateConst", 185, 0, 0.0, 12724.778378378378, 7, 218546, 36.0, 40812.8, 41072.2, 218343.04, 0.6530733277792683, 0.16454386578813596, 0.360976077659244], "isController": true}, {"data": ["GetOverview-58", 146, 18, 12.32876712328767, 30819.123287671227, 15, 515577, 91.5, 218085.6, 218385.7, 375973.8400000003, 0.25204832422391554, 1.1047344947121642, 0.10780973243171389], "isController": false}, {"data": ["AddSatToConst-48", 177, 25, 14.124293785310735, 11362.197740112993, 10, 218777, 50.0, 40810.6, 41197.5, 218377.64, 0.6247154368243278, 0.08639952912515132, 0.3312700021441504], "isController": false}, {"data": ["GetSat", 199, 11, 5.527638190954774, 7508.316582914572, 5, 218329, 23.0, 7808.0, 41039.0, 218268.0, 0.6975456208857077, 0.35230544961897886, 0.2922334681249693], "isController": true}, {"data": ["AddSatToConst", 177, 25, 14.124293785310735, 11362.197740112993, 10, 218777, 50.0, 40810.6, 41197.5, 218377.64, 0.625384240317144, 0.08649202613010819, 0.33162465087129805], "isController": true}, {"data": ["UpdateSat", 194, 15, 7.731958762886598, 12178.577319587632, 9, 218412, 52.5, 7979.5, 85311.75, 218265.7, 0.6811439004265927, 0.34254691641943014, 0.4057595500588101], "isController": true}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["500", 125, 100.0, 8.164598301763553], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1531, 125, "500", 125, "", "", "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["DeleteConst-60", 120, 18, "500", 18, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["UpdateSat-38", 194, 15, "500", 15, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GetConst-49", 172, 38, "500", 38, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["GetSat-36", 199, 11, "500", 11, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["GetOverview-58", 146, 18, "500", 18, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["AddSatToConst-48", 177, 25, "500", 25, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
