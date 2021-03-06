function shopAddress(){
	var shopId = document.getElementById("shopId").value;
    console.log('shopId ' + shopId);
	var xhr = new XMLHttpRequest();

    xhr.open("POST", "/order/order_add/getShop", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {

        var shop = JSON.parse(xhr.responseText);

        document.getElementById("shopAddress").value = shop.shopAddress;
    };

    var data = "shopId="+shopId;
    xhr.send(data);
}


function getComponents(){
    var productId = document.getElementById("productId").value;

    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/order/order_add/getComponents", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {
        var products = JSON.parse(xhr.responseText);
        //console.log(products);
        var table = document.getElementById("productComposition");
        products.forEach(function(item, index){
            var row = table.insertRow(table.rows.length);
            row.id = "component";
            row.insertCell(0).innerHTML = "<button class='btn_small' onclick='getProperties(this, "+ item.id +")'><img style='width: 16px' src='/assets/ico/add-2.png'/></button><input type='hidden' name='componentID' value='"+item.id+"'/>";
            var cell = row.insertCell(1);
            cell.innerHTML = item.name;
            cell.colSpan = 7;

        });
    }
    var data = "productId="+productId;
    xhr.send(data);

}

function getProperties(r, componentId){
    var rowIndex = r.parentNode.parentNode.rowIndex;
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/order/order_add/getProperties", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {
        var properties = JSON.parse(xhr.responseText);
        var table = document.getElementById("productComposition");

        properties.forEach(function(item, index){
            var row = table.insertRow(rowIndex+1);
            row.id = 'properties';
            row.insertCell(0).innerHTML = "<input type='hidden' name='componentId' value='"+componentId+"'/><input type='hidden' name='propertiesId' value='"+item.id+"'/><input type='hidden' name='propertiesType' value='"+item.type+"'/>";
            var cell1 = row.insertCell(1);
            cell1.style.width="5%";
            var cell2 = row.insertCell(2);

            var cell3 = row.insertCell(3);
            cell3.style.width="5%";
            var cell4 = row.insertCell(4);
            cell4.style.width="5%";
            var cell5 = row.insertCell(5);
            cell5.style.width="5%";
            var cell6 = row.insertCell(6);
            cell6.style.width="5%";
            var cell7 = row.insertCell(7);


            if(item.type === 1) {

                cell1.innerHTML = "<input type='checkbox'/>";

                if(item.name != null){
                    cell2.innerHTML = item.name;
                }
                if(item.lenght != null){
                    cell3.innerHTML = item.lenght;
                }
                if(item.width != null){
                    cell4.innerHTML = item.width;
                }

            }

            if(item.type === 2) {
                cell1.innerHTML = "<input type='checkbox'/>";
                if(item.name != null){
                    cell2.innerHTML += item.name + " ";
                }

                if(item.lenght != null){

                    cell3.innerHTML += item.lenght + " ";
                }

                if(item.width != null){
                    cell4.innerHTML += item.width + " ";
                }
                cell7.innerHTML = "<input type='text' name='propertiesTypeName' class='inp' placeholder='??????'/>"
            }

            if(item.type === 3) {
                cell1.innerHTML = "<input type='checkbox'/>";
                if(item.name != null){
                    cell2.innerHTML += item.name + " ";
                }

                if(item.lenght != null){

                    cell3.innerHTML += item.lenght + " ";
                }

                if(item.width != null){
                    cell4.innerHTML += item.width + " ";
                }
                cell6.innerHTML = "<input type='text' name='propertiesCount' class='inp' placeholder='????????????????????'/>"
                cell7.innerHTML = "<input type='text' name='propertiesTypeName' class='inp' placeholder='??????'/>"
            }

            if(item.type === 4) {
                cell1.innerHTML = "<input type='checkbox'/>";
                if(item.name != null){
                    cell2.innerHTML = item.name;
                }
                cell3.innerHTML = "<input type='text' class='inp' />";
                cell4.innerHTML = "<input type='text' class='inp' />";
                cell6.innerHTML = "<input type='text' name='propertiesCount' class='inp' placeholder='????????????????????'/>"
                cell7.innerHTML = "<input type='text' name='propertiesTypeName' class='inp' placeholder='??????'/>"
            }

            if(item.type === 5){
                cell1.innerHTML = "<input type='radio' name='"+item.idComponent.id+"'/>";

                if(item.name != null){
                    cell2.innerHTML = item.name;
                }
                if(item.lenght != null){
                    cell3.innerHTML = item.lenght;
                }
                if(item.width != null){
                    cell4.innerHTML = item.width;
                }
            }
            if(item.type === 6){
                cell1.innerHTML = "<input type='radio' name='"+item.idComponent.id+"'/>";
                if(item.name != null){
                    cell2.innerHTML = item.name;
                }
                if(item.lenght != null){
                    cell3.innerHTML = item.lenght;
                }
                if(item.width != null){
                    cell4.innerHTML = item.width;
                }
                cell7.innerHTML = "<input type='text' name='propertiesTypeName' class='inp' placeholder='??????'/>"
            }
            if(item.type === 7){
                cell1.innerHTML = "<input type='radio' name='"+item.idComponent.id+"'/>";
                if(item.name != null){
                    cell2.innerHTML = item.name;
                }
                if(item.lenght != null){
                    cell3.innerHTML = item.lenght;
                }
                if(item.width != null){
                    cell4.innerHTML = item.width;
                }
                cell6.innerHTML = "<input type='text' name='propertiesCount' class='inp' placeholder='????????????????????'/>"
                cell7.innerHTML = "<input type='text' name='propertiesTypeName' class='inp' placeholder='??????'/>"
            }

            if(item.type === 8) {
                cell1.innerHTML = "<input type='radio' name='"+item.idComponent.id+"'/>";
                if(item.name != null){
                    cell2.innerHTML = item.name;
                }
                cell3.innerHTML = "<input type='text' class='inp' />";
                cell4.innerHTML = "<input type='text' class='inp' />";
                cell6.innerHTML = "<input type='text' name='propertiesCount' class='inp' placeholder='????????????????????'/>"
                cell7.innerHTML = "<input type='text' name='propertiesTypeName' class='inp' placeholder='??????'/>"
            }

            if(item.type === 9) {
                cell1.innerHTML = "<input type='checkbox'/>";
                if(item.name != null){
                    cell2.innerHTML += item.name + " ";
                }

                if(item.lenght != null){

                    cell3.innerHTML += item.lenght + " ";
                }

                if(item.width != null){
                    cell4.innerHTML += item.width + " ";
                }
                cell6.innerHTML = "<input type='text' name='propertiesCount' class='inp' placeholder='????????????????????'/>"

            }
            //row.insertCell(1).innerHTML = item.name;
        });


    }
    var data = "componentId="+componentId;
    xhr.send(data);
}

