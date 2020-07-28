var totalInAmount = 0.00;

getItems();

$('#addDollar, #addQuarter, #addDime, #addNickel').click(function () {
    totalInAmount = totalInAmount + parseFloat($(this).val());
    $('#totalInBoxValue').empty();
    $('#totalInBoxValue').append("$" + totalInAmount.toFixed(2));
    changeAvailable();
    $('#changeReturnOutput').empty();
});

function setItemIdNum(itemId) {
    $('#itemNumSelected').text(itemId);
    changeAvailable();
    $('#changeReturnOutput').empty();
    $('#messageOutputLine').empty();
}

function changeAvailable() {
    if (totalInAmount <= 0) {
        $('#changeReturnButton').hide();
    } else {
        $('#changeReturnButton').show();
    }
}

$('#changeReturnButton').click(function returnChange() {

    changeToReturn = totalInAmount * 100;

    var pennies = 0;
    var nickels = 0;
    var dimes = 0;
    var quarters = 0;

    quarters = Math.floor(changeToReturn / 25);
    changeToReturn = changeToReturn - (quarters * 25);
    dimes = Math.floor(changeToReturn / 10);
    changeToReturn = changeToReturn - (dimes * 10);
    nickels = Math.floor(changeToReturn / 5);
    changeToReturn = changeToReturn - (nickels * 5);
    pennies = Math.floor(changeToReturn / 1);

    $('#changeReturnOutput').empty();
    $('#changeReturnOutput').append(quarters + " quarters, " + dimes +
        " dimes, " + nickels + " nickels, " + pennies + " pennies.");

    totalInAmount = 0.00;
    $('#totalInBoxValue').empty();
    $('#totalInBoxValue').append("$" + totalInAmount.toFixed(2));

});


$('#makePurchase').click(function makePurchase() {
    let amount = totalInAmount;
    var id = parseInt($('#itemNumSelected').text());

    if (id == -1) {
        $('#messageOutputLine').empty();
        $('#messageOutputLine').append("Please make a selection");
    }

    $.ajax({
        type: "POST",
        url: `http://tsg-vending.herokuapp.com/money/${amount}/item/${id}`,

        success: function (data) {

            getItems();
            totalInAmount = 0.00;

            itemPrice = 0.00;
            id = -1;
            $('#changeReturnOutput').empty();
            $('#changeReturnOutput').append(data.quarters + " quarters, " + data.dimes +
                " dimes, " + data.nickels + " nickels, " + data.pennies + " pennies.");
            $('#messageOutputLine').empty();
            $('#messageOutputLine').append("Thank You!!");
        },
        error: function (jqXHR, textStatus, errorThrown, data) {
            $('#messageOutputLine').empty();
            $('#messageOutputLine').append(jqXHR.responseJSON.message);
            getItems();
        }
    });
});

function getItems() {
    $('#firstRow').empty();

    $.ajax({
        type: "GET",
        url: `http://tsg-vending.herokuapp.com/items`,

        success: function (data) {


            for (i = 0; i < data.length; i++) {
                // console.log (data[i].id);
                var id = data[i].id;
                var nameItem = data[i].name;
                var price = data[i].price;
                var quantity = data[i].quantity;

                $('#firstRow').append(`<div class="card-deck col-4 mb-3 mt-3">
                <div class="card border-info">
                    <div class="card-header text-left">${'Vend # ' + id}</div>
                    <div class="card-body text-secondary text-center">
                        <button type="button" class="btn btn-info" id="itemName" onclick="setItemIdNum(${id})" >${nameItem}</button>
                        <h5 class="font-weight-normal" id="item5Price">${'$' + price}</h5>
                    </div>
                    <div class="card-footer text-center" id="item5Quantity">${'Quantity Left: ' + quantity}</div>
                </div>
            </div>`);
            };


            $('#totalInBoxValue').empty();
            $('#totalInBoxValue').append("$" + totalInAmount.toFixed(2));

            changeAvailable();

        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Error, can't load vending items");
        }
    });

}