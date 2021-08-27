var product = new Object();
product.components = [];
var propertieCount = 0;


// Get the modal
var productModal = document.getElementById("productModal");
var componentModal = document.getElementById("componentModal");
var propertieModal = document.getElementById("propertieModal");


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

    if (event.target === componentModal) {
        componentModal.style.display = "none";
    }

    if (event.target === propertieModal){
        propertieModal.style.display = "none";
    }
};

function showComponentForm(){
    componentModal.getElementsByTagName("input")[0].value = "";
    componentModal.style.display = "block";
}

function showPropertieForm(){
    var inputs = propertieModal.getElementsByTagName("input");
    for (var input in inputs){
        inputs[input].value = "";
    }
    propertieModal.style.display = "block";
}

function addProductName(){
    
    var table = document.getElementById("product").getElementsByTagName('tbody')[0];
    var rowProductName = table.insertRow(table.rows.length);
    var productName = document.getElementById("nameProduct");
    
    rowProductName.insertCell(0);
    var cellN = rowProductName.insertCell(1);
    cellN.colSpan = "7";
    cellN.innerHTML = productName.value;
    product.name = productName.value;
    var rowProductComponent = table.insertRow(table.rows.length);
    
    rowProductComponent.insertCell(0).innerHTML = "<button class='btn_small' onclick='showComponentForm()' ><img style='width: 16px' src='/assets/ico/add.png' /></button>";
    var cell = rowProductComponent.insertCell(1);
    
    cell.colSpan = "7";        
    cell.innerHTML = "Новый компонент";
    
    productModal.style.display = "none";
    document.getElementById("addProductBtn").innerHTML = "";    
}

function addComponentName(){
    var table = document.getElementById("product").getElementsByTagName('tbody')[0];
    var row = table.rows.length;
    var rowComponentName = table.insertRow(row - 1);
    var nameComponent = document.getElementById("nameComponent");
    
    rowComponentName.insertCell(0).innerHTML = "<button class='btn_small' onclick='showPropertieForm()'><img style='width: 16px' style='width: 16px' src='/assets/ico/add.png' /></button>";
    var cell = rowComponentName.insertCell(1);
    cell.colSpan = "7";
    cell.innerHTML = nameComponent.value;
    
    var component = new Object();
    component.properties = [];
    component.name = nameComponent.value;
    product.components.push(component);
    
    componentModal.style.display = "none";
}   

function addPropertieName(){
    var table = document.getElementById("product").getElementsByTagName('tbody')[0];
    var row = table.rows.length;
    
    var rowPropertieName = table.insertRow(row-1);
    var propertie = new Object();
    propertie.id = ++propertieCount;
    
    propertie.name = document.getElementById("namepropertie").value;
    propertie.lenght = document.getElementById("lenghtPropertie").value;
    propertie.width = document.getElementById("widthPropertie").value;
    propertie.height = document.getElementById("heightPropertie").value;
    propertie.count = document.getElementById("countPropertie").value;
    propertie.type = document.getElementById("typePropertie").options[document.getElementById("typePropertie").selectedIndex].value;
    
    
    rowPropertieName.insertCell(0).innerHTML = "";
    rowPropertieName.insertCell(1).innerHTML = document.getElementById("namepropertie").value;
    rowPropertieName.insertCell(2).innerHTML = document.getElementById("lenghtPropertie").value;
    rowPropertieName.insertCell(3).innerHTML = document.getElementById("widthPropertie").value;
    rowPropertieName.insertCell(4).innerHTML = document.getElementById("heightPropertie").value;
    rowPropertieName.insertCell(5).innerHTML = document.getElementById("countPropertie").value;
    rowPropertieName.insertCell(6).innerHTML = document.getElementById("typePropertie").options[document.getElementById("typePropertie").selectedIndex].text;
    var cell = rowPropertieName.insertCell(7);
    cell.style.width = "35px";
    cell.innerHTML = "<button class='btn_small' onclick='deleteRow(this,"+propertieCount+" )'><img style='width: 16px' src='/assets/ico/delete.png'/></button>";
    
    
    
    product.components[product.components.length - 1].properties.push(propertie);
    
    propertieModal.style.display = "none";
     
}

function addProduct(){
    
    var table = document.getElementById("product");
    
    var row = table.insertRow(table.rows.length);
    
    var cell0 = row.insertCell(0);
    var cell1 = row.insertCell(1);
    var cell2 = row.insertCell(2);
    var cell3 = row.insertCell(3);
    var cell4 = row.insertCell(4);
    var cell5 = row.insertCell(5);
    
    cell0.innerHTML = "text1";
    cell1.innerHTML = "text2";
    
}

function deleteRow(r, propId){
    var i = r.parentNode.parentNode.rowIndex;
    document.getElementById("product").deleteRow(i);
    
    product.components.forEach(function(item, index){
				
        var ind = item.properties.findIndex(x => x.id === propId);
        
        if (ind !== -1) {
                        
            product.components[index].properties.splice(ind,1);

        }

    });
    console.log(product);
    console.log(JSON.stringify(product));
}

function saveProductServer(){
    if(product.components.length === 0) {
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