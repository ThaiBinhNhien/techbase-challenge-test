$(document).ready(function(){
	
	var datatable;
	var searchData = "";
	var page = 0;
	var recordsTotal= 10;
	
	loadTeamList();
	
	$("#btn-add-new-team").on("click", function(){
		loadListDepartment();
		loadListEmployee();
		$("#add-new-team-modal").modal("show");
	})
	
	$("#action-create-new-team").on("click", function(){
		var teamName = $("#teamName").val();
		var idDepartment = $("#list-department-create").find(":selected").val();
		var idTeamLeader = $("#list-team-leader-create").find(":selected").val();
		var data = {};
		data.name = teamName;
		data.idDepartment = idDepartment;
		data.idTeamLeader = idTeamLeader;
		
		console.log(data);
		
		$("#add-new-team-modal").modal("hide");
		$.ajax({
			url: '/team/create',
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
	
	$(document).on("click", ".edit-team-row", function(){
		console.log($(this).data("id"));
	})
	
	$(document).on("click", ".remove-team-row", function(){
		$("#remove-team-modal").modal("show");
		$("#action-remove-team").attr("data-id", $(this).attr("data-id"))
	})
	
	$("#action-remove-team").on("click", function(){
		var id = $(this).attr("data-id");
		$("#remove-team-modal").modal("hide");
		$.ajax({
			url: '/team/remove?id='+id,
			type: 'DELETE',
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
	
	function loadListEmployee(){
		$.ajax({
			url: '/employee/get-list-employee-for-team',
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
					$("#list-team-leader-create").html(element);
				}
				else {
					alert("List Employee can't load!");
				}
			},

		});
	}
	
	function loadListDepartment(){
		$.ajax({
			url: '/team/get-list-department-for-team',
			type: 'GET',
			contentType: "application/json",
			headers: {
				'X-CSRF-TOKEN': csrf_token
			},
			success: function (result) {
				if(result.success){
					var element = '<option selected value="">Choose a department</option>';
					result.data.forEach(function(item){
						element += '<option value="'+item.id+'">'+item.name+'</option>';
					})
					$("#list-department-create").html(element);
				}
				else {
					alert("List Department can't load!");
				}
			},
			error: function(result){
				alert(result.responseJSON.message);
			}

		});
	}
	
	function loadTeamList(){
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
				url: 'get-list-team-paging?search='+encodeURIComponent(searchData.toString())+'&page='+page+'&record='+recordsTotal,
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
					{ data: 'nameDepartment' ,class: "text-center"},
					{ data: 'nameTeamLeader' ,class: "text-center"},
					{ data: 'updatedAt' ,class: "text-center"},
					{ data: null }
				],
				columnDefs: [
					{
						class: "text-center",
						targets: [-1], render: function (data, type, row, meta) {
							var element = '';
								element = '<div class="btn-group text-center actions">'+
									'<a href="#" style="padding: 5px; color:red" class="on-default remove-team-row" data-id="'+row.id+'"><i class="fa fa-trash-o"></i></a>'+
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
		datatable.ajax.url('get-list-team-paging?search='+encodeURIComponent(searchData.toString())+'&page='+page+'&record='+recordsTotal);
		datatable.ajax.reload();
		
	}
});