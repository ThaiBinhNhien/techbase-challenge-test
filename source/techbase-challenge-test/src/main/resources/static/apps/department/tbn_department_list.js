$(document).ready(function(){
	
	var datatable;
	var searchData = "";
	var page = 0;
	var recordsTotal= 10;
	
	loadDepartmentList();
	
	$("#btn-add-new-department").on("click", function(){
		loadAllImployee();
		$("#add-new-deparment-modal").modal("show");
	})
	
	$("#action-create-new-department").on("click", function(){
		var departmentName = $("#departmentName").val();
		var idEmployee = $("#list-employee-new-department").find(":selected").val();
		var data = {};
		data.name = departmentName;
		data.idEmployee = idEmployee;
		
		$("#add-new-deparment-modal").modal("hide");
		$.ajax({
			url: '/department/create',
			data: JSON.stringify(data),
			type: 'POST',
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
			error: function(result){
			    alert(result.responseJSON.message);
			}
		});
	})
	
	$(document).on("click", ".edit-department-row", function(){
		console.log($(this).data("id"));
		
		$.ajax({
			url: '/department/detail?id='+id,
			type: 'GET',
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
	
	$(document).on("click", ".remove-department-row", function(){
		$("#remove-deparment-modal").modal("show");
		$("#action-remove-department").attr("data-id", $(this).attr("data-id"))
	})
	
	$("#action-remove-department").on("click", function(){
		var id = $(this).attr("data-id");
		$("#remove-deparment-modal").modal("hide");
		$.ajax({
			url: '/department/remove?id='+id,
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
	
	function loadAllImployee(){
		$.ajax({
			url: '/employee/get-list-employee-for-department',
			type: 'GET',
			contentType: "application/json",
			headers: {
				'X-CSRF-TOKEN': csrf_token
			},
			success: function (result) {
				if(result.success){
					var element = '<option selected value="">Choose a employee</option>';
					result.data.forEach(function(item){
						element += '<option value="'+item.id+'">'+item.fullName+'</option>';
					})
					$("#list-employee-new-department").html(element);
				}
				else {
					alert("List Employee can't load!");
				}
			},

		});
	}
	
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
				url: 'get-list-department-paging?search='+encodeURIComponent(searchData.toString())+'&page='+page+'&record='+recordsTotal,
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
					{ data: 'name', class: "text-center"},
					{ data: 'nameEmployee' ,class: "text-center"},
					{ data: 'updatedAt' ,class: "text-center"},
					{ data: null }
				],
				columnDefs: [
					{
						class: "text-center",
						targets: [-1], render: function (data, type, row, meta) {
							var element = '';
							element = '<div class="btn-group text-center actions">'+
			                            '<a href="#" style="padding: 5px; color:red;" class="on-default remove-department-row" data-id="'+row.id+'"><i class="fa fa-trash-o"></i></a>'+
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
		datatable.ajax.url('get-list-department-paging?search='+encodeURIComponent(searchData.toString())+'&page='+page+'&record='+recordsTotal);
		datatable.ajax.reload();
	}
});