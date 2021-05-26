$(function () {

    $('.search').click(function () {
        
        debugger;
        var name = $("#name").val();
        $.ajax({
            type: "GET",
            url: "SearchInvoice", //Tên servlet
            data: "txtSearchInvoiceID=" + name,  //Gán giá trị vào name mục đich để servlet nhận được Parameter
            dataType: "json",
            async: true,
            cache: false,
            success: function (result) {
                //Lấy size của list này
                var listSize = Object.keys(result).length;
                //Nếu List lấy từ Dao là null thì thông báo Not found và return
                if (result.check == "fail") {
                    $('#message').text("List isEmpty! Name not found!");
                    $('#message').css('color', 'red');
                    return;
                }
                //Nếu list tồn tại thì reset thông điệp cảnh báo về rỗng và loop dữ liệu hiển thị ra table
                if (listSize > 0) {
                    $('#message').text("");
                    //Khai báo table kiểu global để sử dụng ngoài fuction này
                    table = document.getElementById("row");
                    for (i = 0; i < listSize; i++) {
                        var row = table.insertRow(i);

                        var cell = row.insertCell(0);
                        var cell1 = row.insertCell(1);
                        var cell2 = row.insertCell(2);
                        var cell3 = row.insertCell(3);
                        var cell4 = row.insertCell(4);
                        var cell5 = row.insertCell(5);
                        var cell6 = row.insertCell(6);
                        var cell7 = row.insertCell(7);
                        var cell8 = row.insertCell(8);
                        
                        
                        
                        cell.innerHTML = result[i].invoiceID;
                        cell1.innerHTML = result[i].roomName;
                        cell2.innerHTML = result[i].roomPrice;
                        cell3.innerHTML = result[i].roomQuantity;
                        cell4.innerHTML = result[i].serviceName;
                        cell5.innerHTML = result[i].servicePrice;
                        cell6.innerHTML = result[i].serviceQuantity;
                        cell7.innerHTML = result[i].customerName;
                        cell8.innerHTML = ((result[i].roomQuantity * result[i].roomPrice) + (result[i].servicePrice * result[i].serviceQuantity));
                        
                        

                        
                    }
                }

            }
        });
    });
    //Reset table về trạng thái không có bản ghi nào
    $('.search').click(function () {
        var trLength = table.getElementsByTagName("tr").length;
        if (trLength > 0) {
            for (i = 0; i < trLength; i++) {
                table.deleteRow(0);
            };
        }
    });
});