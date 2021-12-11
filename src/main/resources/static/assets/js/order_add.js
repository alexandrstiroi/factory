var moduleTypeModal = document.getElementById("moduleTypeModal");
var accessoryModal = document.getElementById("accessoryModal");
var consumableModal = document.getElementById("consumableModal");
var product = new Object();
var module = new Object();
var moduleTypes = new Array();
var accessories = new Array();
var consumables = new Array();

window.onclick = function(event) {
    if (event.target === moduleTypeModal){
        moduleTypeModal.style.display = "none";
    }
    if (event.target === accessoryModal){
        accessoryModal.style.display = "none";
    }
    if (event.target === consumableModal){
        consumableModal.style.display = "none";
    }
};

function getModules(){
    var productId = document.getElementById("productId").value;
    product.id = productId;
    var xhr = new XMLHttpRequest();

    xhr.open("POST", "/order/order_add/getModules", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {
        var modules = JSON.parse(xhr.responseText);
        //console.log(modules);
        var table = document.getElementById("productComposition");
        modules.forEach(function(item, index){
            var row = table.insertRow(table.rows.length);
            row.id = "module";
            if (item.moduleIsAddition == -1) {
                row.insertCell(0).innerHTML = "<button class='btn_small' onclick='getModuleTypes(this, "+ item.idModule +")'><img style='width: 16px' src='/assets/ico/add.png'/></button><input type='hidden' name='idModule' value='"+item.idModule+"'/>";
            } else if (item.moduleIsAddition == 0) {
                row.insertCell(0).innerHTML = "<button class='btn_small' onclick='getAccessories(this, "+ item.idModule +")'><img style='width: 16px' src='/assets/ico/add.png'/></button><input type='hidden' name='idModule' value='"+item.idModule+"'/>";
            } else {
                row.insertCell(0).innerHTML = "<button class='btn_small' onclick='getConsumable(this, "+ item.idModule +", " + item.moduleIsAddition + ")'><img style='width: 16px' src='/assets/ico/add.png'/></button><input type='hidden' name='idModule' value='"+item.idModule+"'/>";
            }
            var cell = row.insertCell(1);
            cell.innerHTML = item.moduleName;
            cell.colSpan = 6;
            row.insertCell(2).innerHTML = "<img style='width: 16px' src='/assets/ico/minus.png'/>";
        });

    }
    var data = "productId="+productId;
    xhr.send(data);

}

function getModuleTypes(r, idModule){
    var rowIndex = r.parentNode.parentNode.rowIndex;
    var xhr = new XMLHttpRequest();

    var table = document.getElementById("moduleTypeList");
    table.innerHTML = "";
    xhr.open("POST", "/order/order_add/getModuleTypes", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {
        var moduleTypes = JSON.parse(xhr.responseText);
        document.getElementById("moduleName").innerHTML = moduleTypes[0].idModule.moduleName;
        moduleTypes.forEach(function(item, index){
            var row = table.insertRow(table.rows.length);
            row.insertCell(0).innerHTML = "<input type='checkbox'/><input type='hidden' name='rowIndex' value='"+rowIndex+"'/><input type='hidden' name='idModule' value='"+idModule+"'/><input type='hidden' name='idModuleType' value='"+item.idModuleType+"'/>";
            row.insertCell(1).innerHTML = item.moduleTypeName;
            row.insertCell(2).innerHTML = item.depth;
            row.insertCell(3).innerHTML = item.width;
            row.insertCell(4).innerHTML = item.height;
            /*var cell = row.insertCell(4)
            cell.innerHTML = item.moduleTypeName;*/
        });
        document.getElementById("addModuleType").setAttribute("onclick","addModuleTypes("+rowIndex+")");
        moduleTypeModal.style.display = "block";
    }
    var data = "idModule="+idModule;
    xhr.send(data);
}

function addModuleTypes(rowIndex){
    var table = document.getElementById("moduleTypeList");
    var ifAdd = false
    for (let row of table.rows) {
        if (row.cells[0].childNodes[0].checked){
            var moduleType = new Object();
            moduleType.id = row.cells[0].childNodes[3].value;
            moduleType.idModule = row.cells[0].childNodes[2].value;
            moduleTypes.push(moduleType);
            ifAdd = true;
        }
    };
    if (ifAdd) {
        document.getElementById("productComposition").rows[rowIndex].cells[2].innerHTML = "<img style='width: 16px' src='/assets/ico/ok.png'/>";
    }
    moduleTypeModal.style.display = "none";
}

function getAccessories(r, idModule){
    var rowIndex = r.parentNode.parentNode.rowIndex;
    var xhr = new XMLHttpRequest();

    var table = document.getElementById("accessoryList");
    table.innerHTML = "";
    xhr.open("POST", "/order/order_add/getAccessories", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onload = function() {
        var accessories = JSON.parse(xhr.responseText);
        document.getElementById("accessoryName").innerHTML = document.getElementById("productComposition").rows[rowIndex].cells[1].innerHTML;
        accessories.forEach(function(item, index){
            var row = table.insertRow(table.rows.length);
            row.insertCell(0).innerHTML = "<input type='checkbox'/><input type='hidden' name='rowIndex' value='"+rowIndex+"'/><input type='hidden' name='idModule' value='"+idModule+"'/><input type='hidden' name='idAccessory' value='"+item.idAccessory+"'/>";
            row.insertCell(1).innerHTML = item.accessoryName;
        });
        document.getElementById("addAccessory").setAttribute("onclick","addAccessories("+rowIndex+")");
        accessoryModal.style.display = "block";
    };
    var data = "idModule="+idModule;
    xhr.send(data);
}

function addAccessories(rowIndex){
    var table = document.getElementById("accessoryList");
    var ifAdd = false
    for (let row of table.rows) {
        if (row.cells[0].childNodes[0].checked){
            var accessory = new Object();
            accessory.id = row.cells[0].childNodes[3].value;
            accessory.idModule = row.cells[0].childNodes[2].value;
            accessories.push(accessory);
            ifAdd = true;
        }
    };
    if (ifAdd) {
        document.getElementById("productComposition").rows[rowIndex].cells[2].innerHTML = "<img style='width: 16px' src='/assets/ico/ok.png'/>";
    }
    accessoryModal.style.display = "none";
}

function getConsumable(r, idModule,moduleIsAddition){
    var rowIndex = r.parentNode.parentNode.rowIndex;
    var xhr = new XMLHttpRequest();

    var table = document.getElementById("consumableList");
    table.innerHTML = "";
    xhr.open("POST", "/order/order_add/getConsumable", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function() {
        var consumables = JSON.parse(xhr.responseText);
        document.getElementById("consumableName").innerHTML = document.getElementById("productComposition").rows[rowIndex].cells[1].innerHTML;
        consumables.forEach(function(item, index){
            var row = table.insertRow(table.rows.length);
            row.insertCell(0).innerHTML = "<input type='checkbox'/><input type='hidden' name='rowIndex' value='"+rowIndex+"'/><input type='hidden' name='idModule' value='"+idModule+"'/><input type='hidden' name='idConsumable' value='"+item.idConsumable+"'/>";
            row.insertCell(1).innerHTML = item.consumableName;
            row.insertCell(2).innerHTML = item.consumableColor;
            row.insertCell(3).innerHTML = item.consumableCat;
        });
        document.getElementById("addConsumable").setAttribute("onclick","addConsumable("+rowIndex+")");
        consumableModal.style.display = "block";
    };
    var data="idModule="+idModule+"&moduleIsAddition="+moduleIsAddition;
    xhr.send(data);
}

function addConsumable(rowIndex){
    var table = document.getElementById("consumableList");
    var ifAdd = false
    for (let row of table.rows) {
        if (row.cells[0].childNodes[0].checked){
            var consumable = new Object();
            consumable.id = row.cells[0].childNodes[3].value;
            consumable.idModule = row.cells[0].childNodes[2].value;
            consumables.push(consumable);
            ifAdd = true;
        }
    };
    if (ifAdd) {
        document.getElementById("productComposition").rows[rowIndex].cells[2].innerHTML = "<img style='width: 16px' src='/assets/ico/ok.png'/>";
     };
    consumableModal.style.display = "none";
}

function orderSaveNew(){
    var order = new Object();
    order.number_shop = document.getElementById("numberShop").value;
    order.number_factory = document.getElementById("numberFactory").value;
    order.date_registration = document.getElementById("dateRegistration").value;
    order.date_production = document.getElementById("dateProduction").value;
    order.supplement = document.getElementById("supplement").value;
    order.id_shop = document.getElementById("shopId").value;
    var client = new Object();
    client.client_name = document.getElementById("clientName").value;
    client.client_address = document.getElementById("clientAddress").value;
    client.client_phone = document.getElementById("clientPhone").value;

    product.order = order;
    product.client = client;

    product.moduleTypeList = moduleTypes;
    product.accessoryList = accessories;
    product.consumableList = consumables;

    console.log(product);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/order/order_save", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onload = function() {
        location.replace("/order/order_list");
    };
    var data="order="+JSON.stringify(product);
    xhr.send(data);
}