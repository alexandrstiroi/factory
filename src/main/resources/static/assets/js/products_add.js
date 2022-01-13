/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var product = new Object();
product.modules = [];
var moduleTypeCount = 0;

var productModal = document.getElementById("productModal");
var moduleModal = document.getElementById("moduleModal");
var moduleTypeModal = document.getElementById("moduleTypeModal");

// Get the button that opens the modal
var productBtn = document.getElementById("productBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
productBtn.onclick = function() {
  productModal.style.display = "block";
};



// When the user clicks on <span> (x), close the modal
span.onclick = function() {
  productModal.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === productModal) {
        productModal.style.display = "none";
    }

    if (event.target === moduleModal) {
        moduleModal.style.display = "none";
    }

    if (event.target === moduleTypeModal){
        moduleTypeModal.style.display = "none";
    }
};

function showModuleForm(){
    moduleModal.getElementsByTagName("input")[0].value = "";
    moduleModal.style.display = "block";
};

function showModuleTypeForm(){
    var inputs = moduleTypeModal.getElementsByTagName("input");
    for (var input in inputs){
        inputs[input].value = "";
    }
    document.getElementById("photoPath").hidden = false;
    document.getElementById("savePhoto").hidden = false;
    document.getElementById("idPhoto").value = "";
    moduleTypeModal.style.display = "block";
}

function addProductName(){

    var table = document.getElementById("product").getElementsByTagName('tbody')[0];
    var rowProductName = table.insertRow(table.rows.length);
    var productName = document.getElementById("nameProduct");

    rowProductName.insertCell(0);
    var cellN = rowProductName.insertCell(1);
    cellN.colSpan = "5";
    cellN.innerHTML = productName.value;
    product.name = productName.value;
    var rowProductComponent = table.insertRow(table.rows.length);

    rowProductComponent.insertCell(0).innerHTML = "<button class='btn_small' onclick='showModuleForm()' ><img style='width: 16px' src='/assets/ico/add.png' /></button>";
    var cell = rowProductComponent.insertCell(1);

    cell.colSpan = "5";
    cell.innerHTML = "Новый компонент";

    productModal.style.display = "none";
    document.getElementById("addProductBtn").innerHTML = "";
}

function addModule(){
    var table = document.getElementById("product").getElementsByTagName('tbody')[0];
    var row = table.rows.length;
    var rowModuleName = table.insertRow(row - 1);
    var moduleName = document.getElementById("moduleName");
    var moduleUnique = document.getElementById("moduleUnique").options[document.getElementById("moduleUnique").selectedIndex].value;
    var moduleIsAddition = document.getElementById("moduleIsAddition").options[document.getElementById("moduleIsAddition").selectedIndex].value;
    if (moduleIsAddition == -1) {
        rowModuleName.insertCell(0).innerHTML = "<button class='btn_small' onclick='showModuleTypeForm()'><img style='width: 16px' style='width: 16px' src='/assets/ico/add.png' /></button>";
    } else {
        rowModuleName.insertCell(0).innerHTML = "";
    }
    var cell = rowModuleName.insertCell(1);
    cell.colSpan = "5";
    cell.innerHTML = moduleName.value;

    var module = new Object();
    module.moduleTypes = [];
    module.name = moduleName.value;
    module.unique = moduleUnique;
    module.isAddition = moduleIsAddition;
    product.modules.push(module);

    moduleModal.style.display = "none";
}

function addModuleType(){
    var table = document.getElementById("product").getElementsByTagName('tbody')[0];
    var row = table.rows.length;

    var rowModuleType = table.insertRow(row-1);
    var moduleType = new Object();
    moduleType.id = ++moduleTypeCount;

    moduleType.name = document.getElementById("moduleTypeName").value;
    moduleType.depth = document.getElementById("moduleTypeDepth").value;
    moduleType.width = document.getElementById("moduleTypeWidth").value;
    moduleType.height = document.getElementById("moduleTypeHeight").value;
    moduleType.idPhoto = document.getElementById("idPhoto").value;

    rowModuleType.insertCell(0).innerHTML = "";
    rowModuleType.insertCell(1).innerHTML = document.getElementById("moduleTypeName").value;
    rowModuleType.insertCell(2).innerHTML = document.getElementById("moduleTypeDepth").value;
    rowModuleType.insertCell(3).innerHTML = document.getElementById("moduleTypeWidth").value;
    rowModuleType.insertCell(4).innerHTML = document.getElementById("moduleTypeHeight").value;
    var cell = rowModuleType.insertCell(5);
    cell.style.width = "35px";
    cell.innerHTML = "<button class='btn_small' onclick='deleteRow(this,"+moduleTypeCount+" )'><img style='width: 16px' src='/assets/ico/delete.png'/></button>";



    product.modules[product.modules.length - 1].moduleTypes.push(moduleType);

    moduleTypeModal.style.display = "none";

}

function saveProductServer(){
    if(product.modules.length === 0) {
        document.getElementById("error").innerHTML = "Введите данные изделия!!!";
    } else {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/admin/product_detail", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function() {

            location.replace("/admin?fr=product")
        };

        var data = "product="+JSON.stringify(product);
        xhr.send(data);
    }

}
function save(){
    console.log(product);
    if(product.modules.length === 0) {
        document.getElementById("error").innerHTML = "Введите данные изделия!!!";
    } else {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/admin/pr_in", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onload = function() {
            location.replace("/admin?fr=product")
        };

        var data = "product="+JSON.stringify(product);
        xhr.send(data);
    }

}

function saveImage(){


    var formData = new FormData();
    var folderName = document.getElementById("product").rows[1].cells[1].innerHTML;
    var file = document.querySelector('#photoPath');
    formData.append("image",file.files[0]);
    formData.append("folderName",folderName);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/admin/product_detail/saveImage",true);
    xhr.onload = function() {
        if(xhr.status === 200){
            console.log(xhr.responseText);
            var photo = JSON.parse(xhr.responseText);
            console.log(photo);
            console.log(photo.idPhoto);
            document.getElementById("idPhoto").value = photo.idPhoto;
            document.getElementById("photoPath").hidden = true;
            document.getElementById("savePhoto").hidden = true;
        }
    };
    xhr.send(formData);
}