function orderSave(){

    var order = new Object();

    order.numberShop = document.getElementById("numberShop").value;
    order.numberFactory = document.getElementById("numberFactory").value;
    order.dateRegistration = document.getElementById("dateRegistration").value;
    order.dateProduction = document.getElementById("dateProduction").value;
    order.supplement = document.getElementById("supplement").value;
    order.idShop = document.getElementById("shopId").value;
    order.clientName = document.getElementById("clientName").value;
    order.clientPhone = document.getElementById("clientPhone").value;
    order.clientAddress = document.getElementById("clientAddress").value;

    if (checkOrderEmpty()) {
        return;
    };

    var table = document.querySelector('#productComposition');

    for (let row of table.rows) {
        param = row.id;
        if (param === 'properties'){
            if (row.cells[1].childNodes[0].checked){
                var propertieType = row.cells[0].childNodes[2].value;
                var propertie = new Object();
                propertie.component_id = row.cells[0].childNodes[0].value;
                propertie.propertiesType = propertieType;
                switch(propertiesType){
                    case 1:

                        break;
                }
            };
        };
    };
    console.log("parseTable stop");
}


function checkOrderEmpty(){
    var table = document.querySelector('#productComposition');
    var result = false;

    for (let row of table.rows) {

        param = row.id;
        if (param === 'properties'){
            if (row.cells[1].childNodes[0].checked){
                var propertieType = row.cells[0].childNodes[2].value;
                if(propertieType == 2 || propertieType == 6){
                    if(row.cells[7].childNodes[0].value == ''){
                        row.cells[7].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                };

                if(propertieType == 3 || propertieType == 7){
                    if(row.cells[7].childNodes[0].value == ''){
                        row.cells[7].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                    if(row.cells[6].childNodes[0].value == ''){
                        row.cells[6].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                };

                if(propertieType == 4 || propertieType == 8){
                    if(row.cells[7].childNodes[0].value == ''){
                        row.cells[7].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                    if(row.cells[6].childNodes[0].value == ''){
                        row.cells[6].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                    if(row.cells[4].childNodes[0].value == ''){
                        row.cells[4].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                    if(row.cells[3].childNodes[0].value == ''){
                        row.cells[3].childNodes[0].style.border = '1px solid red';
                        result = true;
                    };
                }
                if(propertieType == 9){
                    if(row.cells[6].childNodes[0].value == ''){
                        row.cells[6].childNodes[0].style.border = '1px solid red';
                        result = true;
                    }
                }
            }
        }
    }

    return result;
}

function getOrderPdf(idOrder){

}