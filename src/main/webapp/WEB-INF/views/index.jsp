<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>파일 확장자 차단</title>
    <!-- Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />
  </head>
  <body class="container mt-5">
    <h1>파일 확장자 차단</h1>

    <div class="mt-4">
      <div class="row">
        <div class="col-sm-2">
          <span class="fw-bold">고정 확장자</span>
        </div>
        <div class="col-sm-4" id="fixed_div"></div>
      </div>
    </div>

    <div class="mt-4">
      <div class="row">
        <div class="col-sm-2">
          <span class="fw-bold">커스텀 확장자</span>
        </div>
        <div class="col-sm-3">
          <div class="input-group mb-3">
            <input
              type="text"
              class="form-control"
              id="customExtensionInput"
              placeholder="확장자 입력"
              maxlength="20"
            />
            <button
              class="btn btn-primary"
              type="button"
              onclick="addCustomExtension()"
            >
              추가
            </button>
          </div>
        </div>
      </div>

      <div class="card" style="min-height: 60px">
        <div class="row" id="custom_div"></div>
      </div>
    </div>

    <!-- Bootstrap JS (optional) -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

    <script>
      var apiUrl = "http://localhost:8080/api/flow";
      $(document).ready(function () {
        loadFixedExtension();
        loadCustomExtension();
      });
      function loadFixedExtension() {
        $("#fixed_div").html("");
        var inhtml = "";
        $.ajax({
          url: apiUrl + "/extension/fixed",
          type: "GET",
          dataType: "json",
          success: function (res) {
            $.each(res, function (index, row) {
              // 조건 체크 (status == 'active')
              check_row = "";
              if (row.status === "ACTIVE") {
                check_row = "checked";
              }
              inhtml += ' <div class="form-check form-check-inline">';
              inhtml +=
                '<input class="form-check-input" type="checkbox" value="' +
                row.extension +
                '" id="' +
                row.extension +
                '" onclick="changeStatus(\'' +
                row.extension +
                "', '" +
                row.status +
                "')\" " +
                check_row +
                ">";
              inhtml +=
                '<label class="form-check-label" for="' +
                row.extension +
                '">' +
                row.extension +
                "</label>";
              inhtml += "</div>";
            });
            $("#fixed_div").html(inhtml);
          },
        });
      }
      function loadCustomExtension() {
        $("#custom_div").html("");
        var custom_inhtml = "";
        $.ajax({
          url: apiUrl + "/extension/custom",
          type: "GET",
          dataType: "json",
          success: function (res) {
            $.each(res, function (index, row) {
              // 조건 체크 (status == 'active')
              if (row.status === "ACTIVE") {
                custom_inhtml += '<div class="col-sm-1 mb-1">';
                custom_inhtml +=
                  '<div class="input-group" style="width: 90px;">';
                custom_inhtml +=
                  '<span class="form-control">' + row.extension + "</span>";
                custom_inhtml +=
                  '<span class="input-group-text" style="cursor:pointer;" onclick="changeStatus(\'' +
                  row.extension +
                  "', '" +
                  row.status +
                  "')\">X</span>";
                custom_inhtml += "</div>";
                custom_inhtml += "</div>";
              }
            });

            $("#custom_div").html(custom_inhtml);
          },
        });
      }
      function addCustomExtension() {
        // 입력된 확장자 값 가져오기
        var extension = $("#customExtensionInput").val();
        if (extension.trim() !== "") {
          // API 호출
          $.ajax({
            url: apiUrl + "/extension",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ extension: extension }),
            success: function (response) {
              alert("등록되었습니다.");
              loadCustomExtension();
              // 성공 시 필요한 동작 추가
            },
            error: function (error) {
              alert("등록에 실패하였습니다.");
            },
          });
        } else {
          alert("커스텀 확장자를 입력하세요");
        }
      }
      function changeStatus(extension, status) {
        var newStatus = "ACTIVE";
        if (status === "ACTIVE") {
          newStatus = "INACTIVE";
        }
        $.ajax({
          url: apiUrl + "/extension",
          type: "PUT",
          contentType: "application/json",
          data: JSON.stringify({ extension: extension, status: newStatus }),
          success: function (response) {
            alert("변경되었습니다.");
            loadFixedExtension();
            loadCustomExtension();
            // 성공 시 필요한 동작 추가
          },
          error: function (error) {
            alert("변경에 실패하였습니다.");
          },
        });
      }
    </script>
  </body>
</html>
