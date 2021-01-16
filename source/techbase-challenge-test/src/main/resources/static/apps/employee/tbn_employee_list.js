$(document).ready(function(){
	
	var datatable;
	var searchData = "";
	var page = 0;
	var recordsTotal= 10;
	
	loadDepartmentList();
	
	$("#btn-add-new-employee").on("click", function(){
		$("#add-new-employee-modal").modal("show");
	})

	$("#action-create-new-employee").on("click", function(){
		var employeeName = $("#employeeName").val();
		var userName = $("#userName").val();
		var password = $("#password").val();
		var data = {};
		data.userName = userName;
		data.fullName = employeeName;
		data.password = password;
		
		$("#add-new-employee-modal").modal("hide");
		$.ajax({
			url: '/employee/create',
			data: JSON.stringify(data),
			type: 'POST',
			contentType: "application/json",
			headers: {
				'X-CSRF-TOKEN': csrf_token
			},
			success: function (result) {
				if(result.success){
					loadDataPaging();
				}
				else {
					alert(result.message);
				}
			},
		});
	})
	
	$(document).on("click", ".remove-employee-row", function(){
		$("#remove-employee-modal").modal("show");
		$("#action-remove-employee").attr("data-id", $(this).attr("data-id"))
	})
	
	$("#action-remove-employee").on("click", function(){
		var id = $(this).attr("data-id");
		$("#remove-employee-modal").modal("hide");
		$.ajax({
			url: '/employee/remove?id='+id,
			type: 'DELETE',
			contentType: "application/json",
			headers: {
				'X-CSRF-TOKEN': csrf_token
			},
			success: function (result) {
				console.log(result);
				if(result.success){
					loadDataPaging();
				}
				else {
					alert(result.message);
				}
			},
		});
	})
	
	function loadDepartmentList(){
		datatable = $('#datatable').on( 'page.dt',   function () { 
				loadDataPaging();
		}).on( 'length.dt', function ( e, settings, len ) {
			recordsTotal = len;
			loadDataPaging();
		})
		.DataTable({
			processing: true,
			serverSide: true,
			ordering: false,
			autoWidth: false,
			ajax: { 
				url: 'get-list-employee-paging?search='+encodeURIComponent(searchData.toString())+'&page='+page+'&record='+recordsTotal,
				headers: {
					'X-CSRF-TOKEN': csrf_token
				},
				dataFilter: function(data){
					
					try {
						var recordsTotal = $("select[name='datatable_length']").val();
						var jsonResponse = jQuery.parseJSON( data );
						counter = page * recordsTotal;
						var json = {};
						json.data = jsonResponse.data.data;
						json.recordsTotal = recordsTotal;
						json.recordsFiltered = jsonResponse.data.total;

						console.log(json);
						return JSON.stringify( json );
					}
					catch(err) {
						location.reload();
					}

				}
			},
			'language': {
				'loadingRecords': '&nbsp;',
				'processing': '<div class="spinner"></div>'
			},
			columns: [
					{ data: '' ,class: "text-center"},
					{ data: 'fullName', class: "text-center"},
					{ data: 'userName' ,class: "text-center"},
					{ data: 'role' ,class: "text-center"},
					{ data: 'updatedAt' ,class: "text-center"},
					{ data: null }
				],
				columnDefs: [
					{
						class: "text-center",
						targets: [-1], render: function (data, type, row, meta) {
							var element = '';
								element = '<div class="btn-group text-center actions">';
								element += '<a href="#" style="padding: 5px; color:red" class="on-default remove-employee-row" data-id="'+row.id+'"><i class="fa fa-trash-o"></i></a>'+
	                            '</div>';
							return element;
						}
					},
					{
						targets: [0], render: function () {
							counter++;
							return counter;
						}
					}
				]
		});
		
		datatable.on( 'search.dt', function () {
		});
	}
	

	function loadDataPaging(){
		var info = datatable.page.info();
		page = info.page;
		var recordsTotal = $("select[name='datatable_length']").val();
		datatable.ajax.url('get-list-employee-paging?search='+encodeURIComponent(searchData.toString())+'&page='+page+'&record='+recordsTotal);
		datatable.ajax.reload();
		
	}
});